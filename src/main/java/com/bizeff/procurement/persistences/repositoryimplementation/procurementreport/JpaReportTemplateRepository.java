package com.bizeff.procurement.persistences.repositoryimplementation.procurementreport;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ReportTemplateRepository;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.persistences.entity.procurementreport.ReportTemplateEntity;
import com.bizeff.procurement.persistences.jparepository.procurementreport.SpringDataReportTemplateRepository;
import com.bizeff.procurement.persistences.mapper.procurementreport.ReportTemplateMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.procurementreport.ReportTemplateMapper.*;
@Repository
public class JpaReportTemplateRepository implements ReportTemplateRepository {
    private SpringDataReportTemplateRepository springDataReportTemplateRepository;
    public JpaReportTemplateRepository(SpringDataReportTemplateRepository springDataReportTemplateRepository){
        this.springDataReportTemplateRepository = springDataReportTemplateRepository;
    }
    @Override
    public ReportTemplate save(ReportTemplate reportTemplate) {
        ReportTemplateEntity reportTemplateEntity = toEntity(reportTemplate);
        ReportTemplateEntity savedReportTemplateEntity = springDataReportTemplateRepository.save(reportTemplateEntity);
        return toModel(savedReportTemplateEntity);
    }
    @Override
    public ReportTemplate update(ReportTemplate reportTemplate){
        if (reportTemplate.getTemplateId() == null){
            throw new IllegalArgumentException("ReportTemplate Id can't be null or Empty.");
        }
        Optional<ReportTemplateEntity> existingReportTemplateOpt = springDataReportTemplateRepository.findByTemplateId(reportTemplate.getTemplateId());
        if (!existingReportTemplateOpt.isPresent()){
            throw new IllegalArgumentException("there is no Report template with the given template id.");
        }
        ReportTemplateEntity existingReportTemplate = existingReportTemplateOpt.get();
        existingReportTemplate.setTemplateName(reportTemplate.getTemplateName());
        existingReportTemplate.setTemplateDescription(reportTemplate.getTemplateDescription());
        existingReportTemplate.setSelectedFields(reportTemplate.getSelectedFields());
        existingReportTemplate.setCreatedAt(reportTemplate.getCreatedAt());
        existingReportTemplate.setCreatedBy(UserMapper.toEntity(reportTemplate.getCreatedBy()));
        return toModel(existingReportTemplate);
    }
    @Override
    public Optional<ReportTemplate> findBytemplateId(String templateId) {
        return springDataReportTemplateRepository.findByTemplateId(templateId).map(ReportTemplateMapper::toModel);
    }
    @Override
    public void deleteBytemplateId(String templateId) {
        springDataReportTemplateRepository.deleteByTemplateId(templateId);
    }
    @Override
    public void deleteById(Long id) {
        springDataReportTemplateRepository.deleteById(id);
    }
    @Override
    public List<ReportTemplate> findAll() {
        return springDataReportTemplateRepository.findAll().stream()
                .map(ReportTemplateMapper::toModel).collect(Collectors.toList());
    }
    @Override
    public void deleteAll() {
        springDataReportTemplateRepository.deleteAll();
    }
}
