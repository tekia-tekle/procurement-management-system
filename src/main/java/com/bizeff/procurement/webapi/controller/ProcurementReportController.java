package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.CreateCustomizedProcurementDashboardInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.ExportProcurementDataInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.CreateCustomizedProcurementDashboardInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ExportProcurementDataInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.GenerateMonthlyApprovedPurchaseOrderReportInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.CreateCustomizedProcurementDashboardOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.ExportedProcurementDataOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutPutDS;
import com.bizeff.procurement.webapi.presenter.procurementreport.CreateCustomizedProcurementDashboardPresenter;
import com.bizeff.procurement.webapi.presenter.procurementreport.ExportProcurementDataPresenter;
import com.bizeff.procurement.webapi.presenter.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportPresenter;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.CreateCustomizedProcurementDashboardViewModel;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.ExportProcurementDataViewModel;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportViewModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/procurementreport")
public class ProcurementReportController {
    private CreateCustomizedProcurementDashboardInputBoundary createCustomizedProcurementPerformanceDashboardInputBoundary;
    private CreateCustomizedProcurementDashboardPresenter createCustomizedProcurementPerformanceDashboardPresenter;
    private GenerateMonthlyApprovedPurchaseOrderReportInputBoundary generateMonthlyApprovedPurchaseOrderReportInputBoundary;
    private GenerateMonthlyApprovedPurchaseOrderReportPresenter generateMonthlyApprovedPurchaseOrderReportPresenter;
    private ExportProcurementDataInputBoundary exportProcurementDataInputBoundary;
    private ExportProcurementDataPresenter exportProcurementDataPresenter;

    public ProcurementReportController(CreateCustomizedProcurementDashboardInputBoundary createCustomizedProcurementPerformanceDashboardInputBoundary,
                                       CreateCustomizedProcurementDashboardPresenter createCustomizedProcurementPerformanceDashboardPresenter,
                                       GenerateMonthlyApprovedPurchaseOrderReportInputBoundary generateMonthlyApprovedPurchaseOrderReportInputBoundary,
                                       GenerateMonthlyApprovedPurchaseOrderReportPresenter generateMonthlyApprovedPurchaseOrderReportPresenter,
                                       ExportProcurementDataInputBoundary exportProcurementDataInputBoundary,
                                       ExportProcurementDataPresenter exportProcurementDataPresenter) {

        this.createCustomizedProcurementPerformanceDashboardInputBoundary = createCustomizedProcurementPerformanceDashboardInputBoundary;
        this.createCustomizedProcurementPerformanceDashboardPresenter = createCustomizedProcurementPerformanceDashboardPresenter;
        this.generateMonthlyApprovedPurchaseOrderReportInputBoundary = generateMonthlyApprovedPurchaseOrderReportInputBoundary;
        this.generateMonthlyApprovedPurchaseOrderReportPresenter = generateMonthlyApprovedPurchaseOrderReportPresenter;
        this.exportProcurementDataInputBoundary = exportProcurementDataInputBoundary;
        this.exportProcurementDataPresenter = exportProcurementDataPresenter;
    }

    @PostMapping("/generate_approved_purchaseorder")
    public ResponseEntity<?> generateMonthlyApprovedPurchaseOrderReport(@RequestBody GenerateMonthlyApprovedPurchaseOrderReportInputDS inputDS) {
        GenerateMonthlyApprovedPurchaseOrderReportOutPutDS monthlyApprovedPurchaseOrderReportOutPutDS = generateMonthlyApprovedPurchaseOrderReportInputBoundary.generateMonthlyApprovedPurchaseOrderReport(inputDS);
        generateMonthlyApprovedPurchaseOrderReportPresenter.generateMonthlyApprovedPurchaseOrderReport(monthlyApprovedPurchaseOrderReportOutPutDS);
        GenerateMonthlyApprovedPurchaseOrderReportViewModel generateMonthlyApprovedPurchaseOrderReportViewModel = generateMonthlyApprovedPurchaseOrderReportPresenter.getViewModel();
        return ResponseEntity.ok(generateMonthlyApprovedPurchaseOrderReportViewModel);
    }

    @PostMapping("/create_customized_dashboard")
    public ResponseEntity<?> createCustomizedProcurementPerformanceDashboard(@RequestBody CreateCustomizedProcurementDashboardInputDS inputDS) {
        CreateCustomizedProcurementDashboardOutputDS createCustomizedProcurementPerformanceDashboardOutputDS = createCustomizedProcurementPerformanceDashboardInputBoundary.createCustomizedProcurementDashboard(inputDS);
        createCustomizedProcurementPerformanceDashboardPresenter.presentCustomizedProcurementDashboard(createCustomizedProcurementPerformanceDashboardOutputDS);
        CreateCustomizedProcurementDashboardViewModel createCustomizedProcurementDashboardViewModel = createCustomizedProcurementPerformanceDashboardPresenter.getViewModel();
        return ResponseEntity.ok(createCustomizedProcurementDashboardViewModel);
    }

    @PostMapping("/export_procurementdata")
    public ResponseEntity<?> exportProcurementData(@RequestBody ExportProcurementDataInputDS inputDS) {
        ExportedProcurementDataOutputDS exportedProcurementDataOutputDS = exportProcurementDataInputBoundary.exportProcurementData(inputDS);
        exportProcurementDataPresenter.presentExportedProcurementData(exportedProcurementDataOutputDS);
        ExportProcurementDataViewModel exportProcurementDataViewModel = exportProcurementDataPresenter.getViewModel();
        return ResponseEntity.ok(exportProcurementDataViewModel);
    }
}
