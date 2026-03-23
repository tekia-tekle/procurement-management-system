package com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport;

import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.ExportedProcurementDataOutputDS;

public interface ExportProcurementDataOutputBoundary {
    void presentExportedProcurementData(ExportedProcurementDataOutputDS exportedProcurementDataOutputDS);
}
