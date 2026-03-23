package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.DeliveryReceiptReportRepository;
import com.bizeff.procurement.models.procurementreport.DeliveryReceiptReport;
import com.bizeff.procurement.persistences.entity.procurementreport.DeliveryReceiptReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataDeliveryReceiptReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.DeliveryReceiptReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.DeliveryReceiptReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.DeliveryReceiptReportMapper.toModel;

@Repository
public class JpaDeliveryReceiptReportRepository implements DeliveryReceiptReportRepository {
    private SpringDataDeliveryReceiptReportRepository springDataDeliveryReceiptReportRepository;
    public JpaDeliveryReceiptReportRepository(SpringDataDeliveryReceiptReportRepository springDataDeliveryReceiptReportRepository){
        this.springDataDeliveryReceiptReportRepository = springDataDeliveryReceiptReportRepository;
    }
    @Override
    public DeliveryReceiptReport save(DeliveryReceiptReport deliveryReceiptReport) {
        DeliveryReceiptReportEntity deliveryReceiptReportEntity = toEntity(deliveryReceiptReport);
        DeliveryReceiptReportEntity savedDeliveryReceiptReportEntity = springDataDeliveryReceiptReportRepository.save(deliveryReceiptReportEntity);
        return toModel(savedDeliveryReceiptReportEntity);
    }
    @Override
    public Optional<DeliveryReceiptReport> findById(Long id) {
        return springDataDeliveryReceiptReportRepository.findById(id).map(DeliveryReceiptReportMapper::toModel);
    }
    @Override
    public void deleteById(Long id) {
        springDataDeliveryReceiptReportRepository.deleteById(id);
    }
    @Override
    public List<DeliveryReceiptReport> findAll() {
        return springDataDeliveryReceiptReportRepository.findAll().stream()
                .map(DeliveryReceiptReportMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public void deleteAll() {
        springDataDeliveryReceiptReportRepository.deleteAll();
    }
}
