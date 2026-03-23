package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.PaymentReconciliationReportRepository;
import com.bizeff.procurement.models.procurementreport.PaymentReconciliationReport;
import com.bizeff.procurement.persistences.entity.procurementreport.PaymentReconciliationReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataPaymentReconciliationReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.PaymentReconciliationReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.bizeff.procurement.persistences.mapper.procurementreport.PaymentReconciliationReportMapper.*;

@Repository
public class JpaPaymentReconciliationReportRepository implements PaymentReconciliationReportRepository {
    private SpringDataPaymentReconciliationReportRepository springDataPaymentReconciliationReportRepository;
    public JpaPaymentReconciliationReportRepository(SpringDataPaymentReconciliationReportRepository springDataPaymentReconciliationReportRepository){
        this.springDataPaymentReconciliationReportRepository = springDataPaymentReconciliationReportRepository;
    }
    @Override
    public PaymentReconciliationReport save(PaymentReconciliationReport paymentReconciliationReport) {
        PaymentReconciliationReportEntity reconciliationReportEntity = toEntity(paymentReconciliationReport);
        PaymentReconciliationReportEntity savedReconciliationReportEntity = springDataPaymentReconciliationReportRepository.save(reconciliationReportEntity);
        return toModel(savedReconciliationReportEntity);
    }
    @Override
    public Optional<PaymentReconciliationReport> findById(Long id) {
        return springDataPaymentReconciliationReportRepository.findById(id).map(PaymentReconciliationReportMapper::toModel);
    }
    @Override
    public void deleteById(Long id) {
        springDataPaymentReconciliationReportRepository.deleteById(id);
    }
    @Override
    public List<PaymentReconciliationReport> findAll() {
        return springDataPaymentReconciliationReportRepository.findAll().stream()
                .map(PaymentReconciliationReportMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public void deleteAll() {
        springDataPaymentReconciliationReportRepository.deleteAll();
    }
}
