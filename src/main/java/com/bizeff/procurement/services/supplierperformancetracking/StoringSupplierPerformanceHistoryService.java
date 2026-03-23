package com.bizeff.procurement.services.supplierperformancetracking;


import com.bizeff.procurement.models.enums.SupplierPerformanceRate;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateDate;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.validateString;

public class StoringSupplierPerformanceHistoryService {
    private Map<String, Supplier> supplierMap;
    private Map<String, List<SupplierPerformance>> supplierPerformanceHistory; // the String is supplierId.
    public StoringSupplierPerformanceHistoryService() {
        this.supplierMap = new HashMap<>();
        this.supplierPerformanceHistory = new HashMap<>();
    }
    /** Stores a performance entry for the specified supplier. */
    /** Give value for supplier to evaluate its performance.  */
    public Supplier addSupplier(Supplier supplier){
        validateSupplier(supplier);
        if (supplierMap.containsKey(supplier.getSupplierId())){
            throw new IllegalArgumentException("duplication of supplier id is not allowed.");
        }
        supplierMap.put(supplier.getSupplierId(), supplier);
        return supplier;
    }

    public List<SupplierPerformance> addSupplierPerformance(String supplierId,
                                                            SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics,
                                                            SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics,
                                                            LocalDate reviewedDate){

        Supplier supplier = getSupplierById(supplierId);
        validateQuantitativeMetrics(supplierQuantitativePerformanceMetrics);
        validateQualitativeMetrics(supplierQualitativePerformanceMetrics);
        validateDate(reviewedDate,"Reviewed Date");
        SupplierPerformance supplierPerformance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,reviewedDate);

        setSupplierPerformanceRate(supplierPerformance);

        validateSupplierPerformance(supplierPerformance);

        List<SupplierPerformance> supplierPerformanceList = supplierPerformanceHistory.computeIfAbsent(supplierId, k -> new ArrayList<>());

        supplierPerformanceList.add(supplierPerformance);

        return supplierPerformanceList;
    }

    /** Retrieves all performance history entries for the given supplier. */

    public List<SupplierPerformance> getPerformanceHistory(String supplierId) {
        validateString(supplierId,"Supplier Id");
//        validateSupplier(supplier);

        return supplierPerformanceHistory.getOrDefault(supplierId, Collections.emptyList());
    }

    /** Analyzes performance trends over time, returning review dates to performance rates. */
    public Map<LocalDate, SupplierPerformanceRate> analyzePerformanceTrends(String supplierId) {
        validateString(supplierId,"Supplier Id");

        return getPerformanceHistory(supplierId).stream()
                .collect(Collectors.toMap(
                        SupplierPerformance::getEvaluationDate,
                        SupplierPerformance::getSupplierPerformanceRate,
                        (rate1, rate2) -> rate2,
                        TreeMap::new
                ));
    }
    /** Calculates supplier score based on weighted metrics */
    public double calculateSupplierPerformanceScore(SupplierPerformance supplierPerformance) {
        double score = 0.0;
        score += getQualitativePerformanceScore(supplierPerformance) + getQuantitativePerformanceScore(supplierPerformance);
        return Math.round(score * 100.0) / 100.0;
    }


    public double getQualitativePerformanceScore(SupplierPerformance supplierPerformance) {
        double qualitativePerformanceScore = 0.0;
        qualitativePerformanceScore += (supplierPerformance.getQualitativePerformanceMetrics().getContractAdherence() + supplierPerformance.getQualitativePerformanceMetrics().getTechnicalExpertise() + supplierPerformance.getQualitativePerformanceMetrics().getInnovation() + supplierPerformance.getQualitativePerformanceMetrics().getQualityProducts() +
                supplierPerformance.getQualitativePerformanceMetrics().getResponsiveness() + supplierPerformance.getQualitativePerformanceMetrics().getCustomerSatisfaction()) / 6;
        return Math.round(qualitativePerformanceScore * 100.0) / 100.0;
    }

    public double getQuantitativePerformanceScore(SupplierPerformance supplierPerformance) {
        double quntitativePerformanceScore = 0.0;
        quntitativePerformanceScore += (supplierPerformance.getQuantitativePerformanceMetrics().getDeliveryRate() * 0.3) + (supplierPerformance.getQuantitativePerformanceMetrics().getAccuracyRate() * 0.2) + (supplierPerformance.getQuantitativePerformanceMetrics().getComplianceRate() * 0.2) +
                (supplierPerformance.getQuantitativePerformanceMetrics().getCostEfficiency() * 0.2) + (supplierPerformance.getQuantitativePerformanceMetrics().getCorrectionRate() * 0.1) - (supplierPerformance.getQuantitativePerformanceMetrics().getDefectsRate() * 0.2) - (supplierPerformance.getQuantitativePerformanceMetrics().getCanceledOrders() * 0.1);
        return Math.round(quntitativePerformanceScore * 100.0) / 100.0;

    }

    /** 1. Single Supplier: Sort by performance score (descending). */
    public List<SupplierPerformance> getSortedPerformanceForSupplier(Supplier supplier) {
        validateSupplier(supplier);

        return supplierPerformanceHistory.getOrDefault(supplier.getSupplierId(), Collections.emptyList()).stream()
                .sorted(Comparator.comparingDouble(this::calculateSupplierPerformanceScore).reversed())
                .collect(Collectors.toList());
    }
    public List<SupplierPerformance> getSortedPerformanceForSupplier(String supplierId) {
        validateString(supplierId,"Supplier Id");
        getSupplierById(supplierId);

        return supplierPerformanceHistory.getOrDefault(supplierId, Collections.emptyList()).stream()
                .sorted(Comparator.comparingDouble(this::calculateSupplierPerformanceScore).reversed())
                .collect(Collectors.toList());
    }


    /** 2. Single Supplier: Sort by review date only (ascending) */
    public List<SupplierPerformance> getPerformanceSortedByReviewDate(String supplierId) {
        validateString(supplierId,"Supplier Id");
        getSupplierById(supplierId);
        return supplierPerformanceHistory.getOrDefault(supplierId, Collections.emptyList()).stream()
                .sorted(Comparator.comparing(SupplierPerformance::getEvaluationDate))
                .collect(Collectors.toList());
    }
    public List<SupplierPerformance> getPerformanceSortedByReviewDate(Supplier supplier) {
        validateSupplier(supplier);

        return supplierPerformanceHistory.getOrDefault(supplier.getSupplierId(), Collections.emptyList()).stream()
                .sorted(Comparator.comparing(SupplierPerformance::getEvaluationDate))
                .collect(Collectors.toList());
    }

    /** 3. Multi-supplier: Get average score per supplier and sort descending */
    public List<Supplier> getSuppliersSortedByAveragePerformance() {
        return supplierPerformanceHistory.values().stream()
                .filter(list -> !list.isEmpty()) // skip empty lists
                .map(performanceList -> {
                    Supplier supplier = performanceList.getFirst().getSupplier();
                    double avg = calculateAverageForSupplier(performanceList);
                    return new AbstractMap.SimpleEntry<>(supplier, avg);
                })
                .sorted((e1, e2) -> Double.compare(e2.getValue(), e1.getValue())) // sort by average descending
                .map(Map.Entry::getKey) // get the Supplier only
                .collect(Collectors.toList());
    }


    /** 4. Multi-supplier: Sort by latest reviewed performance rate (most recent first) */
    public List<Supplier> getSuppliersSortedByLatestReview() {
        return supplierPerformanceHistory.values().stream()
                .filter(list -> !list.isEmpty())
                .map(performanceList -> {
                    Supplier supplier = performanceList.getFirst().getSupplier();
                    LocalDate latestReview = getLatestReviewDate(performanceList);
                    return new AbstractMap.SimpleEntry<>(supplier, latestReview);
                })
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue())) // sort by latest date descending
                .map(Map.Entry::getKey) // get Supplier only
                .collect(Collectors.toList());
    }

    /** 5. Get performances within date range (inclusive) */
    public List<SupplierPerformance> getPerformancesInDateRange(LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) {
            throw new IllegalArgumentException("Start and end dates must be valid and start must be before or equal to end.");
        }

        return supplierPerformanceHistory.values().stream()
                .flatMap(Collection::stream)
                .filter(p -> !p.getEvaluationDate().isBefore(start) && !p.getEvaluationDate().isAfter(end))
                .collect(Collectors.toList());
    }

    private double calculateAverageForSupplier(List<SupplierPerformance> performances) {
        return performances.stream()
                .mapToDouble(this::calculateSupplierPerformanceScore)
                .average()
                .orElse(0);
    }

    private LocalDate getLatestReviewDate(List<SupplierPerformance> performances) {
        return performances.stream()
                .map(SupplierPerformance::getEvaluationDate)
                .max(LocalDate::compareTo)
                .orElse(LocalDate.MIN);
    }

    /** validation methods. */

    private void validateSupplier(Supplier supplier) {
        if (supplier == null || supplier.getSupplierId() == null || supplier.getSupplierId().isBlank()) {
            throw new IllegalArgumentException("Supplier or supplier ID must not be null or blank.");
        }
    }

    private void validateSupplierPerformance(SupplierPerformance p) {
        if (p == null) throw new IllegalArgumentException("SupplierPerformance must not be null.");

        validateSupplier(p.getSupplier());

        if (p.getEvaluationDate() == null) {
            throw new IllegalArgumentException("Last performance review date must not be null.");
        }
        if (p.getSupplierPerformanceRate() == null) {
            throw new IllegalArgumentException("Performance rate must not be null.");
        }
        if (p.getQualitativePerformanceMetrics() == null || p.getQuantitativePerformanceMetrics() == null) {
            throw new IllegalArgumentException("Performance metrics must not be null.");
        }

        validateQualitativeMetrics(p.getQualitativePerformanceMetrics());
        validateQuantitativeMetrics(p.getQuantitativePerformanceMetrics());
    }

    private void validateQualitativeMetrics(SupplierQualitativePerformanceMetrics metrics) {
        if (metrics.getContractAdherence() < 1 || metrics.getContractAdherence() > 10) throw new IllegalArgumentException("Contract adherence must be between 1 and 10.");
        if (metrics.getTechnicalExpertise() < 1 || metrics.getTechnicalExpertise() > 10) throw new IllegalArgumentException("Technical expertise must be between 1 and 10.");
        if (metrics.getInnovation() < 1 || metrics.getInnovation() > 10) throw new IllegalArgumentException("Innovation must be between 1 and 10.");
        if (metrics.getQualityProducts() < 1 || metrics.getQualityProducts() > 10) throw new IllegalArgumentException("Product quality must be between 1 and 10.");
        if (metrics.getResponsiveness() < 1 || metrics.getResponsiveness() > 10) throw new IllegalArgumentException("Responsiveness must be between 1 and 10.");
        if (metrics.getCustomerSatisfaction() < 1 || metrics.getCustomerSatisfaction() > 10) throw new IllegalArgumentException("Customer satisfaction must be between 1 and 10.");
    }

    private void validateQuantitativeMetrics(SupplierQuantitativePerformanceMetrics metrics) {
        if (metrics.getDeliveryRate() < 0 || metrics.getDeliveryRate() > 100) throw new IllegalArgumentException("Delivery rate must be between 0 and 100.");
        if (metrics.getDefectsRate() < 0 || metrics.getDefectsRate() > 100) throw new IllegalArgumentException("Defects rate must be between 0 and 100.");
        if (metrics.getAccuracyRate() < 0 || metrics.getAccuracyRate() > 100) throw new IllegalArgumentException("Accuracy rate must be between 0 and 100.");
        if (metrics.getComplianceRate() < 0 || metrics.getComplianceRate() > 100) throw new IllegalArgumentException("Compliance rate must be between 0 and 100.");
        if (metrics.getCostEfficiency() < 0 || metrics.getCostEfficiency() > 100) throw new IllegalArgumentException("Cost efficiency must be between 0 and 100.");
        if (metrics.getCorrectionRate() < 0 || metrics.getCorrectionRate() > 100) throw new IllegalArgumentException("Correction rate must be between 0 and 100.");
        if (metrics.getCanceledOrders() < 0 || metrics.getCanceledOrders() > 100) throw new IllegalArgumentException("Canceled orders must be between 0 and 100.");
    }
    public void setSupplierPerformanceRate(SupplierPerformance supplierPerformance){
        double performanceRate = calculateSupplierPerformanceScore(supplierPerformance);
        String comment;
        if (performanceRate < 50.0){
            supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.POOR);
            comment = "Performance is poor. Major improvements needed.";
        }
        else if (performanceRate >= 50.0 && performanceRate < 60.0){
            supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.BELOW_AVERAGE);
            comment = "Below average performance. Needs significant improvement.";
        }
        else if (performanceRate >= 60.0 && performanceRate < 70.0){
            supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.AVERAGE);
            comment = "Acceptable performance, but there's room for growth.";
        }
        else if (performanceRate >= 70.0 && performanceRate < 80.0){
            supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.GOOD);
            comment = "Good performance. Continue maintaining quality.";
        }
        else if (performanceRate >= 80.0 && performanceRate < 90.0){
            supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.VERY_GOOD);
            comment = "Very good performance. Well done.";
        }
        else {
            supplierPerformance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
            comment = "Excellent performance. Preferred vendor candidate.";
        }
        supplierPerformance.setEvaluatorComments(comment);
    }
    public Supplier getSupplierById(String supplierId){
        validateString(supplierId,"Supplier Id");
        return Optional.ofNullable(supplierMap.get(supplierId)).orElseThrow(()->new NoSuchElementException("there is no supplier with id "+ supplierId));
    }

    public Map<String, Supplier> getSupplierMap() {
        return supplierMap;
    }
    public Map<String, List<SupplierPerformance>> getSupplierPerformanceHistory() {
        return supplierPerformanceHistory;
    }
}
