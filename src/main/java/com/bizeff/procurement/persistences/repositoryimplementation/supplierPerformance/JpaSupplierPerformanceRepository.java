package com.bizeff.procurement.persistences.repositoryimplementation.supplierPerformance;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierPerformanceEntity;
import com.bizeff.procurement.persistences.jparepository.supplierperformance.SpringDataSupplierPerformanceRepository;
import com.bizeff.procurement.persistences.mapper.supplierPerformance.SupplierPerformanceMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.supplierPerformance.SupplierPerformanceMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.supplierPerformance.SupplierPerformanceMapper.toModel;

@Repository
public class JpaSupplierPerformanceRepository implements SupplierPerformanceRepository {
    private SpringDataSupplierPerformanceRepository springDataSupplierPerformanceRepository;

    public JpaSupplierPerformanceRepository(SpringDataSupplierPerformanceRepository springDataSupplierPerformanceRepository){
        this.springDataSupplierPerformanceRepository = springDataSupplierPerformanceRepository;
    }
    @Override
    public SupplierPerformance save(SupplierPerformance supplierPerformance) {
        SupplierPerformanceEntity entity = toEntity(supplierPerformance);
        SupplierPerformanceEntity savedPerformanceEntity = springDataSupplierPerformanceRepository.save(entity);

        return toModel(savedPerformanceEntity);
    }

    @Override
    public SupplierPerformance update(SupplierPerformance supplierPerformance) {
        if (supplierPerformance.getId() == null) {
            throw new IllegalArgumentException("Supplier Performance ID cannot be null.");
        }
        Optional<SupplierPerformanceEntity> existingPerformanceOpt = springDataSupplierPerformanceRepository.findById(supplierPerformance.getId());
        if (existingPerformanceOpt.isEmpty()) {
            throw new IllegalArgumentException("Supplier Performance with ID " + supplierPerformance.getId() + " does not exist.");
        }
        SupplierPerformanceEntity existingPerformance = existingPerformanceOpt.get();
        existingPerformance.setSupplier(existingPerformance.getSupplier());
        existingPerformance.setSupplierQualitativePerformanceMetricsEntity(toEntity(supplierPerformance.getQualitativePerformanceMetrics()));
        existingPerformance.setSupplierQuantitativePerformaneMetricsEntity(toEntity(supplierPerformance.getQuantitativePerformanceMetrics()));
        existingPerformance.setPerformanceRate(supplierPerformance.getSupplierPerformanceRate());
        existingPerformance.setEvaluationDate(supplierPerformance.getEvaluationDate());
        existingPerformance.setEvaluatorComments(supplierPerformance.getEvaluatorComments());

        SupplierPerformanceEntity updatedPerformanceEntity = springDataSupplierPerformanceRepository.save(existingPerformance);
        return toModel(updatedPerformanceEntity);
    }

    @Override
    public Optional<SupplierPerformance>findById(Long id){
        return springDataSupplierPerformanceRepository.findById(id).map(SupplierPerformanceMapper::toModel);
    }
    @Override
    public List<SupplierPerformance> findBySupplierId(String supplierId) {
        return springDataSupplierPerformanceRepository.findBySupplier_SupplierId(supplierId).stream()
                .map(SupplierPerformanceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id){
        springDataSupplierPerformanceRepository.deleteById(id);
    }
    @Override
    public void deleteBySupplierId(String supplierId) {
        springDataSupplierPerformanceRepository.deleteBySupplier_SupplierId(supplierId);
    }
    @Override
    public List<SupplierPerformance>findByPerformanceRate(SupplierPerformanceRate performanceRate){
        return springDataSupplierPerformanceRepository.findByPerformanceRate(performanceRate).stream()
                .map(SupplierPerformanceMapper::toModel)
                .collect(Collectors.toList());
    }
    @Override
    public List<SupplierPerformance>findByEvaluationDateAfter(LocalDate date){
        return springDataSupplierPerformanceRepository.findByEvaluationDateAfter(date).stream()
                .map(SupplierPerformanceMapper::toModel)
                .collect(Collectors.toList());
    }
    @Override
    public List<SupplierPerformance>findByEvaluationDateBefore(LocalDate date){
        return springDataSupplierPerformanceRepository.findByEvaluationDateBefore(date).stream()
                .map(SupplierPerformanceMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<SupplierPerformance> findAll() {
        return springDataSupplierPerformanceRepository.findAll().stream()
                .map(SupplierPerformanceMapper::toModel)
                .collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataSupplierPerformanceRepository.deleteAll();
    }
}
