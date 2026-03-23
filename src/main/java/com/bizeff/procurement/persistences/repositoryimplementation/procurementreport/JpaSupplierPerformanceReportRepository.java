package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.SupplierPerformanceReportRepository;
import com.bizeff.procurement.models.procurementreport.SupplierPerformanceReport;
import com.bizeff.procurement.persistences.entity.procurementreport.SupplierPerformanceReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataSupplierPerformanceReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.SupplierPerformanceReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import static com.bizeff.procurement.persistences.mapper.procurementreport.SupplierPerformanceReportMapper.*;
@Repository
public class JpaSupplierPerformanceReportRepository implements SupplierPerformanceReportRepository {
    private SpringDataSupplierPerformanceReportRepository springDataSupplierPerformanceReportRepository;
    public JpaSupplierPerformanceReportRepository(SpringDataSupplierPerformanceReportRepository springDataSupplierPerformanceReportRepository){
        this.springDataSupplierPerformanceReportRepository = springDataSupplierPerformanceReportRepository;
    }
    @Override
    public SupplierPerformanceReport save(SupplierPerformanceReport supplierPerformanceReport) {
        SupplierPerformanceReportEntity supplierPerformanceReportEntity = toEntity(supplierPerformanceReport);
        SupplierPerformanceReportEntity savedSupplierPerformanceEntity = springDataSupplierPerformanceReportRepository.save(supplierPerformanceReportEntity);
        return toModel(savedSupplierPerformanceEntity);
    }
    @Override
    public Optional<SupplierPerformanceReport> findById(Long id) {
        return springDataSupplierPerformanceReportRepository.findById(id).map(SupplierPerformanceReportMapper::toModel);
    }
    @Override
    public void deleteById(Long id) {
        springDataSupplierPerformanceReportRepository.deleteById(id);
    }
    @Override
    public List<SupplierPerformanceReport> findAll() {
        return springDataSupplierPerformanceReportRepository.findAll().stream()
                .map(SupplierPerformanceReportMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public void deleteAll() {
        springDataSupplierPerformanceReportRepository.deleteAll();
    }
}
