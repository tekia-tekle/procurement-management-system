package com.bizeff.procurement;

import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.CreateRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.ItemsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.RequisitionContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.TrackRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractFileRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ProcurementReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.PurchaseOrderReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ReportTemplateRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.TemplateBasedReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.OrderedItemRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.InventoryRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.enums.PriorityLevel;
import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.bizeff.procurement.models.enums.PaymentMethodType.*;
import static com.bizeff.procurement.models.enums.PaymentTerms.NET_30;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests that verify role-based access control:
 * URLs should be accessible only by their assigned roles.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class ProcurementSecurityIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;
    @Autowired private BudgetRepository budgetRepository;
    @Autowired private DepartmentRepository departmentRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CostCenterRepository costCenterRepository;
    @Autowired private SupplierRepository supplierRepository;
    @Autowired private InventoryRepository inventoryRepository;
    @Autowired private PurchaseRequisitionRepository purchaseRequisitionRepository;
    @Autowired private RequestedItemRepository requestedItemRepository;
    @Autowired private SupplierPerformanceRepository supplierPerformanceRepository;
    @Autowired private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired private OrderedItemRepository orderedItemRepository;
    @Autowired private ContractRepository contractRepository;
    @Autowired private ContractFileRepository contractFileRepository;
    @Autowired private InvoiceRepository invoiceRepository;
    @Autowired private InvoicedItemRepository invoicedItemRepository;
    @Autowired private DeliveryReceiptRepository deliveryReceiptRepository;
    @Autowired private DeliveredItemRepository deliveredItemRepository;
    @Autowired private PaymentReconciliationRepository paymentReconciliationRepository;
    @Autowired private TemplateBasedReportRepository templateBasedReportRepository;
    @Autowired private ProcurementReportRepository procurementReportRepository;
    @Autowired private ReportTemplateRepository reportTemplateRepository;
    @Autowired private PurchaseOrderReportRepository purchaseOrderReportRepository;

    @BeforeEach
    public void setUp() {
        templateBasedReportRepository.deleteAll();
        procurementReportRepository.deleteAll();
        purchaseOrderReportRepository.deleteAll();
        reportTemplateRepository.deleteAll();
        paymentReconciliationRepository.deleteAll();
        deliveryReceiptRepository.deleteAll();
        deliveredItemRepository.deleteAll();
        invoiceRepository.deleteAll();
        invoicedItemRepository.deleteAll();
        contractRepository.deleteAll();
        contractFileRepository.deleteAll();
        purchaseOrderRepository.deleteAll();
        orderedItemRepository.deleteAll();
        supplierPerformanceRepository.deleteAll();
        purchaseRequisitionRepository.deleteAll();
        inventoryRepository.deleteAll();
        supplierRepository.deleteAll();
        costCenterRepository.deleteAll();
        userRepository.deleteAll();
        budgetRepository.deleteAll();
        departmentRepository.deleteAll();
        requestedItemRepository.deleteAll();
    }
    @Test
    void publicEndpointShouldBeAccessibleWithoutToken() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "requesterUser", roles = "REQUESTER")
    void requesterCanAccessRequisitionCreate() throws Exception {
        // Arrange
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(13);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());
        supplier.setSupplierId("SUP-001");
        supplierRepository.save(supplier);

        RequisitionContactDetailsInputDS contactDetails = new RequisitionContactDetailsInputDS("Tekia", "tekia@gmail.com", "+251979417636", "Software Development", "BackEnd Developer");

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 2, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        mockMvc.perform(post("/purchaserequisition/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "manageruser", roles = "MANAGER")
    void mangerCannotAccessRequisitionCreate() throws Exception {
        // Arrange
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(13);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());
        supplier.setSupplierId("SUP-001");
        supplierRepository.save(supplier);

        RequisitionContactDetailsInputDS contactDetails = new RequisitionContactDetailsInputDS("Tekia", "tekia@gmail.com", "+251979417636", "Software Development", "BackEnd Developer");

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 2, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        mockMvc.perform(post("/purchaserequisition/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "manageruser", roles = {"MANAGER"})
    void managerCanAccessRequisitionTrack() throws Exception {
        // Arrange
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(13);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());
        supplier.setSupplierId("SUP-001");
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();
        RequestedItem requisitionItem = new RequestedItem(savedInventory1, 2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001",LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition");
        requisition.setSupplier(savedSupplier);
        requisition.getItems().add(requisitionItem);
        requisition.setRequisitionId("REQ-001");
        purchaseRequisitionRepository.save(requisition);

        RequisitionContactDetailsInputDS manageralContact = new RequisitionContactDetailsInputDS("Tekia", "tekia.tekle@gmail.com", "+251979417663", savedDepartment.getDepartmentId(), "BackEnd Developer");
        TrackRequisitionInputDS trackRequisitionInputDS = new TrackRequisitionInputDS(manageralContact, requisition.getRequisitionId());

        mockMvc.perform(post("/purchaserequisition/track")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequisitionInputDS)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "requesterUser", roles = "REQUESTER")
    void requesterCannotApprovePurchaseOrder() throws Exception {
        // REQUESTER should not approve purchase orders
        mockMvc.perform(post("/purchaseorder/approve"))
                .andExpect(status().isForbidden());
    }


    @Test
    @WithMockUser(username = "managerUser", roles = "MANAGER")
    void managerCannotCreateRequisition() throws Exception {
        // MANAGER cannot create requisitions
        mockMvc.perform(post("/purchaserequisition/create"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "vendorUser", roles = "VENDOR")
    void vendorCannotApprovePurchaseOrder() throws Exception {
        // VENDOR should not approve purchase orders
        mockMvc.perform(post("/purchaseorder/approve"))
                .andExpect(status().isForbidden());
    }


    @Test
    void requestWithInvalidTokenShouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/supplierperformance/viewMetrics")
                        .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @WithMockUser(username = "managerUser", roles = "MANAGER")
    void managerCannotViewVendorPerformance() throws Exception {
        // MANAGER should not access vendor performance view endpoint
        mockMvc.perform(get("/supplierperformance/viewMetrics"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "vendorUser", roles = "VENDOR")
    void vendorCannotAccessRequisitionCreate() throws Exception {
        mockMvc.perform(post("/purchaserequisition/create"))
                .andExpect(status().isForbidden());
    }
}
