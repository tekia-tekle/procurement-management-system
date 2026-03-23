package com.bizeff.procurement.services.supplymanagement;

import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.models.supplymanagement.*;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.*;

public class SupplierPerformanceEvaluationServices {
    private Map<String, Supplier> supplierMap = new HashMap<>();
    private Map<String, List<SupplierPerformance>> supplierPerformances = new HashMap<>();

    public Supplier addSupplier(String supplierName,
                                     String supplierCategory,
                                     String taxIdentificationNumber,
                                     String registrationNumber,
                                     SupplierContactDetail supplierContactDetail,
                                     List<SupplierPaymentMethod> supplierPaymentMethods,
                                     List<Inventory> existedItems,
                                     LocalDate registrationDate){

        validateString(supplierName, "SupplierModel name");
        validateString(supplierCategory, "SupplierModel Category");
        validateString(taxIdentificationNumber, "Tax Identification Number");
        validateString(registrationNumber, "Registration Number");
        validateSupplierContactDetail(supplierContactDetail);
        validateItems(existedItems);
        validateSupplierPaymentMethod(supplierPaymentMethods);
        validateDate(registrationDate,"Registration Date");

        Supplier supplierModel = new Supplier(
                supplierName,
                supplierCategory,
                taxIdentificationNumber,
                registrationNumber,
                supplierContactDetail,
                supplierPaymentMethods,
                existedItems,
                registrationDate
        );

        if (supplierMap.containsKey(supplierModel.getSupplierId())){
            throw new IllegalArgumentException("duplication of supplierId is not allowed.");
        }

        supplierMap.put(supplierModel.getSupplierId(), supplierModel);

        return supplierModel;
    }
    public List<SupplierPerformance> addSupplierPerformance(String supplierId,
                                                            SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics,
                                                            SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics,
                                                            LocalDate evaluationDate){
        Supplier supplier = getSupplierById(supplierId);
        if (!supplier.isActive()){
            throw new IllegalArgumentException("SupplierModel is not Active, so that we can't evaluate the performance since it is inactive.");
        }
        if (quantitativePerformanceMetrics == null){
            throw new IllegalArgumentException("quantitative performance metrics for supplierModel can't be null.");
        }
        if (qualitativePerformanceMetrics == null){
            throw new IllegalArgumentException("qualitative performance metrics for supplierModel can't be null.");
        }
        if (evaluationDate == null){
            throw new IllegalArgumentException("evaluation time is required when we are evaluating");
        }
        SupplierPerformance newPerformance = new SupplierPerformance(supplier, quantitativePerformanceMetrics, qualitativePerformanceMetrics, evaluationDate);
        validatePerformanceData(newPerformance);

        List<SupplierPerformance> performances = supplierPerformances.getOrDefault(supplierId, new ArrayList<>());

        // Check for duplicate
        boolean isDuplicate = performances.stream()
                .anyMatch(p -> p.getEvaluationDate().equals(evaluationDate)
                        && p.getQuantitativePerformanceMetrics().equals(quantitativePerformanceMetrics)
                        && p.getQualitativePerformanceMetrics().equals(qualitativePerformanceMetrics));

        if (isDuplicate) {
            throw new IllegalArgumentException("we can't re-evaluate with the same value and at the same time.");
        }

        performances.add(newPerformance);
        supplierPerformances.put(supplierId, performances);
        setSupplierPerformanceRate(supplierId);
        return performances;
    }
    public void setSupplierPerformanceRate(String supplierId){
        validateString( supplierId, "SupplierId");
        List<SupplierPerformance> performances = getPerfromanceBySupplierId(supplierId);
        for (SupplierPerformance supplierPerformance: performances){
            double performanceScore = calculateSupplierPerformanceScore(supplierPerformance);
            String comment;
            if (performanceScore < 50.0){
                supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.POOR);
                comment = "Performance is poor. Major improvements needed.";
            }
            else if (performanceScore >= 50.0 && performanceScore < 60.0){
                supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.BELOW_AVERAGE);
                comment = "Below average performance. Needs significant improvement.";
            }
            else if (performanceScore >= 60.0 && performanceScore < 70.0){
                supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.AVERAGE);
                comment = "Acceptable performance, but there's room for growth.";
            }
            else if (performanceScore >= 70.0 && performanceScore < 80.0){
                supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.GOOD);
                comment = "Good performance. Continue maintaining quality.";
            }
            else if (performanceScore >= 80.0 && performanceScore < 90.0){
                supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.VERY_GOOD);
                comment = "Very good performance. Well done.";
            }
            else {
                supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
                comment = "Excellent performance. Preferred vendor candidate.";
            }
            supplierPerformance.setEvaluatorComments(comment);
        }
    }
    public double calculateSupplierPerformanceScore(SupplierPerformance supplierPerformance){
        if (supplierPerformance == null){
            throw new NullPointerException("supplierPerformance can't be null.");
        }
        double score = 0.0;
        score = getQuantitativePerformanceScore(supplierPerformance)+getQualitativePerformanceScore(supplierPerformance);
        return roundToTwoDecimalPlaces(score);
    }
    public double getQualitativePerformanceScore(SupplierPerformance performance){
        double qualitativePerformanceScore = 0.0;
        if (performance == null){
            throw new IllegalArgumentException("supplier performance evaluator metrics are not allocated for the supplier.");
        }
        if (performance.getQualitativePerformanceMetrics() == null){
            throw new IllegalArgumentException("we can't calculate qualitative performance score if the qualitative metrics is null");
        }
        qualitativePerformanceScore += (performance.getQualitativePerformanceMetrics().getContractAdherence() + performance.getQualitativePerformanceMetrics().getTechnicalExpertise() + performance.getQualitativePerformanceMetrics().getInnovation() + performance.getQualitativePerformanceMetrics().getQualityProducts() +
                performance.getQualitativePerformanceMetrics().getResponsiveness() + performance.getQualitativePerformanceMetrics().getCustomerSatisfaction()) / 6;
        return roundToTwoDecimalPlaces(qualitativePerformanceScore);
    }
    public double getQuantitativePerformanceScore(SupplierPerformance supplierPerformance){

        double quntitativePerformanceScore = 0.0;

        if (supplierPerformance == null){
            throw new IllegalArgumentException("supplier performance evaluator metrics are not allocated for the supplier.");
        }
        quntitativePerformanceScore += (supplierPerformance.getQuantitativePerformanceMetrics().getDeliveryRate() * 0.3) + (supplierPerformance.getQuantitativePerformanceMetrics().getAccuracyRate() * 0.2) + (supplierPerformance.getQuantitativePerformanceMetrics().getComplianceRate() * 0.2) +
                (supplierPerformance.getQuantitativePerformanceMetrics().getCostEfficiency() * 0.2) + (supplierPerformance.getQuantitativePerformanceMetrics().getCorrectionRate() * 0.1) - (supplierPerformance.getQuantitativePerformanceMetrics().getDefectsRate() * 0.2) - (supplierPerformance.getQuantitativePerformanceMetrics().getCanceledOrders() * 0.1);

        return roundToTwoDecimalPlaces(quntitativePerformanceScore);
    }
    public double averagePerformanceScore(String supplierId){
        validateString(supplierId,"SupplierModel Id");
        Supplier supplierModel = getSupplierById(supplierId);
        List<SupplierPerformance> supplierPerformanceList = getPerfromanceBySupplierId(supplierModel.getSupplierId());

        return supplierPerformanceList.stream().mapToDouble(this::calculateSupplierPerformanceScore).average().orElse(0);

    }
    public List<Supplier> getSupplierByPerformanceRate(SupplierPerformanceRate performanceRate) {

        List<Supplier> matchingSupplierModels = new ArrayList<>();

        // Flatten all supplier performance records
        supplierPerformances.values().stream()
                .flatMap(List::stream) // Flatten List<SupplierPerformance> to SupplierPerformance
                .filter(performance -> performanceRate.equals(performance.getSupplierPerformanceRate()))
                .map(performance -> supplierMap.get(performance.getSupplier().getSupplierId())) // get SupplierModel from supplierMap
                .filter(Objects::nonNull) // In case of inconsistency
                .distinct() // Avoid duplicates if multiple performances match
                .forEach(matchingSupplierModels::add);

        return matchingSupplierModels;
    }

    public List<Supplier> getSupplierByCategory(String supplierCategory){
        validateString(supplierCategory,"SupplierModel Category");

        return supplierMap.values().stream().filter(supplier -> supplier.getSupplierCategory().equals(supplierCategory)).collect(Collectors.toList());
    }
    public Map<String, List<Supplier>> suppliersWithSameCategory(){
        if (supplierMap.isEmpty()){
            throw new IllegalArgumentException("there is no supplier that is registered before.");
        }
        return supplierMap.values().stream().collect(Collectors.groupingBy(Supplier::getSupplierCategory));
    }
    public List<Supplier> sortSupplierByLatestPerformanceScore(){
        if (supplierMap.isEmpty()){
            throw new IllegalArgumentException("supplier must be existed to calculate performance score.");
        }
        if (supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("SupplierModel must be evaluated to sort based on performance score.");
        }

        Map<Supplier,Double>supplierScoreMap = new HashMap<>();
        for (Supplier supplierModel : supplierMap.values()){
            if (!supplierPerformances.containsKey(supplierModel.getSupplierId())){
                throw new IllegalArgumentException("the supplierModel that exist in the supplierModel map is not evaluated before.");
            }
            List<SupplierPerformance> performances = supplierPerformances.get(supplierModel.getSupplierId());
            SupplierPerformance latestPerformance = performances.stream()
                    .max(Comparator.comparing(SupplierPerformance::getEvaluationDate)).orElseThrow();

            double performanceScore = calculateSupplierPerformanceScore(latestPerformance);

            supplierScoreMap.put(supplierModel,performanceScore);
        }

        return supplierScoreMap.entrySet().stream()
                .sorted((a,b)->Double.compare(b.getValue(), a.getValue())) // descending order
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Supplier>sortSupplierByAveragePerformanceScore(){
        if (supplierMap.isEmpty()){
            throw new IllegalArgumentException("there is no supplier that is registered before.");
        }
        if (supplierPerformances.isEmpty()){
            throw new IllegalArgumentException("there is no supplier that is evaluated before.");
        }
        Map<Supplier,Double> supplierAverageScoreMap = new HashMap<>();
        for (Supplier supplierModel : supplierMap.values()){
            if (!supplierPerformances.containsKey(supplierModel.getSupplierId())){
                throw new IllegalArgumentException("the supplierModel that exist in the supplierModel map is not evaluated before.");
            }
            List<SupplierPerformance> performances = supplierPerformances.get(supplierModel.getSupplierId());

            double averagePerformanceScore = averagePerformanceScore(supplierModel.getSupplierId());
            supplierAverageScoreMap.put(supplierModel,averagePerformanceScore);
        }
        return supplierAverageScoreMap.entrySet().stream()
                .sorted((a,b)->Double.compare(b.getValue(),a.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Supplier getSupplierById(String supplierId){
        validateString(supplierId,"SupplierModel Id");

        return Optional.ofNullable(getSupplierMap().get(supplierId)).orElseThrow(()->new IllegalArgumentException("there is no supplier added before with the supplier id:"+ supplierId));
    }
    public List<SupplierPerformance> getPerfromanceBySupplierId(String supplierId){
        validateString(supplierId,"SupplierModel Id");
        Supplier supplierModel = getSupplierById(supplierId);
        return Optional.ofNullable(getSupplierPerformances().get(supplierModel.getSupplierId())).orElseThrow(()->new IllegalArgumentException("there is no performance that is stored for the supplierModel: "+supplierId));
    }
    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }

    public Map<String, List<SupplierPerformance>> getSupplierPerformances() {
        return supplierPerformances;
    }
    /** Validates performance data before saving */
    private void validatePerformanceData(SupplierPerformance performance) {

        if (performance == null || performance.getSupplier() == null) {
            throw new IllegalArgumentException("Vendor performance data cannot be null.");
        }
        Supplier supplierModel = getSupplierById(performance.getSupplier().getSupplierId());
        if (!supplierModel.isActive()){
            throw new IllegalArgumentException("we can't add any supplierModel performanceEvaluatorMetrics for not active supplierModel");
        }
        if (performance.getQuantitativePerformanceMetrics() == null && performance.getQualitativePerformanceMetrics() == null){
            throw new IllegalArgumentException("the supplierModel must have at least one not null performance indicator.");
        }
    }
    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
