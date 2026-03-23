package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.PurchaseRequisitionReport;

import java.util.List;
import java.util.Optional;

public interface PurchaseRequisitionReportRepository {
    PurchaseRequisitionReport save(PurchaseRequisitionReport purchaseRequisitionReport);
    Optional<PurchaseRequisitionReport> findById(Long id);
    void deleteById(Long id);
    List<PurchaseRequisitionReport> findAll();
    void deleteAll();
}
