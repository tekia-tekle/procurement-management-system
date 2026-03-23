package com.bizeff.procurement.persistences.repositoryimplementation.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveryReceiptRepository;
import com.bizeff.procurement.models.enums.DeliveryStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.DeliveryReceipt;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.DeliveryReceiptEntity;
import com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation.SpringDataDeliveryReceiptsRepository;
import com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.DeliveryReceiptsMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.DeliveryReceiptsMapper.toModel;

@Repository
public class JpaDeliveryReceiptsRepository implements DeliveryReceiptRepository {
    private SpringDataDeliveryReceiptsRepository springDataDeliveryReceiptsRepository;

    public JpaDeliveryReceiptsRepository(SpringDataDeliveryReceiptsRepository springDataDeliveryReceiptsRepository) {
        this.springDataDeliveryReceiptsRepository = springDataDeliveryReceiptsRepository;
    }
    @Override
    public DeliveryReceipt save(DeliveryReceipt deliveryReceipts) {
        DeliveryReceiptEntity deliveryReceiptsEntity = DeliveryReceiptsMapper.toEntity(deliveryReceipts);

        DeliveryReceiptEntity savedDeliveryReceipts = springDataDeliveryReceiptsRepository.save(deliveryReceiptsEntity);

        return toModel(savedDeliveryReceipts);
    }

    @Override
    public Optional<DeliveryReceipt> findByReceiptId(String receiptId) {
        return springDataDeliveryReceiptsRepository.findByReceiptId(receiptId).map(deliveryReceiptsEntity -> toModel(deliveryReceiptsEntity));
    }

    @Override
    public List<DeliveryReceipt> findAll() {
        return springDataDeliveryReceiptsRepository.findAll().stream()
                .map(DeliveryReceiptsMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryReceipt> findBySupplierId(String supplierId) {
        return springDataDeliveryReceiptsRepository.findBySupplier_SupplierId(supplierId).stream()
                .map(DeliveryReceiptsMapper::toModel)
                .collect(Collectors.toList());
            }

    @Override
    public List<DeliveryReceipt> findByOrderId(String orderId) {
        return springDataDeliveryReceiptsRepository.findByPurchaseOrder_OrderId(orderId).stream()
                .map(DeliveryReceiptsMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryReceipt> findByUser(String userId) {
        return springDataDeliveryReceiptsRepository.findByReceivedBy_UserId(userId).stream()
                .map(DeliveryReceiptsMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryReceipt> findByStatus(DeliveryStatus status) {
        return springDataDeliveryReceiptsRepository.findByDeliveryStatus(status).stream()
                .map(DeliveryReceiptsMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryReceipt> findByDeliveryDateBefore(LocalDate date) {
        return springDataDeliveryReceiptsRepository.findByDeliveryDateBefore(date).stream()
                .map(DeliveryReceiptsMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByReceiptId(String receiptId) {
        springDataDeliveryReceiptsRepository.deleteByReceiptId(receiptId);
    }



    @Override
    public void deleteAll(){
        springDataDeliveryReceiptsRepository.deleteAll();
    }
}
