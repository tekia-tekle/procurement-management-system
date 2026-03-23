package com.bizeff.procurement.webapi.configuration;

import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.CreateContractInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.NotifyExpiringContractInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.contractsmanagement.ViewContractDetailsInputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.CreateContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.NotifyExpiringContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.ViewContractDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractFileRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.persistences.jparepository.contracts.SpringDataContractFileRepository;
import com.bizeff.procurement.persistences.jparepository.contracts.SpringDataContractRepository;
import com.bizeff.procurement.persistences.jparepository.purchaseOrder.SpringDataPurchaseOrderRepository;
import com.bizeff.procurement.persistences.jparepository.suppliermanagement.SpringDataSupplierManagementRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.contracts.JpaContractFileRepository;
import com.bizeff.procurement.persistences.repositoryimplementation.contracts.JpaContractRepository;
import com.bizeff.procurement.services.contract.AlertingContractsServices;
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;
import com.bizeff.procurement.usecases.contractmanagement.CreateContractUseCase;
import com.bizeff.procurement.usecases.contractmanagement.NotifyExpiringContractUseCase;
import com.bizeff.procurement.usecases.contractmanagement.ViewContractDetailsUseCase;
import com.bizeff.procurement.webapi.presenter.contracts.ViewContractDetailPresenter;
import com.bizeff.procurement.webapi.viewmodel.contracts.CreateContractViewModel;
import com.bizeff.procurement.webapi.viewmodel.contracts.NotifyExpiringContractViewModel;
import com.bizeff.procurement.webapi.viewmodel.contracts.ViewContractDetailsViewModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractConfiguration {
    @Bean
    public ContractRepository contractRepository(SpringDataContractRepository springDataContractRepository,
                                                 SpringDataPurchaseOrderRepository springDataPurchaseOrderRepository,
                                                 SpringDataContractFileRepository springDataContractFileRepository,
                                                 SpringDataSupplierManagementRepository springDataSupplierManagementRepository) {

        return new JpaContractRepository(
                springDataContractRepository,
                springDataPurchaseOrderRepository,
                springDataContractFileRepository,
                springDataSupplierManagementRepository);
    }

    @Bean
    public StoreAndTrackContractServices storeAndTrackContractServices(){
        return new StoreAndTrackContractServices();
    }

    @Bean
    public CreateContractViewModel createContractViewModel(){
        return new CreateContractViewModel();
    }
    @Bean
    public CreateContractInputBoundary createContractInputBoundary(ContractRepository contractRepository,
                                                                   SupplierRepository supplierRepository,
                                                                   PurchaseOrderRepository purchaseOrderRepository,
                                                                   StoreAndTrackContractServices storeAndTrackContractServices,
                                                                   CreateContractOutputBoundary createContractOutputBoundary){
        return new CreateContractUseCase(contractRepository,supplierRepository,purchaseOrderRepository,storeAndTrackContractServices,createContractOutputBoundary);
    }

    @Bean
    public AlertingContractsServices alertingContractsServices(){
        return new AlertingContractsServices();
    }

    @Bean
    public NotifyExpiringContractViewModel notifyExpiringContractViewModel(){
        return new NotifyExpiringContractViewModel();
    }
    @Bean
    public NotifyExpiringContractInputBoundary notifyExpiringContractInputBoundary(ContractRepository contractRepository,
                                                                                   AlertingContractsServices alertingContractsServices,
                                                                                   NotifyExpiringContractOutputBoundary notifyExpiringContractOutputBoundary){

        return new NotifyExpiringContractUseCase(contractRepository,alertingContractsServices,notifyExpiringContractOutputBoundary);
    }

    @Bean
    public ViewContractDetailsViewModel viewContractDetailsViewModel(){
        return new ViewContractDetailsViewModel();
    }
    @Bean
    public ViewContractDetailOutputBoundary viewContractDetailOutputBoundary(ViewContractDetailsViewModel viewContractDetailsViewModel){
        return new ViewContractDetailPresenter(viewContractDetailsViewModel);
    }
    @Bean
    public ViewContractDetailsInputBoundary viewContractDetailsInputBoundary(ContractRepository contractRepository,
                                                                             PurchaseOrderRepository purchaseOrderRepository,
                                                                             SupplierRepository supplierRepository,
                                                                             PurchaseRequisitionRepository requisitionRepository,
                                                                             DepartmentRepository departmentRepository,
                                                                             StoreAndTrackContractServices storeAndTrackContractServices,
                                                                             ViewContractDetailOutputBoundary viewContractDetailOutputBoundary){

        return new ViewContractDetailsUseCase(
                contractRepository,
                purchaseOrderRepository,
                supplierRepository,
                requisitionRepository,
                departmentRepository,
                storeAndTrackContractServices,
                viewContractDetailOutputBoundary);
    }
    @Bean
    public ContractFileRepository contractFileRepository(SpringDataContractFileRepository contractFileRepository) {
        return new JpaContractFileRepository(contractFileRepository);
    }
}
