package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.ApprovePurchaseOrderInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.CreatePurchaseOrderInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.purchaseorder.SendPurchaseOrderToSupplierInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.ApprovePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.CreatePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.SendPurchaseOrderToSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.OrderedItemRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.persistences.jparepository.purchaseOrder.SpringDataOrderedItemRepository;
import com.bizeff.procurement.persistences.jparepository.purchaseOrder.SpringDataPurchaseOrderRepository;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataPurchaseRequisitionRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataInventoryRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.purchaseorder.JpaOrderedItemRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.purchaseorder.JpaPurchaseOrderRepository;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderEnsuringServices;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderTrackingStatusService;
import com.bizeff.procurement.usecases.purchaseorder.ApprovePurchaseOrderUseCase;
import com.bizeff.procurement.usecases.purchaseorder.CreatePurchaseOrderUseCase;
import com.bizeff.procurement.usecases.purchaseorder.SendPurchaseOrderToSupplierUseCase;
import com.bizeff.procurement.webapi.presenter.purchaseorder.SendPurchaseOrderToSupplierPresenter;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.ApprovedPurchaseOrderViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.CreatePurchaseOrderViewModel;
import com.bizeff.procurement.webapi.viewmodel.purchaseorder.SendPurchaseOrderToSupplierViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PurchaseOrderConfiguration {

    @Bean
    public PurchaseOrderRepository purchaseOrderRepository(SpringDataPurchaseOrderRepository springDataPurchaseOrderRepository,
                                                           SpringDataPurchaseRequisitionRepository springDataPurchaseRequisitionRepository) {
        return new JpaPurchaseOrderRepository(springDataPurchaseOrderRepository, springDataPurchaseRequisitionRepository);
    }
    @Bean
    public CreatePurchaseOrderViewModel createPurchaseOrderViewModel(){
        return new CreatePurchaseOrderViewModel();
    }
    @Bean
    public PurchaseOrderEnsuringServices purchaseOrderEnsuringServices(){
        return new PurchaseOrderEnsuringServices();
    }
    @Bean
    public CreatePurchaseOrderInputBoundary createPurchaseOrderInputBoundary(PurchaseOrderRepository purchaseOrderRepository,
                                                                             PurchaseRequisitionRepository requisitionRepository,
                                                                             DepartmentRepository departmentRepository,
                                                                             SupplierRepository supplierRepository,
                                                                             CreatePurchaseOrderOutputBoundary createPurchaseOrderOutputBoundary,
                                                                             PurchaseOrderEnsuringServices purchaseOrderEnsuringServices){

        return new CreatePurchaseOrderUseCase(purchaseOrderRepository, requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);
    }

    @Bean
    public PurchaseOrderTrackingStatusService purchaseOrderTrackingStatusService(){
        return new PurchaseOrderTrackingStatusService();
    }

    @Bean
    public ApprovedPurchaseOrderViewModel approvedPurchaseOrderViewModel(){
        return new ApprovedPurchaseOrderViewModel();
    }
    @Bean
    public ApprovePurchaseOrderInputBoundary approvePurchaseOrderInputBoundary(PurchaseOrderRepository purchaseOrderRepository,
                                                                               DepartmentRepository departmentRepository,
                                                                               BudgetRepository budgetRepository,
                                                                               PurchaseOrderTrackingStatusService purchaseOrderTrackingStatusService,
                                                                               ApprovePurchaseOrderOutputBoundary approvePurchaseOrderOutputBoundary){
        return new ApprovePurchaseOrderUseCase(
                purchaseOrderRepository,
                departmentRepository,
                budgetRepository,
                purchaseOrderTrackingStatusService,
                approvePurchaseOrderOutputBoundary
        );
    }

    @Bean
    public SendPurchaseOrderToSupplierViewModel sendPurchaseOrderToSupplierViewModel(){
        return new SendPurchaseOrderToSupplierViewModel();
    }
    @Bean
    public SendPurchaseOrderToSupplierOutputBoundary sendPurchaseOrderToSupplierOutputBoundary(List<SendPurchaseOrderToSupplierViewModel> sendPurchaseOrderToSupplierViewModel){
        return new SendPurchaseOrderToSupplierPresenter(sendPurchaseOrderToSupplierViewModel);
    }
    @Bean
    public SendPurchaseOrderToSupplierInputBoundary sendPurchaseOrderToSupplierInputBoundary(PurchaseOrderRepository purchaseOrderRepository,
                                                                                             SupplierRepository supplierRepository,
                                                                                             PurchaseOrderEnsuringServices purchaseOrderEnsuringServices,
                                                                                             SendPurchaseOrderToSupplierOutputBoundary sendPurchaseOrderToSupplierOutputBoundary){
        return new SendPurchaseOrderToSupplierUseCase(
                purchaseOrderRepository,
                supplierRepository,
                purchaseOrderEnsuringServices,
                sendPurchaseOrderToSupplierOutputBoundary
        );
    }
    @Bean
    public OrderedItemRepository orderedItemRepository(SpringDataOrderedItemRepository springDataOrderedItemRepository,
                                                       SpringDataInventoryRepository springDataInventoryRepository){
        return new JpaOrderedItemRepository(springDataOrderedItemRepository,springDataInventoryRepository);
    }
}
