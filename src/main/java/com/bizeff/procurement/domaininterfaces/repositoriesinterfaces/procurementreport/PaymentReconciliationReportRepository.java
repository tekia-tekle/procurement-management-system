package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.PaymentReconciliationReport;

import java.util.List;
import java.util.Optional;

public interface PaymentReconciliationReportRepository {
    PaymentReconciliationReport save(PaymentReconciliationReport paymentReconciliationReport);
    Optional<PaymentReconciliationReport> findById(Long id);
    void deleteById(Long id);
    List<PaymentReconciliationReport> findAll();
    void deleteAll();
}
