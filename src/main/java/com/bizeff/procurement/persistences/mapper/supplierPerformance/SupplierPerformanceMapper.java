package com.bizeff.procurement.persistences.mapper.supplierPerformance;

import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierPerformanceEntity;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierQualitativePerformanceMetricsEntity;
import com.bizeff.procurement.persistences.entity.supplierPerformance.SupplierQuantitativePerformanceMetricsEntity;
import com.bizeff.procurement.persistences.mapper.suppliermanagement.SupplierMapper;

public class SupplierPerformanceMapper {
    public static SupplierPerformanceEntity toEntity(SupplierPerformance supplierPerformance){
        if (supplierPerformance == null)return null;
        SupplierPerformanceEntity entity = new SupplierPerformanceEntity();
        if (supplierPerformance.getId() != null){
            entity.setId(supplierPerformance.getId());
        }
        entity.setSupplier(SupplierMapper.toEntity(supplierPerformance.getSupplier()));
        entity.setSupplierQuantitativePerformaneMetricsEntity(toEntity(supplierPerformance.getQuantitativePerformanceMetrics()));
        entity.setSupplierQualitativePerformanceMetricsEntity(toEntity(supplierPerformance.getQualitativePerformanceMetrics()));
        entity.setPerformanceRate(supplierPerformance.getSupplierPerformanceRate());
        entity.setEvaluationDate(supplierPerformance.getEvaluationDate());
        entity.setEvaluatorComments(supplierPerformance.getEvaluatorComments());

        return entity;
    }
    public static SupplierPerformance toModel(SupplierPerformanceEntity entity){
        if (entity == null) return null;

        SupplierPerformance model = new SupplierPerformance();
        model.setId(entity.getId());
        model.setSupplier(SupplierMapper.toModel(entity.getSupplier()));
        model.setQualitativePerformanceMetrics(toModel(entity.getSupplierQualitativePerformanceMetricsEntity()));
        model.setQuantitativePerformanceMetrics(toModel(entity.getSupplierQuantitativePerformaneMetricsEntity()));
        model.setSupplierPerformanceRate(entity.getPerformanceRate());
        model.setEvaluationDate(entity.getEvaluationDate());
        model.setEvaluatorComments(entity.getEvaluatorComments());

        return model;
    }

    public static SupplierQuantitativePerformanceMetricsEntity toEntity(SupplierQuantitativePerformanceMetrics model){
        if (model == null) return null;
        SupplierQuantitativePerformanceMetricsEntity entity = new SupplierQuantitativePerformanceMetricsEntity();
        entity.setDeliveryRate(model.getDeliveryRate());
        entity.setDefectsRate(model.getDefectsRate());
        entity.setAccuracyRate(model.getAccuracyRate());
        entity.setComplianceRate(model.getComplianceRate());
        entity.setCostEfficiency(model.getCostEfficiency());
        entity.setCorrection(model.getCorrectionRate());
        entity.setCanceledOrders(model.getCanceledOrders());
        return entity;
    }
    public static SupplierQualitativePerformanceMetricsEntity toEntity(SupplierQualitativePerformanceMetrics model){
        if (model == null) return null;
        SupplierQualitativePerformanceMetricsEntity entity = new SupplierQualitativePerformanceMetricsEntity();
        entity.setContractAdherence(model.getContractAdherence());
        entity.setTechnicalExpertise(model.getTechnicalExpertise());
        entity.setInnovation(model.getInnovation());
        entity.setQualityProducts(model.getQualityProducts());
        entity.setResponsiveness(model.getResponsiveness());
        entity.setCustomerSatisfaction(model.getCustomerSatisfaction());

        return entity;
    }
    public static SupplierQualitativePerformanceMetrics toModel(SupplierQualitativePerformanceMetricsEntity entity){
        if (entity == null) return null;
        SupplierQualitativePerformanceMetrics model = new SupplierQualitativePerformanceMetrics();
        model.setContractAdherence(entity.getContractAdherence());
        model.setTechnicalExpertise(entity.getTechnicalExpertise());
        model.setInnovation(entity.getInnovation());
        model.setQualityProducts(entity.getQualityProducts());
        model.setResponsiveness(entity.getResponsiveness());
        model.setCustomerSatisfaction(entity.getCustomerSatisfaction());

        return model;
    }
    public static SupplierQuantitativePerformanceMetrics toModel(SupplierQuantitativePerformanceMetricsEntity entity){
        if (entity == null)return null;
        SupplierQuantitativePerformanceMetrics model = new SupplierQuantitativePerformanceMetrics();

        model.setDeliveryRate(entity.getDeliveryRate());
        model.setDefectsRate(entity.getDefectsRate());
        model.setAccuracyRate(entity.getAccuracyRate());
        model.setComplianceRate(entity.getComplianceRate());
        model.setCostEfficiency(entity.getCostEfficiency());
        model.setCorrection(entity.getCorrectionRate());
        model.setCanceledOrders(entity.getCanceledOrders());

        return model;
    }
}
