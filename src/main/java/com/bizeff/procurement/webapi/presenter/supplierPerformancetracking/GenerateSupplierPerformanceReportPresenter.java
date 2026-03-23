package com.bizeff.procurement.webapi.presenter.supplierPerformancetracking;

import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.GenerateSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.GeneratedSupplierPerformanceReportViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GenerateSupplierPerformanceReportPresenter implements GenerateSupplierPerformanceReportOutputBoundary {
    private List<GeneratedSupplierPerformanceReportViewModel> generatedSupplierPerformanceReportViewModels;

    public GenerateSupplierPerformanceReportPresenter(List<GeneratedSupplierPerformanceReportViewModel> generatedSupplierPerformanceReportViewModels) {
        this.generatedSupplierPerformanceReportViewModels = generatedSupplierPerformanceReportViewModels;
    }

    @Override
    public void presentSupplierPerformanceReport(List<GenerateSupplierPerformanceReportOutputDS> supplierPerformanceReportOutputDSs) {
        List<GeneratedSupplierPerformanceReportViewModel> generatedSupplierPerformanceReportViewModelList = new ArrayList<>();

        for (GenerateSupplierPerformanceReportOutputDS supplierPerformanceReportOutputDS : supplierPerformanceReportOutputDSs) {
            GeneratedSupplierPerformanceReportViewModel generatedSupplierPerformanceReportViewModel = new GeneratedSupplierPerformanceReportViewModel(
                    supplierPerformanceReportOutputDS.getSupplierId(),
                    supplierPerformanceReportOutputDS.getQuantitativePerformanceScore(),
                    supplierPerformanceReportOutputDS.getQualitativePerformanceScore(),
                    supplierPerformanceReportOutputDS.getTotalPerformanceScore(),
                    supplierPerformanceReportOutputDS.getSupplierPerformanceRate().toString(),
                    supplierPerformanceReportOutputDS.getEvaluatedDate().toString()
            );
            generatedSupplierPerformanceReportViewModelList.add(generatedSupplierPerformanceReportViewModel);
        }
        this.generatedSupplierPerformanceReportViewModels = generatedSupplierPerformanceReportViewModelList;

    }
    public List<GeneratedSupplierPerformanceReportViewModel> getGeneratedSupplierPerformanceViewModels() {
        return generatedSupplierPerformanceReportViewModels;
    }

    @Override
    public String toString() {
        return "GenerateSupplierPerformanceReportPresenter{" +
                "generatedSupplierPerformanceReportViewModels=" + generatedSupplierPerformanceReportViewModels +
                '}';
    }
}
