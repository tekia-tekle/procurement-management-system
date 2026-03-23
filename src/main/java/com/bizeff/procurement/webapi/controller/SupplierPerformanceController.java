package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.ViewSupplierPerformanceMetricsInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.GenerateSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.ViewSupplierPerformanceMetricsOutputDS;
import com.bizeff.procurement.webapi.presenter.supplierPerformancetracking.EvaluateSupplierForContractRenewalPresenter;
import com.bizeff.procurement.webapi.presenter.supplierPerformancetracking.GenerateSupplierPerformanceReportPresenter;
import com.bizeff.procurement.webapi.presenter.supplierPerformancetracking.ViewSupplierPerformanceMetricsPresenter;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.EvaluatedSupplierForContractRenewalViewModel;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.GeneratedSupplierPerformanceReportViewModel;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.ViewSupplierPerformanceMetricsViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/supplierperformance")
public class SupplierPerformanceController {
    private GenerateSupplierPerformanceReportInputBoundary generateSupplierPerformanceInputBoundary;
    private GenerateSupplierPerformanceReportPresenter generateSupplierPerformanceReportPresenter;
    private EvaluateSupplierForContractRenewalInputBoundary evaluateSupplierForContractRenewalInputBoundary;
    private EvaluateSupplierForContractRenewalPresenter evaluateSupplierForContractRenewalPresenter;
    private ViewSupplierPerformanceMetricsInputBoundary viewSupplierPerformanceMetricsInputBoundary;
    private ViewSupplierPerformanceMetricsPresenter viewSupplierPerformanceMetricsPresenter;

    public SupplierPerformanceController(GenerateSupplierPerformanceReportInputBoundary generateSupplierPerformanceInputBoundary,
                                         GenerateSupplierPerformanceReportPresenter generateSupplierPerformanceReportPresenter,
                                         EvaluateSupplierForContractRenewalInputBoundary evaluateSupplierForContractRenewalInputBoundary,
                                         EvaluateSupplierForContractRenewalPresenter evaluateSupplierForContractRenewalPresenter,
                                         ViewSupplierPerformanceMetricsInputBoundary viewSupplierPerformanceMetricsInputBoundary,
                                         ViewSupplierPerformanceMetricsPresenter viewSupplierPerformanceMetricsPresenter) {
        this.generateSupplierPerformanceInputBoundary = generateSupplierPerformanceInputBoundary;
        this.generateSupplierPerformanceReportPresenter = generateSupplierPerformanceReportPresenter;
        this.evaluateSupplierForContractRenewalInputBoundary = evaluateSupplierForContractRenewalInputBoundary;
        this.evaluateSupplierForContractRenewalPresenter = evaluateSupplierForContractRenewalPresenter;
        this.viewSupplierPerformanceMetricsInputBoundary = viewSupplierPerformanceMetricsInputBoundary;
        this.viewSupplierPerformanceMetricsPresenter = viewSupplierPerformanceMetricsPresenter;
    }

    @GetMapping("/generateReport")
    public ResponseEntity<?> generateSupplierPerformanceReport(@RequestParam String supplierId) {

        List<GenerateSupplierPerformanceReportOutputDS> supplierPerformanceReportOutputDSList = generateSupplierPerformanceInputBoundary.generatePerformanceReport(supplierId);
        generateSupplierPerformanceReportPresenter.presentSupplierPerformanceReport(supplierPerformanceReportOutputDSList);
        // Return the presenter which contains the generated performance report view models
        List<GeneratedSupplierPerformanceReportViewModel> generatedSupplierPerformanceReportViewModels = generateSupplierPerformanceReportPresenter.getGeneratedSupplierPerformanceViewModels();

        return ResponseEntity.ok(generatedSupplierPerformanceReportViewModels);
    }
    @PostMapping("/review")
    public ResponseEntity<?> evaluateSupplierForContractRenewal(@RequestParam String supplierCatergory) {
        List<EvaluateSupplierForContractRenewalOutputDS> evaluateSupplierForContractRenewalOutputs = evaluateSupplierForContractRenewalInputBoundary.evaluateSupplierforContractRenewal(supplierCatergory);

        evaluateSupplierForContractRenewalPresenter.presentRenewalContract(evaluateSupplierForContractRenewalOutputs);

        List<EvaluatedSupplierForContractRenewalViewModel> evaluatedSupplierForContractRenewalViewModels = evaluateSupplierForContractRenewalPresenter.getEvaluatedSupplierForContractRenewalViewModels();

        return ResponseEntity.ok(evaluatedSupplierForContractRenewalViewModels);
    }
    @GetMapping("/viewMetrics")
    public ResponseEntity<?> viewSupplierPerformanceMetrics(@RequestParam String supplierId){
        ViewSupplierPerformanceMetricsOutputDS supplierPerformanceMetricsOutputDS = viewSupplierPerformanceMetricsInputBoundary.viewPerformanceMetrics(supplierId);
        viewSupplierPerformanceMetricsPresenter.presentSupplierPerformanceMetrics(supplierPerformanceMetricsOutputDS);
        ViewSupplierPerformanceMetricsViewModel viewSupplierPerformanceMetricsViewModel = viewSupplierPerformanceMetricsPresenter.getViewSupplierPerformanceMetricsViewModel();

        return ResponseEntity.ok(viewSupplierPerformanceMetricsViewModel);
    }
}
