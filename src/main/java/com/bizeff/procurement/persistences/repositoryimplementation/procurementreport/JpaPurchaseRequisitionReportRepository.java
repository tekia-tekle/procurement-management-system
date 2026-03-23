package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.PurchaseRequisitionReportRepository;
import com.bizeff.procurement.models.procurementreport.PurchaseRequisitionReport;
import com.bizeff.procurement.persistences.entity.procurementreport.PurchaseRequisitionReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataPurchaseRequisitionReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.PurchaseRequisitionReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.PurchaseRequisitionReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.PurchaseRequisitionReportMapper.toModel;

@Repository
public class JpaPurchaseRequisitionReportRepository implements PurchaseRequisitionReportRepository {
    private SpringDataPurchaseRequisitionReportRepository springDataPurchaseRequisitionReportRepository;
    public JpaPurchaseRequisitionReportRepository(SpringDataPurchaseRequisitionReportRepository springDataPurchaseRequisitionReportRepository){
        this.springDataPurchaseRequisitionReportRepository = springDataPurchaseRequisitionReportRepository;
    }
    @Override
    public PurchaseRequisitionReport save(PurchaseRequisitionReport purchaseRequisitionReport) {
        PurchaseRequisitionReportEntity requisitionReportEntity= toEntity(purchaseRequisitionReport);
        PurchaseRequisitionReportEntity savedRequisitionReportEntity = springDataPurchaseRequisitionReportRepository.save(requisitionReportEntity);
        return toModel(savedRequisitionReportEntity);
    }

    @Override
    public Optional<PurchaseRequisitionReport> findById(Long id) {
        return springDataPurchaseRequisitionReportRepository.findById(id).map(PurchaseRequisitionReportMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        springDataPurchaseRequisitionReportRepository.deleteById(id);
    }

    @Override
    public List<PurchaseRequisitionReport> findAll() {
        return springDataPurchaseRequisitionReportRepository.findAll().stream()
                .map(PurchaseRequisitionReportMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataPurchaseRequisitionReportRepository.deleteAll();
    }
}
