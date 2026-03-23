package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.PurchaseOrderReport;

import java.util.List;
import java.util.Optional;

public interface PurchaseOrderReportRepository {
    PurchaseOrderReport save(PurchaseOrderReport purchaseOrderReport);
    Optional<PurchaseOrderReport> findById(Long id);
    void deleteById(Long id);
    List<PurchaseOrderReport> findAll();
    void deleteAll();
}
