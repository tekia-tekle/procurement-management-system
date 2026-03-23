package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.PurchaseOrderReportRepository;
import com.bizeff.procurement.models.procurementreport.PurchaseOrderReport;
import com.bizeff.procurement.persistences.entity.procurementreport.PurchaseOrderReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataPurchaseOrderReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.PurchaseOrderReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.PurchaseOrderReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.PurchaseOrderReportMapper.toModel;

@Repository
public class JpaPurchaseOrderReportRepository implements PurchaseOrderReportRepository {
    private SpringDataPurchaseOrderReportRepository springDataPurchaseOrderReportRepository;
    public JpaPurchaseOrderReportRepository(SpringDataPurchaseOrderReportRepository springDataPurchaseOrderReportRepository){
        this.springDataPurchaseOrderReportRepository = springDataPurchaseOrderReportRepository;
    }
    @Override
    public PurchaseOrderReport save(PurchaseOrderReport purchaseOrderReport) {
        PurchaseOrderReportEntity orderReportEntity = toEntity(purchaseOrderReport);
        PurchaseOrderReportEntity savedOrderReportEntity = springDataPurchaseOrderReportRepository.save(orderReportEntity);
        return toModel(savedOrderReportEntity);
    }

    @Override
    public Optional<PurchaseOrderReport> findById(Long id) {
        return springDataPurchaseOrderReportRepository.findById(id).map(PurchaseOrderReportMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        springDataPurchaseOrderReportRepository.deleteById(id);
    }

    @Override
    public List<PurchaseOrderReport> findAll() {
        return springDataPurchaseOrderReportRepository.findAll().stream()
                .map(PurchaseOrderReportMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataPurchaseOrderReportRepository.deleteAll();
    }
}
