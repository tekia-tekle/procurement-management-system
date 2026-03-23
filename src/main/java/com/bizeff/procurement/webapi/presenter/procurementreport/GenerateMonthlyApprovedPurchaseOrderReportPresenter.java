package com.bizeff.procurement.webapi.presenter.procurementreport;

import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutPutDS;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportViewModel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GenerateMonthlyApprovedPurchaseOrderReportPresenter implements GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary {
    private GenerateMonthlyApprovedPurchaseOrderReportViewModel generateMonthlyApprovedPurchaseOrderReportViewModel;
    public GenerateMonthlyApprovedPurchaseOrderReportPresenter(GenerateMonthlyApprovedPurchaseOrderReportViewModel generateMonthlyApprovedPurchaseOrderReportViewModel) {
        this.generateMonthlyApprovedPurchaseOrderReportViewModel = generateMonthlyApprovedPurchaseOrderReportViewModel;

    }
    @Override
    public void generateMonthlyApprovedPurchaseOrderReport(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS outputDS){
        Map<String, String> supplierSpending = outputDS.getSupplierSpending().entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().toString()
                ));
        Map<String, String> departmentSpending = outputDS.getDepartmentSpending().entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().toString()
                ));

        this.generateMonthlyApprovedPurchaseOrderReportViewModel = new GenerateMonthlyApprovedPurchaseOrderReportViewModel(
                outputDS.getReportId(),
                outputDS.getReportTitle(),
                outputDS.getReportDescription(),
                outputDS.getStartDate().toString(),
                outputDS.getEndDate().toString(),
                outputDS.getTotalPurchaseOrders(),
                outputDS.getTotalSpending().toString(),
                supplierSpending,
                outputDS.getSupplierPurchaseOrders(),
                outputDS.getDepartmentPurchaseOrders(),
                departmentSpending
        );
    }
    public GenerateMonthlyApprovedPurchaseOrderReportViewModel getViewModel() {
        return generateMonthlyApprovedPurchaseOrderReportViewModel;
    }

    //toString() method
    @Override
    public String toString() {
        return "GenerateMonthlyApprovedPurchaseOrderReportPresenter{" +
                "generateMonthlyApprovedPurchaseOrderReportViewModel=" + generateMonthlyApprovedPurchaseOrderReportViewModel +
                '}';
    }
}
