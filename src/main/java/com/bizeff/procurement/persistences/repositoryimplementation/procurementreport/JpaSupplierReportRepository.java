package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.SupplierReportRepository;
import com.bizeff.procurement.models.procurementreport.SupplierReport;
import com.bizeff.procurement.persistences.entity.procurementreport.SupplierReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataSupplierReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.SupplierReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.SupplierReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.SupplierReportMapper.toModel;
@Repository
public class JpaSupplierReportRepository implements SupplierReportRepository {
    private SpringDataSupplierReportRepository springDataSupplierReportRepository;
    public JpaSupplierReportRepository(SpringDataSupplierReportRepository springDataSupplierReportRepository){
        this.springDataSupplierReportRepository = springDataSupplierReportRepository;
    }
    @Override
    public SupplierReport save(SupplierReport supplierReport) {
        SupplierReportEntity supplierReportEntity = toEntity(supplierReport);
        SupplierReportEntity savedSupplierReportEntity = springDataSupplierReportRepository.save(supplierReportEntity);
        return toModel(savedSupplierReportEntity);
    }

    @Override
    public Optional<SupplierReport> findById(Long id) {
        return springDataSupplierReportRepository.findById(id).map(SupplierReportMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        springDataSupplierReportRepository.deleteById(id);
    }

    @Override
    public List<SupplierReport> findAll() {
        return springDataSupplierReportRepository.findAll().stream()
                .map(SupplierReportMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataSupplierReportRepository.deleteAll();
    }
}
