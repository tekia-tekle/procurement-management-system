package com.bizeff.procurement.webapi.presenter.supplierPerformancetracking;

import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.ViewSupplierPerformanceMerticsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.ViewSupplierPerformanceMetricsOutputDS;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.ViewSupplierPerformanceMetricsViewModel;
import org.springframework.stereotype.Component;

@Component
public class ViewSupplierPerformanceMetricsPresenter implements ViewSupplierPerformanceMerticsOutputBoundary {

    private ViewSupplierPerformanceMetricsViewModel viewSupplierPerformanceMetricsViewModel;
    public ViewSupplierPerformanceMetricsPresenter(ViewSupplierPerformanceMetricsViewModel viewSupplierPerformanceMetricsViewModel) {
        this.viewSupplierPerformanceMetricsViewModel = viewSupplierPerformanceMetricsViewModel;
    }

    @Override
    public void presentSupplierPerformanceMetrics(ViewSupplierPerformanceMetricsOutputDS supplierPerformanceMetricsOutputDS) {

        ViewSupplierPerformanceMetricsViewModel viewModel = new ViewSupplierPerformanceMetricsViewModel(
                supplierPerformanceMetricsOutputDS.getSupplierId(),
                supplierPerformanceMetricsOutputDS.getSupplierQuantitativePerformanceMetrics(),
                supplierPerformanceMetricsOutputDS.getSupplierQualitativePerformanceMetrics()
        );
        this.viewSupplierPerformanceMetricsViewModel = viewModel;
    }

    public ViewSupplierPerformanceMetricsViewModel getViewSupplierPerformanceMetricsViewModel() {
        return viewSupplierPerformanceMetricsViewModel;
    }

    @Override
    public String toString() {
        return "ViewSupplierPerformanceMetricsPresenter{" +
                "viewSupplierPerformanceMetricsViewModel=" + viewSupplierPerformanceMetricsViewModel +
                '}';
    }
}
