package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation;
import com.bizeff.procurement.models.enums.DeliveryStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface DeliveryReceiptRepository {
    DeliveryReceipt save(DeliveryReceipt receipt);
    Optional<DeliveryReceipt> findByReceiptId(String receiptId);
    List<DeliveryReceipt> findBySupplierId(String supplierId);
    List<DeliveryReceipt> findByOrderId(String orderId);
    List<DeliveryReceipt>findByUser(String userId);
    List<DeliveryReceipt>findByStatus(DeliveryStatus status);
    List<DeliveryReceipt>findByDeliveryDateBefore(LocalDate date);
    void deleteByReceiptId(String receiptId);
    List<DeliveryReceipt> findAll();
    void deleteAll();


}
