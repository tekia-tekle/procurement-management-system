package com.bizeff.procurement.usecases.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.ViewSupplierPerformanceReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.ViewSupplierPerformanceReportInputDS;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.ViewSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.ViewSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ViewSupplierPerformancesUseCase implements ViewSupplierPerformanceReportInputBoundary {
    private SupplierRepository supplierRepository;
    private ViewSupplierPerformanceReportOutputBoundary viewSupplierPerformanceReportOutputBoundary;
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices;

    public ViewSupplierPerformancesUseCase(SupplierRepository supplierRepository,
                                           SupplierPerformanceRepository supplierPerformanceRepository,
                                           SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices,
                                           ViewSupplierPerformanceReportOutputBoundary viewSupplierPerformanceReportOutputBoundary){
        this.supplierRepository = supplierRepository;
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.supplierPerformanceEvaluationServices = supplierPerformanceEvaluationServices;
        this.viewSupplierPerformanceReportOutputBoundary = viewSupplierPerformanceReportOutputBoundary;
    }
    @Override
    public List<ViewSupplierPerformanceReportOutputDS> viewSupplierPerformances(ViewSupplierPerformanceReportInputDS inputDS){
        // Check if any suppliers exist
        List<Supplier> allSuppliers = Optional.ofNullable(supplierRepository.findAll())
                .orElseThrow(() -> new IllegalArgumentException("No suppliers found in the system."));

        if (allSuppliers.isEmpty()) {
            throw new IllegalArgumentException("No suppliers found in the system.");
        }

        // Filter suppliers by category
        List<Supplier> filteredSuppliers = allSuppliers.stream()
                .filter(s -> s.getSupplierCategory().equals(inputDS.getSupplierCategory()))
                .collect(Collectors.toList());

        if (filteredSuppliers.isEmpty()) {
            throw new IllegalArgumentException("No suppliers found for the specified category.");
        }

        // Check if any supplier performance records exist
        List<SupplierPerformance> allPerformances = Optional.ofNullable(supplierPerformanceRepository.findAll())
                .orElseThrow(() -> new IllegalArgumentException("No supplier is evaluated before."));

        if (allPerformances.isEmpty()) {
            throw new IllegalArgumentException("No supplier is evaluated before.");
        }

        // Filter performances by supplier category
        List<SupplierPerformance> categoryPerformances = allPerformances.stream()
                .filter(performance -> performance.getSupplier().getSupplierCategory().equals(inputDS.getSupplierCategory()))
                .collect(Collectors.toList());

        if (categoryPerformances.isEmpty()) {
            throw new IllegalArgumentException("No performance records found for suppliers in the specified category.");
        }

        // Filter by date range
        List<SupplierPerformance> dateFilteredPerformances = categoryPerformances.stream()
                .filter(performance -> !performance.getEvaluationDate().isBefore(inputDS.getStartDate()) &&
                        !performance.getEvaluationDate().isAfter(inputDS.getEndDate()))
                .collect(Collectors.toList());

        if (dateFilteredPerformances.isEmpty()) {
            throw new IllegalArgumentException("No supplier performance records found within the specified date range.");
        }

        // Map to Output DTO
        List<ViewSupplierPerformanceReportOutputDS> outputResult = new ArrayList<>();
        for (SupplierPerformance performance:dateFilteredPerformances){
            Supplier supplier = performance.getSupplier();
            double qualitativePerformanceScore = supplierPerformanceEvaluationServices.getQualitativePerformanceScore(performance);
            double quantitativePerformanceScore = supplierPerformanceEvaluationServices.getQuantitativePerformanceScore(performance);
            double totalSupplierPerformanceScore = calculateSupplierPerformance(performance);

            ViewSupplierPerformanceReportOutputDS outputDS = new ViewSupplierPerformanceReportOutputDS(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getSupplierCategory(),
                    supplier.getTaxIdentificationNumber(),
                    supplier.getRegistrationDate(),
                    quantitativePerformanceScore,
                    qualitativePerformanceScore,
                    totalSupplierPerformanceScore,
                    performance.getSupplierPerformanceRate(),
                    performance.getEvaluationDate());

            outputResult.add(outputDS);
        }

        // prioritization.
        outputResult.sort(Comparator.comparing(ViewSupplierPerformanceReportOutputDS::getTotalSupplierPerformanceScore).reversed());

        viewSupplierPerformanceReportOutputBoundary.presentSuppliersBasedOnPerformance(outputResult);
        return outputResult;
    }
    public double calculateSupplierPerformance(SupplierPerformance performance){

        if (performance == null ){
            throw new NullPointerException("performance can't be null. to calculate the supplier performance score");
        }

        List<Supplier> supplierList = Optional.ofNullable(supplierRepository.findAll())
                .orElseThrow(()->new IllegalArgumentException("there is no supplier saved before."));

        boolean matched = supplierList.stream().anyMatch(supplier -> supplier.getSupplierId().equals(performance.getSupplier().getSupplierId()));
        if (!matched){
            throw new IllegalArgumentException("ther supplier in the performance is not saved in the repository.");
        }
        List<SupplierPerformance> performanceList = Optional.ofNullable(supplierPerformanceRepository.findAll())
                .orElseThrow(()->new IllegalArgumentException("there is no performance stored before."));

        boolean anymatch = performanceList.stream().anyMatch(p -> p.getId().equals(performance.getId()));
        if (!anymatch){
            throw new IllegalArgumentException("the performance is not stored in the performance repository. ");
        }
        double performanceScore = supplierPerformanceEvaluationServices.calculateSupplierPerformanceScore(performance);

        return performanceScore;
    }
    public void validateId(String supplierId){
        if (supplierId == null || supplierId.trim().isEmpty()){
            throw new IllegalArgumentException("supplier id can't be null or empty.");
        }
    }
}