package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ContractReportRepository;
import com.bizeff.procurement.models.procurementreport.ContractReport;
import com.bizeff.procurement.persistences.entity.procurementreport.ContractReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataContractReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.ContractReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.ContractReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.ContractReportMapper.toModel;

@Repository
public class JpaContractReportRepository implements ContractReportRepository {
    private SpringDataContractReportRepository springDataContractReportRepository;
    public JpaContractReportRepository(SpringDataContractReportRepository springDataContractReportRepository){
        this.springDataContractReportRepository = springDataContractReportRepository;
    }
    @Override
    public ContractReport save(ContractReport contractReport) {
        ContractReportEntity contractReportEntity = toEntity(contractReport);
        ContractReportEntity savedContractReportEntity = springDataContractReportRepository.save(contractReportEntity);

        return toModel(savedContractReportEntity);
    }

    @Override
    public Optional<ContractReport> findById(Long id) {
        return springDataContractReportRepository.findById(id).map(ContractReportMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        springDataContractReportRepository.deleteById(id);
    }

    @Override
    public List<ContractReport> findAll() {
        return springDataContractReportRepository.findAll().stream()
                .map(ContractReportMapper::toModel).collect(Collectors.toList());
    }

    @Override
    public void deleteAll() {
        springDataContractReportRepository.deleteAll();
    }
}
