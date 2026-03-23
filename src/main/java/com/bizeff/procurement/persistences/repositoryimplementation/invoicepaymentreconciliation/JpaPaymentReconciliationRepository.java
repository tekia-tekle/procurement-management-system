package com.bizeff.procurement.persistences.repositoryimplementation.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.models.enums.ReconciliationStatus;
import com.bizeff.procurement.models.invoicepaymentreconciliation.PaymentReconciliation;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.PaymentReconciliationEntity;
import com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation.SpringDataPaymentReconciliationRepository;
import com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.PaymentReconciliationMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.PaymentReconciliationMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.PaymentReconciliationMapper.toModel;

@Repository
public class JpaPaymentReconciliationRepository implements PaymentReconciliationRepository {
    private SpringDataPaymentReconciliationRepository springDataPaymentReconciliationRepository;
    public JpaPaymentReconciliationRepository(SpringDataPaymentReconciliationRepository springDataPaymentReconciliationRepository){
        this.springDataPaymentReconciliationRepository = springDataPaymentReconciliationRepository;
    }

    @Override
    public PaymentReconciliation save(PaymentReconciliation paymentReconciliation) {
        PaymentReconciliationEntity entity = toEntity(paymentReconciliation);
        PaymentReconciliationEntity savedReconciledEntity = springDataPaymentReconciliationRepository.save(entity);

        return toModel(savedReconciledEntity);
    }

    @Override
    public Optional<PaymentReconciliation> findByPaymentId(String paymentId) {
        return springDataPaymentReconciliationRepository.findByPaymentId(paymentId).map(PaymentReconciliationMapper::toModel);
    }

    @Override
    public List<PaymentReconciliation> findByInvoiceId(String invoiceId) {
        return springDataPaymentReconciliationRepository.findByInvoice_InvoiceId(invoiceId).stream()
                .map(PaymentReconciliationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentReconciliation> findByOrderId(String orderId) {
        return springDataPaymentReconciliationRepository.findByPurchaseOrder_OrderId(orderId).stream()
                .map(PaymentReconciliationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentReconciliation> findByReceiptId(String receiptId) {
        return springDataPaymentReconciliationRepository.findByDeliveryReceipt_ReceiptId(receiptId).stream()
                .map(PaymentReconciliationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentReconciliation> findByStatus(ReconciliationStatus status) {
        return springDataPaymentReconciliationRepository.findByReconciliationStatus(status).stream()
                .map(PaymentReconciliationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentReconciliation> findByReconciliationDateBefore(LocalDate date) {
        return springDataPaymentReconciliationRepository.findByReconciliationDateBefore(date).stream()
                .map(PaymentReconciliationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentReconciliation> findAll() {
        return springDataPaymentReconciliationRepository.findAll().stream()
                .map(PaymentReconciliationMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteByPaymentId(String paymentId) {
        springDataPaymentReconciliationRepository.deleteByPaymentId(paymentId);
    }

    @Override
    public void deleteAll() {
        springDataPaymentReconciliationRepository.deleteAll();
    }
}
