package com.bizeff.procurement.usecases.supplierperformancetracking;

import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.ViewSupplierPerformanceMetricsInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.ViewSupplierPerformanceMerticsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.ViewSupplierPerformanceMetricsOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;

import java.util.Comparator;
import java.util.List;

import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;

public class ViewSupplierPerformanceMetricsUseCase implements ViewSupplierPerformanceMetricsInputBoundary {
    private SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices;
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private SupplierRepository supplierRepository;
    private ViewSupplierPerformanceMerticsOutputBoundary viewSupplierPerformanceMerticsOutputBoundary;

    public ViewSupplierPerformanceMetricsUseCase(SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices,
                                                 SupplierPerformanceRepository supplierPerformanceRepository,
                                                 SupplierRepository supplierRepository,
                                                 ViewSupplierPerformanceMerticsOutputBoundary viewSupplierPerformanceMerticsOutputBoundary)
    {
        this.supplierPerformanceEvaluationServices = supplierPerformanceEvaluationServices;
        this.supplierPerformanceRepository = supplierPerformanceRepository;
        this.supplierRepository = supplierRepository;
        this.viewSupplierPerformanceMerticsOutputBoundary = viewSupplierPerformanceMerticsOutputBoundary;
    }

    @Override
    public ViewSupplierPerformanceMetricsOutputDS viewPerformanceMetrics(String supplierId){ // view the current supplier performance metrics.
        validateString(supplierId,"Supplier Id");
        Supplier supplier = supplierRepository.findBySupplierId(supplierId).orElseThrow(()-> new IllegalArgumentException("Supplier Doesn't Found."));

        List<SupplierPerformance> supplierPerformanceList = supplierPerformanceRepository.findBySupplierId(supplier.getSupplierId());
        if (supplierPerformanceList == null || supplierPerformanceList.isEmpty()){
            throw new IllegalArgumentException("Supplier has not been evaluated yet.");
        }
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierId, supplier); // save the supplier in the service collection.
        supplierPerformanceEvaluationServices.getSupplierPerformances().put(supplierId, supplierPerformanceList); // save the supplier performance history in the service collection.

        Supplier storedSupplier = supplierPerformanceEvaluationServices.getSupplierById(supplierId); // get the supplier from the service collection.

        List<SupplierPerformance> supplierPerformances = supplierPerformanceEvaluationServices.getPerfromanceBySupplierId(storedSupplier.getSupplierId()); // check the total evaluated supplier performance.

        if (supplierPerformances == null || supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("supplier is not evaluated before.we can't see any perfomrnace metrics.");
        }

        SupplierPerformance performance = supplierPerformances.stream().max(Comparator.comparing(SupplierPerformance::getEvaluationDate)).get(); // geting the latest supplier performance.

        performance = supplierPerformanceRepository.findById(performance.getId()).orElseThrow(()->new IllegalArgumentException("the performance is not saved in the supplier performance repository."));

        ViewSupplierPerformanceMetricsOutputDS supplierPerformanceMetricsOutputDS = new ViewSupplierPerformanceMetricsOutputDS(performance.getSupplier().getSupplierId(), performance.getQuantitativePerformanceMetrics() ,performance.getQualitativePerformanceMetrics());

        viewSupplierPerformanceMerticsOutputBoundary.presentSupplierPerformanceMetrics(supplierPerformanceMetricsOutputDS);

        return supplierPerformanceMetricsOutputDS;
    }
}
