package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ProcurementReportRepository;
import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.persistences.entity.procurementreport.ProcurementReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataProcurementReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.ProcurementReportMapper;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.ProcurementReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.ProcurementReportMapper.toModel;

@Repository
public class JpaProcurementReportRepository implements ProcurementReportRepository {
    private SpringDataProcurementReportRepository springDataReportAnalysisRepository;
    public JpaProcurementReportRepository(SpringDataProcurementReportRepository springDataReportAnalysisRepository){
        this.springDataReportAnalysisRepository = springDataReportAnalysisRepository;
    }
    @Override
    public Optional<ProcurementReport> findByReportId(String reportId) {
        return springDataReportAnalysisRepository.findByReportId(reportId).map(reportAnalysisEntity -> toModel(reportAnalysisEntity));
    }
    @Override
    public void deleteByReportId(String reportId) {
        springDataReportAnalysisRepository.deleteByReportId(reportId);
    }
    @Override
    public List<ProcurementReport> findAll() {
        return springDataReportAnalysisRepository.findAll().stream().map(ProcurementReportMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public ProcurementReport save(ProcurementReport reportAnalysis) {
        ProcurementReportEntity reportAnalysisEntity = toEntity(reportAnalysis);
        ProcurementReportEntity savedReport = springDataReportAnalysisRepository.save(reportAnalysisEntity);
        return toModel(savedReport);
    }
    @Override
    public Optional<ProcurementReport> findById(Long id) {
        return springDataReportAnalysisRepository.findById(id)
                .map(ProcurementReportMapper::toModel);
    }
    @Override
    public void deleteById(Long id) {
        springDataReportAnalysisRepository.deleteById(id);
    }
    @Override
    public void deleteAll() {
        springDataReportAnalysisRepository.deleteAll();
    }
}
