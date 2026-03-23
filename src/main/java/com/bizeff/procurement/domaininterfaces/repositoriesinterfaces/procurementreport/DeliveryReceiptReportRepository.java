package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport;

import com.bizeff.procurement.models.procurementreport.DeliveryReceiptReport;

import java.util.List;
import java.util.Optional;

public interface DeliveryReceiptReportRepository {
    DeliveryReceiptReport save(DeliveryReceiptReport deliveryReceiptReport);
    Optional<DeliveryReceiptReport> findById(Long id);
    void deleteById(Long id);
    List<DeliveryReceiptReport> findAll();
    void deleteAll();
}
