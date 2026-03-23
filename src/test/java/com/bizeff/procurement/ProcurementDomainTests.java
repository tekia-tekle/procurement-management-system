package com.bizeff.procurement;

import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.*;
import com.bizeff.procurement.models.invoicepaymentreconciliation.*;
import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.models.procurementreport.TemplateBasedReport;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;
import com.bizeff.procurement.models.supplyperformancemanagement.ProcurementActivity;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;
import com.bizeff.procurement.services.contract.AlertingContractsServices;
import com.bizeff.procurement.services.contract.EnforceContractsTermsServices;
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;
import com.bizeff.procurement.services.invoicepaymentreconciliation.ContractPaymentTermsEnforcementService;
import com.bizeff.procurement.services.invoicepaymentreconciliation.InvoiceReconciliationServices;
import com.bizeff.procurement.services.invoicepaymentreconciliation.PaymentReconciliationMaintainingService;
import com.bizeff.procurement.services.procurementreport.ProcurementReportCustomizationServices;
import com.bizeff.procurement.services.procurementreport.ProcurementReportDataAggregationServices;
import com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderEnsuringServices;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderGeneratingService;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderTrackingStatusService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionCatalogValidationService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionMaintenancesService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionTrackingStatusService;
import com.bizeff.procurement.services.supplierperformancetracking.LinkingSupplierPerformanceMetricsService;
import com.bizeff.procurement.services.supplierperformancetracking.StoringSupplierPerformanceHistoryService;
import com.bizeff.procurement.services.supplymanagement.SupplierLinkingService;
import com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static com.bizeff.procurement.models.enums.PaymentMethodType.*;
import static com.bizeff.procurement.models.enums.PaymentTerms.NET_30;
import static com.bizeff.procurement.models.enums.RequisitionStatus.*;
import static org.junit.jupiter.api.Assertions.*;
public class ProcurementDomainTests {
    private PurchaseRequisitionMaintenancesService purchaseRequisitionMaintenancesService;
    private PurchaseRequisitionCatalogValidationService purchaseRequisitionCatalogValidationService;
    private PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusService;
    private SupplierMaintenanceService supplierMaintenanceService;
    private SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices;
    private SupplierLinkingService supplierLinkingService;
    private PurchaseOrderGeneratingService purchaseOrderGeneratingService;
    private PurchaseOrderTrackingStatusService purchaseOrderTrackingStatusService;
    private PurchaseOrderEnsuringServices purchaseOrderEnsuringServices;
    private StoreAndTrackContractServices storeAndTrackContractServices;
    private EnforceContractsTermsServices enforceContractsTermsServices;
    private AlertingContractsServices alertingContractsServices;
    private InvoiceReconciliationServices invoiceReconciliationServices;
    private PaymentReconciliationMaintainingService paymentReconciliationMaintainingService;
    private ContractPaymentTermsEnforcementService contractPaymentTermsEnforcementService;
    private StoringSupplierPerformanceHistoryService storingSupplierPerformanceHistoryService;
    private ProcurementReportGenerationServices procurementReportGeneratingService;
    private ProcurementReportDataAggregationServices procurementReportDataAggregationService;
    private ProcurementReportCustomizationServices procurementReportCustomizationService;
    private LinkingSupplierPerformanceMetricsService linkingSupplierPerformanceMetricsService;

    @Test
    public void testCreateRequisition_Success() {
        //Arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");
        Inventory inventory = new Inventory("ITEM-001", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory, 3);

        //Act.
        PurchaseRequisition result = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        // Assert.
        assertNotNull(result);
        assertEquals("REQ-001", result.getRequisitionNumber());
        assertEquals(requester.getFullName(),result.getRequestedBy().getFullName());
        assertEquals(department.getDepartmentId(),result.getDepartment().getDepartmentId());
        assertEquals(BigDecimal.valueOf(30000.0), result.getTotalEstimatedCosts());
    }
    @Test
    public void testCreateRequisition_WithEmptyRequisitionNumber_Unsuccessfully() {
        // arrange

        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");
        Inventory inventory = new Inventory("ITEM-001", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,3);

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.createPurchaseRequisition(" ", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work"));

    }
    @Test
    public void testCreateRequisition_WithEmptyItems_Unsuccessfully() {
        // arrange.
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");

        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, List.of(), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work"));
    }
    @Test
    public void testCreateRequisition_with_NegativeQuantity_Unsuccessfully() {
        //arrange.
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");

        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory invetoyry = new Inventory("ITEM-001", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem items = new RequestedItem(invetoyry,-3);


        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(items), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work"));
    }
    @Test
    public void testCreateRequisition_forExpiredItems_Unsuccessfully() {
        // arrange.
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory inventory = new Inventory("ITEM-001", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().minusMonths(2), "Intel Core i7");
        RequestedItem items = new RequestedItem(inventory,3);

        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(items),PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work"));
    }
    @Test
    public void testCreateRequisition_WithExpectedDeliveryDateBeforeRequestedDate_Unsuccessfully() {
        // arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");

        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory inventory = new Inventory("ITEM-001", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,3);

        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().minusMonths(1), "Need for project work"));
    }
    @Test
    public void testUpdateRequisition_WithValidfield_Successfully() {
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");



        Inventory inventoryOne = new Inventory("ITEM-001", "Laptop", 2, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        Inventory inventoryTwo = new Inventory("ITEM-002", "Desktop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");

        RequestedItem item1 = new RequestedItem(inventoryOne,1);
        RequestedItem item2 = new RequestedItem(inventoryTwo,5);


        PurchaseRequisition createdRequisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, List.of(item1), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        PurchaseRequisition updatedReq = purchaseRequisitionMaintenancesService.updateRequisition(createdRequisition.getRequisitionId(),"REQ-001", requester, department, costCenter, List.of(item1,item2), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now());

        assertEquals("Updated Justification", updatedReq.getJustification());
        assertEquals(2, updatedReq.getItems().size());
        assertEquals(PriorityLevel.CRITICAL, updatedReq.getPriorityLevel());
    }
    @Test
    public void testUpdateRequisition_ForNonExistingRequisition_ShouldThrowException() { // since there is no requisition created before.
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        List<RequestedItem> requestedItem = List.of(new RequestedItem(inventory,1));

        String reqId = IdGenerator.generateId("REQ");
        assertThrows(NoSuchElementException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(reqId,"REQ-001", requester, department, costCenter, requestedItem, PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));
    }
    @Test
    public void testUpdatingRequisition_WithNullId_ShouldThrowException() { // we can't update any requisition to null.
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem requestedItem = new RequestedItem(inventory,1);

        PurchaseRequisition requisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(requestedItem), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");//creating valid requisition.

        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(null,"REQ-001", requester, department, costCenter, List.of(requestedItem), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));// when we try to update the valid requisition to null requisition.
    }
    @Test
    public void testUpdatingRequisition_WithMissingElements_ShouldThrowException() { // we can't update Existed requisition to non-valid requisition
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,inventory.getQuantityAvailable());


        PurchaseRequisition createdRequisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");//creating valid requisition.

        String requisitionId = createdRequisition.getRequisitionId();

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(requisitionId," ", requester, department, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));
        assertThrows(NullPointerException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(requisitionId,"REQ-001", null,department, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));
        assertThrows(NullPointerException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(requisitionId,"REQ-001", requester, null, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));
        assertThrows(NullPointerException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(requisitionId,"REQ-001", requester, department, null, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(requisitionId,"REQ-001", requester, department, costCenter, List.of(), PriorityLevel.CRITICAL, LocalDate.now().plusMonths(3), "Updated Justification",null,LocalDate.now()));
    }
    @Test
    public void testUpdatingRequisition_WithDeliveryBeforeUpdatedDate_ShouldThrowException() {// since the expectedDeliveryDate of newRequisition is before the requisitionDate. so that the newRequisition is not valid requisition.
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        Inventory newInventory = new Inventory("ITEM-002", "Laptop", 2, BigDecimal.valueOf(9000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i5");
        RequestedItem newItem = new RequestedItem(newInventory,2);
        PurchaseRequisition createdRequisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(createdRequisition.getRequisitionId(), "REQ-002", requester, department, costCenter,
                List.of(newItem), PriorityLevel.HIGH, LocalDate.now().minusMonths(1), "Need for project work",null,LocalDate.now()));
    }
    @Test
    public void testUpdatingRequisition_WithDeliveryBeforRequestedDate_ShouldThrowException() {// since the expectedDeliveryDate of newRequisition is before the requisitionDate. so that the newRequisition is not valid requisition.
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        Inventory newInventory = new Inventory("ITEM-002", "Laptop", 2, BigDecimal.valueOf(9000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i5");
        RequestedItem newItem = new RequestedItem(newInventory,2);

        PurchaseRequisition createdRequisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.updateRequisition(createdRequisition.getRequisitionId(), "REQ-002", requester, department, costCenter,
                List.of(newItem), PriorityLevel.HIGH, LocalDate.now().minusDays(1), "Need for project work",null,LocalDate.now().minusDays(10)));
    }
    @Test
    public void testDeleteRequisition_Success() {
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in Malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        PurchaseRequisition createdRequisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");//creating valid requisition.

        String reqID = createdRequisition.getRequisitionId();

        purchaseRequisitionMaintenancesService.deleteRequisition(reqID);

        assertThrows(NoSuchElementException.class, () -> purchaseRequisitionMaintenancesService.getRequisitionById(reqID));
    }
    @Test
    public void testDeleteRequisition_WithNon_ExistedRequisitionID_ShouldThrowException() { //try to delete requisition with  requisitionNumber "REQ-999", but there is no requisition with that requisition number.
        // arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in Malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        // act
        PurchaseRequisition requisition = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        //assert
        assertThrows(NoSuchElementException.class, () -> purchaseRequisitionMaintenancesService.deleteRequisition("REQ-999"));
    }
    @Test
    public void testDeletingRequisition_WithNullRequisitionID_ShouldThrowException() { //try to delete requisition with null requisitionNumber.since there is no requisition with null requisitionNumber.
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in Malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);
        //act
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        //assert.
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.deleteRequisition(null));
    }
    @Test
    public void testGetRequisitionById_Successfully(){
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in Malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);
        //act
        PurchaseRequisition result = purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        //assert
        assertNotNull(result);
        assertEquals("REQ-001", purchaseRequisitionMaintenancesService.getRequisitionById(result.getRequisitionId()).getRequisitionNumber());
        assertEquals(department.getDepartmentId(), purchaseRequisitionMaintenancesService.getRequisitionById(result.getRequisitionId()).getDepartment().getDepartmentId());
        assertEquals("Tekia Tekle", purchaseRequisitionMaintenancesService.getRequisitionById(result.getRequisitionId()).getRequestedBy().getFullName());
    }
    @Test
    public void testGetRequisition_WithNOtExistedId_ThrowException(){
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in Malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);
        //act

        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");


        String Id = IdGenerator.generateId("REQ");
        //assert.
        assertThrows(NoSuchElementException.class,()->purchaseRequisitionMaintenancesService.getRequisitionById(Id));
    }
    @Test
    public void testGetRequisition_WithNullRequisitionId_ThrowException(){
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in Malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);
        //act

        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        // assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionMaintenancesService.getRequisitionById(null));
    }
    @Test
    public void testGetRequisition_ByRequisitionNumber_Success() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        //act
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter,
                List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        //assert

        PurchaseRequisition result = purchaseRequisitionMaintenancesService.getRequisitionByRequisitionNumber("REQ-001");

    }
    @Test
    public void testGetRequisitionByRequisitionNumber_NonExisting_ShouldThrowException() { // try to find requisition with non existed requisition number.
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        //act and assert.
        assertThrows(NoSuchElementException.class, () -> purchaseRequisitionMaintenancesService.getRequisitionByRequisitionNumber("REQ-999"));
    }
    @Test
    public void testGetRequisitionsByCategory_Success() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);
        //act

        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.MEDIUM, LocalDate.now().plusMonths(4), "Need for project deployment");

        List<PurchaseRequisition> results = purchaseRequisitionMaintenancesService.getRequisitionsByCategory("Electronics");

        //assert
        assertFalse(results.isEmpty());
        assertEquals(2, results.size());
    }
    @Test
    public void testGetRequisition_NonExistedCategory_shouldThrowException() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        //act

        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");


        List<PurchaseRequisition> requisitions = purchaseRequisitionMaintenancesService.getRequisitionsByCategory("Construction");

        //assert.
        assertEquals(0, requisitions.size());
    }
    @Test
    public void testSortRequisitionByLevelOfPriority_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter,  PriorityLevel.MEDIUM, LocalDate.of(2025, 5, 10), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter,  PriorityLevel.CRITICAL, LocalDate.of(2025, 4, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter,  PriorityLevel.HIGH, LocalDate.of(2025, 4, 25), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByLevelOfPriority(requisitions);

        //assert
        assertEquals(PriorityLevel.CRITICAL, sortedList.get(0).getPriorityLevel());
        assertEquals(PriorityLevel.HIGH, sortedList.get(1).getPriorityLevel());
        assertEquals(PriorityLevel.MEDIUM, sortedList.get(2).getPriorityLevel());
    }
    @Test
    public void testSortRequisitions_WithSameLevelOfPriorityAndDifferentExpectedDeliveryDate_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 5, 10), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 4, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 4, 25), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByLevelOfPriority(requisitions);

        //assert
        assertEquals("REQ002", sortedList.get(0).getRequisitionNumber());
        assertEquals("REQ003", sortedList.get(1).getRequisitionNumber());
        assertEquals("REQ001", sortedList.get(2).getRequisitionNumber());
    }
    @Test
    public void testSortRequisitions_WithMixedPriorityLevelAndExpectedDeliveryDate_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.CRITICAL, LocalDate.of(2025, 5, 10), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 4, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, PriorityLevel.CRITICAL, LocalDate.of(2025, 4, 25), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByLevelOfPriority(requisitions);

        //assert
        assertEquals("REQ003", sortedList.get(0).getRequisitionNumber());
        assertEquals("REQ001", sortedList.get(1).getRequisitionNumber());
        assertEquals("REQ002", sortedList.get(2).getRequisitionNumber());
    }
    @Test
    public void testSortRequisitions_WithSameLevelOfPriorityAndExpectedDeliveryDate_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 5, 20), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 5, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 5, 20), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByLevelOfPriority(requisitions);

        //assert
        assertEquals("REQ001", sortedList.get(0).getRequisitionNumber());
        assertEquals("REQ002", sortedList.get(1).getRequisitionNumber());
        assertEquals("REQ003", sortedList.get(2).getRequisitionNumber());
    }
    @Test
    public void testSortRequisitionByExpectedDeliveryDate_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.of(2025, 5, 10), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.CRITICAL, LocalDate.of(2025, 4, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 4, 25), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByExpectedDeliveryDate(requisitions);

        //assert
        assertEquals(LocalDate.of(2025, 4, 20), sortedList.get(0).getExpectedDeliveryDate());
        assertEquals(LocalDate.of(2025, 4, 25), sortedList.get(1).getExpectedDeliveryDate());
        assertEquals(LocalDate.of(2025, 5, 10), sortedList.get(2).getExpectedDeliveryDate());
    }
    @Test
    public void testSortRequisition_WithSameExpectedDeliveryDateAndDifferentPriorityLevel_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.of(2025, 5, 20), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.CRITICAL, LocalDate.of(2025, 5, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 5, 20), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByExpectedDeliveryDate(requisitions);

        //assert
        assertEquals("REQ002", sortedList.get(0).getRequisitionNumber());
        assertEquals("REQ003", sortedList.get(1).getRequisitionNumber());
        assertEquals("REQ001", sortedList.get(2).getRequisitionNumber());
    }
    @Test
    public void testSortRequisition_WithMixedExpectedDeliveryDateAndPriorityLevel_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        List<PurchaseRequisition> requisitions = Arrays.asList(
                new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.CRITICAL, LocalDate.of(2025, 5, 10), "Routine"),
                new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.CRITICAL, LocalDate.of(2025, 4, 20), "Emergency"),
                new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.of(2025, 5, 10), "High Importance")
        );
        for (PurchaseRequisition requisition: requisitions){
            requisition.addItem(item);
        }
        //act
        List<PurchaseRequisition> sortedList = purchaseRequisitionMaintenancesService.sortRequisitionByExpectedDeliveryDate(requisitions);

        //assert
        assertEquals("REQ002", sortedList.get(0).getRequisitionNumber());
        assertEquals("REQ001", sortedList.get(1).getRequisitionNumber());
        assertEquals("REQ003", sortedList.get(2).getRequisitionNumber());
    }
    @Test
    public void testGetRequisitionsByPriority_Successfully() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);

        //act
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(16), "Important");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusDays(20), "Emergency");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(10), "High Importance");

        List<PurchaseRequisition> result = purchaseRequisitionMaintenancesService.getRequisitionsByPriority(PriorityLevel.HIGH);

        //assert.
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals(PriorityLevel.HIGH, result.getFirst().getPriorityLevel());
    }
    @Test
    public void testGetRequisitionByPriority_returnEmpty() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);


        //act
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(16), "Important");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusDays(20), "Emergency");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(10), "High Importance");

        List<PurchaseRequisition> result = purchaseRequisitionMaintenancesService.getRequisitionsByPriority(PriorityLevel.MEDIUM);

        //assert
        assertTrue(result.isEmpty());
    }
    @Test
    public void testTrackOvertime_Success() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem requestedItem =  new RequestedItem(inventory,1);

        //act
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        purchaseRequisitionMaintenancesService.getRequisitionMap().put(requisition.getRequisitionId(),requisition );

        purchaseRequisitionMaintenancesService.getRequisitionByRequisitionNumber("REQ-001").setRequisitionDate(LocalDate.now().minusDays(45));

        List<PurchaseRequisition> results = purchaseRequisitionMaintenancesService.trackOvertime(30);

        //assert
        assertFalse(results.isEmpty());
    }
    @Test
    public void testTrackRequisitionOverTime_withInvalidThreshold_ThrowException() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem requestedItem =  new RequestedItem(inventory,1);
        //act
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        purchaseRequisitionMaintenancesService.getRequisitionMap().put(requisition.getRequisitionId(),requisition );

        purchaseRequisitionMaintenancesService.getRequisitionByRequisitionNumber("REQ-001").setRequisitionDate(LocalDate.now().minusDays(45));

        //assert
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionMaintenancesService.trackOvertime(-30));
    }
    @Test
    public void testTrackRequisitionOverTime_ReturningEmpty() {
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem requestedItem =  new RequestedItem(inventory,1);

        //act
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        purchaseRequisitionMaintenancesService.getRequisitionMap().put(requisition.getRequisitionId(),requisition );

        purchaseRequisitionMaintenancesService.getRequisitionByRequisitionNumber("REQ-001").setRequisitionDate(LocalDate.now().minusDays(5));

        List<PurchaseRequisition> results = purchaseRequisitionMaintenancesService.trackOvertime(10);

        //assert
        assertEquals(0, results.size());
    }
    @Test
    public void testGetRequisitions_WithValidSpecifiedTimeRange_Successfully(){
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);


        //act
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(16), "Important");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ002", LocalDate.now().minusDays(10), requester, department, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusDays(20), "Emergency");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(10), "High Importance");

        List<PurchaseRequisition> result = purchaseRequisitionMaintenancesService.getRequisitionInSpecifiedTimeRange(LocalDate.now().minusDays(5),LocalDate.now().plusDays(2));

        //assert
        assertEquals(2,result.size());
    }
    @Test
    public void testGetRequisitions_WithInValidTimeRange_Unsuccessfully(){
        //arrange
        purchaseRequisitionMaintenancesService = new PurchaseRequisitionMaintenancesService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software Development", "we are new department in malam Engineering plc.", budget);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventory = new Inventory("ITEM-001", "Laptop", 1, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7");
        RequestedItem item = new RequestedItem(inventory,1);


        //act
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(16), "Important");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ002", LocalDate.now().minusDays(10), requester, department, costCenter, List.of(item), PriorityLevel.CRITICAL, LocalDate.now().plusDays(20), "Emergency");
        purchaseRequisitionMaintenancesService.createPurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter, List.of(item), PriorityLevel.HIGH, LocalDate.now().plusDays(10), "High Importance");

        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionMaintenancesService.getRequisitionInSpecifiedTimeRange(LocalDate.now().plusDays(5),LocalDate.now().minusDays(12)));

    }

    /** this is the testing for validating the requisition field with the existed catalog data.*/
    @Test
    public void testAddDataToCatalog_WithValidField_Successfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory itemTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory itemThree = new Inventory("SN103","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        // act
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN103","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        Map<String,Inventory> totalCatalogData = purchaseRequisitionCatalogValidationService.getExistedCatalogMap();

        //assert
        assertEquals(3,totalCatalogData.values().size());
        assertTrue(totalCatalogData.containsKey("SN101"));
        assertTrue(totalCatalogData.containsKey("SN102"));
        assertTrue(totalCatalogData.containsKey("SN103"));
    }
    @Test
    public void testAddItemToCatalogData_WithDuplicationID_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();

        Inventory itemOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory itemTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory itemThree = new Inventory("SN102","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        //assert
        assertThrows(IllegalArgumentException.class,()-> purchaseRequisitionCatalogValidationService.addItemToCatalog("SN102","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7"));
    }
    @Test
    public void testADDItemToCatalogData_WithMissingId_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory(" ","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        //act and assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addItemToCatalog(" ","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7"));
    }
    @Test
    public void testAddItemToCatalogData_WithInvalidAvailableQuantity_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory("SN101","HP Laptops",0,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        //act and assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",0,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7"));

    }
    @Test
    public void testAddItemToCatalogData_WithInvalidUnitPrice_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory("SN101","HP Laptops",10,BigDecimal.valueOf(-10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        //act and assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",10,BigDecimal.valueOf(-10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7"));

    }
    @Test
    public void testAddExpiredItemToCatalogData_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory("SN101","HP Laptops",10,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().minusMonths(10),"new Generation,Core I7");

        //act and assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",10,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().minusMonths(10),"new Generation,Core I7"));

    }
    @Test
    public void testAddPurchaseRequisition_WithValidField_Successfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory inventoryOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryThree = new Inventory("SN103","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        RequestedItem itemOne = new RequestedItem(inventoryOne,40);
        RequestedItem itemTwo = new RequestedItem(inventoryTwo, 25);
        RequestedItem itemThree = new RequestedItem(inventoryThree,20);

        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Software development","we are developing secure, testable and maintenable software",budget);
        CostCenter costCenter = new CostCenter("BackEnd developers","we are developing backend using clean code architecture.");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"java developer");

        // act
        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryOne.getItemId(),inventoryOne.getItemName(),inventoryOne.getQuantityAvailable(),inventoryOne.getUnitPrice(),inventoryOne.getItemCategory(),inventoryOne.getExpiryDate(),inventoryOne.getSpecification());
        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryTwo.getItemId(),inventoryTwo.getItemName(),inventoryTwo.getQuantityAvailable(),inventoryTwo.getUnitPrice(),inventoryTwo.getItemCategory(),inventoryTwo.getExpiryDate(),inventoryTwo.getSpecification());
        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryThree.getItemId(),inventoryThree.getItemName(),inventoryThree.getQuantityAvailable(),inventoryThree.getUnitPrice(),inventoryThree.getItemCategory(),inventoryThree.getExpiryDate(),inventoryThree.getSpecification());

        PurchaseRequisition result = purchaseRequisitionCatalogValidationService.addPurchaseRequisition("REQ101",LocalDate.now(), requester,department, costCenter,List.of(itemOne,itemThree),PriorityLevel.HIGH,LocalDate.now().plusMonths(2),"we are new department.");

        assertEquals(2,result.getItems().size());
        assertTrue(result.getItems().containsAll(List.of(itemOne,itemThree)));
    }
    @Test
    public void testADDPurchaseRequisition_WhenCatalogDataEmpty_Unsuccessfully(){
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory inventoryOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryThree = new Inventory("SN103","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        RequestedItem itemOne = new RequestedItem(inventoryOne,40);
        RequestedItem itemTwo = new RequestedItem(inventoryTwo, 25);
        RequestedItem itemThree = new RequestedItem(inventoryThree,20);

        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Software development","we are developing secure, testable and maintenable software",budget);
        CostCenter costCenter = new CostCenter("BackEnd developers","we are developing backend using clean code architecture.");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"java developer");

        // act and assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addPurchaseRequisition("REQ101",LocalDate.now(), requester,department, costCenter,List.of(itemOne,itemThree),PriorityLevel.HIGH,LocalDate.now().plusMonths(2),"we are new department."));
    }
    @Test
    public void testADDPurchaseRequisition_WithNon_existedItem_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory inventoryOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryThree = new Inventory("SN103","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        RequestedItem itemOne = new RequestedItem(inventoryOne,40);
        RequestedItem itemTwo = new RequestedItem(inventoryTwo, 25);
        RequestedItem itemThree = new RequestedItem(inventoryThree,20);

        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Software development","we are developing secure, testable and maintenable software",budget);
        CostCenter costCenter = new CostCenter("BackEnd developers","we are developing backend using clean code architecture.");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"java developer");

        // act

        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryOne.getItemId(),inventoryOne.getItemName(),inventoryOne.getQuantityAvailable(),inventoryOne.getUnitPrice(),inventoryOne.getItemCategory(),inventoryOne.getExpiryDate(),inventoryOne.getSpecification());
        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryTwo.getItemId(),inventoryTwo.getItemName(),inventoryTwo.getQuantityAvailable(),inventoryTwo.getUnitPrice(),inventoryTwo.getItemCategory(),inventoryTwo.getExpiryDate(),inventoryTwo.getSpecification());

        //assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addPurchaseRequisition("REQ101",LocalDate.now(), requester,department, costCenter,List.of(itemOne,itemThree),PriorityLevel.HIGH,LocalDate.now().plusMonths(2),"we are new department."));
    }
    @Test
    public void testADDPurchaseRequisition_WithQuantityMoreThanExistedCatalog_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory inventoryOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryThree = new Inventory("SN103","Lenovo Laptops",20,BigDecimal.valueOf(9000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");

        RequestedItem itemOne = new RequestedItem(inventoryOne,40);
        RequestedItem itemTwo = new RequestedItem(inventoryTwo, 25);
        RequestedItem itemThree = new RequestedItem(inventoryThree,50);

        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Software development","we are developing secure, testable and maintenable software",budget);
        CostCenter costCenter = new CostCenter("BackEnd developers","we are developing backend using clean code architecture.");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"java developer");

        // act

        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryOne.getItemId(),inventoryOne.getItemName(),inventoryOne.getQuantityAvailable(),inventoryOne.getUnitPrice(),inventoryOne.getItemCategory(),inventoryOne.getExpiryDate(),inventoryOne.getSpecification());
        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryTwo.getItemId(),inventoryTwo.getItemName(),inventoryTwo.getQuantityAvailable(),inventoryTwo.getUnitPrice(),inventoryTwo.getItemCategory(),inventoryTwo.getExpiryDate(),inventoryTwo.getSpecification());

        //assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addPurchaseRequisition("REQ101",LocalDate.now(), requester,department, costCenter,List.of(itemOne,itemThree),PriorityLevel.HIGH,LocalDate.now().plusMonths(2),"we are new department."));
    }
    @Test
    public void testAddPurchaseRequisition_WithExpireDateBeforeDeliveryDate_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory inventoryOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusYears(10),"new Generation,Core I7");
        Inventory inventoryTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusDays(10),"new Generation,Core I7");

        RequestedItem itemOne = new RequestedItem(inventoryOne,40);
        RequestedItem itemTwo = new RequestedItem(inventoryTwo, 25);

        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Software development","we are developing secure, testable and maintenable software",budget);
        CostCenter costCenter = new CostCenter("BackEnd developers","we are developing backend using clean code architecture.");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"java developer");

        // act

        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryOne.getItemId(),inventoryOne.getItemName(),inventoryOne.getQuantityAvailable(),inventoryOne.getUnitPrice(),inventoryOne.getItemCategory(),inventoryOne.getExpiryDate(),inventoryOne.getSpecification());
        purchaseRequisitionCatalogValidationService.addItemToCatalog(inventoryTwo.getItemId(),inventoryTwo.getItemName(),inventoryTwo.getQuantityAvailable(),inventoryTwo.getUnitPrice(),inventoryTwo.getItemCategory(),inventoryTwo.getExpiryDate(),inventoryTwo.getSpecification());


        //assert
        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.addPurchaseRequisition("REQ101",LocalDate.now(), requester,department, costCenter,List.of(itemOne,itemTwo),PriorityLevel.HIGH,LocalDate.now().plusMonths(2),"we are new department."));
    }
    @Test
    public void testRemoveItemFromCatalog_WhenExpiredWithSpecifiedTime_Successfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusMonths(10),"new Generation,Core I7");
        Inventory itemTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusMonths(1),"new Generation,Core I7");

        // act
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusMonths(10),"new Generation,Core I7");
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusMonths(1),"new Generation,Core I7");

        Map<String, Inventory> totalCatalogData = purchaseRequisitionCatalogValidationService.getExistedCatalogMap();

        assertEquals(2,totalCatalogData.size()); //checking before removing.

        purchaseRequisitionCatalogValidationService.deleteItemFromCatalog(LocalDate.now().plusYears(1));

        assertEquals(0,totalCatalogData.size()); // checking after removing.

    }
    @Test
    public void testRemoveItemFromCatalogData_WithNoExpiringItem_Unsuccessfully(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        Inventory itemOne = new Inventory("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusMonths(10),"new Generation,Core I7");
        Inventory itemTwo = new Inventory("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusMonths(10),"new Generation,Core I7");

        // act
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN101","HP Laptops",50,BigDecimal.valueOf(10000.0),"Laptops",LocalDate.now().plusMonths(10),"new Generation,Core I7");
        purchaseRequisitionCatalogValidationService.addItemToCatalog("SN102","Dell Laptops",30,BigDecimal.valueOf(11000.0),"Laptops",LocalDate.now().plusMonths(10),"new Generation,Core I7");

        Map<String, Inventory> totalCatalogData = purchaseRequisitionCatalogValidationService.getExistedCatalogMap();

        assertEquals(2,totalCatalogData.size()); //checking before removing.

        assertThrows(IllegalArgumentException.class,()->purchaseRequisitionCatalogValidationService.deleteItemFromCatalog(LocalDate.now().plusMonths(1)));

    }

    /** this is the testing for Tracking purchase Requisition status. not approved requisition can't be passed to next execution. */
    @Test
    public void testApprovalRequisitionStatus_WithAllocatedBudget_Successfully() { // approved with sufficient Allocated Budget.
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventoryOne = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 2, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(inventoryOne,5);
        RequestedItem item2 = new RequestedItem(inventoryTwo,2);
        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);
        //act
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(20000.0));
        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);

        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId());

        //assert.
        assertEquals(APPROVED, requisition.getRequisitionStatus());
    }
    @Test
    public void testApprovalRequisitionStatus_WithHighPriorityBUtTotalCostExceedingAllocatedBudgetButCoveredByRemainingBudget_Successfully() {
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventoryOne = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 2, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(inventoryOne,5);
        RequestedItem item2 = new RequestedItem(inventoryTwo,2);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);
        //act
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);
//        purchaseRequisitionTrackingStatusService.createRequisition(requisition);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);
        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(), requisition);
        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId());

        //assert.
        assertEquals(APPROVED, requisition.getRequisitionStatus());
    }
    @Test
    public void testApprovalRequisitionStatus_WithLowPriorityRequisitionButTotalCostExceedingAllocatedBudgetAndCoveredByDepartmentBudget_Unsuccessfully() {
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory invetoryOne = new Inventory("I01", "Laptop", 8, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 8, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(invetoryOne,8);
        RequestedItem item2 = new RequestedItem(inventoryTwo,8);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);
        //act
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);
//        purchaseRequisitionTrackingStatusService.createRequisition(requisition);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);
        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId());

        //assert
        assertEquals(PENDING, requisition.getRequisitionStatus());

    }
    @Test
    public void testApprovalRequisitionStatus_WithHighPriorityAndCostExceedsAllocatedBudgetAndDepartmentTotalRemainingBudget_Unsuccessfully() {
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
        CostCenter costCenter1 = new CostCenter( "Procurement", "Manages acquisitions");
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory invetoryOne = new Inventory("I01", "Laptop", 8, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 8, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(invetoryOne,8);
        RequestedItem item2 = new RequestedItem(inventoryTwo,8);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);
        //act
        department.allocateBudgetToCostCenter(costCenter1, BigDecimal.valueOf(34000.0));
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);
//        purchaseRequisitionTrackingStatusService.createRequisition(requisition);

        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter1);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);
        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId());

        //assert.
        assertEquals(PENDING, requisition.getRequisitionStatus());
    }
    @Test
    public void testApprovalRequisitionStatus_WithLowPriorityExceedingAllocatedBudgetAndDepartmentTotalRemainingBudget_Unsuccessfully() {
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Finance", "Handles financial operations", budget);
        CostCenter costCenter1 = new CostCenter( "Procurement", "Manages acquisitions");
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");



        Inventory invetoryOne = new Inventory("I01", "Laptop", 8, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 8, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(invetoryOne,8);
        RequestedItem item2 = new RequestedItem(inventoryTwo,8);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.LOW, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);
        //act

        purchaseRequisitionTrackingStatusService.createDepartment(department);

        department.allocateBudgetToCostCenter(costCenter1, BigDecimal.valueOf(34000.0));
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));


        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter1);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);

//        purchaseRequisitionTrackingStatusService.createRequisition(requisition);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);
        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId());

        //assert.
        assertEquals(REJECTED, requisition.getRequisitionStatus());
    }
    @Test
    public void testShowAllApprovedRequisitionAfterTracking_Successfully() {
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software Development", "developing testable, maintainable, dynamic software", budget);
        CostCenter costCenter = new CostCenter( "Backend", "Manages acquisitions");
        CostCenter costCenter1 = new CostCenter( "FrontEnd", "Manages acquisitions");
        CostCenter costCenter2 = new CostCenter( "Deployment", "Manages acquisitions");

        Inventory item1 = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory item2 = new Inventory("I02", "Printer", 5, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        Inventory item3 = new Inventory("I03", "Switch", 2, BigDecimal.valueOf(2000), "IOT", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory item4 = new Inventory("I04", "Internet Cable", 5, BigDecimal.valueOf(500), "IOT", LocalDate.of(2026, 3, 1), "Wireless");
        Inventory item5 = new Inventory("I05", "CCTV camera", 8, BigDecimal.valueOf(2000), "IOT", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory item6 = new Inventory("I06", "Router", 5, BigDecimal.valueOf(500), "IOT", LocalDate.of(2026, 3, 1), "Wireless");

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");
        RequestedItem requestedItem1 = new RequestedItem(item1,3);
        RequestedItem requestedItem2 = new RequestedItem(item2,3);
        RequestedItem requestedItem3 = new RequestedItem(item3,3);
        RequestedItem requestedItem4 = new RequestedItem(item4,3);
        RequestedItem requestedItem5 = new RequestedItem(item5,3);
        RequestedItem requestedItem6 = new RequestedItem(item6,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.LOW, LocalDate.now().plusDays(15), "Office upgrade");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter1, PriorityLevel.LOW, LocalDate.now().plusDays(10), "Office upgrade");
        requisitionOne.addItem(requestedItem3);
        requisitionOne.addItem(requestedItem4);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ003", LocalDate.now(), requester, department, costCenter2, PriorityLevel.LOW, LocalDate.now().plusDays(20), "Office upgrade");
        requisitionTwo.addItem(requestedItem5);
        requisitionTwo.addItem(requestedItem6);
        //act
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        department.allocateBudgetToCostCenter(costCenter1, BigDecimal.valueOf(14000.0));
        department.allocateBudgetToCostCenter(costCenter2, BigDecimal.valueOf(20000.0));
        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);
        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisitionOne.getRequisitionId(),requisitionOne);
        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisitionTwo.getRequisitionId(), requisitionTwo);

        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId());
        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisitionOne.getRequisitionId());
        purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisitionTwo.getRequisitionId());

        Map<String, PurchaseRequisition> totalRequisitions = purchaseRequisitionTrackingStatusService.getAllRequisitions();

        List<PurchaseRequisition> approvedRequisitions = purchaseRequisitionTrackingStatusService.approvedRequisitions();

        //assert.
        assertEquals(approvedRequisitions.size(), totalRequisitions.size());
    }

    /** Here are some negative Testing for the Tracking Requisition Status.*/
    @Test
    public void testTrackApprovalRequisitionStatus_WithNOtActiveBudget_Unsuccessfully() { // budget is not active.
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(12), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventoryOne = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 2, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(inventoryOne,5);
        RequestedItem item2 = new RequestedItem(inventoryTwo,2);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);

        //act
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(20000.0));

        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);
        //assert
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId()));
    }
    @Test
    public void testTrackApprovalRequisitionStatus_WithNOnExistedDepartment_Unsuccessfully() { // budget is not active.
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(12), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventoryOne = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 2, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(inventoryOne,5);
        RequestedItem item2 = new RequestedItem(inventoryTwo,2);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);

        //act
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(20000.0));

        budget.updateBudgetStatus();


        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);

        //assert
        assertThrows(NullPointerException.class, () -> purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId()));
    }
    @Test
    public void testTrackApprovalRequisitionStatus_WithINAvctiveBudget_Unsuccessfully() { // budget is not active.
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(12), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
                CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");


        Inventory inventoryOne = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 2, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(inventoryOne,5);
        RequestedItem item2 = new RequestedItem(inventoryTwo,2);        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusDays(10), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);

        //act
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(20000.0));

        budget.updateBudgetStatus();

        purchaseRequisitionTrackingStatusService.createDepartment(department);

//        PurchaseRequisition createdRequisition = purchaseRequisitionTrackingStatusService.createRequisition(requisition);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);
        //assert
        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId()));
    }
    @Test
    public void testTrackApprovalRequisitionStatus_WithNonExistedRequisition_Unsuccessfully() { // budget is not active.
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(12), "USD");
        Department department = new Department("Finance", "Handles financial operations", budget);
        CostCenter costCenter = new CostCenter( "Procurement", "Manages acquisitions");

        budget.updateBudgetStatus();

        // Sample inventory list
        Inventory inventoryOne = new Inventory("I01", "Laptop", 5, BigDecimal.valueOf(2000), "Electronics", LocalDate.of(2026, 1, 1), "16GB RAM");
        Inventory inventoryTwo = new Inventory("I02", "Printer", 2, BigDecimal.valueOf(500), "Office Supplies", LocalDate.of(2026, 3, 1), "Wireless");
        RequestedItem item1 = new RequestedItem(inventoryOne,5);
        RequestedItem item2 = new RequestedItem(inventoryTwo,2);

        User requester = new User("John Doe", "john@example.com", "123456789", department, "Manager");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.of(2025, 5, 1), "Office upgrade");
        requisition.addItem(item1);
        requisition.addItem(item2);

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        purchaseRequisitionTrackingStatusService.addCostCenterToDepartment(department.getDepartmentId(), costCenter);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(20000.0));

        assertThrows(IllegalArgumentException.class, () -> purchaseRequisitionTrackingStatusService.trackRequisitionStatus(requisition.getRequisitionId()));
    }

    /** This is the testing for supplier management System.*/
    @Test
    public void testCreateSupplier_WithValidFields_Successfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("degu mamo", "degu@ibm.com", "+312674589459856", "Dubai");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        // act
        Supplier result = supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        //assert
        assertNotNull(result.getSupplierId());
        assertEquals("IBM",result.getSupplierName());
        assertEquals(supplierContactDetail,result.getSupplierContactDetail());
        assertEquals(List.of(supplierPaymentMethod), result.getSupplierPaymentMethods());
        assertEquals(3,result.getExistedItems().size());
    }
    @Test
    public void testCreateSupplier_WithEmptyItems_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tekia tekle", "tekia@gmail.com", "+251979417636", "Addis ababa");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(), LocalDate.now()));
    }
    @Test
    public void testCreateSupplier_WithMissingEmailRegularExpression_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tekiamalam.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        // act and assert
        assertThrows(IllegalArgumentException.class, () -> supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now()));
    }
    @Test
    public void testCreateSupplier_WithMissingPhoneRegularExpression_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "0979417636", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now()));
    }
    @Test
    public void testUpdateSupplier_Successfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));


        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail newSupplierContactDetail = new SupplierContactDetail("tsegay teklu", "tsegay@ibm.com", "+213348347684594", "Sudan");
        SupplierPaymentMethod newSupplierPaymentMethod = new SupplierPaymentMethod( "Lion Bank", "10213843945456", newSupplierContactDetail.getFullName(), newSupplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER,  PAYPAL),  PAYPAL, NET_30,"Euro", BigDecimal.valueOf(12000.0));

                //act
        Supplier result = supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        supplierMaintenanceService.updateSupplier(result.getSupplierId(),"Cisco", "Vendor", "Cis10112", "123212", newSupplierContactDetail, List.of(newSupplierPaymentMethod), List.of(item, item1), LocalDate.now());

        //assert
        assertEquals(newSupplierContactDetail, supplierMaintenanceService.getSupplierById(result.getSupplierId()).getSupplierContactDetail());


    }
    @Test
    public void testUpdateSupplier_ForNon_ExistedId_Unsuccessfully() { //try to update non existed supplier id.
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail newSupplierContactDetail = new SupplierContactDetail("tsegay teklu", "tsegay@ibm.com", "+213348347684594", "Sudan");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod newSupplierPaymentMethod = new SupplierPaymentMethod("Lion Bank", "10213843945456", newSupplierContactDetail.getFullName(), newSupplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER,  PAYPAL),  PAYPAL, NET_30, "Euro", BigDecimal.valueOf(12000.0));


        //act
        supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        String supplierId = IdGenerator.generateId("Supp");

        //assert.
        assertThrows(NoSuchElementException.class, () -> supplierMaintenanceService.updateSupplier(supplierId,"Cisco", "Vendor", "Cis10112", "123212", newSupplierContactDetail, List.of(newSupplierPaymentMethod), List.of(item, item1), LocalDate.now()));
    }
    @Test
    public void testUpdateSupplier_WithEmptyItems_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        // act.
        Supplier createdsupplier = supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());


        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierMaintenanceService.updateSupplier(createdsupplier.getSupplierId(),"Cisco", "Vendor", "Cis10112", "123212", supplierContactDetail, List.of(supplierPaymentMethod),  List.of( ), LocalDate.now()));
    }
    @Test
    public void testDeleteSupplier_SuccessFully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));


        Supplier supplier = supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());


        supplierMaintenanceService.removeSupplier(supplier.getSupplierId());

        //assert
        assertThrows(NoSuchElementException.class, () -> supplierMaintenanceService.getSupplierById(supplier.getSupplierId()));


    }
    @Test
    public void testDeleteSupplier_WithNon_ExistedSupplierId_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        //act
        supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());


        //assert
        assertThrows(NoSuchElementException.class, () -> supplierMaintenanceService.removeSupplier("VIN11"));
    }
    @Test
    public void testDeleteSupplier_WithNullSupplierId_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        //act
        supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierMaintenanceService.removeSupplier(null));
    }
    @Test
    public void testGetSupplier_BySupplierId_Success() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));


        //Act
        Supplier newsupplier = supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        Supplier result = supplierMaintenanceService.getSupplierById(newsupplier.getSupplierId());

        //assert.
        assertEquals(newsupplier.getSupplierId(),result.getSupplierId());
    }
    @Test
    public void testGetSupplier_WithNullSupplierId_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));


        supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> supplierMaintenanceService.getSupplierById(null));
    }
    @Test
    public void testGettingSupplier_WithNon_ExistedSupplierId_Unsuccessfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));


        supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        // act and assert.
        assertThrows(NoSuchElementException.class, () -> supplierMaintenanceService.getSupplierById("VN11"));
    }
    @Test
    public void testGetAllSupplier_Successfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail newSupplierContactDetail = new SupplierContactDetail("tsegay teklu", "tsegay@ibm.com", "+213348347684594", "Sudan");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod newSupplierPaymentMethod = new SupplierPaymentMethod( "Lion Bank", "10213843945456", newSupplierContactDetail.getFullName(), newSupplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER,  PAYPAL),  PAYPAL, NET_30, "Euro", BigDecimal.valueOf(12000.0));


        // act.
        supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        supplierMaintenanceService.createSupplier("Cisco", "Vendor", "Cis10112", "123212", newSupplierContactDetail, List.of(newSupplierPaymentMethod), List.of(item, item1), LocalDate.now());


        Map<String, Supplier> totalSuppliers = supplierMaintenanceService.getSupplierMap();

        //assert
        assertEquals(2, totalSuppliers.values().size());

    }
    @Test
    public void testGetActiveSupplier_Successfully() {
        //arrange
        supplierMaintenanceService = new SupplierMaintenanceService();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail newSupplierContactDetail = new SupplierContactDetail("tsegay teklu", "tsegay@ibm.com", "+213348347684594", "Sudan");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod newSupplierPaymentMethod = new SupplierPaymentMethod("Lion Bank", "10213843945456", newSupplierContactDetail.getFullName(), newSupplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER,  PAYPAL),  PAYPAL,  NET_30, "Euro", BigDecimal.valueOf(12000.0));

        //act
        Supplier supplier = supplierMaintenanceService.createSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        Supplier newsupplier = supplierMaintenanceService.createSupplier("Cisco", "Vendor", "Cis10112", "123212", newSupplierContactDetail, List.of(newSupplierPaymentMethod), List.of(item, item1), LocalDate.now());
        newsupplier.setActive(false);

        List<Supplier> activesuppliers = supplierMaintenanceService.getActiveSuppliers();


        //assert
        assertEquals(1, activesuppliers.size());
    }

    /** supplier Performance evaluation tests */
    @Test
    public void testAddSupplierPerformance_Successfully() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        //act
        List<SupplierPerformance>performances = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), quantitativeMetrics,qualitativeMetrics,LocalDate.now().minusMonths(3));

        assertNotNull(performances);
        //assert
        assertEquals(1, performances.size());
        assertEquals(supplier.getSupplierId(), performances.get(0).getSupplier().getSupplierId());
        assertEquals(quantitativeMetrics,performances.get(0).getQuantitativePerformanceMetrics());
    }
    @Test
    public void testCalculateSupplierPerformanceScore_WithValidSupplier_Successfully() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);


        //act
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);


        List<SupplierPerformance>performances = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), quantitativeMetrics,qualitativeMetrics,LocalDate.now().minusMonths(3));

        assertNotNull(performances);
        //assert
        assertEquals(1, performances.size());
        assertEquals(supplier.getSupplierId(), performances.get(0).getSupplier().getSupplierId());
        assertEquals(quantitativeMetrics,performances.get(0).getQuantitativePerformanceMetrics());
        double performanceScore = supplierPerformanceEvaluationServices.averagePerformanceScore(supplier.getSupplierId());

        //assert
        assertEquals(96,Math.round(performanceScore));
    }
    @Test
    public void testCalculateQualitativeScore_Successfully() {
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 8, 7, 9);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(95, 90, 85, 88, 92, 1, 0);

        Supplier supplier1 = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        List<SupplierPerformance>performances = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(), quantitativeMetrics,qualitativeMetrics,LocalDate.now().minusMonths(3));

        assertNotNull(performances);
        //assert
        assertEquals(1, performances.size());
        assertEquals(supplier1.getSupplierId(), performances.get(0).getSupplier().getSupplierId());
        assertEquals(quantitativeMetrics,performances.get(0).getQuantitativePerformanceMetrics());
        double performanceScore = supplierPerformanceEvaluationServices.getQualitativePerformanceScore(performances.get(0));


        assertTrue(performanceScore >= 0 && performanceScore <= 10 );
        assertEquals(8,performanceScore);
    }
    @Test
    public void testCalculateQuantitativeScore() {
        //
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));


        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 8, 7, 9);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(95, 90, 85, 88, 92, 1, 0);

        //
        Supplier supplier1 = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        List<SupplierPerformance>performances = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(), quantitativeMetrics,qualitativeMetrics,LocalDate.now().minusMonths(3));

        assertNotNull(performances);
        //assert
        assertEquals(1, performances.size());
        assertEquals(supplier1.getSupplierId(), performances.get(0).getSupplier().getSupplierId());
        assertEquals(quantitativeMetrics,performances.get(0).getQuantitativePerformanceMetrics());
        double performanceScore = supplierPerformanceEvaluationServices.getQuantitativePerformanceScore(performances.get(0));

        //
        assertEquals(63.6,performanceScore);
    }
    @Test
    public void testGetSuppliersByPerformanceScore_SuccessFully() {
        //
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 8, 7, 9);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(95, 90, 85, 88, 92, 1, 0);

        //
        Supplier supplier1 = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        List<SupplierPerformance>performances = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(), quantitativeMetrics,qualitativeMetrics,LocalDate.now().minusMonths(3));

        //
        List<Supplier> supplierList = supplierPerformanceEvaluationServices.getSupplierByPerformanceRate(SupplierPerformanceRate.GOOD);
        assertTrue(supplierList.contains(supplier1));
    }
    @Test
    public void testSortSuppliers_ByPerformance_Successfully(){
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail newSupplierContactDetail = new SupplierContactDetail("tsegay teklu", "tsegay@ibm.com", "+213348347684594", "Sudan");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod newSupplierPaymentMethod = new SupplierPaymentMethod("Lion Bank", "10213843945456", newSupplierContactDetail.getFullName(), newSupplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER,  PAYPAL),  PAYPAL, NET_30, "Euro", BigDecimal.valueOf(12000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item,item1,item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "Cis10112", "123212", newSupplierContactDetail, List.of(newSupplierPaymentMethod), List.of(item, item1), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(70, 15, 75, 72, 88, 95, 4);
        SupplierQuantitativePerformanceMetrics supplierOneQuantitativeMetrics = new SupplierQuantitativePerformanceMetrics(85,5,87,90,92,90,0);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(4, 5, 3, 6, 4, 8);
        SupplierQualitativePerformanceMetrics supplierOneQualitativeMetrics = new SupplierQualitativePerformanceMetrics(9,9,9,9,9,9);

        //act
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);



        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusDays(10));
        supplierPerformanceEvaluationServices.addSupplierPerformance(supplierOne.getSupplierId(), supplierOneQuantitativeMetrics, supplierOneQualitativeMetrics,LocalDate.now().minusDays(10));

        List<Supplier> sortedsuppliers = supplierPerformanceEvaluationServices.sortSupplierByLatestPerformanceScore();

        assertEquals(supplierOne, sortedsuppliers.get(0)); //EXECELLENT
        assertEquals(supplier, sortedsuppliers.get(1));//GOOD

    }


    // Negative Test Cases
    @Test
    public void testAddSupplierPerformance_ForInactiveSupplier_Unsuccessfully() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(), LocalDate.now());

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);
        supplier.setActive(false);

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(5)));
    }
    @Test
    public void testAddSupplierPerformance_WithInvalidPerformanceData_Unsuccessfully() {
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        //act
        Supplier supplier1 = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(), null, null,null));
    }
    @Test
    public void testGetSupplierPerformance_ForNonExistentSupplier_Unsuccessfully() { // there is no supplier added before .that mean there is no existed supplier before.
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierPerformance performance = new SupplierPerformance(supplier, quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(5));

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> supplierPerformanceEvaluationServices.getSupplierById(performance.getSupplier().getSupplierId()));
    }
    @Test
    public void testGetSupplierById_SupplierNotFound_Unsuccessfully() {
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 8, 7, 9);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(95, 90, 85, 88, 92, 1, 0);

        //act


        Supplier supplier1 = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(), quantitativeMetrics, qualitativeMetrics,LocalDate.now());

        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierPerformanceEvaluationServices.getSupplierById("NON_EXISTENT"));
    }
    @Test
    public void testCalculateSupplierPerformanceScore_SupplierNotFound_Unsuccessfully() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 8, 7, 9);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(95, 90, 85, 88, 92, 1, 0);

        //act

        Supplier supplier1 = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(), quantitativeMetrics, qualitativeMetrics,LocalDate.now());

        //assert
        assertThrows(NullPointerException.class, () -> supplierPerformanceEvaluationServices.calculateSupplierPerformanceScore(null));
    }
    @Test
    public void testCalculateSupplierPerformanceScore_NoPerformanceData() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        //act
        Supplier supplier = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        // assert
        assertThrows(IllegalArgumentException.class, () -> supplierPerformanceEvaluationServices.calculateSupplierPerformanceScore(new SupplierPerformance(supplier,null,null,null)));
    }
    @Test
    public void testSuppliersByPerformanceScore_ForUnmatchedRate_ReturnsEmptyList() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(), LocalDate.now());
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierPerformance performance = new SupplierPerformance(supplier, quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(5));

        //act
        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusDays(10));
        List<Supplier> supplierList = supplierPerformanceEvaluationServices.getSupplierByPerformanceRate(SupplierPerformanceRate.POOR);

        //assert
        assertTrue(supplierList.isEmpty());
    }
    @Test
    public void testSetSupplierPerformanceRate_InactiveSupplier_Unsuccessfully() {
        //arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        //act
        Supplier supplier = supplierPerformanceEvaluationServices.addSupplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplier.setActive(false);

        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierPerformanceEvaluationServices.setSupplierPerformanceRate("SUP001"));
    }

    /** supplier Linkage testing.*/
    @Test
    public void testLinkingSupplier_WithContracts_Successfully() {
        //arrange
        supplierLinkingService = new SupplierLinkingService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        // about requisition
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        Inventory item0 = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item0,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        //about supplier
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item0, item1, item2), LocalDate.now());

        //purchase order
        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        // contract
        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(),LocalDate.now().minusDays(3));


        //act
        supplierLinkingService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        supplierLinkingService.getContractsByVendor().put(supplier.getSupplierId(), List.of(contract));

        supplierLinkingService.associateContract(supplier.getSupplierId(), contract);

        // Assert - Ensure that the contract is now linked to the correct vendor
        List<Contract> linkedContracts = supplierLinkingService.getContractsBySupplier(supplier.getSupplierId());

        assertEquals(1, linkedContracts.size());
    }
    @Test
    public void testLinkingSupplier_WithNullContract_Unsuccessfully() {
        //arrange
        supplierLinkingService = new SupplierLinkingService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        supplierLinkingService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> supplierLinkingService.associateContract(supplier.getSupplierId(), null));
    }
    @Test
    public void testLinkingSupplier_WithNon_ExistedContractId_Unsuccessfully() {
        //arrange
        supplierLinkingService = new SupplierLinkingService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        //about requisition.
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        //about supplier
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of( item1, item2), LocalDate.now());

        //purchase order
        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //contract
        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(),LocalDate.now().minusDays(3));

        //act
        supplierLinkingService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        supplierLinkingService.getContractsByVendor().put(supplier.getSupplierId(), List.of());

        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierLinkingService.associateContract(supplier.getSupplierId(), contract));
    }
    @Test
    public void testLinkingSupplier_WithPurchaseOrder_Successfully() {
        //arrange
        supplierLinkingService = new SupplierLinkingService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** about Requisition */

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** about supplier. */

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        /** Purchase order */
        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        supplierLinkingService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierLinkingService.getPurchaseOrdersByVendor().put(supplier.getSupplierId(), List.of(purchaseOrder));

        supplierLinkingService.associatePurchaseOrder(supplier.getSupplierId(), purchaseOrder);

        List<PurchaseOrder> linkedPurchaseOrder = supplierLinkingService.getPurchaseOrdersBySupplier(supplier.getSupplierId());

        // assert
        assertEquals(1, linkedPurchaseOrder.size());
    }
    @Test
    public void testLinkingSupplier_WithNullPurchaseOrder_Unsuccessfully() {
        //arrange
        supplierLinkingService = new SupplierLinkingService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** about supplier .*/
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        supplierLinkingService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierLinkingService.getPurchaseOrdersByVendor().put(supplier.getSupplierId(), List.of(purchaseOrder));

        //assert
        assertThrows(IllegalArgumentException.class, () -> supplierLinkingService.associatePurchaseOrder(supplier.getSupplierId(), null));
    }
    @Test
    public void testLinkingSupplier_WithNon_ExistedPurchaseOrderId_Unsuccessfully() {
        //arrange
        supplierLinkingService = new SupplierLinkingService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** about supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        /** Purchase Order. */
        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        supplierLinkingService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierLinkingService.getPurchaseOrdersByVendor().put(supplier.getSupplierId(), List.of());

        //assert.
        assertThrows(IllegalArgumentException.class, () -> supplierLinkingService.associatePurchaseOrder(supplier.getSupplierId(), purchaseOrder));
    }

    /** this is the testing for purchase orders. */
    @Test
    public void testCreatePurchaseOrder_WithValidFields_Successfully() {
        //arrange
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        purchaseOrderGeneratingService.addDepartment(department);
        purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);

        //assert
        assertNotNull(purchaseOrder);
        assertEquals(supplier, purchaseOrder.getSupplier());
        assertEquals(LocalDate.now(), purchaseOrder.getOrderDate());
        assertEquals(LocalDate.now().plusMonths(4), purchaseOrder.getDeliveryDate());
        assertEquals(PurchaseOrderStatus.PENDING, purchaseOrder.getPurchaseOrderStatus());
    }
    @Test
    public void testCreatePurchaseOrder_WithNullField_Unsuccessfully(){
        //arrange
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();

        //act and assert
        assertThrows(IllegalArgumentException.class,()->purchaseOrderGeneratingService.createPurchaseOrder(null));
    }
    @Test
    public void testCreatePurchaseOrder_WithMissingData_Unsuccessfully() {
        //arrange
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition .*/
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4))));
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(new PurchaseOrder( null, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4))));
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(new PurchaseOrder( department, List.of(), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4))));
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(new PurchaseOrder( department, List.of(requisition), LocalDate.now(), null, "Air", LocalDate.now().plusMonths(4))));
    }
    @Test
    public void testCreatePurchaseOrder_WithInvalidOrderDate_Unsuccessfully() {
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        LocalDate pastDate = LocalDate.now().minusDays(1);
        purchaseOrder.setOrderDate(pastDate);

        //assert
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder));
    }
    @Test
    public void testGeneratePurchaseOrder_WithDeliveryDateBeforeOrderDate_Unsuccessfully() {
        //Arrange
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition.*/
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        LocalDate invalidDeliveryDate = LocalDate.now().minusDays(1);
        purchaseOrder.setDeliveryDate(invalidDeliveryDate);

        //assert
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder));
    }
    @Test
    public void testGeneratePurchaseOrder_WithEmptySupplierFields_Unsuccessfully() {
        //arrange
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /**About supplier.*/
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), null, "Air", LocalDate.now().plusMonths(4));

        //assert
        assertThrows(IllegalArgumentException.class, () -> purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder));
    }
    @Test
    public void testUpdatePurchaseOrder_Successfully(){
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,10);
        RequestedItem requestedItem1 = new RequestedItem(item1,10);
        RequestedItem requestedItem2 = new RequestedItem(item2,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ101",LocalDate.now().minusDays(2),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusMonths(3),"we are new department.");
        requisition1.addItem(requestedItem1);
        requisition1.addItem(requestedItem2);
        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        purchaseOrderGeneratingService.addDepartment(department);
        PurchaseOrder createdOrder = purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);
        PurchaseOrder result = purchaseOrderGeneratingService.updatePurchaseOrder(createdOrder.getOrderId(),department,List.of(requisition,requisition1), supplier,"Air",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now());

        //assert
        assertEquals(List.of(requisition,requisition1),result.getRequisitionList());
    }
    @Test
    public void testUpdatePurchaseOrder_WithNon_ExistedOrderId_Unsuccessfully(){
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,10);
        RequestedItem requestedItem1 = new RequestedItem(item1,10);
        RequestedItem requestedItem2 = new RequestedItem(item2,10);


        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ101",LocalDate.now().minusDays(2),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusMonths(3),"we are new department.");
        requisition1.addItem(requestedItem1);
        requisition1.addItem(requestedItem2);
        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        purchaseOrderGeneratingService.addDepartment(department);
        purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);

        String orderId = IdGenerator.generateId("PO");
        //assert.
        assertThrows(NoSuchElementException.class,()->purchaseOrderGeneratingService.updatePurchaseOrder(orderId, department,List.of(requisition,requisition1), supplier,"Air",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now()));

    }
    @Test
    public void testUpdatePurchaseOrder_WithNullID_Unsuccessfully(){
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,10);
        RequestedItem requestedItem1 = new RequestedItem(item1,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        //act
        purchaseOrderGeneratingService.addDepartment(department);
        purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);

        //assert
        assertThrows(IllegalArgumentException.class,()->purchaseOrderGeneratingService.updatePurchaseOrder(null, department,List.of(requisition), supplier,"Air",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now()));
    }
    @Test
    public void testUpdatePurchaseOrder_WithInvalidValues_Unsuccessfully(){
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,10);
        RequestedItem requestedItem1 = new RequestedItem(item1,10);
        RequestedItem requestedItem2 = new RequestedItem(item2,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ101",LocalDate.now().minusDays(2),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusMonths(3),"we are new department.");
        requisition1.addItem(requestedItem1);
        requisition1.addItem(requestedItem2);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        //act
        purchaseOrderGeneratingService.addDepartment(department);
        PurchaseOrder createdOrder = purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);

        //assert
        assertThrows(IllegalArgumentException.class,()->purchaseOrderGeneratingService.updatePurchaseOrder(createdOrder.getOrderId(), null,List.of(requisition,requisition1), supplier,"Air",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now()));
        assertThrows(IllegalArgumentException.class,()->purchaseOrderGeneratingService.updatePurchaseOrder(createdOrder.getOrderId(), department,List.of(), supplier,"Air",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now()));
        assertThrows(IllegalArgumentException.class,()->purchaseOrderGeneratingService.updatePurchaseOrder(createdOrder.getOrderId(), department,List.of(requisition,requisition1),null,"Air",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now()));
        assertThrows(IllegalArgumentException.class,()->purchaseOrderGeneratingService.updatePurchaseOrder(createdOrder.getOrderId(), department,List.of(requisition,requisition1), supplier,"",LocalDate.now().plusMonths(4),BigDecimal.valueOf(40000.0),LocalDate.now()));
    }
    @Test
    public void testDeletePurchaseOrder_Successfully(){
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        purchaseOrderGeneratingService.addDepartment(department);
        PurchaseOrder result = purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder); //first added the purchase order to the list of created orders.

        //act
        purchaseOrderGeneratingService.deletePurchaseOrder(result.getOrderId());//deleting the created order.

        //assert
        assertTrue(purchaseOrderGeneratingService.getAllPurchaseOrders().isEmpty());// checking the created orderList after removing the purchase order.
    }
    @Test
    public void testDeletePurchaseOrder_ForNon_ExistedOrderId_Unsuccessfully(){
        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        purchaseOrderGeneratingService.addDepartment(department);
        purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);

        //act and assert.
        assertThrows(NoSuchElementException.class,()->purchaseOrderGeneratingService.deletePurchaseOrder("ORD321"));
    }
    @Test
    public void testGetPurchaseOrdersBySupplier_Successfully(){

        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,10);
        RequestedItem requestedItem1 = new RequestedItem(item1,10);
        RequestedItem requestedItem2 = new RequestedItem(item2,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ101",LocalDate.now().minusDays(2),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusMonths(3),"we are new department.");
        requisition1.addItem(requestedItem1);
        requisition1.addItem(requestedItem2);

        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        System.out.println(purchaseOrder.getOrderId());
        PurchaseOrder newPurchaseOrder = new PurchaseOrder(department,List.of(requisition,requisition1),LocalDate.now(), supplier,"Air",LocalDate.now().plusMonths(4));
        System.out.println(newPurchaseOrder.getOrderId());
        purchaseOrderGeneratingService.addDepartment(department);
        purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);
        purchaseOrderGeneratingService.createPurchaseOrder(newPurchaseOrder);

        //act
        List<PurchaseOrder> purchaseOrderList = purchaseOrderGeneratingService.purchaseOrderBySupplier(supplier.getSupplierId());

        purchaseOrderList.sort(Comparator.comparing(PurchaseOrder::getOrderId));


        //assert.
        assertEquals(2,purchaseOrderList.size());
        assertEquals(purchaseOrder,purchaseOrderList.get(0));
        assertEquals(newPurchaseOrder,purchaseOrderList.get(1));
    }
    @Test
    public void testGetPurchaseOrdersByDepartment_Successfully() {

        purchaseOrderGeneratingService = new PurchaseOrderGeneratingService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(1000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        /** About Requisition. */
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,10);
        RequestedItem requestedItem1 = new RequestedItem(item1,10);
        RequestedItem requestedItem2 = new RequestedItem(item2,10);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ101", LocalDate.now().minusDays(2), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(3), "we are new department.");
        requisition1.addItem(requestedItem1);
        requisition1.addItem(requestedItem2);
        /** About supplier. */
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        PurchaseOrder newPurchaseOrder = new PurchaseOrder( department, List.of(requisition, requisition1), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        purchaseOrderGeneratingService.addDepartment(department);
        purchaseOrderGeneratingService.createPurchaseOrder(purchaseOrder);
        purchaseOrderGeneratingService.createPurchaseOrder(newPurchaseOrder);

        //act
        List<PurchaseOrder> purchaseOrderList = purchaseOrderGeneratingService.findOrdersByDepartment(department.getDepartmentId());

        //assert.

        assertEquals(2, purchaseOrderList.size());

        assertTrue(purchaseOrderList.containsAll(List.of(purchaseOrder,newPurchaseOrder)) );
    }


    /** this is the test for tracking purchase order.*/
    @Test
    public void testTrackingPurchaseOrder_FromPendingToApproved_Successfully() {
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        purchaseOrderTrackingStatusService.getDepartments().add(department);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);
        //act
        PurchaseOrder result = purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId());

        // assert.
        assertNotNull(result);
        assertEquals(PurchaseOrderStatus.APPROVED, result.getPurchaseOrderStatus());
    }
    @Test
    public void testTrackingPurchaseOrder_FromApprovedToShipped_Successfully() {
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrderTrackingStatusService.getDepartments().add(department);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);
        //act
        PurchaseOrder result = purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId());
        //assert
        assertNotNull(purchaseOrder);
        assertEquals(PurchaseOrderStatus.SHIPPED, result.getPurchaseOrderStatus());
    }
    @Test
    public void testTrackingPurchaseOrder_FromShippedToCompleteStatus_Successfully() {
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        purchaseOrderTrackingStatusService.getDepartments().add(department);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);

        //act
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.SHIPPED);
        purchaseOrder = purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId());

        //assert
        assertNotNull(purchaseOrder);
        assertEquals(PurchaseOrderStatus.COMPLETED, purchaseOrder.getPurchaseOrderStatus());
    }
    @Test
    public void testTrackPurchaseOrderStatusToTermination_DueToTotalCostGreaterThanBudget_Successfully() {
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,6);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        purchaseOrderTrackingStatusService.getDepartments().add(department);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);

        //act
        PurchaseOrder result = purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId());

        // assert
        assertEquals(PurchaseOrderStatus.TERMINATED, result.getPurchaseOrderStatus());
    }
    @Test
    public void testTrackPurchaseOrder_WithNOn_ExistedOrder_Unsuccessfully(){
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");


        RequestedItem requestedItem = new RequestedItem(item,3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        purchaseOrderTrackingStatusService.getDepartments().add(department);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);

        //act and assert
        assertThrows(NoSuchElementException.class,()->purchaseOrderTrackingStatusService.trackPurchaseOrderStatus("ORD13"));

    }
    @Test
    public void testTrackPurchaseOrder_WithINActiveBudget_Unsuccessfully(){
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        purchaseOrderTrackingStatusService.getDepartments().add(department);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);

        assertThrows(IllegalArgumentException.class,()->purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId()));

    }
    @Test
    public void testTrackPurchaseOrder_WithNon_ExistedDepartment_Unsuccessfully(){
        // arrange
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        purchaseOrderTrackingStatusService.getAllPurchaseOrders().put(purchaseOrder.getOrderId(), purchaseOrder);

        //act and assert
        assertThrows(NullPointerException.class,()->purchaseOrderTrackingStatusService.trackPurchaseOrderStatus(purchaseOrder.getOrderId()));

    }

    /** test for ensuring the purchase order.*/
    @Test
    public void testCreatePurchaseOrder_WithApprovedRequisition_Successfully() {
        //arrange
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        // act
        requisition.setRequisitionStatus(APPROVED);

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrder = purchaseOrderEnsuringServices.createPurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        // assert
        assertNotNull(purchaseOrder);
        assertEquals(PurchaseOrderStatus.PENDING, purchaseOrder.getPurchaseOrderStatus());
        assertEquals("Air", purchaseOrder.getShippingMethod());
        assertEquals(supplier, purchaseOrder.getSupplier());
        assertEquals(LocalDate.now(), purchaseOrder.getOrderDate());
        assertEquals(LocalDate.now().plusMonths(4), purchaseOrder.getDeliveryDate());
    }
    @Test
    public void testEnsuringRequisitionStatusOfPurchaseOrder_WithApprovedRequisition_Successfully(){
        //arrange
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        // act
        requisition.setRequisitionStatus(APPROVED);

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrder = purchaseOrderEnsuringServices.createPurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        RequisitionStatus status = purchaseOrderEnsuringServices.ensureRequisitionStatusOfPurchaseOrder(purchaseOrder.getOrderId());

        //assert
        assertEquals(APPROVED,status);
    }
    @Test
    public void testCreatePurchaseOrder_WithNOtApprovedRequisition_Unsuccessfully(){
        //arrange
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        // act and assert

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        assertThrows(IllegalArgumentException.class,()->purchaseOrderEnsuringServices.createPurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4)));
    }
    @Test
    public void testEnsuringPurchaseOrder_WithNotApprovedRequisition() {
        //arrange
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionNumber(), requisition);
        purchaseOrderEnsuringServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        //assert
        assertThrows(IllegalStateException.class,()->purchaseOrderEnsuringServices.ensureRequisitionStatusOfPurchaseOrder(purchaseOrder.getOrderId()));
    }
    @Test
    public void testCreatePurchaseOrder_WithNotExistedRequisition_Unsuccessfully() {
        //arrange
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionNumber(), requisition);

        // act and assert.
        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        assertThrows(IllegalArgumentException.class, () -> purchaseOrderEnsuringServices.createPurchaseOrder( department, List.of(), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4)));
    }


    /** this is the contract managements system testing. */
    @Test
    public void testCreateContract_WithValidfields_Successfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        Contract result = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        // assert.
        assertNotNull(result);
        assertEquals("Contract with Cisco", result.getContractTitle());
        assertEquals(LocalDate.now().plusDays(10), result.getStartDate());
        assertEquals(LocalDate.now().plusMonths(10), result.getEndDate());
        assertEquals(BigDecimal.valueOf(45000.0), result.getTotalCost());
        assertEquals(NET_30, result.getPaymentTerms());
        assertEquals(DeliveryTerms.CIF, result.getDeliveryTerms());
        assertEquals(ContractStatus.ACTIVE, result.getStatus());
        assertEquals(List.of(purchaseOrder), result.getPurchaseOrders());
    }
    @Test
    public void testCreatingContract_WithMissingData_Unsuccessfully() {
        //arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.createContract(" ", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3)));
        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.createContract("urgent Contract", null, LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3)));
        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.createContract("urgent Contract", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(), List.of(contractFile),LocalDate.now().minusDays(3)));
    }
    @Test
    public void testCreateContract_WithPastStartTime() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().minusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3)));
    }
    @Test
    public void testCreateContract_WithEndDateIsBeforeStartDate_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", " pdf", "/file/contractOne", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusDays(5), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3)));
    }
    @Test
    public void testGetContractById_Successfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        Contract createdContract = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));



        Contract result = storeAndTrackContractServices.getContractById(createdContract.getContractId());

        assertNotNull(result);
        assertEquals(createdContract.getContractId(),result.getContractId());
        assertEquals(createdContract.getContractTitle(),result.getContractTitle());
        assertEquals(supplier,result.getSupplier());
    }
    @Test
    public void testGetContract_WithNonExistedContractId_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));


        String contractId = "CON001";

        // act and assert
        assertThrows(NoSuchElementException.class, () -> storeAndTrackContractServices.getContractById(contractId));
    }
    @Test
    public void testGetContract_WithActiveStatus_Successfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));// contract with active status

        storeAndTrackContractServices.createContract("new Contract", supplier, LocalDate.now(), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));// contract with pending status.

        // act
        List<Contract> activeContracts = storeAndTrackContractServices.getActiveContract();

        // assert.
        assertEquals(2, activeContracts.size());
    }
    @Test
    public void testGetContract_WithNotActiveStatus_Successfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        Contract contract = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        contract.setStatus(ContractStatus.PENDING);

        Contract contractOne =  storeAndTrackContractServices.createContract("new Contract", supplier, LocalDate.now(), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        contractOne.setStatus(ContractStatus.PENDING);

        //act
        Map<String, Contract> totalContracts = storeAndTrackContractServices.getContract();

        // assert
        assertEquals(2, totalContracts.size());
        assertEquals(ContractStatus.PENDING, contract.getStatus());
        assertEquals(ContractStatus.PENDING, contractOne.getStatus());
        assertEquals(0,  storeAndTrackContractServices.getActiveContract().size());
    }
    @Test
    public void testGetContractBySupplier_Successfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        Contract contract = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        Contract contractOne = storeAndTrackContractServices.createContract("new Contract", supplier, LocalDate.now(), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        // act
        List<Contract> contractsWithSameVendor = storeAndTrackContractServices.getContractBySupplier(supplier.getSupplierId());

        // assert.
        assertEquals(2, contractsWithSameVendor.size());
        assertEquals(supplier, contract.getSupplier());
        assertEquals(supplier, contractOne.getSupplier());
    }
    @Test
    public void testGetContract_ByNonExistedSupplier_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act
        Supplier supplier1 = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        supplier1.getSupplierContactDetail().setFullName("mulu berhe");
        supplier1.getSupplierContactDetail().setEmail("mulu@ibm.com");
        supplier1.getSupplierContactDetail().setPhone("+251971425364");

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        storeAndTrackContractServices.createContract("new Contract", supplier, LocalDate.now(), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        // act and assert
        assertEquals(0,storeAndTrackContractServices.getContractBySupplier(supplier1.getSupplierId()).size());
    }
    @Test
    public void testUpdateContract_Successfully() {
        /// arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act


        Supplier supplier1 = new Supplier("malam ", "distributor", "1012384", "9090909", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        supplier1.getSupplierContactDetail().setFullName("mulu berhe");
        supplier1.getSupplierContactDetail().setEmail("mulu@malam.com");
        supplier1.getSupplierContactDetail().setPhone("+251971425364");

        // act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        storeAndTrackContractServices.getSupplierMap().put(supplier1.getSupplierId(), supplier1);

        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        Contract result = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        storeAndTrackContractServices.updateContract(result.getContractId(),"urgent Contract", supplier1.getSupplierId(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0),  List.of(purchaseOrder), List.of(contractFile),LocalDate.now());

        // assert
        assertNotNull(result);
        assertEquals(supplier1, result.getSupplier());
        assertEquals("urgent Contract", result.getContractTitle());
        assertEquals(LocalDate.now().plusDays(2), result.getStartDate());
    }
    @Test
    public void testUpdateContract_WithNullContractID_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        // act and assert.
        assertThrows(NullPointerException.class, () -> storeAndTrackContractServices.updateContract(null,"urgent Contract", supplier.getSupplierId(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0),  List.of(purchaseOrder), List.of(contractFile),LocalDate.now()));
    }
    @Test
    public void testUpdateContract_WithNonExistedContract_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));


        String contractId = IdGenerator.generateId("CO");
        // act and assert.
        assertThrows(NoSuchElementException.class, () -> storeAndTrackContractServices.updateContract(contractId,"urgent Contract", supplier.getSupplierId(), LocalDate.now().plusDays(2), LocalDate.now().plusDays(21), PaymentTerms.PREPAID, DeliveryTerms.FOB, BigDecimal.valueOf(32000.0),  List.of(purchaseOrder), List.of(contractFile),LocalDate.now()));//the contract that exist in the contract list has an id CON101 not CON001.
    }
    @Test
    public void testDeleteExistedContract_Successfully() {
        /// arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        Contract createdContract = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        //act
        String contractId = createdContract.getContractId();// getting the contract id of the contract that existed in contract list

        //assert
        assertDoesNotThrow(() -> storeAndTrackContractServices.deleteContract(contractId));// deleting existing contract.
    }
    @Test
    public void testDeleteContract_WithNonExistedID_Unsucceessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));


        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.deleteContract("CON001")); // since there is no contract with id CON001.
    }
    @Test
    public void testDeleteContract_WithNullID_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));



        assertThrows(NullPointerException.class, () -> storeAndTrackContractServices.deleteContract(null));// there is no contract with contractId null.
    }
    @Test
    public void testTerminatingContract_Successfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        Contract contract = storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));



        //act
        Contract terminatedContract = storeAndTrackContractServices.terminateContract(contract.getContractId());

        // assert
        assertEquals(ContractStatus.TERMINATED, terminatedContract.getStatus());
    }
    @Test
    public void testTerminatingContract_WithNon_ExistedID_Unsuccessfully() {// we are trying to terminate non existed-contract
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> storeAndTrackContractServices.terminateContract("CON001"));
    }
    @Test
    public void testTerminatingContract_WithNullID_Unsuccessfully() {
        // arrange
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());


        //act
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storeAndTrackContractServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        storeAndTrackContractServices.createContract("Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));


        // act and assert.
        assertThrows(NullPointerException.class, () -> storeAndTrackContractServices.terminateContract(null));// there is no contract its contract id is null.
    }


    /** this is the testing for altering contract services. */
    @Test
    public void testGetExpiringContracts_Successfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        Contract contractsTwo = new Contract( "Expired Contract", supplier, LocalDate.now().plusDays(3), LocalDate.now().plusDays(5), PaymentTerms.PREPAID, DeliveryTerms.CIF,  BigDecimal.valueOf(1500.0),true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);
        alertingContractsServices.createContract(contractsTwo);


        // act
        List<Contract> expiringContracts = alertingContractsServices.getExpiringContracts(7);

        // assert
        assertEquals(3, alertingContractsServices.getAllContracts().size());
        assertEquals(1, expiringContracts.size());// the reason why only one contract is expiring is since first check is the contract  active or else.if not active it fails from passing the next testing.if active check the time of ending contract.
        assertEquals(contractOne.getContractId(),expiringContracts.getFirst().getContractId()); // in order to alert the contract status must be active.so that is the reason to alert only one contract.
    }
    @Test
    public void testGetExpiringContracts_WithNegativeExpiringDays_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        Contract contractsTwo = new Contract( "Expired Contract", supplier, LocalDate.now().plusDays(3), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF,  BigDecimal.valueOf(1500.0),true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);
        alertingContractsServices.createContract(contractsTwo);

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.getExpiringContracts(-1)); // even if all are active contracts but the day before expiration is negative. days can't be negative.
    }
    @Test
    public void testAlertingContracts_WhenNoContractExists_Unsuccessfully() {
        //arrange
        alertingContractsServices = new AlertingContractsServices();

        //act
        Map<String, Contract> contracts = alertingContractsServices.getAllContracts();

        // assert
        assertEquals(0, contracts.size());
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.alertingContracts(7));
    }
    @Test
    public void testAlertingContracts_WithNonExpiringContracts_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.alertingContracts(7)); // all contracts are not expiring with the specified time.
    }
    @Test
    public void testMarkRenewalForExpiringContracts_Successfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // **Act:** Perform the action
        alertingContractsServices.getExpiringContracts(7);
        boolean renewalResult = alertingContractsServices.markForRenewal(contractOne.getContractId(),7);

        // **Assert:** Verify expected results
        assertTrue(renewalResult);
    }
    @Test
    public void testMarkRenewalForNot_ExpiringContracts_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.markForRenewal("CON004", 7));// the contract is not expiring with the given time.
    }
    @Test
    public void testMarkRenewalForNotExisted_Contracts_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.markForRenewal("CON03", 7)); // we are trying to renew non-existed contract
    }
    @Test
    public void testMarkRenewalOfContracts_WithNullId_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.markForRenewal(null, 7));// since there is no contract with null contractId.
    }
    @Test
    public void testMarkRenewalOfContracts_WithNegativeDaysExpiration_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        //act and assert.
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.markForRenewal(contract.getContractId(), -7));
    }
    @Test
    public void testInitiateNegotiation_ForExpiringContracts_Successfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusDays(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // act
        alertingContractsServices.initiateNegotiations(contractOne.getContractId(), 7,LocalDate.now().plusDays(17)); // this negotiation set the end date to -> 10 + daysBeforeExpiration which means LocalDate.now.PlusDays(17)

        //assert
        Contract updatedContract = alertingContractsServices.getContractById(contractOne.getContractId());// this is contracts with updated contract endDate.
        assertEquals(ContractStatus.PENDING, updatedContract.getStatus());// checking the status after renewing,
        assertEquals(LocalDate.now().plusDays(17), updatedContract.getEndDate());// check the time after changing the end date
    }
    @Test
    public void testInitiateNegotiation_ForNonExpiringContracts_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.initiateNegotiations("CON004", 7,LocalDate.now().plusDays(17)));//if yo want to negotiate non expiring data.
    }
    @Test
    public void testInitiateNegotiation_ForNon_ExistedContracts_Unsuccessfully() {

        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.initiateNegotiations("CON005", 7,LocalDate.now().plusDays(17)));//if yo want to negotiate non existing data.
    }
    @Test
    public void testInitiateNegotiation_ForContractsWithNullId_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // act and assert.
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.initiateNegotiations(null, 7,LocalDate.now().plusDays(17)));
    }
    @Test
    public void testInitiateNegotiationOfContracts_WithNegativeDaysExpiration_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.initiateNegotiations(contractOne.getContractId(), -7,LocalDate.now().plusDays(17)));
    }
    @Test
    public void testInitiateNegotiationOfContracts_WithNewEndDateBeforeExpiringDate_Unsuccessfully() {
        // arrange
        alertingContractsServices = new AlertingContractsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        Contract contractOne = new Contract("Another Title", supplier, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(5), PaymentTerms.PREPAID, DeliveryTerms.CIF, BigDecimal.valueOf(2000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);
        contractOne.setStatus(ContractStatus.ACTIVE);

        alertingContractsServices.createContract(contract);
        alertingContractsServices.createContract(contractOne);

        // act and assert
        assertThrows(IllegalArgumentException.class, () -> alertingContractsServices.initiateNegotiations(contractOne.getContractId(), 7,LocalDate.now().plusDays(3)));
    }

    /** this is the testing for enforcing contract terms. */
    @Test
    public void testEnforcingTerms_TransactionWithinTotalValue_Success() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);

        // Act & Assert (no exception means success)
        assertEquals(contract, enforceContractsTermsServices.enforceTerms(contract.getContractId(), BigDecimal.valueOf(500.0), LocalDate.now().plusDays(22), NET_30));
    }
    @Test
    public void testEnforceTerms_TransactionExceedsTotalValue_Unsuccessfully() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);

        // Exceeds contract total value → should throw exception
        Contract result = enforceContractsTermsServices.enforceTerms(contract.getContractId(), BigDecimal.valueOf(55000.0), LocalDate.now().plusMonths(2), NET_30);

        //assert
        assertEquals(ContractStatus.INACTIVE,result.getStatus());

    }
    @Test
    public void testEnforcingTerms_TransactionWithInactiveContract_Unsuccessfully() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);
        contract.setStatus(ContractStatus.TERMINATED); // Contract is inactive

        // Transaction on inactive contract → should throw exception
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.enforceTerms(contract.getContractId(), BigDecimal.valueOf(500.0), LocalDate.now().plusDays(2), PaymentTerms.PREPAID));
    }
    @Test
    public void testEnforcingTransaction_WithNonExistentContractId_Unsuccessfully() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);
        contract.setStatus(ContractStatus.ACTIVE);

        // Contract ID does not exist → should throw exception
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.enforceTerms("CON110", BigDecimal.valueOf(500.0), LocalDate.now().plusDays(2), PaymentTerms.PREPAID));
    }
    @Test
    public void testEnforcingTransaction_WithNullContractId_Unsuccessfully() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);
        contract.setStatus(ContractStatus.ACTIVE);

        // Null contract ID → should throw exception
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.enforceTerms(null, BigDecimal.valueOf(500.0), LocalDate.now().plusDays(2), PaymentTerms.PREPAID));
    }
    @Test
    public void testEnforcingTransaction_WithNegativeTransactionValue_Unsuccessfully() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);
        contract.setStatus(ContractStatus.ACTIVE);

        // Negative transaction value → should throw exception
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.enforceTerms(contract.getContractId(), BigDecimal.valueOf(-500.0), LocalDate.now().plusDays(2), PaymentTerms.PREPAID));
    }
    @Test
    public void testEnforcingTransaction_WithInvalidDeliveryDate_Unsuccessfully() {
        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);
        contract.setStatus(ContractStatus.ACTIVE);

        // Delivery date outside contract period → should throw exception
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.enforceTerms(contract.getContractId(), BigDecimal.valueOf(500.0), LocalDate.now().plusDays(10), PaymentTerms.PREPAID));
    }
    @Test
    public void testEnforcingTransaction_WithMismatchedPaymentTerms() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);
        contract.setStatus(ContractStatus.ACTIVE);

        // Payment terms do not match → should throw exception
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.enforceTerms(contract.getContractId(), BigDecimal.valueOf(500.0), LocalDate.now().plusDays(2), PaymentTerms.NET_60));
    }
    @Test
    public void testTerminateContract_Successfully() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        Contract createdContract = enforceContractsTermsServices.createContract(contract);

        //let's check before Terminating the status.
        assertEquals(ContractStatus.ACTIVE,createdContract.getStatus());
        // act
        enforceContractsTermsServices.terminateContract(createdContract.getContractId());

        //assert
        assertEquals(ContractStatus.TERMINATED, createdContract.getStatus());
    }
    @Test
    public void testTerminateContract_NonExistedContractId() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);

        //act and assert. throw exceptions since there is no contract with contractId CON111
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.terminateContract("CON111"));
    }
    @Test
    public void testTerminateContract_WithNullId() {

        // Arrange
        enforceContractsTermsServices = new EnforceContractsTermsServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        contract.setStatus(ContractStatus.ACTIVE);

        enforceContractsTermsServices.createContract(contract);

        // act and assert.throw an exception, since the contractId is null.
        assertThrows(IllegalArgumentException.class, () -> enforceContractsTermsServices.terminateContract(null));
    }


    /** this is testing for Invoice and payment Reconciliation. */
    @Test
    public void testAddReconciledRecord_WithValidFields_Successfully() {
        //Arrange
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryReceipt.getReceiptId(), deliveryReceipt);

        // act
        PaymentReconciliation paymentReconciliationRecord = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryReceipt);

        // assert
        assertNotNull(paymentReconciliationRecord);

        assertEquals( invoice.getInvoiceId(), paymentReconciliationRecord.getInvoice().getInvoiceId());

    }
    @Test
    public void testAddReconciledRecord_WithNullElements_Unsuccessfully() {
        //arrange
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        //act and assert
        assertThrows(NullPointerException.class, () -> paymentReconciliationMaintainingService.addReconciledRecord(null,null,null));
    }
    @Test
    public void testAddReconciledRecord_MissingElements_ShouldThrowException() {
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryReceipt.getReceiptId(), deliveryReceipt);

        // act and assert, since the invoices include null element.
        assertThrows(NullPointerException.class, () -> paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,null));
        assertThrows(IllegalArgumentException.class, () -> paymentReconciliationMaintainingService.addReconciledRecord(invoice,null,deliveryReceipt));
        assertThrows(NullPointerException.class, () -> paymentReconciliationMaintainingService.addReconciledRecord(null,purchaseOrder,deliveryReceipt));

    }
    @Test
    public void testGetReconciledPaymentRecord_ByValidePaymentId_successfully(){
        //Arrange
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem23 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem23),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryReceipt.getReceiptId(), deliveryReceipt);


        PaymentReconciliation reconciliationRecord = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryReceipt);

        PaymentReconciliation result = paymentReconciliationMaintainingService.getReconciledRecordById(reconciliationRecord.getPaymentId());

        //assert
        assertEquals(reconciliationRecord,result);
    }
    @Test
    public void testGetReconciledPaymentRecord_ForNonExistedRecordByValidePaymentId_Unsuccessfully(){
        //Arrange
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryReceipt.getReceiptId(), deliveryReceipt);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryReceipt);

        assertThrows(IllegalArgumentException.class,()->paymentReconciliationMaintainingService.getReconciledRecordById("INV13"));
    }
    @Test
    public void testGetRecordsByDateRange_ShouldReturnRecords() {
        //arrange
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);


        //act
        LocalDate startDate = LocalDate.now().minusDays(4);
        LocalDate endDate = LocalDate.now().plusDays(5);
        Map<String,PaymentReconciliation> totalInvoiceRecords = paymentReconciliationMaintainingService.getPaymentReconciliationMap();
        List<PaymentReconciliation> result = paymentReconciliationMaintainingService.getRecordsByDateRange(startDate, endDate);

        // assert
        assertEquals(3, totalInvoiceRecords.size());
        assertEquals(2, result.size());
    }
    @Test
    public void testUpdateReconciledPayment_Successfully() {
        // arrange
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        PaymentReconciliation paymentReconciliation = paymentReconciliationMaintainingService.addReconciledRecord(invoice, purchaseOrder, deliveryOne);

        PaymentReconciliation updatedPayment = paymentReconciliationMaintainingService.updateReconciledPayment(paymentReconciliation.getPaymentId(), invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryTwo.getReceiptId());


        //assert
        PaymentReconciliation result = paymentReconciliationMaintainingService.getReconciledRecordById(updatedPayment.getPaymentId());

        assertEquals(paymentReconciliation.getPaymentId(),result.getPaymentId());
        assertEquals(deliveryTwo, paymentReconciliation.getDeliveryReceipt());
    }
    @Test
    public void testUpdateReconciliation_WithNonExistedInvoice_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);

        assertThrows(IllegalArgumentException.class,()-> paymentReconciliationMaintainingService.updateReconciledPayment(reconciliationOne.getPaymentId(),invoiceOne.getInvoiceId(),purchaseOrder.getOrderId(),deliveryThree.getReceiptId()));

    }
    @Test
    public void testUpdateReconciliation_WithNonExistedDelivery_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);

        PaymentReconciliation recordone = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);

        assertThrows(IllegalArgumentException.class,()-> paymentReconciliationMaintainingService.updateReconciledPayment(recordone.getPaymentId(),invoice.getInvoiceId(),purchaseOrder.getOrderId(),deliveryThree.getReceiptId()));


    }
    @Test
    public void testGettingReconciledRecordBySupplier_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);

        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationMaintainingService.getReconciledRecordsBySupplier(supplier.getSupplierId());

        assertEquals(3, paymentReconciliationList.size());

    }
    @Test
    public void testGettingReconciledRecordBysupplier_WithnotValidSupplierId_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        assertThrows(IllegalArgumentException.class,()->paymentReconciliationMaintainingService.getReconciledRecordsBySupplier(null));


    }
    @Test
    public void testGettingReconciledRecordBySupplier_WithNonExistedSupplier_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        String supplierId = IdGenerator.generateId("SUP");
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationMaintainingService.getReconciledRecordsBySupplier(supplierId);

        assertEquals(0, paymentReconciliationList.size());

    }
    @Test
    public void testGettingReconciledRecordByStatus_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);
        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationMaintainingService.getReconciledRecordsByStatus(ReconciliationStatus.UNDERPAID);

        assertEquals(3, paymentReconciliationList.size());


    }
    @Test
    public void testGettingReconciledrecord_withNonExistedStatus_ReturnEmpty(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(1500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationMaintainingService.getReconciledRecordsByStatus(ReconciliationStatus.MATCHED);

        assertEquals(0, paymentReconciliationList.size());

    }
    @Test
    public void testDeleteReconciledPayment_Successfully() {
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        Map<String, PaymentReconciliation> reconciliationMap = paymentReconciliationMaintainingService.getPaymentReconciliationMap();

        assertEquals(3, reconciliationMap.size());
        paymentReconciliationMaintainingService.deleteReconciledPayment(reconciliationOne.getPaymentId());

        assertEquals(2,reconciliationMap.size());
    }
    @Test
    public void testGetReconciledRecord_withMisMatchedAmount_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationMaintainingService.findMismatchedAmounts();
        assertEquals(3, paymentReconciliationList.size());

    }
    @Test
    public void testGettingTotalPaidAmountBySupplier_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(),purchaseOrder);

        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);

        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder,deliveryThree);

        Map<String,BigDecimal> totalCostsBySupplier = paymentReconciliationMaintainingService.summarizeTotalPaidBySupplier();
        assertEquals(Map.of(supplier.getSupplierId(),BigDecimal.valueOf(60000.0)), totalCostsBySupplier);

    }

    /** this is the unit test for matching invoice with purchase order and delivery receipts. */
    @Test
    public void testReconcileInvoice_WhenPaid_Successfully() { //matching with no discrepancy amount.
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);
        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),LocalDate.now());

        assertEquals(BigDecimal.valueOf(0.0), paymentReconciliation.getDiscrepancyAmount().setScale(1));//matching the whole at the same time.
        assertEquals(ReconciliationStatus.MATCHED,paymentReconciliation.getReconciliationStatus());
    }
    @Test
    public void testReconcileInvoice_WhenOverPaid_Successfully() { //over Paid
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2500.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),LocalDate.now());

        assertEquals(BigDecimal.valueOf(500.0), paymentReconciliation.getDiscrepancyAmount().setScale(1));
        assertEquals(ReconciliationStatus.OVERPAID,paymentReconciliation.getReconciliationStatus()); // over paid.
    }
    @Test
    public void testReconcileInvoice_WhenUnderPaid_Successfully() { //over Paid
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,4);
        RequestedItem requestedItem1 = new RequestedItem(item2,4);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,4);
        InvoicedItem invoicedItem1 = new InvoicedItem(item2,4);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier, purchaseOrder,List.of(invoicedItem,invoicedItem1),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem deliveredItem = new DeliveredItem(item,4);


        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(deliveredItem), LocalDate.now().plusMonths(10),"Addiss Ababa,Ethiopia",requester,"");

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());
        assertEquals(BigDecimal.valueOf(500.0), paymentReconciliation.getDiscrepancyAmount().setScale(1));
        assertEquals(ReconciliationStatus.UNDERPAID,paymentReconciliation.getReconciliationStatus()); // over paid.
    }
    @Test
    public void testReconcileInvoice_WhenPartialDelivered_Successfully() { // Mismatch in quantity among invoiced and delivered.

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,5);

        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(3000.0),BigDecimal.valueOf(1000.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem deliveredItem = new DeliveredItem(item,2);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(deliveredItem), LocalDate.now().minusDays(10),"",requester,"");

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        PaymentReconciliation result = invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),LocalDate.now());

        assertEquals(DeliveryStatus.PARTIAL, deliveryReceipt.getDeliveryStatus());
        assertEquals(BigDecimal.valueOf(1200.0), result.getDiscrepancyAmount().setScale(1));
        assertEquals(ReconciliationStatus.UNDERPAID, result.getReconciliationStatus());
    }
    @Test
    public void testInvoiceReconciliation_whenNoOrderedItem_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);

        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        assertThrows(IllegalArgumentException.class,()->invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate()));

    }
    @Test
    public void testInvoiceReconciliation_whenOrderedNotInvoiced_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        assertThrows(IllegalArgumentException.class,()->invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate()));

    }
    @Test
    public void testInvoiceReconciliation_whenInvoicedNotOrdered_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);


        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item1,6);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        assertThrows(IllegalArgumentException.class,()->invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate()));

    }
    @Test
    public void testInvoiceReconciliation_whenNoDeliveryReceipts_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);
PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);

        assertThrows(IllegalArgumentException.class,()->invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate()));

    }
    @Test
    public void testInvoiceReconciliation_whenDeliveryMorethanInvoicedQuantity_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");


        DeliveredItem receivedItem = new DeliveredItem(item,4);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        assertThrows(IllegalArgumentException.class,()->invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate()));

    }
    @Test
    public void testInvoiceReconciliation_whenMisMatchInSupplier_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);
PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplierOne, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        assertThrows(IllegalArgumentException.class,()->invoiceReconciliationServices.reconcileInvoice(invoice.getInvoiceId(), purchaseOrder.getOrderId(),deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate()));

    }

    /** test for Enforcement Of Payment Terms of Contract. */
    @Test
    public void testEnforcePaymentTermsOfContract_Successfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId());

        //assert
        PaymentReconciliation paymentReconciliation = contractPaymentTermsEnforcementService.getPaymentReconciliationByInvoiceId(invoice.getInvoiceId());
        assertEquals(invoice.getInvoiceId(), paymentReconciliation.getInvoice().getInvoiceId());
        assertEquals(purchaseOrder.getOrderId(), paymentReconciliation.getPurchaseOrder().getOrderId());
        assertEquals(ReconciliationStatus.MATCHED,paymentReconciliation.getReconciliationStatus());
    }
    @Test
    public void testEnforcePaymentTermsOfContract_withNullContractId_Unsuccessfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(null));

    }
    @Test
    public void testEnforcePaymentTermsOfContractt_WithNonExistedContract_Unsuccessfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm("CON-101"));

    }

    @Test
    public void testEnforcePaymentTermsOfContract_WithNonExistedSupplier_Unsuccessfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));

    }
    @Test
    public void testEnforcePaymentTermsOfContract_WithMissMatchOfSupplierAmongContractAndPurchaseOrder_Unsuccessfully(){
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("IBM", "Vendor", "1763", "41244", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addSupplier(supplierOne);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));

    }
    @Test
    public void testEnforcePaymentTermsOfContract_WithNotExistedPurchaseOrder_Unsuccessfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));

    }
    @Test
    public void testEnforcePaymentTermsOfContract_WithNotExistedInvoice_Unsuccessfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);

        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);

        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));
    }

    @Test
    public void testEnforcePaymentTermsOfContract_WithNotExistedDeliveryReceipt_Unsuccessfully() {
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));
    }
    @Test
    public void testEnforcePaymentTermsOfContract_WithPaymentDateAfterContractEndDate_unsuccessfully(){
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now().plusMonths(12), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));
    }
    @Test
    public void testEnforcePaymentTermsOfContract_WithExpectedAmountGreaterThanContractTotalCost_unsuccessfully(){
        contractPaymentTermsEnforcementService = new ContractPaymentTermsEnforcementService();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract( "Contract with Cisco", supplier, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(purchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));

        InvoicedItem invoicedItem = new InvoicedItem(item,5);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,5);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().plusMonths(4),"",requester,"");

        contractPaymentTermsEnforcementService.addSupplier(supplier);
        contractPaymentTermsEnforcementService.addPurchaseOrder(purchaseOrder);
        contractPaymentTermsEnforcementService.addInvoice(invoice);
        contractPaymentTermsEnforcementService.addDeliveryReceipt(deliveryReceipt);
        contractPaymentTermsEnforcementService.addContract(contract);
        assertThrows(IllegalArgumentException.class, () -> contractPaymentTermsEnforcementService.enforceContractPaymentTerm(contract.getContractId()));
    }

    /** testing for storing supplier Performance History.*/
    @Test
    public void testStoreAndRetrievePerformanceData_Successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());


        List<SupplierPerformance> history = storingSupplierPerformanceHistoryService.getPerformanceHistory(supplier.getSupplierId());

        assertEquals(2, history.size());
        assertEquals(quantitativePerformanceMetrics, history.get(0).getQuantitativePerformanceMetrics());
        assertEquals(newQuantitativePerformanceMetrics,history.get(1).getQuantitativePerformanceMetrics());
    }
    @Test
    public void testStorePerformanceData_NullSupplier_Unsuccessfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        assertThrows(NoSuchElementException.class, () ->storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5)));
    }
    @Test
    public void testStorePerformanceData_NullSupplierId_Unsuccessfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        assertThrows(IllegalArgumentException.class, () ->         storingSupplierPerformanceHistoryService.addSupplierPerformance(null,quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5)));
    }
    @Test
    public void testAnalyzePerformanceTrends_Successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());

        List<SupplierPerformance> supplierPerformanceList = storingSupplierPerformanceHistoryService.getPerformanceHistory(supplier.getSupplierId());


        Map<LocalDate, SupplierPerformanceRate> trends = storingSupplierPerformanceHistoryService.analyzePerformanceTrends(supplier.getSupplierId());

        assertEquals(2, trends.size());
        assertEquals(SupplierPerformanceRate.EXCELLENT, trends.get(supplierPerformanceList.get(0).getEvaluationDate()));
    }
    @Test
    public void testGetSortedPerformanceForSupplier_Successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());


        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());

        List<SupplierPerformance> sorted = storingSupplierPerformanceHistoryService.getSortedPerformanceForSupplier(supplier.getSupplierId());

        assertEquals(newQuantitativePerformanceMetrics, sorted.get(0).getQuantitativePerformanceMetrics());
        assertEquals(quantitativePerformanceMetrics, sorted.get(1).getQuantitativePerformanceMetrics());
    }
    @Test
    public void testGetPerformanceSortedByReviewDate_successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());

        List<SupplierPerformance> supplierPerformanceList = storingSupplierPerformanceHistoryService.getPerformanceHistory(supplier.getSupplierId());
       SupplierPerformance performance1 = supplierPerformanceList.get(0);
       SupplierPerformance performance2 = supplierPerformanceList.get(1);
        List<SupplierPerformance> sorted = storingSupplierPerformanceHistoryService.getPerformanceSortedByReviewDate(supplier.getSupplierId());

        assertEquals(performance1, sorted.get(0));
        assertEquals(performance2, sorted.get(1));
    }
    @Test
    public void testGetSuppliersSortedByAveragePerformance_Successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());



        Supplier supplierOne = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplierOne.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());

        List<Supplier> sorted = storingSupplierPerformanceHistoryService.getSuppliersSortedByAveragePerformance();
        assertEquals(supplierOne, sorted.get(0));
        assertEquals(supplier, sorted.get(1));
    }
    @Test
    public void testGetSuppliersSortedByLatestReview_Successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        Supplier supplierOne = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplierOne.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());

        List<Supplier> sorted = storingSupplierPerformanceHistoryService.getSuppliersSortedByLatestReview();
        assertEquals(supplierOne, sorted.getFirst());
    }
    @Test
    public void testGetPerformancesInDateRange_Successfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics newQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 0, 96, 90, 85, 10, 0);
        SupplierQualitativePerformanceMetrics newQualitativePerformanceMetrics =  new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        storingSupplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),quantitativePerformanceMetrics,qualitativePerformanceMetrics,LocalDate.now().minusMonths(5));

        storingSupplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(),newQuantitativePerformanceMetrics,newQualitativePerformanceMetrics,LocalDate.now());

        List<SupplierPerformance> results = storingSupplierPerformanceHistoryService.getPerformancesInDateRange(LocalDate.of(2024, 4, 1), LocalDate.now().plusDays(3));
        assertEquals(2, results.size());
    }
    @Test
    public void testGetPerformancesInDateRange_InvalidRange_Unsuccessfully() {
        storingSupplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        performance.setEvaluationDate(LocalDate.of(2024, 5, 1));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        assertThrows(IllegalArgumentException.class, () -> storingSupplierPerformanceHistoryService.getPerformancesInDateRange(LocalDate.of(2024, 6, 1), LocalDate.of(2024, 4, 1)));
    }

    /** testing for linking supplier performance metrics with procurement activity. */
    @Test
    public void testLinkSupplierPerformanceMetrics_WithPurchaseRequisitionToApprove_Successfully(){ //when selecting the right supplier.
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId());
        assertEquals(APPROVED, purchaseRequisition.getRequisitionStatus());
    }
    @Test
    public void testLinkSupplierPerformanceMetrics_WithPurchaseRequisitionToReject_Successfully(){ //when selecting supplier with less performance metrics.and has supplier with high supplier performance metrics.
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId());
        assertEquals(REJECTED, purchaseRequisition.getRequisitionStatus());
    }
    @Test
    public void testLinkSpecificPerformanceMetricsWithPurchaseOrderToApprove_Successfully() { // when selecting the right supplier.
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnSpecificPerformanceMetrics(purchaseOrder.getOrderId());

        assertEquals(PurchaseOrderStatus.APPROVED, purchaseOrder.getPurchaseOrderStatus());
    }
    @Test
    public void testLinkSpecificPerformanceMetricsWithPurchaseOrderToReject_Successfully() { // when selecting the wrong supplier.
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplier);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplier,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnSpecificPerformanceMetrics(purchaseOrder.getOrderId());

        assertEquals(PurchaseOrderStatus.TERMINATED, purchaseOrder.getPurchaseOrderStatus());
    }
    @Test
    public void testLinkSpecificPerformanceMetrics_WithContractToApproved_Successfully() { // when the supplier performance metrics is good.
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        // on this take supplier not supplier one.
        Contract contract = new Contract("this is contract for National Bank",supplier,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        linkingSupplierPerformanceMetricsService.linkContractBasedOnSpecificPerformanceMetrics(contract.getContractId());
        assertEquals(ContractStatus.ACTIVE, contract.getStatus());
    }
    @Test
    public void testLinkSpecificPerformanceMetrics_WithContractToTerminate_Successfully() { // when we select the wrong supplier.
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("this is contract for National Bank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        linkingSupplierPerformanceMetricsService.linkContractBasedOnSpecificPerformanceMetrics(contract.getContractId());
        assertEquals(ContractStatus.TERMINATED, contract.getStatus());
    }

    // negative tests. for linking specific performance metrics to procurement activity.
    @Test
    public void testLinkPurchaseRequisition_WithNotEvaluatedSupplier_fails(){
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Mouse", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier, new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0), new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne, new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0), new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        assertThrows(IllegalArgumentException.class, () -> linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId()));
    }
    @Test
    public void testLinkPurchaseRequisition_WithNonExistedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Mouse", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier, new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0), new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne, new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0), new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        assertThrows(IllegalArgumentException.class, () -> linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId()));
    }

    @Test
    public void testLinkPurchaseOrder_WithNotEvaluatedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        assertThrows(IllegalArgumentException.class, () -> linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnSpecificPerformanceMetrics(purchaseOrder.getOrderId()));
    }
    @Test
    public void testLinkPurchaseOrder_WithNonExistedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        assertThrows(IllegalArgumentException.class, () -> linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnSpecificPerformanceMetrics(purchaseOrder.getOrderId()));
    }
    @Test
    public void testLinkContract_WithNotEvaluatedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        // on this take supplier not supplier one.
        Contract contract = new Contract("this is contract for National Bank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        assertThrows(IllegalArgumentException.class,()-> linkingSupplierPerformanceMetricsService.linkContractBasedOnSpecificPerformanceMetrics(contract.getContractId()));
    }
    @Test
    public void testLinkContract_WithNonExistedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplier);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplier,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        // on this take supplier not supplier one.
        Contract contract = new Contract("this is contract for National Bank",supplier,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        assertThrows(IllegalArgumentException.class,()-> linkingSupplierPerformanceMetricsService.linkContractBasedOnSpecificPerformanceMetrics(contract.getContractId()));
    }

    /** test for linking Procurement Activity based on General supplier performance.*/
    @Test
    public void testLinkPurchaseRequisitionBasedOnGeneralSupplierPerformanceToApprove_Successfully() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId());
        assertEquals(APPROVED, purchaseRequisition.getRequisitionStatus());
    }
    @Test
    public void testLinkPurchaseRequisitionBasedOnGeneralSupplierPerformanceToReject_Successfully() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId());
        assertEquals(REJECTED, purchaseRequisition.getRequisitionStatus());
    }

    @Test
    public void testLinkPurchaseRequisitionBasedOnGeneralSupplierPerformance_WithNonExistedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        assertThrows(IllegalArgumentException.class, () -> linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnSpecificPerformanceMetrics(purchaseRequisition.getRequisitionId()));
    }
    @Test
    public void testLinkPurchaseRequisitionBasedOnGeneralSupplierPerformance_WithNotEvaluatedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(92, 4, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();

        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now().minusMonths(1));
        purchaseRequisition.setDepartment(department);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setRequestedBy(requester);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(5));
        purchaseRequisition.setJustification("need new equipments");
        purchaseRequisition.setSupplier(supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        assertThrows(IllegalArgumentException.class, () -> linkingSupplierPerformanceMetricsService.linkPurchaseRequisitionBasedOnOverallPerformance(purchaseRequisition.getRequisitionId()));
    }
    @Test
    public void testLinkPurchaseOrderBasedOnGeneralSupplierPerformanceToApprove_Successfully() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplier);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplier,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnOverallPerformance(purchaseOrder.getOrderId());
        assertEquals(PurchaseOrderStatus.APPROVED,purchaseOrder.getPurchaseOrderStatus());
    }
    @Test
    public void testLinkPurchaseOrderBasedOnGeneralSupplierPerformanceToRejected_Successfully() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnOverallPerformance(purchaseOrder.getOrderId());
        assertEquals(PurchaseOrderStatus.TERMINATED,purchaseOrder.getPurchaseOrderStatus());
    }
    @Test
    public void testLinkPurchaseOrderBasedOnGeneralSupplierPerformance_WithNonEvaluatedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        assertThrows(IllegalArgumentException.class,()-> linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnOverallPerformance(purchaseOrder.getOrderId()));
    }
    @Test
    public void testLinkPurchaseOrderBasedOnGeneralSupplierPerformance_WithNotExistedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        assertThrows(IllegalArgumentException.class,()-> linkingSupplierPerformanceMetricsService.linkPurchaseOrderBasedOnOverallPerformance(purchaseOrder.getOrderId()));
    }
    @Test
    public void testLinkContractBasedOnGeneralSupplierPerformanceToApprove_Successfully() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        // on this take supplier not supplier one.
        Contract contract = new Contract("this is contract for National Bank",supplier,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        Contract linkedContract = linkingSupplierPerformanceMetricsService.linkContractBasedOnOverallPerformance(contract.getContractId());

        assertEquals(contract.getContractId(), linkedContract.getContractId());
        assertEquals(ContractStatus.ACTIVE, linkedContract.getStatus());
    }
    @Test
    public void testLinkContractBasedOnGeneralSupplierPerformanceToTerminate_Successfully() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 90, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 90, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        // on this take supplier not supplier one.
        Contract contract = new Contract("this is contract for National Bank",supplier,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        Contract linkedContract = linkingSupplierPerformanceMetricsService.linkContractBasedOnOverallPerformance(contract.getContractId());

        assertEquals(contract.getContractId(), linkedContract.getContractId());
        assertEquals(ContractStatus.TERMINATED, linkedContract.getStatus());
    }
    @Test
    public void testLinkContractBasedOnGeneralSupplierPerformance_WithNonExistedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        // on this take supplier not supplier one.
        Contract contract = new Contract("this is contract for National Bank",supplier,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        assertThrows(IllegalArgumentException.class,()-> linkingSupplierPerformanceMetricsService.linkContractBasedOnOverallPerformance(contract.getContractId()));
    }
    @Test
    public void testLinkContractBasedOnGeneralSupplierPerformance_WithNotEvaluatedSupplier_Fails() {
        linkingSupplierPerformanceMetricsService = new LinkingSupplierPerformanceMetricsService();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@Ibm.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979421513", "Ethiopia");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284396555", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier("Cisco", "Vendor", "17663", "412443", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 8, 8, 10),LocalDate.now());
        SupplierPerformance performance1 = new SupplierPerformance(supplierOne,
                new SupplierQuantitativePerformanceMetrics(98, 2, 90, 80, 95, 1, 0),
                new SupplierQualitativePerformanceMetrics(2, 3, 7, 9, 8, 10),LocalDate.now());

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem1 = new RequestedItem(item, 3);
        RequestedItem requestedItem2 = new RequestedItem(item1, 3);
        RequestedItem requestedItem3 = new RequestedItem(item2, 3);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition( "REQ-001", LocalDate.now().minusMonths(1),  requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(5), "need new equipments");
        purchaseRequisition.addItem(requestedItem1);
        purchaseRequisition.addItem(requestedItem2);
        purchaseRequisition.addItem(requestedItem3);

        purchaseRequisition.setSupplier(supplierOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(purchaseRequisition),LocalDate.now(),supplierOne,"Air",LocalDate.now().plusMonths(5));

        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("this is contract for National Bank",supplier,LocalDate.now(),LocalDate.now().plusMonths(4),NET_30,DeliveryTerms.CIF,purchaseOrder.getTotalCost().add(BigDecimal.valueOf(10000.0)),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        linkingSupplierPerformanceMetricsService.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        linkingSupplierPerformanceMetricsService.getSupplierPerformanceList().add(performance1);

        linkingSupplierPerformanceMetricsService.getPurchaseRequisitionMap().put(purchaseRequisition.getRequisitionId(), purchaseRequisition);
        linkingSupplierPerformanceMetricsService.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);
        linkingSupplierPerformanceMetricsService.getContractMap().put(contract.getContractId(), contract);

        assertThrows(IllegalArgumentException.class,()-> linkingSupplierPerformanceMetricsService.linkContractBasedOnOverallPerformance(contract.getContractId()));
    }
    /** testing for Reporting and Analysis of procurement management system.*/
    @Test
    public void testGeneratingTimeBasedReport_WithSameActivity_Successfully() {
        //arrange
        procurementReportGeneratingService = new ProcurementReportGenerationServices();

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now().minusDays(30), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisition.addItem(items);
        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now().minusDays(20), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(4), "");
        requisitionOne.addItem(items);
        User reporter = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "ReporterContactDetail");

        //act
        procurementReportGeneratingService.addPurchaseRequisition(requisition);
        procurementReportGeneratingService.addPurchaseRequisition(requisitionOne);
        String title = "TimeBased Procurement Report for Purchase Requisition";
        String reportType = "TimeBased";
        ProcurementReport procurementReport = procurementReportGeneratingService.generateTimeBasedProcurementReport( reporter,List.of( ProcurementActivity.PURCHASE_REQUISITION), LocalDate.now().minusMonths(3), LocalDate.now(),LocalDate.now(),title,reportType);

        //assert
        assertNotNull(procurementReport.getRequisitionReport());
        assertEquals(2,procurementReport.getRequisitionReport().getRequisitionsByPriority().size());
        assertEquals(1,procurementReport.getRequisitionReport().getRequisitionsByPriority().get(PriorityLevel.HIGH).doubleValue());

    }
    @Test
    public void testGenerateReportByTimeRange_WithDifferentActivity_Successfully() {
        //arrange
        procurementReportGeneratingService = new ProcurementReportGenerationServices();
        Budget budget = new Budget(BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "USD");
        Department department = new Department("Software Development", "Bizeff Software Company", budget);
        CostCenter costCenter = new CostCenter( "BackEnd development", "Back End development using the clean code architecture.");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("hager", "hager@gmail.com", "+251975487598", "Addis Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod( "Lion Bank", "00311237239-03", "Tekia Tekle", "+251979417636",List.of(CASH,BANK_TRANSFER),CASH , NET_30,"Birr", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier("SupplierOne", "vendor", "2441575", "0396", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        User requester = new User("new Requester", "requester@gmail.com", "+251798321516", department, "requester");
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now().minusDays(3), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(2), "new generation");
        requisition.addItem(items);
        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Ship", LocalDate.now().plusMonths(2));

        ContractFile attachments = new ContractFile("ContractFile", "pdf", "newFolder/file", LocalDate.now());
        Contract contract = new Contract( "Contract With Mekelle University", supplier, LocalDate.now(), LocalDate.now().plusDays(30), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(49000.0), true, List.of(purchaseOrder), List.of(attachments),LocalDate.now().minusDays(3));
        User reporter = new User("reporter One", "reporter@gmail.com", "+251978457896", department, "reporter");


        //act
        procurementReportGeneratingService.addSupplier(supplier);
        procurementReportGeneratingService.addPurchaseOrder(purchaseOrder);
        procurementReportGeneratingService.addContract(contract);
        String title = "TimeBased Procurement Report With Different Activity";
        String reportType = "TimeBased";
        ProcurementReport procurementReport = procurementReportGeneratingService.generateTimeBasedProcurementReport(reporter,List.of(ProcurementActivity.SUPPLIER_MANAGEMENT,ProcurementActivity.PURCHASE_ORDER,ProcurementActivity.CONTRACT_MANAGEMENT), LocalDate.now().minusMonths(1), LocalDate.now().plusMonths(1),LocalDate.now(),title,reportType);

        //assert
        assertNotNull(procurementReport.getSupplierReport());
        assertNotNull(procurementReport.getPurchaseOrderReport());
        assertNotNull(procurementReport.getContractReport());
    }

    @Test
    public void testGenerateReport_WithNullProcurementActivity_Unsuccessfully() {
        //arrange
        procurementReportGeneratingService = new ProcurementReportGenerationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now().minusDays(30), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisition.addItem(items);
        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now().minusDays(20), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisitionOne.addItem(items);

        User reporter = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "ReporterContactDetail");

        procurementReportGeneratingService.addPurchaseRequisition(requisition);
        procurementReportGeneratingService.addPurchaseRequisition(requisitionOne);
        String title = "TimeBased Procurement Report With Null Activity";
        String reportType = "TimeBased";
        assertThrows(IllegalArgumentException.class, () -> procurementReportGeneratingService.generateTimeBasedProcurementReport(reporter,List.of(), LocalDate.now().minusMonths(3), LocalDate.now(),LocalDate.now(),title,reportType));
    }
    @Test
    public void testGenerateReport_WithNullReporter_Unsuccessfully() {
        //arrange
        procurementReportGeneratingService = new ProcurementReportGenerationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now().minusDays(30), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now().minusDays(20), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisitionOne.addItem(items);

        //act
        procurementReportGeneratingService.addPurchaseRequisition(requisition);
        procurementReportGeneratingService.addPurchaseRequisition(requisitionOne);
        String title = "TimeBased Procurement Report With Null Reporter";
        String reportType = "TimeBased";
        //assert
        assertThrows(IllegalArgumentException.class, () -> procurementReportGeneratingService.generateTimeBasedProcurementReport(null,List.of(ProcurementActivity.PURCHASE_REQUISITION), LocalDate.now().minusMonths(3), LocalDate.now(),LocalDate.now(),title,reportType));

    }
    @Test
    public void testGenerateReportWithInvalidDateRange_Unsuccessfully() {
        //arrange
        procurementReportGeneratingService = new ProcurementReportGenerationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        User requester = new User("tekia", "tekia2034@gmail.com", "+251979417636", department, "developer");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now().minusDays(30), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now().minusDays(20), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(4), "");
        requisitionOne.addItem(items);

        User reporter = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "ReporterContactDetail");


        procurementReportGeneratingService.addPurchaseRequisition(requisition);
        procurementReportGeneratingService.addPurchaseRequisition(requisitionOne);
        String title = "TimeBased Procurement Report With Invalid Date range";
        String reportType = "TimeBased";
        //act and assert
        assertThrows(IllegalArgumentException.class, () -> procurementReportGeneratingService.generateTimeBasedProcurementReport(reporter,List.of(ProcurementActivity.PURCHASE_REQUISITION), LocalDate.now().plusDays(3), LocalDate.now(),LocalDate.now(),title,reportType));
    }
    @Test
    public void testGenerateReport_WithNonExistingActivity_Unsuccessfully() {
        procurementReportGeneratingService = new ProcurementReportGenerationServices();
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        User reporter = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "ReporterContactDetail");
        List<ProcurementActivity> procurementActivities = List.of(ProcurementActivity.PURCHASE_REQUISITION, ProcurementActivity.SUPPLIER_MANAGEMENT,ProcurementActivity.PURCHASE_ORDER,ProcurementActivity.CONTRACT_MANAGEMENT,ProcurementActivity.INVOICE_MANAGEMENT,ProcurementActivity.DELIVERY_RECEIPT,ProcurementActivity.PAYMENT_RECONCILIATION, ProcurementActivity.SUPPLIER_PERFORMANCE);
        String title = "TimeBased Procurement Report With Non Existing Activity";
        String reportType = "TimeBased";
        assertThrows(IllegalArgumentException.class,()-> procurementReportGeneratingService.generateTimeBasedProcurementReport(reporter,procurementActivities,LocalDate.now().minusMonths(3),LocalDate.now(),LocalDate.now(),title,reportType));
    }

    /**testing for aggregation of total spending, supplier performance, requisitionStatus. */
    @Test
    public void testAggregateTotalSpendingWhenOrderingItems_WithValidDate_Successfully() {

        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItems( LocalDate.now().minusDays(21), LocalDate.now().plusDays(10));
        assertEquals(BigDecimal.valueOf(25000.00).setScale(2), total);
    }
    @Test
    public void testAggregateTotalSpendingWhenOrderingItem_WithSameDepartment_Successfully(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department", "Department", budget);

        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");

        Department departmentOne = new Department("departmentOne", "new Department", budgetOne);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, departmentOne, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(departmentOne,List.of(requisitionOne), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItemsByDepartment(department.getDepartmentId(), LocalDate.now().minusDays(21), LocalDate.now().plusDays(10));
        assertEquals(BigDecimal.valueOf(15000.00).setScale(2), total);
    }

    @Test
    public void testAggregateTotalSpendingWhenOrderingItem_WithSameSupplier_Successfully(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItemsBySupplier(supplier.getSupplierId(), LocalDate.now().minusDays(21), LocalDate.now().plusDays(10));
        assertEquals(BigDecimal.valueOf(15000.00).setScale(2), total);
    }
    @Test
    public void testAggregateTotalSpendingWhenOrderingItem_WithSameSupplierAndDepartment_Successfully(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItemsByDepartmentAndSupplier(department.getDepartmentId(), supplierOne.getSupplierId(), LocalDate.now().minusDays(21), LocalDate.now().plusDays(10));
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2), total);
    }

    @Test
    public void testAggregateTotalSpendingWhenOrderingItems_withInvalidDate_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItems( LocalDate.now().plusDays(21), LocalDate.now().plusDays(10)));
    }
    @Test
    public void testAggregateTotalSpendingWhenOrderingItems_WithNonExistedDepartment_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItemsByDepartment("DEP-001", LocalDate.now().minusDays(21), LocalDate.now().plusDays(10)));

    }
    @Test
    public void testAggregateTotalSpendingWhenOrderingItems_WithNonExistedSupplier_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItemsBySupplier("SUP-101", LocalDate.now().minusDays(21), LocalDate.now().plusDays(10)));

    }
    @Test
    public void testAggregateTotalSpendingWhenOrderingItems_WithNonExistedSupplierAndDepartment_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenOrderingItemsByDepartmentAndSupplier("DEP-010","SUP--101", LocalDate.now().minusDays(21), LocalDate.now().plusDays(10)));

    }

    // testing for expected total spending when requesting items
    @Test
    public void testExpectedTotalSpendingWhenRequestingItems_WithValidDate_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("department one","this is software department",budget);
        CostCenter costCenter = new CostCenter("CostCenter One","new Cost Center");
        User requester = new User("tekia Tekle","tekia@gmail.com","+251979157646",department,"programmer");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusMonths(3),"we are new department");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);

        BigDecimal total = procurementReportDataAggregationService.expectedSpendingWhenRequestingItemsByTimeRange(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));
        assertEquals(BigDecimal.valueOf(25000.00).setScale(2), total);
    }
    @Test
    public void testExpectedTotalSpendingWhenRequestingItems_WithSameDepartment_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department", "Department", budget);

        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");

        Department departmentOne = new Department("departmentOne", "new Department", budgetOne);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, departmentOne, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addDepartment(departmentOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);

        BigDecimal total = procurementReportDataAggregationService.expectedSpendingWhenRequestingItemsByDepartment(department.getDepartmentId());
        assertEquals(BigDecimal.valueOf(15000.00).setScale(2), total);
    }
    @Test
    public void testExpectedTotalSpendingWhenRequestingItems_forSingleRequisition_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department", "Department", budget);

        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");

        Department departmentOne = new Department("departmentOne", "new Department", budgetOne);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, departmentOne, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addDepartment(departmentOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);

        BigDecimal total = procurementReportDataAggregationService.expectedspendingWhenRequestingItems(requisitionOne.getRequisitionId());
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2), total);
    }

    @Test
    public void testExpectedTotalSpendingWhenRequestingItems_WithInvalidDate_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department", "Department", budget);

        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");

        Department departmentOne = new Department("departmentOne", "new Department", budgetOne);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, departmentOne, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addDepartment(departmentOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);

        assertThrows(IllegalArgumentException.class,()-> procurementReportDataAggregationService.expectedSpendingWhenRequestingItemsByTimeRange(LocalDate.now().plusDays(22),LocalDate.now().plusDays(12)));
    }
    @Test
    public void testExpectedTotalSpendingWhenRequestingItems_WithNonExistedDepartment_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department", "Department", budget);

        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");

        Department departmentOne = new Department("departmentOne", "new Department", budgetOne);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        procurementReportDataAggregationService.addDepartment(department);

        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.expectedSpendingWhenRequestingItemsByDepartment(departmentOne.getDepartmentId()));
    }

    @Test
    public void testExpectedTotalSpendingWhenRequestingItems_WithNonExistedRequisition_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department", "Department", budget);

        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");

        Department departmentOne = new Department("departmentOne", "new Department", budgetOne);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, departmentOne, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addDepartment(departmentOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.expectedspendingWhenRequestingItems("Req-101"));
    }

    // total spending when preparing contract
    @Test
    public void testAggregateTotalSpendingforSingleContract_WithValidContractId_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));


        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("Contract For NationalBank",supplier,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        Contract contractOne = new Contract("Contract For NationalBank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrderOne),List.of(contractFile),LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addContract(contract);
        procurementReportDataAggregationService.addContract(contractOne);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingforSingleContract(contract.getContractId());
        assertEquals(BigDecimal.valueOf(15000.00).setScale(2), total);
    }
    @Test
    public void testAggregateTotalSpendingforSingleContract_WithSameSupplier_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));


        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("Contract For NationalBank",supplier,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        Contract contractOne = new Contract("Contract For NationalBank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrderOne),List.of(contractFile),LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addContract(contract);
        procurementReportDataAggregationService.addContract(contractOne);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingforSingleContract(contract.getContractId());
        assertEquals(BigDecimal.valueOf(15000.00).setScale(2), total);
    }
    @Test
    public void testAggregateTotalSpendingforSingleContract_WithValidTimeRange_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));


        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("Contract For NationalBank",supplier,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        Contract contractOne = new Contract("Contract For NationalBank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrderOne),List.of(contractFile),LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addContract(contract);
        procurementReportDataAggregationService.addContract(contractOne);

        BigDecimal total = procurementReportDataAggregationService.aggregateTotalSpendingWhenPreparingContract(LocalDate.now().minusMonths(2),LocalDate.now().plusMonths(2));
        assertEquals(BigDecimal.valueOf(50000.00).setScale(2), total);
    }

    @Test
    public void testAggregateTotalSpendingforSingleContract_WithInvalidContractId_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));


        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("Contract For NationalBank",supplier,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        Contract contractOne = new Contract("Contract For NationalBank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrderOne),List.of(contractFile),LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addContract(contract);
        procurementReportDataAggregationService.addContract(contractOne);

        assertThrows(IllegalArgumentException.class,()-> procurementReportDataAggregationService.aggregateTotalSpendingforSingleContract("CN-101"));

    }
    @Test
    public void testAggregateTotalSpendingforSingleContract_WithNonExistedSupplier_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));


        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("Contract For NationalBank",supplier,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        Contract contractOne = new Contract("Contract For NationalBank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrderOne),List.of(contractFile),LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addContract(contract);
        procurementReportDataAggregationService.addContract(contractOne);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenPreparingContractBySupplier("SUP101",LocalDate.now().minusMonths(2),LocalDate.now().plusMonths(2)));
    }
    @Test
    public void testAggregateTotalSpendingforSingleContract_WithInvalidTimeRange_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("departmentOne", "new Department", budget);
        CostCenter costCenter = new CostCenter( "CostCenterOne", "First CostCenter");
        User requester = new User("requester", "requester@gmail.com", "+251979417636", department, "requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,3);
        RequestedItem itemsOne = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisitionOne.addItem(itemsOne);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("supp", "supp@gmail.com", "+251714417636", "Addiss Ababa");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("suppp", "suppp@gmail.com", "+251914417636", "Addiss Ababa");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Enat Bank", "10123129048589", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "supplier", "IT Industry", "ADS11212", "11212", supplierContactDetail, List.of(paymentMethod), List.of(inventory), LocalDate.now());
        Supplier supplierOne = new Supplier( "supplier One", "IT Industry", "ADS10212", "10212", supplierContactDetailOne, List.of(paymentMethod), List.of(inventory), LocalDate.now());

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department,List.of(requisition), LocalDate.now(), supplier,"Plane", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne), LocalDate.now(), supplierOne,"Plane", LocalDate.now().plusMonths(2));


        ContractFile contractFile = new ContractFile("nationalBankBid","Pdf","tekie/one.pdf",LocalDate.now());

        Contract contract = new Contract("Contract For NationalBank",supplier,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrder),List.of(contractFile),LocalDate.now());
        Contract contractOne = new Contract("Contract For NationalBank",supplierOne,LocalDate.now(),LocalDate.now().plusMonths(2), NET_30,DeliveryTerms.CIF,BigDecimal.valueOf(25000.00),true,List.of(purchaseOrderOne),List.of(contractFile),LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addContract(contract);
        procurementReportDataAggregationService.addContract(contractOne);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenPreparingContract(LocalDate.now().plusMonths(2),LocalDate.now()));
    }

    // total spending when Invoice is made.
    @Test
    public void testAggregateTotalSpendingWhenInvoiceIsMade_WithValidInvoiceId_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingforSingleInvoice(invoice.getInvoiceId());
        BigDecimal totalSpendingOne = procurementReportDataAggregationService.aggregateTotalSpendingforSingleInvoice(invoiceOne.getInvoiceId());
        BigDecimal totalSpendingTwo = procurementReportDataAggregationService.aggregateTotalSpendingforSingleInvoice(invoiceTwo.getInvoiceId());

        assertEquals(BigDecimal.valueOf(30000.0).setScale(2), totalSpending);
        assertEquals(BigDecimal.valueOf(20000.0).setScale(2), totalSpendingOne);
        assertEquals(BigDecimal.valueOf(10000.0).setScale(2), totalSpendingTwo);
    }
    @Test
    public void testAggregateTotalSpendingWhenInvoiceIsMade_WithValidTimeRange_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingWhenInvoiceIsMade(LocalDate.now().minusDays(30),LocalDate.now().plusDays(15));
        assertEquals(BigDecimal.valueOf(60000.0).setScale(2), totalSpending);
    }
    @Test
    public void testAggregateTotalSpendingWhenInvoiceIsMade_WithSameSupplier_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenInvoiceIsMadeBySupplier(supplierOne.getSupplierId(),LocalDate.now().minusMonths(1),LocalDate.now().plusDays(20)));
    }
    @Test
    public void testAggregateTotalSpendingWhenInvoiceIsMade_WithNonExistedSupplier_ThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenInvoiceIsMadeBySupplier(supplierOne.getSupplierId(),LocalDate.now().minusMonths(1),LocalDate.now().plusDays(20)));
    }
    @Test
    public void testAggregateTotalSpendingWhenInvoiceIsMade_WithInvalidTimeRange_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenInvoiceIsMade(LocalDate.now().minusDays(1),LocalDate.now().minusDays(20)));
    }
    @Test
    public void testAggreagateTotalSpendingWhenInvoiceIsMade_WithInvalidInvoiceId_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);

        assertThrows(IllegalArgumentException.class,()-> procurementReportDataAggregationService.aggregateTotalSpendingforSingleInvoice(invoiceTwo.getInvoiceId()));
    }

    // total spending for delivery receipts
    @Test
    public void testAggregateTotalSpendingforDeliveryReceipt_WithValidReceiptId_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingforSingleDeliveryReceipt(deliveryReceipt.getReceiptId());
        BigDecimal totalSpendingOne = procurementReportDataAggregationService.aggregateTotalSpendingforSingleDeliveryReceipt(deliveryReceiptOne.getReceiptId());
        BigDecimal totalSpendingTwo = procurementReportDataAggregationService.aggregateTotalSpendingforSingleDeliveryReceipt(deliveryReceiptTwo.getReceiptId());

        assertEquals(BigDecimal.valueOf(30000.00).setScale(2),totalSpending);
        assertEquals(BigDecimal.valueOf(20000.00).setScale(2),totalSpendingOne);
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2),totalSpendingTwo);
    }
    @Test
    public void testAggregateTotalSpendingforDeliveryReceipt_WithValidTimeRange_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingWhenDeliveryReceiptIsMadeByTimeRange(LocalDate.now().minusDays(10),LocalDate.now());
        assertEquals(BigDecimal.valueOf(60000.00).setScale(2),totalSpending);
    }
    @Test
    public void testAggregateTotalSpendingforDeliveryReceipt_WithSameSupplier_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplierOne,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplierOne,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingWhenDeliveryReceiptIsMadeBySupplier(supplier.getSupplierId(),LocalDate.now().minusDays(10),LocalDate.now());
        BigDecimal totalSpendingOne = procurementReportDataAggregationService.aggregateTotalSpendingWhenDeliveryReceiptIsMadeBySupplier(supplierOne.getSupplierId(),LocalDate.now().minusDays(10),LocalDate.now());

        assertEquals(BigDecimal.valueOf(30000.00).setScale(2),totalSpending);
        assertEquals(BigDecimal.valueOf(30000.00).setScale(2),totalSpendingOne);
    }
    @Test
    public void testAggregateTotalSpendingforDeliveryReceipt_WithNonExistedSupplier_ThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplierOne,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplierOne,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

//        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateTotalSpendingWhenDeliveryReceiptIsMadeBySupplier(supplier.getSupplierId(),LocalDate.now().minusDays(10),LocalDate.now()));
    }
    @Test
    public void testAggregateTotalSpendingForDeliveryReceipt_WithInvalidTimeRange_ThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateTotalSpendingWhenDeliveryReceiptIsMadeByTimeRange(LocalDate.now().plusDays(10),LocalDate.now()));

    }
    @Test
    public void testAggreagateTotalSpendingforDeliveryReceipt_WithInvalidReceiptId_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateTotalSpendingforSingleDeliveryReceipt(deliveryReceipt.getReceiptId()));
        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateTotalSpendingforSingleDeliveryReceipt(deliveryReceiptOne.getReceiptId()));
        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateTotalSpendingforSingleDeliveryReceipt(deliveryReceiptTwo.getReceiptId()));
    }

    /** total spending for payment reconciliation. */
    @Test
    public void testAggregateTotalSpendingforPaymentReconciliation_WithValidPaymentId_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,invoice.getTotalCosts(),deliveryReceipt.getTotalCost(),"USD",invoice.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationOne = new PaymentReconciliation(invoiceOne,purchaseOrderOne,deliveryReceiptOne,invoiceOne.getTotalCosts(),deliveryReceiptOne.getTotalCost(),"USD",invoiceOne.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationTwo = new PaymentReconciliation(invoiceTwo,purchaseOrderTwo,deliveryReceiptTwo,invoiceTwo.getTotalCosts(),deliveryReceiptTwo.getTotalCost(),"USD",invoiceTwo.getPaymentDate(),LocalDate.now());

        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliation);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationOne);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingforSpecificPaymentReconciliation(paymentReconciliation.getPaymentId());
        BigDecimal totalSpendingOne = procurementReportDataAggregationService.aggregateTotalSpendingforSpecificPaymentReconciliation(paymentReconciliationOne.getPaymentId());
        BigDecimal totalSpendingTwo = procurementReportDataAggregationService.aggregateTotalSpendingforSpecificPaymentReconciliation(paymentReconciliationTwo.getPaymentId());

        assertEquals(BigDecimal.valueOf(30000.00).setScale(2),totalSpending);
        assertEquals(BigDecimal.valueOf(20000.00).setScale(2),totalSpendingOne);
        assertEquals(BigDecimal.valueOf(10000.00).setScale(2),totalSpendingTwo);
    }
    @Test
    public void testAggregateTotalSpendingforPaymentReconciliation_WithValidTimeRange_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();


        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,invoice.getTotalCosts(),deliveryReceipt.getTotalCost(),"USD",invoice.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationOne = new PaymentReconciliation(invoiceOne,purchaseOrderOne,deliveryReceiptOne,invoiceOne.getTotalCosts(),deliveryReceiptOne.getTotalCost(),"USD",invoiceOne.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationTwo = new PaymentReconciliation(invoiceTwo,purchaseOrderTwo,deliveryReceiptTwo,invoiceTwo.getTotalCosts(),deliveryReceiptTwo.getTotalCost(),"USD",invoiceTwo.getPaymentDate(),LocalDate.now());

        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliation);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationOne);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingWhenDeliveryReceiptIsMadeByTimeRange(LocalDate.now().minusDays(1),LocalDate.now());

        assertEquals(BigDecimal.valueOf(60000.00).setScale(2),totalSpending);
    }
    @Test
    public void testAggregateTotalSpendingforPaymentReconciliation_WithSameSupplier_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,invoice.getTotalCosts(),deliveryReceipt.getTotalCost(),"USD",invoice.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationOne = new PaymentReconciliation(invoiceOne,purchaseOrderOne,deliveryReceiptOne,invoiceOne.getTotalCosts(),deliveryReceiptOne.getTotalCost(),"USD",invoiceOne.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationTwo = new PaymentReconciliation(invoiceTwo,purchaseOrderTwo,deliveryReceiptTwo,invoiceTwo.getTotalCosts(),deliveryReceiptTwo.getTotalCost(),"USD",invoiceTwo.getPaymentDate(),LocalDate.now());

        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliation);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationOne);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationTwo);

        BigDecimal totalSpending = procurementReportDataAggregationService.aggregateTotalSpendingWhenPaymentReconciliationIsMadeBySupplier(supplierOne.getSupplierId(), LocalDate.now().minusDays(1),LocalDate.now());
        assertEquals(BigDecimal.valueOf(30000.00).setScale(2),totalSpending);
    }
    @Test
    public void testAggregateTotalSpendingforPaymentReconciliation_WithNonExistedSupplier_ThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,invoice.getTotalCosts(),deliveryReceipt.getTotalCost(),"USD",invoice.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationOne = new PaymentReconciliation(invoiceOne,purchaseOrderOne,deliveryReceiptOne,invoiceOne.getTotalCosts(),deliveryReceiptOne.getTotalCost(),"USD",invoiceOne.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationTwo = new PaymentReconciliation(invoiceTwo,purchaseOrderTwo,deliveryReceiptTwo,invoiceTwo.getTotalCosts(),deliveryReceiptTwo.getTotalCost(),"USD",invoiceTwo.getPaymentDate(),LocalDate.now());

        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationOne);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationTwo);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenPaymentReconciliationIsMadeBySupplier(supplier.getSupplierId(), LocalDate.now().minusDays(1),LocalDate.now()));

    }
    @Test
    public void testAggregateTotalSpendingforPaymentReconciliation_WithInvalidTimeRange_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);


        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,invoice.getTotalCosts(),deliveryReceipt.getTotalCost(),"USD",invoice.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationOne = new PaymentReconciliation(invoiceOne,purchaseOrderOne,deliveryReceiptOne,invoiceOne.getTotalCosts(),deliveryReceiptOne.getTotalCost(),"USD",invoiceOne.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationTwo = new PaymentReconciliation(invoiceTwo,purchaseOrderTwo,deliveryReceiptTwo,invoiceTwo.getTotalCosts(),deliveryReceiptTwo.getTotalCost(),"USD",invoiceTwo.getPaymentDate(),LocalDate.now());

        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliation);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationOne);
        procurementReportDataAggregationService.addPaymentReconciliation(paymentReconciliationTwo);

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingWhenPaymentReconciliationIsMade( LocalDate.now().plusDays(1),LocalDate.now()));
    }
    @Test
    public void testAggreagateTotalSpendingforPaymentReconciliation_WithInvalidPaymentId_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");
        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem1);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem2);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem3);

        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionOne);
        procurementReportDataAggregationService.addPurchaseRequisition(requisitionTwo);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierPaymentMethodOne = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetailOne.getFullName(),supplierContactDetailOne.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));
        Supplier supplier = new Supplier( "IBM", "Vendor", "1736", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());
        Supplier supplierOne = new Supplier( "IBM", "Vendor", "17636", "41244", supplierContactDetailOne, List.of(supplierPaymentMethodOne),  List.of(item, item1, item2), LocalDate.now());
        procurementReportDataAggregationService.addSupplier(supplier);
        procurementReportDataAggregationService.addSupplier(supplierOne);


        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrderOne = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        PurchaseOrder purchaseOrderTwo = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(1));

        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrder);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderOne);
        procurementReportDataAggregationService.addPurchaseOrder(purchaseOrderTwo);

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderOne,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoiceTwo = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplierOne,purchaseOrderTwo,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        procurementReportDataAggregationService.addInvoice(invoice);
        procurementReportDataAggregationService.addInvoice(invoiceOne);
        procurementReportDataAggregationService.addInvoice(invoiceTwo);

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier,purchaseOrder,List.of(deliveredItem1),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(supplier,purchaseOrderOne,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryReceiptTwo = new DeliveryReceipt(supplier,purchaseOrderTwo,List.of(deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceipt);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptOne);
        procurementReportDataAggregationService.addDeliveryReceipt(deliveryReceiptTwo);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation(invoice,purchaseOrder,deliveryReceipt,invoice.getTotalCosts(),deliveryReceipt.getTotalCost(),"USD",invoice.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationOne = new PaymentReconciliation(invoiceOne,purchaseOrderOne,deliveryReceiptOne,invoiceOne.getTotalCosts(),deliveryReceiptOne.getTotalCost(),"USD",invoiceOne.getPaymentDate(),LocalDate.now());
        PaymentReconciliation paymentReconciliationTwo = new PaymentReconciliation(invoiceTwo,purchaseOrderTwo,deliveryReceiptTwo,invoiceTwo.getTotalCosts(),deliveryReceiptTwo.getTotalCost(),"USD",invoiceTwo.getPaymentDate(),LocalDate.now());

        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingforSpecificPaymentReconciliation(paymentReconciliation.getPaymentId()));
        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingforSpecificPaymentReconciliation(paymentReconciliationOne.getPaymentId()));
        assertThrows(IllegalArgumentException.class,()->procurementReportDataAggregationService.aggregateTotalSpendingforSpecificPaymentReconciliation(paymentReconciliationTwo.getPaymentId()));

    }
    @Test
    public void testCalculateAverageSupplierPerformance_withValidSupplier_shouldReturnScore() { // average performance score.
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        performance.setEvaluationDate(LocalDate.of(2024, 5, 1));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);

        double score = procurementReportDataAggregationService.averageSupplierPerformance(supplier.getSupplierId());

        assertEquals(90.9, score);
    }

    @Test
    public void testCalculateAverageSupplierPerformance_withNonExistingSupplierId_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        performance.setEvaluationDate(LocalDate.of(2024, 5, 1));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.averageSupplierPerformance("123"));
    }
    @Test
    public void testCalculateAverageSupplierPerformance_withNullSupplierId_shouldThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),LocalDate.now());
        performance.setEvaluationDate(LocalDate.of(2024, 5, 1));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.averageSupplierPerformance(null));
    }
    @Test
    public void testShowLatestSupplierPerformance_WithValidSupplier_Successfully(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.of(2024, 5, 1));

        SupplierPerformance performanceOne = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(92, 4, 93, 93, 80, 5, 4),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.now());

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        procurementReportDataAggregationService.getSupplierPerformanceList().add(performanceOne);

        assertEquals(88.6,procurementReportDataAggregationService.latestSupplierPerformance(supplier.getSupplierId()));

    }
    @Test
    public void testShowLatestSupplierPerformance_WithNonExistingSupplierId_shouldThrowException(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.of(2024, 5, 1));

        SupplierPerformance performanceOne = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(92, 4, 93, 93, 80, 5, 4),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.now());

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        procurementReportDataAggregationService.getSupplierPerformanceList().add(performanceOne);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.latestSupplierPerformance("123"));
    }
    @Test
    public void testShowLatestSupplierPerformance_WithNullSupplierId_shouldThrowException(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.of(2024, 5, 1));

        SupplierPerformance performanceOne = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(92, 4, 93, 93, 80, 5, 4),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.now());

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        procurementReportDataAggregationService.getSupplierPerformanceList().add(performanceOne);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.latestSupplierPerformance(null));
    }
    @Test
    public void testShowTotalSupplierPerformances_WithValidSupplier_Successfully(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.of(2024, 5, 1));

        SupplierPerformance performanceOne = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(92, 4, 93, 93, 80, 5, 4),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.now());

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        procurementReportDataAggregationService.getSupplierPerformanceList().add(performanceOne);

        List<SupplierPerformance> supplierPerformanceList = procurementReportDataAggregationService.listSupplierPerformance(supplier.getSupplierId());
        assertEquals(List.of(performance, performanceOne), supplierPerformanceList);
        assertEquals(2, supplierPerformanceList.size());
    }
    @Test
    public void testShowTotalSupplierPerformances_WithNonExistingSupplierId_shouldThrowException(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.of(2024, 5, 1));

        SupplierPerformance performanceOne = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(92, 4, 93, 93, 80, 5, 4),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.now());

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.listSupplierPerformance(supplier.getSupplierId()));

    }
    @Test
    public void testShowTotalSupplierPerformances_WithNullSupplierId_shouldThrowException(){
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod),  List.of(item, item1, item2), LocalDate.now());

        SupplierPerformance performance = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.of(2024, 5, 1));

        SupplierPerformance performanceOne = new SupplierPerformance(supplier,
                new SupplierQuantitativePerformanceMetrics(92, 4, 93, 93, 80, 5, 4),
                new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10),
                LocalDate.now());

        supplier.setActive(true);
        procurementReportDataAggregationService.addSupplier(supplier);

        procurementReportDataAggregationService.getSupplierPerformanceList().add(performance);
        procurementReportDataAggregationService.getSupplierPerformanceList().add(performanceOne);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.listSupplierPerformance(null));
    }
    @Test
    public void testAggregateRequisitionStatusByTimeRange_withValidInput_shouldReturnStatusCounts() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now(),LocalDate.now().plusMonths(5),"USD");
        Department department = new Department("Department one","new Department",budget);
        CostCenter costCenter = new CostCenter("CostCenter one","first cost center");
        User requester = new User("requester one","requester@gmail.com","+251978374657",department,"requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition req1 = new PurchaseRequisition("R1",LocalDate.now(),requester,department, costCenter, PriorityLevel.MEDIUM,LocalDate.now().plusMonths(2),"we are new departments.");
        req1.addItem(items);
        req1.setRequisitionStatus(PENDING);

        PurchaseRequisition req2 = new PurchaseRequisition("R2",LocalDate.now(),requester,department, costCenter, PriorityLevel.MEDIUM,LocalDate.now().plusMonths(2),"this is required since we are preparing new office.");
        req2.addItem(items);
        req2.setRequisitionStatus(APPROVED);
        req2.setRequisitionDate(LocalDate.now());
        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(req1);
        procurementReportDataAggregationService.addPurchaseRequisition(req2);

        Map<RequisitionStatus, Long> result = procurementReportDataAggregationService.aggregateTotalRequisitionStatusByDateRange(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        assertEquals(1L, result.getOrDefault(PENDING, 0L));
        assertEquals(1L, result.getOrDefault(APPROVED, 0L));
        assertEquals(0L, result.getOrDefault(REJECTED, 0L));
    }
    @Test
    public void testAggregateRequisitionStatusByDepartment_WithValidData_Successfully() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now(),LocalDate.now().plusMonths(5),"USD");
        Department department = new Department("Department one","new Department",budget);
        Budget budgetOne = new Budget(BigDecimal.valueOf(100000.0),LocalDate.now(),LocalDate.now().plusMonths(5),"USD");
        Department departmentOne = new Department("Department one","new Department",budgetOne);
        CostCenter costCenter = new CostCenter("CostCenter one","first cost center");
        User requester = new User("requester one","requester@gmail.com","+251978374657",department,"requester");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,2);
        RequestedItem itemOne = new RequestedItem(inventory,3);

        PurchaseRequisition req1 = new PurchaseRequisition("R1",LocalDate.now(),requester,department, costCenter, PriorityLevel.MEDIUM,LocalDate.now().plusMonths(2),"we are new departments.");
        req1.addItem(items);
        req1.setRequisitionStatus(PENDING);

        PurchaseRequisition req2 = new PurchaseRequisition("R2",LocalDate.now(),requester,departmentOne, costCenter, PriorityLevel.MEDIUM,LocalDate.now().plusMonths(2),"this is required since we are preparing new office.");
        req2.addItem(itemOne);
        req2.setRequisitionStatus(APPROVED);
        req2.setRequisitionDate(LocalDate.now());
        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addDepartment(departmentOne);

        procurementReportDataAggregationService.addPurchaseRequisition(req1);
        procurementReportDataAggregationService.addPurchaseRequisition(req2);

        Map<RequisitionStatus, Long> result = procurementReportDataAggregationService.aggregateRequisitionStatusByDepartment(department.getDepartmentId(), LocalDate.now().minusDays(1), LocalDate.now().plusDays(1));

        assertEquals(1L, result.getOrDefault(PENDING, 0L));
        assertEquals(0L, result.getOrDefault(APPROVED, 0L));
        assertEquals(0L, result.getOrDefault(REJECTED, 0L));
    }
    @Test
    public void testAggregateRequisitionStatus_WithNoRequisitionInGivenDepartment_ThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Budget budgetOne = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department( "Bizeff", "Software Development", budget);
        Department department2 = new Department( "codvex", "Software Development", budgetOne);
        CostCenter costCenter = new CostCenter( "back end Development", "using clean code architecture");
        User requester = new User("Tekia Tekle", "tekia@gmail.com", "+251978374657", department, "java developer");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        PurchaseRequisition req = new PurchaseRequisition("REQ002", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        req.addItem(items);


        procurementReportDataAggregationService.addDepartment(department);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);
        requisition.setRequisitionStatus(APPROVED);

        procurementReportDataAggregationService.addDepartment(department2);
        procurementReportDataAggregationService.addPurchaseRequisition(req);
        req.setRequisitionStatus(APPROVED);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateRequisitionStatusByDepartment(department2.getDepartmentId(), LocalDate.now().minusDays(1), LocalDate.now().plusDays(10)));

    }
    @Test
    public void testAggregateRequisitionStatus_WithInvalidDate_ThrowException() {
        procurementReportDataAggregationService = new ProcurementReportDataAggregationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        User requester = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        procurementReportDataAggregationService.addDepartment(department);
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);

        requisition.setRequisitionStatus(APPROVED);
        procurementReportDataAggregationService.addPurchaseRequisition(requisition);

        assertThrows(IllegalArgumentException.class, () -> procurementReportDataAggregationService.aggregateTotalRequisitionStatusByDateRange(LocalDate.now().minusDays(1), LocalDate.now().minusDays(10)));
    }

    /**testing for report Customization. */
    @Test
    public void testAddValidReportTemplate_Successfully() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);

        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);
        procurementReportCustomizationService.addPurchaseRequisition(requisition);

        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user);

        ReportTemplate createdReportTemplate = procurementReportCustomizationService.addReportTemplate(reportTemplate.getTemplateId(),reportTemplate.getTemplateName(),reportTemplate.getTemplateDescription(),reportTemplate.getSelectedFields(),reportTemplate.getCreatedAt(),reportTemplate.getCreatedBy());
        assertEquals(reportTemplate.getTemplateId(), createdReportTemplate.getTemplateId());
    }
    @Test
    public void testAddReportTemplate_WithEmptyFields_Fails() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);

        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);
        procurementReportCustomizationService.addPurchaseRequisition(requisition);

        assertThrows(IllegalArgumentException.class, () -> procurementReportCustomizationService.addReportTemplate("template1", "template1", "template1",List.of(),LocalDate.now(),user));
    }
    @Test
    public void testAddReportTemplate_WithMissingElements_Fails() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);
        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");

        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);
        procurementReportCustomizationService.addPurchaseRequisition(requisition);

        assertThrows(IllegalArgumentException.class, () -> procurementReportCustomizationService.addReportTemplate(" ", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user));
        assertThrows(IllegalArgumentException.class, () -> procurementReportCustomizationService.addReportTemplate("template1", " ", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user));
        assertThrows(IllegalArgumentException.class, () -> procurementReportCustomizationService.addReportTemplate("template1", "template1", " ",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user));
        assertThrows(IllegalArgumentException.class, () -> procurementReportCustomizationService.addReportTemplate(" ", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),null,user));
        assertThrows(IllegalArgumentException.class, () -> procurementReportCustomizationService.addReportTemplate(" template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),null));
    }
    @Test
    public void testAddReportTemplate_WithExistedSelectedFields_Successfully() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();

        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);
        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");

        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        procurementReportCustomizationService.addPurchaseRequisition(requisition);

        procurementReportCustomizationService.addReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user);

        assertThrows(IllegalArgumentException.class,()-> procurementReportCustomizationService.addReportTemplate("template2", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user));
    }
    @Test
    public void testAddReportTemplate_WithExistedTemplate_Fails() { //since duplication is not allowed.
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);
        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");

        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,5);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        procurementReportCustomizationService.addPurchaseRequisition(requisition);

        procurementReportCustomizationService.addReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus"),LocalDate.now(),user);

        assertThrows(IllegalArgumentException.class,()-> procurementReportCustomizationService.addReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByStatus"),LocalDate.now(),user));

    }
     @Test
    public void testGenerateCustomedReport_WithSelectedFields_Successfully() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);

        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,2);
        RequestedItem items2 = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ002", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");

        requisition2.addItem(items2);
        requisition2.setRequisitionStatus(APPROVED);

        procurementReportCustomizationService.getPurchaseRequisitions().add(requisition);
        procurementReportCustomizationService.getPurchaseRequisitions().add(requisition2);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("Tekia Tekle","email@gmail.com", "+251797988778","Mekelle");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(inventory), LocalDate.now());        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        procurementReportCustomizationService.getSuppliers().add(supplier);

        ReportTemplate createdReportTemplate = procurementReportCustomizationService.addReportTemplate(reportTemplate.getTemplateId(),reportTemplate.getTemplateName(),reportTemplate.getTemplateDescription(),reportTemplate.getSelectedFields(),reportTemplate.getCreatedAt(),reportTemplate.getCreatedBy());

        TemplateBasedReport templateBasedReport = procurementReportCustomizationService.buildCustomizedReport(createdReportTemplate.getTemplateId(),List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT));

        assertEquals("template1", templateBasedReport.getReportTemplate().getTemplateId());
        assertEquals("template1", templateBasedReport.getReportTemplate().getTemplateName());
        assertEquals(LocalDate.now(), templateBasedReport.getReportTemplate().getCreatedAt());
        assertEquals(user, templateBasedReport.getReportTemplate().getCreatedBy());

        assertEquals(List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT), templateBasedReport.getProcurementActivities());

        assertEquals(2L, ((Map<?, ?>) templateBasedReport.getData().get("requisitionsByDepartment")).get(department.getDepartmentId()));
        assertEquals(2L, ((Map<?, ?>) templateBasedReport.getData().get("requisitionsByStatus")).get(APPROVED));

        assertEquals(2L, templateBasedReport.getData().get("totalRequisitions"));
        assertEquals(BigDecimal.valueOf(20000.0).setScale(2), templateBasedReport.getData().get("totalSpendingAmount"));
        assertEquals(2L, ((Map<?, ?>) templateBasedReport.getData().get("requisitionsByPriority")).get(PriorityLevel.HIGH));

    }
    @Test
    public void testGenerateCustomedReport_WithEmptyProcurementActivity_throwsException() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);

        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,2);
        RequestedItem items2 = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ002", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");

        requisition2.addItem(items2);
        requisition2.setRequisitionStatus(APPROVED);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("Tekia Tekle","email@gmail.com", "+251797988778","Mekelle");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(inventory), LocalDate.now());
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        ReportTemplate createdReportTemplate = procurementReportCustomizationService.addReportTemplate(reportTemplate.getTemplateId(),reportTemplate.getTemplateName(),reportTemplate.getTemplateDescription(),reportTemplate.getSelectedFields(),reportTemplate.getCreatedAt(),reportTemplate.getCreatedBy());

        assertThrows(IllegalArgumentException.class,()->procurementReportCustomizationService.buildCustomizedReport(createdReportTemplate.getTemplateId(),List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT)));

    }
    @Test
    public void testGenerateCustomedReport_WithEmptyReportTemplate_throwsException() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);

        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,2);
        RequestedItem items2 = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ002", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");

        requisition2.addItem(items2);
        requisition2.setRequisitionStatus(APPROVED);

        procurementReportCustomizationService.getPurchaseRequisitions().add(requisition);
        procurementReportCustomizationService.getPurchaseRequisitions().add(requisition2);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("Tekia Tekle","email@gmail.com", "+251797988778","Mekelle");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(inventory), LocalDate.now());        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        procurementReportCustomizationService.getSuppliers().add(supplier);

        assertThrows(IllegalArgumentException.class,()->procurementReportCustomizationService.buildCustomizedReport("templateOne",List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT)));

    }
    @Test
    public void testGenerateCustomedReport_WithMismatchingSelectedFieldAndProcurementActivity_throwsException() {
        procurementReportCustomizationService = new ProcurementReportCustomizationServices();
        Budget budget = new Budget( BigDecimal.valueOf(100000.0), LocalDate.now().minusDays(10), LocalDate.now().plusDays(25), "Birr");
        Department department = new Department("department1", "Department One", budget);

        User user = new User("requester", "email@gmail.com", "+251797988778", department, "requester");
        CostCenter costCenter = new CostCenter( "costCenterOne", "Cost Center One");
        Inventory inventory = new Inventory("Sn-101", "Laptop", 5, BigDecimal.valueOf(5000.0), "Electronics", LocalDate.now().plusYears(7), "new brand");
        RequestedItem items = new RequestedItem(inventory,2);
        RequestedItem items2 = new RequestedItem(inventory,2);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");
        requisition.addItem(items);
        requisition.setRequisitionStatus(APPROVED);

        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ002", LocalDate.now(), user, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "");

        requisition2.addItem(items2);
        requisition2.setRequisitionStatus(APPROVED);

        procurementReportCustomizationService.getPurchaseRequisitions().add(requisition);
        procurementReportCustomizationService.getPurchaseRequisitions().add(requisition2);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("Tekia Tekle","email@gmail.com", "+251797988778","Mekelle");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod),  List.of(inventory), LocalDate.now());        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalSupplier","activeSupplier","suppliersByCategory","existedItems"),LocalDate.now(),user);

        procurementReportCustomizationService.getSuppliers().add(supplier);

        ReportTemplate createdReportTemplate = procurementReportCustomizationService.addReportTemplate(reportTemplate.getTemplateId(),reportTemplate.getTemplateName(),reportTemplate.getTemplateDescription(),reportTemplate.getSelectedFields(),reportTemplate.getCreatedAt(),reportTemplate.getCreatedBy());

        assertThrows(IllegalArgumentException.class,()->procurementReportCustomizationService.buildCustomizedReport(createdReportTemplate.getTemplateId(),List.of(ProcurementActivity.PURCHASE_REQUISITION)));

    }
}