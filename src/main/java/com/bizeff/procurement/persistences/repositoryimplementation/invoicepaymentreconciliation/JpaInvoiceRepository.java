package com.bizeff.procurement.persistences.repositoryimplementation.invoicepaymentreconciliation;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoiceRepository;
import com.bizeff.procurement.models.invoicepaymentreconciliation.Invoice;
import com.bizeff.procurement.persistences.entity.paymentreconciliation.InvoiceEntity;
import com.bizeff.procurement.persistences.jparepository.invoicepaymentreconciliation.SpringDataInvoiceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.InvoiceMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.invoicepaymentreconciliation.InvoiceMapper.toModel;
@Repository
public class JpaInvoiceRepository implements InvoiceRepository {
    private SpringDataInvoiceRepository springDataInvoiceRepository;
    public JpaInvoiceRepository(SpringDataInvoiceRepository springDataInvoiceRepository){
        this.springDataInvoiceRepository = springDataInvoiceRepository;
    }
    @Override
    public Invoice save(Invoice invoice) {
        InvoiceEntity entity = toEntity(invoice);
        InvoiceEntity savedInvoice = springDataInvoiceRepository.save(entity);

        return toModel(savedInvoice);
    }
    @Override
    public Optional<Invoice> findByInvoiceId(String invoiceId) {
        return springDataInvoiceRepository.findByInvoiceId(invoiceId).map(invoiceEntity -> toModel(invoiceEntity));
    }



    @Override
    public void deleteByInvoiceId(String invoiceId) {springDataInvoiceRepository.deleteByInvoiceId(invoiceId);
    }

    @Override
    public List<Invoice> findAll() {
        return springDataInvoiceRepository.findAll().stream()
                .map(invoiceEntity -> toModel(invoiceEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataInvoiceRepository.deleteAll();
    }

    @Override
    public List<Invoice> findBySupplierId(String supplierId) {
        return springDataInvoiceRepository.findBySupplier_SupplierId(supplierId).stream()
                .map(invoiceEntity -> toModel(invoiceEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Invoice> findByPurchaseOrderId(String purchaseOrderId) {
        return springDataInvoiceRepository.findByPurchaseOrder_OrderId(purchaseOrderId).map(invoiceEntity -> toModel(invoiceEntity));
    }

    @Override
    public List<Invoice> findInvoiceByUserId(String userId) {
        return springDataInvoiceRepository.findByUser_UserId(userId).stream()
                .map(invoiceEntity -> toModel(invoiceEntity))
                .collect(Collectors.toList());
    }

    @Override
    public List<Invoice> findByInvoiceDateBetween(LocalDate startDate, LocalDate endDate) {
        return springDataInvoiceRepository.findByInvoiceDateBetween(startDate,endDate).stream()
                .map(invoiceEntity -> toModel(invoiceEntity))
                .collect(Collectors.toList());
    }
}
