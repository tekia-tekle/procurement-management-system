package com.bizeff.procurement.webapi.presenter.procurementreport;

import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.ExportProcurementDataOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.ExportedProcurementDataOutputDS;
import com.bizeff.procurement.webapi.viewmodel.procurementreport.ExportProcurementDataViewModel;
import org.springframework.stereotype.Component;

@Component
public class ExportProcurementDataPresenter implements ExportProcurementDataOutputBoundary {
    private ExportProcurementDataViewModel exportProcurementDataViewModel;
    public ExportProcurementDataPresenter(ExportProcurementDataViewModel exportProcurementDataViewModel) {
        this.exportProcurementDataViewModel = exportProcurementDataViewModel;
    }

    @Override
    public void presentExportedProcurementData(ExportedProcurementDataOutputDS exportedProcurementDataOutputDS){
        this.exportProcurementDataViewModel = new ExportProcurementDataViewModel(
                exportedProcurementDataOutputDS.getReportId(),
                exportedProcurementDataOutputDS.getReportTitle(),
                exportedProcurementDataOutputDS.getStartDate().toString(),
                exportedProcurementDataOutputDS.getEndDate().toString(),
                exportedProcurementDataOutputDS.getFileFormat().name(),
                exportedProcurementDataOutputDS.getExportedBy(),
                exportedProcurementDataOutputDS.getExportedAt().toString(),
                exportedProcurementDataOutputDS.getReportData()
        );
    }
    public ExportProcurementDataViewModel getViewModel() {
        return exportProcurementDataViewModel;
    }

    //toString() method
    @Override
    public String toString() {
        return "ExportProcurementDataPresenter{" +
                "exportProcurementDataViewModel=" + exportProcurementDataViewModel +
                '}';
    }
}
