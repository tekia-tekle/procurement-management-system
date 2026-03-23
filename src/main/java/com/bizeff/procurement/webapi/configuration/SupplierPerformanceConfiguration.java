package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.supplierperformancetracking.ViewSupplierPerformanceMetricsInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.ViewSupplierPerformanceMerticsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.persistences.jparepository.supplierperformance.SpringDataSupplierPerformanceRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.supplierPerformance.JpaSupplierPerformanceRepository;
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;
import com.bizeff.procurement.services.supplierperformancetracking.StoringSupplierPerformanceHistoryService;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;
import com.bizeff.procurement.usecases.supplierperformancetracking.EvaluateSupplierForContractRenewalUseCase;
import com.bizeff.procurement.usecases.supplierperformancetracking.GenerateSupplierPerformanceReportUseCase;
import com.bizeff.procurement.usecases.supplierperformancetracking.ViewSupplierPerformanceMetricsUseCase;
import com.bizeff.procurement.webapi.presenter.supplierPerformancetracking.EvaluateSupplierForContractRenewalPresenter;
import com.bizeff.procurement.webapi.presenter.supplierPerformancetracking.GenerateSupplierPerformanceReportPresenter;
import com.bizeff.procurement.webapi.presenter.supplierPerformancetracking.ViewSupplierPerformanceMetricsPresenter;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.EvaluatedSupplierForContractRenewalViewModel;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.GeneratedSupplierPerformanceReportViewModel;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.ViewSupplierPerformanceMetricsViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SupplierPerformanceConfiguration {

    @Bean
    public SupplierPerformanceRepository supplierPerformanceRepository(SpringDataSupplierPerformanceRepository supplierPerformanceRepository) {
        return new JpaSupplierPerformanceRepository(supplierPerformanceRepository);
    }

    @Bean
    public StoringSupplierPerformanceHistoryService storingSupplierPerformanceHistoryService() {
        return new StoringSupplierPerformanceHistoryService();
    }

    @Bean
    public GeneratedSupplierPerformanceReportViewModel generatedSupplierPerformanceReportViewModel() {
        return new GeneratedSupplierPerformanceReportViewModel();
    }

    @Bean
    public GenerateSupplierPerformanceReportOutputBoundary generateSupplierPerformanceReportOutputBoundary(List<GeneratedSupplierPerformanceReportViewModel> supplierPerformanceReportViewModels) {
        return new GenerateSupplierPerformanceReportPresenter(supplierPerformanceReportViewModels);
    }

    @Bean
    public GenerateSupplierPerformanceReportInputBoundary generateSupplierPerformanceReportInputBoundary(SupplierPerformanceRepository supplierPerformanceRepository,
                                                                                                         SupplierRepository supplierRepository,
                                                                                                         StoringSupplierPerformanceHistoryService storingSupplierPerformanceHistoryService,
                                                                                                         GenerateSupplierPerformanceReportOutputBoundary generateSupplierPerformanceReportOutputBoundary) {
        return new GenerateSupplierPerformanceReportUseCase(
                supplierPerformanceRepository,
                supplierRepository,
                storingSupplierPerformanceHistoryService,
                generateSupplierPerformanceReportOutputBoundary
        );
    }
    @Bean
    public EvaluatedSupplierForContractRenewalViewModel evaluatedSupplierForContractRenewalViewModel() {
        return new EvaluatedSupplierForContractRenewalViewModel();
    }
    @Bean
    public EvaluateSupplierForContractRenewalOutputBoundary evaluateSupplierForContractRenewalOutputBoundary(List<EvaluatedSupplierForContractRenewalViewModel> evaluatedSupplierForContractRenewalViewModels) {
        return new EvaluateSupplierForContractRenewalPresenter(evaluatedSupplierForContractRenewalViewModels);
    }
    @Bean
    public EvaluateSupplierForContractRenewalInputBoundary evaluateSupplierForContractRenewalInputBoundary(SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices,
                                                                                                           SupplierPerformanceRepository supplierPerformanceRepository,
                                                                                                           SupplierRepository supplierRepository,
                                                                                                           ContractRepository contractRepository,
                                                                                                           StoreAndTrackContractServices storeAndTrackContractServices,
                                                                                                           EvaluateSupplierForContractRenewalOutputBoundary evaluateSupplierForContractRenewalOutputBoundary) {
        return new EvaluateSupplierForContractRenewalUseCase(
                supplierPerformanceEvaluationServices,
                supplierPerformanceRepository,
                supplierRepository,
                contractRepository,
                storeAndTrackContractServices,
                evaluateSupplierForContractRenewalOutputBoundary);
    }

    @Bean
    public ViewSupplierPerformanceMetricsViewModel viewSupplierPerformanceMetricsViewModel() {
        return new ViewSupplierPerformanceMetricsViewModel();
    }
    @Bean
    public ViewSupplierPerformanceMerticsOutputBoundary viewSupplierPerformanceMerticsOutputBoundary(ViewSupplierPerformanceMetricsViewModel viewSupplierPerformanceMetricsViewModel) {
        return new ViewSupplierPerformanceMetricsPresenter(viewSupplierPerformanceMetricsViewModel);
    }

    @Bean
    public ViewSupplierPerformanceMetricsInputBoundary viewSupplierPerformanceMetricsInputBoundary(SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices,
                                                                                                   SupplierPerformanceRepository supplierPerformanceRepository,
                                                                                                   SupplierRepository supplierRepository,
                                                                                                   ViewSupplierPerformanceMerticsOutputBoundary viewSupplierPerformanceMerticsOutputBoundary) {
        return new ViewSupplierPerformanceMetricsUseCase(
                supplierPerformanceEvaluationServices,
                supplierPerformanceRepository,
                supplierRepository,
                viewSupplierPerformanceMerticsOutputBoundary);
    }
}
