package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.InvoiceReportRepository;
import com.bizeff.procurement.models.procurementreport.InvoiceReport;
import com.bizeff.procurement.persistences.entity.procurementreport.InvoiceReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataInvoiceReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.InvoiceReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.InvoiceReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.InvoiceReportMapper.toModel;
@Repository
public class JpaInvoiceReportRepository implements InvoiceReportRepository {
    private SpringDataInvoiceReportRepository springDataInvoiceReportRepository;
    public JpaInvoiceReportRepository(SpringDataInvoiceReportRepository springDataInvoiceReportRepository){
        this.springDataInvoiceReportRepository = springDataInvoiceReportRepository;
    }
    @Override
    public InvoiceReport save(InvoiceReport invoiceReport) {
        InvoiceReportEntity invoiceReportEntity = toEntity(invoiceReport);
        InvoiceReportEntity savedInvoiceReportEntity = springDataInvoiceReportRepository.save(invoiceReportEntity);
        return toModel(savedInvoiceReportEntity);
    }
    @Override
    public Optional<InvoiceReport> findById(Long id) {
        return springDataInvoiceReportRepository.findById(id).map(InvoiceReportMapper::toModel);
    }
    @Override
    public void deleteById(Long id) {
        springDataInvoiceReportRepository.deleteById(id);
    }
    @Override
    public List<InvoiceReport> findAll() {
        return springDataInvoiceReportRepository.findAll().stream()
                .map(InvoiceReportMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public void deleteAll() {
        springDataInvoiceReportRepository.deleteAll();
    }
}
