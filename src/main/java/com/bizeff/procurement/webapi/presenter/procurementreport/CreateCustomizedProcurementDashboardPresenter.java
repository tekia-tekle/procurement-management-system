package com.bizeff.procurement.webapi.presenter.procurementreport;

import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.CreateCustomizedProcurementDashboardOutPutBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.CreateCustomizedProcurementDashboardOutputDS;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.CreateCustomizedProcurementDashboardViewModel;
import org.springframework.stereotype.Component;

@Component
public class CreateCustomizedProcurementDashboardPresenter implements CreateCustomizedProcurementDashboardOutPutBoundary {
    private CreateCustomizedProcurementDashboardViewModel createCustomizedProcurementDashboardViewModel;
    public CreateCustomizedProcurementDashboardPresenter(CreateCustomizedProcurementDashboardViewModel createCustomizedProcurementDashboardViewModel) {
        this.createCustomizedProcurementDashboardViewModel = createCustomizedProcurementDashboardViewModel;
    }
    @Override
    public void presentCustomizedProcurementDashboard(CreateCustomizedProcurementDashboardOutputDS outputDS){
        this.createCustomizedProcurementDashboardViewModel = new CreateCustomizedProcurementDashboardViewModel(
                outputDS.getDashboardId(),
                outputDS.getDashboardTitle(),
                outputDS.getReportData(),
                outputDS.getCreatedBy().getFullName(),
                outputDS.getCreatedAt().toString()
        );
    }
    public CreateCustomizedProcurementDashboardViewModel getViewModel() {
        return createCustomizedProcurementDashboardViewModel;
    }

    //toString() method
    @Override
    public String toString() {
        return "CreateCustomizedProcurementDashboardPresenter{" +
                "viewModel=" + createCustomizedProcurementDashboardViewModel +
                '}';
    }
}
