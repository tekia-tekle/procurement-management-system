package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.AddSupplierInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.UpdateSupplierContactDetailInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.ViewSupplierPerformanceReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.AddSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.UpdateSupplierContactDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.ViewSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.InventoryRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataSupplierManagementRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.suppliermanagement.JpaInventoryRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.suppliermanagement.JpaSupplierManagementRepository;
import com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;
import com.bizeff.procurement.usecases.suppliermanagement.AddSupplierUseCase;
import com.bizeff.procurement.usecases.suppliermanagement.UpdateSupplierDetailUseCase;
import com.bizeff.procurement.usecases.suppliermanagement.ViewSupplierPerformancesUseCase;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.AddedSupplierViewModel;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.UpdatedVendorDetailViewModel;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.ViewSupplierPerformanceViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SupplierConfiguration {
    @Bean
    public SupplierRepository supplierRepository(SpringDataSupplierManagementRepository springDataSupplierManagementRepository){
        return new JpaSupplierManagementRepository(springDataSupplierManagementRepository);
    }
    @Bean
    public SupplierMaintenanceService supplierMaintenanceService(){
        return new SupplierMaintenanceService();
    }
    @Bean
    public AddSupplierInputBoundary addSupplierInputBoundary(SupplierRepository supplierRepository,
                                                             SupplierMaintenanceService supplierMaintenanceService,
                                                             AddSupplierOutputBoundary addSupplierOutputBoundary) {
        return new AddSupplierUseCase(
                supplierRepository,
                supplierMaintenanceService,
                addSupplierOutputBoundary);
    }
    @Bean
    public AddedSupplierViewModel addedSupplierViewModel(){
        return new AddedSupplierViewModel();
    }
    @Bean
    public ViewSupplierPerformanceViewModel viewSupplierPerformanceViewModel(){
        return new ViewSupplierPerformanceViewModel();
    }
    @Bean
    public UpdatedVendorDetailViewModel updatedVendorDetailViewModel(){return new UpdatedVendorDetailViewModel();}
    @Bean
    public SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices(){
        return new SupplierPerformanceEvaluationServices();
    }
    @Bean
    public ViewSupplierPerformanceReportInputBoundary viewSupplierPerformanceReportInputBoundary(SupplierRepository supplierRepository,
                                                                                                 SupplierPerformanceRepository supplierPerformanceRepository,
                                                                                                 SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices,
                                                                                                 ViewSupplierPerformanceReportOutputBoundary viewSupplierPerformanceReportOutputBoundary){

        return new ViewSupplierPerformancesUseCase(supplierRepository, supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

    }
    @Bean
    public UpdateSupplierContactDetailInputBoundary updateSupplierContactDetailInputBoundary(SupplierRepository supplierRepository,
                                                                                             SupplierMaintenanceService supplierMaintenanceService,
                                                                                             UpdateSupplierContactDetailOutputBoundary updateSupplierContactDetailOutputBoundary){

        return new UpdateSupplierDetailUseCase(
                supplierRepository,
                supplierMaintenanceService,
                updateSupplierContactDetailOutputBoundary);
    }
    @Bean
    public InventoryRepository inventoryRepository(SpringDataInventoryRepository springDataInventoryRepository){
        return new JpaInventoryRepository(springDataInventoryRepository);
    }
}
