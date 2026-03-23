package com.bizeff.procurement.webapi.presenter.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.ViewSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.ViewSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.ViewSupplierPerformanceViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class ViewSupplierPerformancePresenter implements ViewSupplierPerformanceReportOutputBoundary {

    private List<ViewSupplierPerformanceViewModel> viewSupplierPerformanceViewModels;
    public ViewSupplierPerformancePresenter(List<ViewSupplierPerformanceViewModel> viewSupplierPerformanceViewModels){
        this.viewSupplierPerformanceViewModels = viewSupplierPerformanceViewModels;
    }

    @Override
    public void presentSuppliersBasedOnPerformance(List<ViewSupplierPerformanceReportOutputDS>viewSupplierPerformanceReportOutputDSList){
        this.viewSupplierPerformanceViewModels = new ArrayList<>();
        for (ViewSupplierPerformanceReportOutputDS supplierPerformance: viewSupplierPerformanceReportOutputDSList){
            ViewSupplierPerformanceViewModel viewModel = new ViewSupplierPerformanceViewModel(
                    supplierPerformance.getSupplierId(),
                    supplierPerformance.getSupplierName(),
                    supplierPerformance.getSupplierCategory(),
                    supplierPerformance.getTaxIdentificationNumber(),
                    supplierPerformance.getRegistrationDate().toString(),
                    String.valueOf(supplierPerformance.getQuantitativePerformanceScore()),
                    String.valueOf(supplierPerformance.getQualitativePerformanceScore()),
                    String.valueOf(supplierPerformance.getTotalSupplierPerformanceScore()),
                    supplierPerformance.getPerformanceRate().toString(),
                    supplierPerformance.getEvaluationDate().toString()
            );

            viewSupplierPerformanceViewModels.add(viewModel);
        }

    }

    public List<ViewSupplierPerformanceViewModel> getViewSupplierPerformanceViewModels() {
        return viewSupplierPerformanceViewModels;
    }

    @Override
    public String toString() {
        return "ViewSupplierPerformancePresenter{" +
                "viewSupplierPerformanceViewModels=" + viewSupplierPerformanceViewModels +
                '}';
    }
}
