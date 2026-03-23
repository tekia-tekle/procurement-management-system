package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.TemplateBasedReportRepository;
import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;
import com.bizeff.procurement.persistences.entity.procurementreport.ReportTemplateEntity;
import com.bizeff.procurement.persistences.entity.procurementreport.TemplateBasedReportEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataReportTemplateRepository;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataTemplateBasedReportRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.TemplateBasedReportMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.bizeff.procurement.persistences.mapper.procurementreport.TemplateBasedReportMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.procurementreport.TemplateBasedReportMapper.toModel;

@Repository
public class JpaTemplateBasedReportRepository implements TemplateBasedReportRepository {
    private SpringDataTemplateBasedReportRepository springDataTemplateBasedReportRepository;
    private SpringDataReportTemplateRepository springDataReportTemplateRepository;
    public JpaTemplateBasedReportRepository(SpringDataTemplateBasedReportRepository springDataTemplateBasedReportRepository,
                                            SpringDataReportTemplateRepository springDataReportTemplateRepository) {
        this.springDataTemplateBasedReportRepository = springDataTemplateBasedReportRepository;
        this.springDataReportTemplateRepository = springDataReportTemplateRepository;
    }
    @Override
    public TemplateBasedReport save(TemplateBasedReport templateBasedReport) {
        ReportTemplateEntity reportTemplateEntity = springDataReportTemplateRepository.findById(templateBasedReport.getReportTemplate().getId()).orElseThrow(() -> new RuntimeException("Report template not found"));
        TemplateBasedReportEntity templateBasedReportEntity = toEntity(templateBasedReport, reportTemplateEntity);
        TemplateBasedReportEntity savedTemplateBasedReportEntity = springDataTemplateBasedReportRepository.save(templateBasedReportEntity);
        return toModel(savedTemplateBasedReportEntity);
    }

    @Override
    public Optional<TemplateBasedReport> findById(Long id) {
        return springDataTemplateBasedReportRepository.findById(id).map(TemplateBasedReportMapper::toModel);
    }

    @Override
    public void deleteById(Long id) {
        springDataTemplateBasedReportRepository.deleteById(id);
    }

    @Override
    public List<TemplateBasedReport> findAll() {
        return springDataTemplateBasedReportRepository.findAll().stream().map(TemplateBasedReportMapper::toModel).toList();
    }

    @Override
    public List<TemplateBasedReport> findByReportTemplateId(String templateId) {
        return springDataTemplateBasedReportRepository.findByReportTemplate_TemplateId(templateId).stream().map(TemplateBasedReportMapper::toModel).toList();
    }

    @Override
    public void deleteAll() {
        springDataTemplateBasedReportRepository.deleteAll();
    }
}
