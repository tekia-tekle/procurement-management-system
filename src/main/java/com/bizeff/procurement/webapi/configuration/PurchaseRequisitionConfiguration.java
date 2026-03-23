package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.CreateRequisitionInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.TrackRequisitionInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaserequisition.ViewPendingRequisitionsInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.CreateRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.TrackRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.ViewPendingRequisitionsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.InventoryRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.*;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition.*;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionCatalogValidationService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionMaintenancesService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionTrackingStatusService;
import com.bizeff.procurement.usecases.purchaserequisition.CreatePurchaseRequisitionUseCase;
import com.bizeff.procurement.usecases.purchaserequisition.TrackPurchaseRequisitionUseCase;
import com.bizeff.procurement.usecases.purchaserequisition.ViewPendingRequisitionsUseCase;
import com.bizeff.procurement.webapi.presenter.purchaserequisition.CreatePurchaseRequisitionPresenter;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.CreatePurchaseRequisitionViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.TrackPurchaseRequisitionViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PurchaseRequisitionConfiguration {

    @Bean
    public PurchaseRequisitionMaintenancesService purchaseRequisitionMaintenancesService(){
        return new PurchaseRequisitionMaintenancesService();
    }
    @Bean
    public CreatePurchaseRequisitionViewModel createPurchaseRequisitionViewModel(){
        return new CreatePurchaseRequisitionViewModel();
    }
    @Bean
    public CreateRequisitionOutputBoundary createRequisitionOutputBoundary(CreatePurchaseRequisitionViewModel createPurchaseRequisitionViewModel){
        return new CreatePurchaseRequisitionPresenter(createPurchaseRequisitionViewModel);
    }
    @Bean
    public CreateRequisitionInputBoundary createRequisitionInputBoundary(PurchaseRequisitionRepository purchaseRequisitionRepository,
                                                                         UserRepository userRepository,
                                                                         DepartmentRepository departmentRepository,
                                                                         CostCenterRepository costCenterRepository,
                                                                         SupplierRepository supplierRepository,
                                                                         PurchaseRequisitionMaintenancesService purchaseRequisitionMaintenancesService,
                                                                         CreateRequisitionOutputBoundary createRequisitionOutputBoundary){
        return new CreatePurchaseRequisitionUseCase(
                purchaseRequisitionRepository,
                userRepository,
                departmentRepository,
                costCenterRepository,
                supplierRepository,
                purchaseRequisitionMaintenancesService,
                createRequisitionOutputBoundary);
    }
    @Bean
    public PurchaseRequisitionCatalogValidationService purchaseRequisitionCatalogValidationService(){
        return new PurchaseRequisitionCatalogValidationService();
    }
    @Bean
    public PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusService(){
        return new PurchaseRequisitionTrackingStatusService();
    }

    @Bean
    public TrackPurchaseRequisitionViewModel trackPurchaseRequisitionViewModel(){
        return new TrackPurchaseRequisitionViewModel();
    }
    @Bean
    public TrackRequisitionInputBoundary trackRequisitionInputBoundary(
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            PurchaseRequisitionCatalogValidationService purchaseRequisitionCatalogValidationService,
            PurchaseRequisitionTrackingStatusService trackingStatusService,
            TrackRequisitionOutputBoundary trackRequisitionOutputBoundary,
            InventoryRepository inventoryRepository)
    {

        return new TrackPurchaseRequisitionUseCase(
                purchaseRequisitionRepository,
                purchaseRequisitionCatalogValidationService,
                trackingStatusService,trackRequisitionOutputBoundary,
                inventoryRepository);
    }
    @Bean
    public ViewPendingRequisitionsInputBoundary viewPendingRequisitionsInputBoundary(
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            ViewPendingRequisitionsOutputBoundary viewPendingRequisitionsOutputBoundary,
            PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusService)
    {

        return new ViewPendingRequisitionsUseCase(
                purchaseRequisitionRepository,
                viewPendingRequisitionsOutputBoundary,
                purchaseRequisitionTrackingStatusService);
    }
    @Bean
    public UserRepository userRepository(SpringDataUserRepository springDataUserRepository){

        return new JpaUserRepository(springDataUserRepository);
    }
    @Bean
    public RequestedItemRepository requestedItemRepository(SpringDataRequestedItemRepository springDataRequestedItemRepository,
                                                           SpringDataInventoryRepository springDataInventoryRepository){

        return new JpaRequestedItemRepository(springDataRequestedItemRepository,springDataInventoryRepository);
    }
    @Bean
    public DepartmentRepository departmentRepository(SpringDataDepartmentRepository springDataDepartmentRepository){

        return new JpaDepartmentRepository(springDataDepartmentRepository);
    }
    @Bean
    public CostCenterRepository costCenterReporitory(SpringDataCostCenterRepository springDataCostCenterRepository)
    {

        return new JpaCostCenterRepository(springDataCostCenterRepository);
    }
    @Bean
    public BudgetRepository budgetRepository(SpringDataBudgetRepository springDataBudgetRepository){

        return new JpaBudgetRepository(springDataBudgetRepository);
    }
}
