package com.bizeff.procurement.usecases.supplierperformancetracking;

import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.GenerateSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.services.supplierperformancetracking.StoringSupplierPerformanceHistoryService;

import java.util.ArrayList;
import java.util.List;

import static com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionFieldValidator.validateString;

public class GenerateSupplierPerformanceReportUseCase implements GenerateSupplierPerformanceReportInputBoundary {
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private SupplierRepository supplierRepository;

    private StoringSupplierPerformanceHistoryService storingSupplierPerformanceHistoryService;

    private GenerateSupplierPerformanceReportOutputBoundary generateSupplierPerformanceReportOutputBoundary;

    public GenerateSupplierPerformanceReportUseCase(SupplierPerformanceRepository supplierPerformanceRepository,
                                                    SupplierRepository supplierRepository,
                                                    StoringSupplierPerformanceHistoryService storingSupplierPerformanceHistoryService,
                                                    GenerateSupplierPerformanceReportOutputBoundary generateSupplierPerformanceReportOutputBoundary){
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.supplierRepository = supplierRepository;
        this.storingSupplierPerformanceHistoryService = storingSupplierPerformanceHistoryService;
        this.generateSupplierPerformanceReportOutputBoundary = generateSupplierPerformanceReportOutputBoundary;
    }

    @Override
    public List<GenerateSupplierPerformanceReportOutputDS> generatePerformanceReport(String supplierId){
        validateString(supplierId,"Supplier Id");
        Supplier supplier = supplierRepository.findBySupplierId(supplierId).orElseThrow(()-> new IllegalArgumentException("Supplier Doesn't Found."));
        if (!supplier.isActive()){
            throw new IllegalArgumentException("The supplier with id " + supplier.getSupplierId() + " is not active.");
        }
        List<SupplierPerformance> supplierPerformanceList = supplierPerformanceRepository.findBySupplierId(supplier.getSupplierId());
        if (supplierPerformanceList == null || supplierPerformanceList.isEmpty()){
            throw new IllegalArgumentException("There is no performance metrics that is added to the supplier with id " + supplier.getSupplierId());
        }
        // Store the supplier in the service to maintain the supplier's performance history
        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storingSupplierPerformanceHistoryService.getSupplierPerformanceHistory().put(supplier.getSupplierId(), supplierPerformanceList);

        // Retrieve the performance history for the supplier
        List<SupplierPerformance> supplierPerformances = storingSupplierPerformanceHistoryService.getPerformanceHistory(supplier.getSupplierId());

        if (supplierPerformances == null || supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("there is no performance metrics that is added to the supplier with id " + supplier.getSupplierId());
        }

        List<GenerateSupplierPerformanceReportOutputDS> supplierPerformanceReportOutputDSList = new ArrayList<>();
        for (SupplierPerformance supplierPerformance: supplierPerformances){

            double supplierQualitativePerformanceScore = storingSupplierPerformanceHistoryService.getQualitativePerformanceScore(supplierPerformance);
            double supplierQuantitativePerformanceScore = storingSupplierPerformanceHistoryService.getQuantitativePerformanceScore(supplierPerformance);
            double supplierTotalPerformanceScore = storingSupplierPerformanceHistoryService.calculateSupplierPerformanceScore(supplierPerformance);

            GenerateSupplierPerformanceReportOutputDS supplierPerformanceReportOutputDS = new GenerateSupplierPerformanceReportOutputDS(supplierPerformance.getSupplier().getSupplierId(),supplierQuantitativePerformanceScore,supplierQualitativePerformanceScore,supplierTotalPerformanceScore,supplierPerformance.getSupplierPerformanceRate(),supplierPerformance.getEvaluationDate());

            supplierPerformanceReportOutputDSList.add(supplierPerformanceReportOutputDS);

        }
        generateSupplierPerformanceReportOutputBoundary.presentSupplierPerformanceReport(supplierPerformanceReportOutputDSList);
        return supplierPerformanceReportOutputDSList;
    }

}
