package com.bizeff.procurement.usecases.supplierperformancetracking;

import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;

import java.util.*;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;

public class EvaluateSupplierForContractRenewalUseCase implements EvaluateSupplierForContractRenewalInputBoundary {

    private SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices;
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private SupplierRepository supplierRepository;
    private ContractRepository contractRepository;
    private StoreAndTrackContractServices storeAndTrackContractServices;
    private EvaluateSupplierForContractRenewalOutputBoundary evaluateSupplierForContractRenewalOutputBoundary;

    public EvaluateSupplierForContractRenewalUseCase(
            SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices,
            SupplierPerformanceRepository supplierPerformanceRepository,
            SupplierRepository supplierRepository,
            ContractRepository contractRepository,
            StoreAndTrackContractServices storeAndTrackContractServices,
            EvaluateSupplierForContractRenewalOutputBoundary evaluateSupplierForContractRenewalOutputBoundary) {
        this.supplierPerformanceEvaluationServices = supplierPerformanceEvaluationServices;
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.supplierRepository = supplierRepository;
        this.contractRepository = contractRepository;
        this.storeAndTrackContractServices = storeAndTrackContractServices;
        this.evaluateSupplierForContractRenewalOutputBoundary = evaluateSupplierForContractRenewalOutputBoundary;
    }
    @Override
    public List<EvaluateSupplierForContractRenewalOutputDS> evaluateSupplierforContractRenewal(String supplierCategory) {

        validateString(supplierCategory, "Supplier Category");
        List<Supplier> suppliers = supplierRepository.findBySupplierCategory(supplierCategory);
        if (suppliers == null || suppliers.isEmpty()) {
            throw new IllegalArgumentException("There is no supplier with category " + supplierCategory);
        }

        // Load suppliers + performance history
        for (Supplier supplier : suppliers) {
            if (!supplier.isActive()) {
                throw new IllegalArgumentException("Supplier " + supplier.getSupplierId() + " is not active.");
            }

            List<SupplierPerformance> performances = supplierPerformanceRepository.findBySupplierId(supplier.getSupplierId());
            if (performances == null || performances.isEmpty()) {
                throw new IllegalArgumentException("No performance metrics for supplier " + supplier.getSupplierId());
            }

            supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
            supplierPerformanceEvaluationServices.getSupplierPerformances().put(supplier.getSupplierId(), performances);
        }

        // Get supplier list again (filtered by service)
        List<Supplier> supplierList = supplierPerformanceEvaluationServices.getSupplierByCategory(supplierCategory);
        if (supplierList == null || supplierList.isEmpty()) {
            throw new IllegalArgumentException("There is no supplier with category " + supplierCategory);
        }

        // Build performance map
        Map<String, List<SupplierPerformance>> performanceMap = new HashMap<>();
        for (Supplier supplier : supplierList) {
            List<SupplierPerformance> performances = supplierPerformanceEvaluationServices.getPerfromanceBySupplierId(supplier.getSupplierId());
            if (performances == null || performances.isEmpty()) {
                throw new IllegalArgumentException("No performance metrics for supplier " + supplier.getSupplierId());
            }
            performanceMap.put(supplier.getSupplierId(), performances);
        }

        // Rank suppliers
        List<SupplierPerformance> rankedPerformances = new ArrayList<>();
        for (Map.Entry<String, List<SupplierPerformance>> entry : performanceMap.entrySet()) {
            List<SupplierPerformance> performances = new ArrayList<>(entry.getValue());
            performances.sort(Comparator.comparing(SupplierPerformance::getEvaluationDate));
            rankedPerformances.add(performances.get(performances.size() - 1));
        }

        rankedPerformances.sort((a, b) -> {
            int trendA = getTrend(performanceMap.get(a.getSupplier().getSupplierId()));
            int trendB = getTrend(performanceMap.get(b.getSupplier().getSupplierId()));
            if (trendA != trendB) return Integer.compare(trendB, trendA);
            return Integer.compare(b.getSupplierPerformanceRate().ordinal(), a.getSupplierPerformanceRate().ordinal());
        });

        SupplierPerformance topScorer = rankedPerformances.get(0);
        Supplier topSupplier = supplierRepository.findBySupplierId(topScorer.getSupplier().getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Top supplier not found."));

        // Process contracts for all suppliers
        List<EvaluateSupplierForContractRenewalOutputDS> results = processContracts(rankedPerformances, topSupplier);

        evaluateSupplierForContractRenewalOutputBoundary.presentRenewalContract(results);
        return results;
    }

    private List<EvaluateSupplierForContractRenewalOutputDS> processContracts(
            List<SupplierPerformance> rankedPerformances, Supplier topSupplier) {

        List<EvaluateSupplierForContractRenewalOutputDS> results = new ArrayList<>();

        for (SupplierPerformance performance : rankedPerformances) {
            Supplier currentSupplier = performance.getSupplier();
            List<Contract> totalContracts = contractRepository.findAll();
            if (totalContracts == null || totalContracts.isEmpty()) {
                throw new IllegalArgumentException("No contracts found in the repository.");
            }
            String supplierCategory = currentSupplier.getSupplierCategory();
            for (Contract contract : totalContracts) {
                if (!contract.getSupplier().getSupplierCategory().equals(supplierCategory)) {
                    throw new IllegalArgumentException("No contracts found for supplier category " + supplierCategory);
                }
            }
            List<Contract> contracts = contractRepository.findBySupplierId(currentSupplier.getSupplierId());
            if (contracts == null || contracts.isEmpty()) continue;

            // Track supplier + contracts
            storeAndTrackContractServices.getSupplierMap().put(currentSupplier.getSupplierId(), currentSupplier);
            contracts.forEach(c -> storeAndTrackContractServices.getContract().put(c.getContractId(), c));

            for (Contract contract : contracts) {
                if (!contract.isRenewable()) continue;

                boolean renewed = false;
                if (!contract.getSupplier().getSupplierId().equals(topSupplier.getSupplierId())) {
                    renewed = true;
                    contract.setSupplier(topSupplier);
                    contractRepository.update(contract);
                }

                results.add(new EvaluateSupplierForContractRenewalOutputDS(
                        contract.getContractId(),
                        currentSupplier.getSupplierId(),
                        contract.getSupplier().getSupplierId(),
                        renewed
                ));
            }
        }
        return results;
    }
    /**
     * Helper: determine trend of supplier performance
     * return 1 = increasing, 0 = stable, -1 = decreasing
     */
    private int getTrend(List<SupplierPerformance> performances) {
        if (performances == null || performances.size() < 2) return 0;

        performances.sort(Comparator.comparing(SupplierPerformance::getEvaluationDate));
        int first = performances.get(0).getSupplierPerformanceRate().ordinal();
        int last = performances.get(performances.size() - 1).getSupplierPerformanceRate().ordinal();

        return Integer.compare(last, first);
    }
}