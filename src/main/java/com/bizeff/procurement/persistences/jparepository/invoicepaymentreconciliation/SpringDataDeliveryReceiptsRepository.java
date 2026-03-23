package com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation;


import com.bizeff.procurement.models.enums.DeliveryStatus;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveryReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SpringDataDeliveryReceiptsRepository extends JpaRepository<DeliveryReceiptEntity, Long> {
    Optional<DeliveryReceiptEntity>findByReceiptId(String receiptId);
    List<DeliveryReceiptEntity>findBySupplier_SupplierId(String supplierId);
    List<DeliveryReceiptEntity>findByPurchaseOrder_OrderId(String orderId);
    List<DeliveryReceiptEntity>findByReceivedBy_UserId(String userId);
    List<DeliveryReceiptEntity>findByDeliveryStatus(DeliveryStatus status);
    List<DeliveryReceiptEntity>findByDeliveryDateBefore(LocalDate date);
    void deleteByReceiptId(String receiptId);

}
