package com.bizeff.procurement;

import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.ContractsContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.contractsmanagement.CreateContractInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.invoicepaymentreconciliation.AccountantContactDetails;
import com.bizeff.procurement.domaininterfaces.inputds.invoicepaymentreconciliation.ReconcileInvoiceInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.CreateCustomizedProcurementDashboardInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ExportProcurementDataInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.GenerateMonthlyApprovedPurchaseOrderReportInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ReporterContactDetail;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.ApprovePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.CreatePurchaseOrderInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.PurchaseOrderContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaseorder.SendPurchaseOrderToSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.CreateRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.ItemsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.RequisitionContactDetailsInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.purchaserequisition.TrackRequisitionInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.*;
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
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.*;
import com.bizeff.procurement.models.invoicepaymentreconciliation.*;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
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
import com.bizeff.procurement.webapi.viewmodel.purchaserequisition.TrackPurchaseRequisitionViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static com.bizeff.procurement.models.enums.PaymentMethodType.*;
import static com.bizeff.procurement.models.enums.PaymentTerms.NET_30;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest(classes = ProcurementApplication.class)
@AutoConfigureMockMvc(addFilters = false) // disables JWT security filters
@Transactional
public class ProcurementHttpIntegrationTests {

    @Autowired private MockMvc mockMvc;
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
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisitionSuccessfully() throws Exception {
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

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requisitionId").exists())
                .andExpect(jsonPath("$.requisitionNumber").value("REQ-001"))
                .andExpect(jsonPath("$.departmentId").value("DI-101"))
                .andExpect(jsonPath("$.costCenterId").value("CC-101"))
                .andExpect(jsonPath("$.status").value("PENDING"))
                .andExpect(jsonPath("$.requestedItems.length()").value(1))
                .andExpect(jsonPath("$.requestedItems[0].itemId").value("ITEM-0010"))
                .andExpect(jsonPath("$.requestedItems[0].requestedQuantity").value(2))
                .andExpect(jsonPath("$.requestedBy").value("Tekia Tekle"))
                .andExpect(jsonPath("$.totalEstimatedCosts").value("20000.0"));

    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreateRequisitionWithNullInputRequisition() throws Exception {
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf()).with(user("testuser").roles("USER"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreateRequisitionWithDuplicateRequisitionNumber() throws Exception {

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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-002", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        CreateRequisitionInputDS duplicateRequest = new CreateRequisitionInputDS(
                contactDetails,
                savedDepartment.getDepartmentId(),
                savedCostCenter.getCostCenterId(),
                "REQ-002", // Duplicate requisition number
                LocalDate.now(),
                items,
                PriorityLevel.HIGH,
                LocalDate.now().plusMonths(8),
                "Creating duplicate requisition",
                "SUP-001"
        );
        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());


        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Requisition number already exists."));
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreateRequisitionWithNullRequisitionNumber() throws Exception {
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
        System.out.println(savedCostCenter);
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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), null, LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Can't create requisition with null requisition Number."));
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreateRequisition_EmptyItems_ShouldReturnBadRequest() throws Exception {
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


        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), List.of(), PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("At least one item is required in the requisition."));
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreateRequisition_DeliveryBeforeRequest_ShouldReturnBadRequest() throws Exception {
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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().minusDays(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Delivery date can't be before the requested date."));

    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisition_WithNUllDepartmentID_ShouldReturnBadRequest() throws Exception{
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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, null, savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisition_WithNullCostCenterId_shouldReturnBadRequest()throws Exception{
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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), null, "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisition_WithNonExistedDepartment_ShouldReturnBadRequest() throws Exception {
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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, "DI101", savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Department not found"));
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisition_WithNonExistedCostCenter_ShouldReturnBadRequest()throws Exception{
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

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), "CC101", "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Cost Center not found"));
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisition_WhenRequestedItem_NonExisted_ShouldReturnBadRequest() throws Exception{

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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-10", "Laptop", 2, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Requested item not found in supplier inventory: ITEM-10"));
    }
    @Test
    @WithMockUser(roles = "REQUESTER",username = "Tekia Tekle")
    public void testCreatePurchaseRequisition_WhenRequestMoreThanExistedQuantity_shouldReturnBadRequest() throws Exception{

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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 20, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Requested quantity (20) exceeds available inventory (13) for item: ITEM-0010"));
    }
    @Test
    @WithMockUser(roles = "MANAGER",username = "Tekia Tekle")
    public void testTrackRequisitionToApproveSuccessfully() throws Exception {
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(30000.0));
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

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        // Fetch the saved purchase requisition
        PurchaseRequisition purchaseRequisition = purchaseRequisitionRepository.findByRequisitionNumber(request.getRequisitionNumber()).get();

        RequisitionContactDetailsInputDS manageralContact = new RequisitionContactDetailsInputDS("Tekia", "tekia.tekle@gmail.com", "+251979417663", savedDepartment.getDepartmentId(), "BackEnd Developer");
        TrackRequisitionInputDS trackRequisitionInputDS = new TrackRequisitionInputDS(manageralContact, purchaseRequisition.getRequisitionId());

        TrackPurchaseRequisitionViewModel trackRequisitionViewModel = new TrackPurchaseRequisitionViewModel();
        trackRequisitionViewModel.setRequisitionId(trackRequisitionInputDS.getRequisitionId());
        trackRequisitionViewModel.setRequisitionStatus("APPROVED");
        trackRequisitionViewModel.setTrackedBy("Tekia");
        trackRequisitionViewModel.setTrackedDate(LocalDate.now().toString());

        mockMvc.perform(post("/purchaserequisition/track")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequisitionInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requisitionId").value(trackRequisitionViewModel.getRequisitionId()))
                .andExpect(jsonPath("$.requisitionStatus").value(trackRequisitionViewModel.getRequisitionStatus()))
                .andExpect(jsonPath("$.trackedBy").value(trackRequisitionViewModel.getTrackedBy()))
                .andExpect(jsonPath("$.trackedDate").value(trackRequisitionViewModel.getTrackedDate()));

    }
    @Test
    @WithMockUser(username = "tekia", roles = "MANAGER")
    public void testTrackRequisitionToRejectSuccessfully() throws Exception {
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG105");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-102");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-01");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));
        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-001");
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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-001", "Laptop", 2, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-002", LocalDate.now(), items, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        PurchaseRequisition purchaseRequisition = purchaseRequisitionRepository.findByRequisitionNumber(request.getRequisitionNumber()).orElseThrow();

        RequisitionContactDetailsInputDS manageralContact = new RequisitionContactDetailsInputDS("Tekia", "tekia.tekle@gmail.com", "+251979417663", savedDepartment.getDepartmentId(), "BackEnd Developer");
        TrackRequisitionInputDS trackRequisitionInputDS = new TrackRequisitionInputDS(manageralContact, purchaseRequisition.getRequisitionId());

        String requisitionId = purchaseRequisition.getRequisitionId();
        String requisitionStatus = "REJECTED";
        String trackedBy = manageralContact.getName();
        String trackedDate = trackRequisitionInputDS.getTrackedDate().toString();

        mockMvc.perform(post("/purchaserequisition/track")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequisitionInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requisitionId").value(requisitionId))
                .andExpect(jsonPath("$.requisitionStatus").value(requisitionStatus))
                .andExpect(jsonPath("$.trackedBy").value(trackedBy))
                .andExpect(jsonPath("$.trackedDate").value(trackedDate));

    }
    @Test
    @WithMockUser(username = "tekia", roles = "MANAGER")
    public void testTrackRequisitionToPendingSuccessfully() throws Exception {

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG106");
        budgetOne.setTotalBudget(BigDecimal.valueOf(25000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-10");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-10");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-010");
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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-010", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS request = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), "CC-10", "REQ-003", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        PurchaseRequisition purchaseRequisition = purchaseRequisitionRepository.findByRequisitionNumber(request.getRequisitionNumber()).get();

        RequisitionContactDetailsInputDS manageralContact = new RequisitionContactDetailsInputDS("Tekia", "tekia.tekle@gmail.com", "+251979417663", savedDepartment.getDepartmentId(), "BackEnd Developer");
        TrackRequisitionInputDS trackRequisitionInputDS = new TrackRequisitionInputDS(manageralContact, purchaseRequisition.getRequisitionId());

        String requisitionId = purchaseRequisition.getRequisitionId();

        mockMvc.perform(post("/purchaserequisition/track")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequisitionInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.requisitionId").value(requisitionId))
                .andExpect(jsonPath("$.requisitionStatus").value("PENDING"))
                .andExpect(jsonPath("$.trackedBy").value("Tekia"))
                .andExpect(jsonPath("$.trackedDate").value(LocalDate.now().toString()));

    }
    @Test
    @WithMockUser(username = "tekia", roles = "MANAGER")
    public void testTrackRequisition_WithNullInput_shouldReturnBadRequest() throws Exception {
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

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());


        mockMvc.perform(post("/purchaserequisition/track")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "MANAGER")
    public void testTrackRequisition_WithNullRequisitionId_shouldReturnBadRequest() throws Exception {
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

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        RequisitionContactDetailsInputDS manageralContact = new RequisitionContactDetailsInputDS("Tekia", "tekia.tekle@gmail.com", "+251979417663", savedDepartment.getDepartmentId(), "BackEnd Developer");
        TrackRequisitionInputDS trackRequisitionInputDS = new TrackRequisitionInputDS(manageralContact, null);

        mockMvc.perform(post("/purchaserequisition/track")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(trackRequisitionInputDS)))
                .andExpect(status().isInternalServerError());

    }
    @Test
    @WithMockUser(roles = "MANAGER",username = "Tekia Tekle")
    @Rollback(value = false)
    public void testTrackRequisition_WithInActiveBudget() throws Exception{
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
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

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        PurchaseRequisition requisition = purchaseRequisitionRepository.findByRequisitionNumber(request.getRequisitionNumber()).get();
        RequisitionContactDetailsInputDS manageralContact = new RequisitionContactDetailsInputDS("Tekia", "tekia.tekle@gmail.com", "+251979417663", savedDepartment.getDepartmentId(), "BackEnd Developer");
        TrackRequisitionInputDS trackRequisitionInputDS = new TrackRequisitionInputDS(manageralContact, requisition.getRequisitionId());

        mockMvc.perform(post("/purchaserequisition/track")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(trackRequisitionInputDS)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "PROCUREMENT_CLERK")
    public void testViewingPendingRequisitions_WhenAllRequisitionsArePending_Successfully() throws Exception {

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(25000.0));
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

        CostCenter costCenter1 = new CostCenter();
        costCenter1.setCostCenterId("CC-102");
        costCenter1.setName("Procurement");
        costCenter1.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        CostCenter savedCostCenter1 = costCenterRepository.findByCostCenterId(costCenter1.getCostCenterId()).orElseThrow();
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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));
        List<ItemsInputDS> itemsInputDSList = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 5, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS requestOne = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");
        CreateRequisitionInputDS requestTwo = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter1.getCostCenterId(), "REQ-002", LocalDate.now(), itemsInputDSList, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestOne)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTwo)))
                .andExpect(status().isOk());

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        mockMvc.perform(get("/purchaserequisition/viewpending")
                        .param("departmentId", savedDepartment.getDepartmentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requisitionId").value(requisitions.get(0).getRequisitionId()))
                .andExpect(jsonPath("$[0].requisitionNumber").value(requisitions.get(0).getRequisitionNumber()))
                .andExpect(jsonPath("$[0].requisitionStatus").value(requisitions.get(0).getRequisitionStatus().toString()))
                .andExpect(jsonPath("$[1].requisitionId").value(requisitions.get(1).getRequisitionId()))
                .andExpect(jsonPath("$[1].requisitionNumber").value(requisitions.get(1).getRequisitionNumber()))
                .andExpect(jsonPath("$[1].requisitionStatus").value(requisitions.get(0).getRequisitionStatus().toString()));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "PROCUREMENT_CLERK")
    public void testViewingPendingRequisitions_WhenMixedRequistions() throws Exception {

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(40000.0));
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

        CostCenter costCenter1 = new CostCenter();
        costCenter1.setCostCenterId("CC-102");
        costCenter1.setName("Procurement");
        costCenter1.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(23000.0));
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(10000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        CostCenter savedCostCenter1 = costCenterRepository.findByCostCenterId(costCenter1.getCostCenterId()).orElseThrow();
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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));
        List<ItemsInputDS> itemsInputDSList = List.of(new ItemsInputDS("ITEM-0010", "Laptop", 5, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS requestOne = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-001", LocalDate.now(), items, PriorityLevel.CRITICAL, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");
        CreateRequisitionInputDS requestTwo = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter1.getCostCenterId(), "REQ-002", LocalDate.now(), itemsInputDSList, PriorityLevel.HIGH, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestOne)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestTwo)))
                .andExpect(status().isOk());

        PurchaseRequisition purchaseRequisitionOne = purchaseRequisitionRepository.findByRequisitionNumber(requestOne.getRequisitionNumber()).orElseThrow();
        PurchaseRequisition purchaseRequisitionTwo = purchaseRequisitionRepository.findByRequisitionNumber(requestTwo.getRequisitionNumber()).orElseThrow();
        mockMvc.perform(get("/purchaserequisition/viewpending")
                        .param("departmentId", savedDepartment.getDepartmentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].requisitionId").value(purchaseRequisitionTwo.getRequisitionId()))
                .andExpect(jsonPath("$[0].requisitionNumber").value("REQ-002"))
                .andExpect(jsonPath("$[0].requisitionStatus").value("PENDING"));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "PROCUREMENT_CLERK")
    public void testShouldReturnEmptyListWhenNoPendingRequisitions() throws Exception {

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG-104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(50000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-11");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-11");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(33000.0));
        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0001");
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

        List<ItemsInputDS> items = List.of(new ItemsInputDS("ITEM-0001", "Laptop", 3, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(2), "Intel Core i7"));

        CreateRequisitionInputDS requestOne = new CreateRequisitionInputDS(contactDetails, savedDepartment.getDepartmentId(), savedCostCenter.getCostCenterId(), "REQ-004", LocalDate.now(), items, PriorityLevel.CRITICAL, LocalDate.now().plusMonths(8), "Creating new requisition", "SUP-001");

        // Act & Assert
        mockMvc.perform(post("/purchaserequisition/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestOne)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/purchaserequisition/viewpending")
                        .param("departmentId", savedDepartment.getDepartmentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplierSuccessfully() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.supplierName").value("malam"))
                .andExpect(jsonPath("$.supplierCategory").value("It industry"))
                .andExpect(jsonPath("$.supplierContactDetail.fullName").value("VendorName"))
                .andExpect(jsonPath("$.supplierContactDetail.email").value("email@domain.com"))
                .andExpect(jsonPath("$.supplierContactDetail.phone").value("+123456789"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].paymentMethods[0]").value("BANK_TRANSFER"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].preferredPaymentMethod").value("BANK_TRANSFER"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].accountHolderName").value("VendorName"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].accountHolderPhoneNumber").value("+123456789"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].bankName").value("First National Bank"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].bankAccountNumber").value("12345678901234"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].currencyType").value("USD"))
                .andExpect(jsonPath("$.supplierPaymentMethods[0].paymentTerms").value("NET_30"));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithNullInputData_shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithoutSupplierName_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS(null,"It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier name is required field can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithMissingSupplierCategory_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam",null,"TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier category can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithMissingTIN_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry",null,"REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier tax identification number is required field can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithMissingRegistrationNumber_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321",null,supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier Registration Number can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithMissingContactDetails_shouldReturnBadRequest() throws Exception {
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",null,null, "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",null,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier Person's contact detail can't be null."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithMissingPaymentMethods_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,null,items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("vendor's payment method must be specified. can't be null or Empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithMissingItems_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),null,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("inventory data can't be null."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithInvalidEmail_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "invalid-email", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("please Enter valid email address.based on the emailRegex = \"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$\";\n"));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithDuplicateEmail_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierContactDetailsInputDS supplierContactDetailsInputDS2 =  new SupplierContactDetailsInputDS("Vendor", "email@domain.com", "+124356789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        List<ItemsInputDS> itemsInputDSList = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"));
        AddSupplierInputDS addSupplierInputDSOne = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        AddSupplierInputDS addSupplierInputDSTwo = new AddSupplierInputDS("malam","It industry","TIN-987654322","REG-2024-4568",supplierContactDetailsInputDS2,List.of(supplierPaymentMethodsInputDS),itemsInputDSList,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDSOne)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSupplierInputDSTwo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier with email email@domain.com already exists."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithInvalidPhoneNumber_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Please Enter valid phone number with country code."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithDuplicatePhoneNumber_shouldReturnBadRequest() throws Exception {

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierContactDetailsInputDS supplierContactDetailsInputDS1 =  new SupplierContactDetailsInputDS("Vendor", "vendor@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        List<ItemsInputDS> itemsInputDSList = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"));

        AddSupplierInputDS addSupplierInputDSOne = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        AddSupplierInputDS addSupplierInputDSTwo = new AddSupplierInputDS("IE","It industry","TIN-987654322","REG-2024-4568",supplierContactDetailsInputDS1,List.of(supplierPaymentMethodsInputDS),itemsInputDSList,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDSOne)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSupplierInputDSTwo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier with phone Number +123456789 already exists. "));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithDuplicateTIN_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierContactDetailsInputDS supplierContactDetailsInputDS1 =  new SupplierContactDetailsInputDS("Vendor", "vendor@domain.com", "+124356789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        List<ItemsInputDS> itemsInputDSList = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"));

        AddSupplierInputDS addSupplierInputDSOne = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        AddSupplierInputDS addSupplierInputDSTwo = new AddSupplierInputDS("IE","It industry","TIN-987654321","REG-2024-4568",supplierContactDetailsInputDS1,List.of(supplierPaymentMethodsInputDS),itemsInputDSList,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDSOne)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSupplierInputDSTwo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier with tax identification number TIN-987654321 already exists."));

    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testCreateSupplier_WithDuplicateRegistrationNumber_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierContactDetailsInputDS supplierContactDetailsInputDS1 =  new SupplierContactDetailsInputDS("Vendor", "vendor@domain.com", "+124356789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        List<ItemsInputDS> itemsInputDSList = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"));
        AddSupplierInputDS addSupplierInputDSOne = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        AddSupplierInputDS addSupplierInputDSTwo = new AddSupplierInputDS("IE","It industry","TIN-987654312","REG-2024-4567",supplierContactDetailsInputDS1,List.of(supplierPaymentMethodsInputDS),itemsInputDSList,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSupplierInputDSOne)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addSupplierInputDSTwo)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier with registration number REG-2024-4567 already exists."));
    }
    @Test
    @WithMockUser(username = "tekia",roles = "supplierOfficer")
    public void testCreateSupplier_WithoutIndustryType_shouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam",null,"TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(addSupplierInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier category can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    @Rollback(value = false)
    public void testUpdateSupplierContactDetail_WithValidData_Successfully() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();

        SupplierContactDetailsInputDS newSupplierContactDetail = new SupplierContactDetailsInputDS("Meried Bekele","meriedbekele@ie.com","+251911122233","Addis Ababa");
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE","ICT Solution Services","TIN-12345678","SUP-2025-0901",newSupplierContactDetail,List.of(supplierPaymentMethodsInputDS),LocalDate.now());

        mockMvc.perform(post("/supplier/update")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.supplierName").value("IE"))
                .andExpect(jsonPath("$.supplierCategory").value("ICT Solution Services"))
                .andExpect(jsonPath("$.contactDetail.fullName").value("Meried Bekele"))
                .andExpect(jsonPath("$.contactDetail.email").value("meriedbekele@ie.com"))
                .andExpect(jsonPath("$.contactDetail.phone").value("+251911122233"))
                .andExpect(jsonPath("$.paymentMethods[0].paymentMethods[0]").value("BANK_TRANSFER"))
                .andExpect(jsonPath("$.paymentMethods[0].preferredPaymentMethod").value("BANK_TRANSFER"))
                .andExpect(jsonPath("$.paymentMethods[0].accountHolderName").value("VendorName"))
                .andExpect(jsonPath("$.paymentMethods[0].accountHolderPhoneNumber").value("+123456789"))
                .andExpect(jsonPath("$.paymentMethods[0].bankName").value("First National Bank"))
                .andExpect(jsonPath("$.paymentMethods[0].bankAccountNumber").value("12345678901234"))
                .andExpect(jsonPath("$.paymentMethods[0].currencyType").value("USD"))
                .andExpect(jsonPath("$.paymentMethods[0].paymentTerms").value("NET_30"))
                .andExpect(jsonPath("$.updatedDate" ).value(LocalDate.now().toString()));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNullInput_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();

        SupplierContactDetailsInputDS newSupplierContactDetail = new SupplierContactDetailsInputDS("Meried Bekele","meriedbekele@ie.com","+251911122233","Addis Ababa");
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),null,null,null,null,null,null,LocalDate.now());

        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isInternalServerError());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetial_WithNonExistedSupplierId_ShouldReturnNotFound() throws Exception {

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        SupplierContactDetailsInputDS newSupplierContactDetail = new SupplierContactDetailsInputDS("Meried Bekele","meriedbekele@ie.com","+251911122233","Addis Ababa");
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS("SUP_2025_0901","IE","ICT Solution Services","TIN-12345678","SUP-2025-0901",newSupplierContactDetail,List.of(supplierPaymentMethodsInputDS),LocalDate.now());

        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Supplier with id SUP_2025_0901 not found."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNUllContactDetail_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE","ICT Solution Services","TIN-12345678","SUP-2025-0901",null,List.of(supplierPaymentMethodsInputDS),LocalDate.now());

        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier contact detail can't be null."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNUllPaymentMethods_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));
        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));
        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE","ICT Solution Services","TIN-12345678","SUP-2025-0901",supplierContactDetailsInputDS,null,LocalDate.now());
        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("supplier's payment method must be specified. can't be null or Empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNUllSupplierName_ShouldReturnInternalServerError() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));
        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),null,"ICT Solution Services","TIN-12345678","SUP-2025-0901",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),LocalDate.now());

        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("supplier name can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNullSupplierCategory_ShouldReturnInternalServerError() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());
        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE",null,"TIN-12345678","SUP-2025-0901",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),LocalDate.now());
        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("supplier category can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNUllSupplierRegistrationNumber_ShouldReturnInternalServerError() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));
        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());
        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE","ICT Solution Services","TIN-12345678",null,supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),LocalDate.now());
        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("supplier registration number can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithNUllSupplierTinNumber_ShouldReturnInternalServerError() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));
        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());
        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());

        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE","ICT Solution Services",null,"SUP-2025-0901",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),LocalDate.now());

        mockMvc.perform(post("/supplier/update")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("supplier tax identification number can't be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testUpdateSupplierContactDetail_WithInvalidUpationDate_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));
        AddSupplierInputDS supplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        mockMvc.perform(post("/supplier/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(supplierInputDS)))
                .andExpect(status().isOk());
        Supplier supplier =  supplierRepository.findByRegistrationNumber(supplierInputDS.getRegistrationNumber()).get();
        UpdateSupplierContactDetailInputDS supplierContactDetailInputDS = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"IE","ICT Solution Services","TIN-12345678","SUP-2025-0901",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),LocalDate.now().minusDays(1));

        mockMvc.perform(post("/supplier/update")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(supplierContactDetailInputDS)))
                .andExpect(status().isBadRequest());
    }

    /** test for viewing supplier performance */
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testViewSupplierPerformance_successfully() throws Exception{
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));
        Supplier savedSupplier = supplierRepository.save(supplier);

        Supplier supplier1 = new Supplier("Microsoft","Electronics","21323","MS365",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Lion International Bank","101293049950", "ME Me","+2510987654433",List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0))),
                List.of(item,item2),LocalDate.now().minusMonths(5));
        Supplier savedSupplier1 = supplierRepository.save(supplier1);

        Supplier supplier2 = new Supplier("Lenevo","Electronics","098594676","LN10232",new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai"),
                List.of(new SupplierPaymentMethod("Lion International Bank","101293049950", "Me Me","+251978465232",List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0))),
                List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier savedSupplier2 = supplierRepository.save(supplier2);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplier,quantitativeMetrics,qualitativeMetrics,LocalDate.now());
        SupplierPerformance supplierPerformance1 = new SupplierPerformance(savedSupplier1,quantitativeMetricsOne,qualitativeMetricsOne,LocalDate.now().minusDays(10));
        SupplierPerformance supplierPerformance2 =  new SupplierPerformance(savedSupplier2, quantitativeMetricsTwo,qualitativeMetricsTwo,LocalDate.now().minusDays(10));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformance1);
        supplierPerformanceRepository.save(supplierPerformance2);


        // Create the input data for the request
        ViewSupplierPerformanceReportInputDS viewSupplierPerformanceReportInputDS =  new ViewSupplierPerformanceReportInputDS("Electronics",LocalDate.now().minusDays(20),LocalDate.now());

        String category = viewSupplierPerformanceReportInputDS.getSupplierCategory();
        LocalDate startDate = viewSupplierPerformanceReportInputDS.getStartDate();
        LocalDate endDate = viewSupplierPerformanceReportInputDS.getEndDate();

        List<SupplierPerformance> supplierPerformances = supplierPerformanceRepository.findAll();
        // Perform the request with proper query parameters
        mockMvc.perform(get("/supplier/view-performance")
                        .param("category", category)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testViewSupplierPerformance_withNotExistedCategory_returnBadRequest() throws Exception {
        // Create the input data for the request
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));
        Supplier savedSupplier = supplierRepository.save(supplier);

        Supplier supplier1 = new Supplier("Microsoft","Electronics","21323","MS365",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Lion International Bank","101293049950", "ME Me","+2510987654433",List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0))),
                List.of(item,item2),LocalDate.now().minusMonths(5));
        Supplier savedSupplier1 = supplierRepository.save(supplier1);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplier,quantitativeMetrics,qualitativeMetrics,LocalDate.now());
        SupplierPerformance supplierPerformance1 = new SupplierPerformance(savedSupplier1,quantitativeMetricsOne,qualitativeMetricsOne,LocalDate.now().minusDays(10));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformance1);

        String category = "HOUSEHOLD";
        LocalDate startDate = LocalDate.now().minusMonths(1);
        LocalDate endDate = LocalDate.now();
        mockMvc.perform(get("/supplier/view-performance")
                        .param("category", category)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No suppliers found for the specified category."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    public void testViewSupplierPerformance_withNoSuppliersEvaluatedWithInDate_returnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(), supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1, item2), LocalDate.now().minusMonths(5));
        Supplier savedSupplier = supplierRepository.save(supplier);

        Supplier supplier1 = new Supplier("Microsoft", "Electronics", "21323", "MS365", new SupplierContactDetail("MicroSoft", "microsoft@gmail.com", "+2510987654433", "Dubai"),
                List.of(new SupplierPaymentMethod("Lion International Bank", "101293049950", "ME Me", "+2510987654433", List.of(CREDIT_CARD, CASH, PAYPAL), CASH, NET_30, "USD", BigDecimal.valueOf(20000.0))),
                List.of(item, item2), LocalDate.now().minusMonths(3));
        Supplier savedSupplier1 = supplierRepository.save(supplier1);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplier, quantitativeMetrics, qualitativeMetrics, LocalDate.now());
        SupplierPerformance supplierPerformance1 = new SupplierPerformance(savedSupplier1, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusDays(10));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformance1);

        String category = "Electronics";
        LocalDate startDate = LocalDate.now().minusMonths(2);
        LocalDate endDate = LocalDate.now().minusDays(11);
        mockMvc.perform(get("/supplier/view-performance")
                        .param("category", category)
                        .param("startDate", startDate.toString())
                        .param("endDate", endDate.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("No supplier performance records found within the specified date range."));
    }

    /** this is the integration test for Purchase Order. */
    @Test
    @WithMockUser(username = "tekia", roles = "supplierOfficer")
    @Rollback(value = false)
    public void testCreatePurchaseOrderSuccessfully() throws Exception{
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "tekia", roles = "clerk purchase order")
    public void testCreatePurchaseOrder_WithEmptyRequisition_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(requisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");
        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Requisition not found"));

    }
    @Test
    @WithMockUser(username = "tekia", roles = "clerk purchase order")
    public void testCreatePurchaseOrder_WithNotApprovedRequisition_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("there is no requisition in which status is approved,so we can't create any purchase order for not approved requisitions."));

    }
    @Test
    @WithMockUser(username = "tekia", roles = "clerk purchase order")
    public void testCreatePurchaseOrder_WithNullSupplier_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, null, List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("SupplierId must not be null or empty."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "clerk purchase order")
    public void testCreatePurchaseOrder_WithInvalidDeliveryDate_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().minusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Delivery Date must not be in the past."));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "clerk purchase order")
    public void testCreatePurchaseOrder_WithNonExistedDepartment_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS("DI-101", purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Department not found"));
    }
    @Test
    @WithMockUser(username = "tekia", roles = "clerk purchase order")
    public void testCreatePurchaseOrder_WithNullInputData_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        purchaseRequisitionRepository.save(requisition);

        CreatePurchaseOrderInputDS purchaseOrderInputDS = null;
        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseOrderInputDS)))
                .andExpect(status().isBadRequest());
    }
    /** Track Purchase Order Integration Tests */
    @Test
    @WithMockUser(username = "Tekia", roles = "purchaseOrderOfficer")
    @Rollback(value = false)
    public void testTrackPurchaseOrder_WithValidData_Successfully() throws Exception{
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.getFirst();
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "Tekia", roles = "purchaseOrderOfficer")
    public void testTrackPurchaseOrderWithNullInput_returnsInternalServerError() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Tekia", roles = "purchaseOrderOfficer")
    public void testTrackPurchaseOrderWithEmptyOrderId_returnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.getFirst();
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), null, LocalDate.now().plusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Order Id must not be null or empty."));

    }
    @Test
    @WithMockUser(username = "Tekia", roles = "purchaseOrderOfficer")
    public void testTrackPurchaseOrderWithNullApprovalDate_ReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.getFirst();
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), null);

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Approval Date must not be null."));
    }
    @Test
    @WithMockUser(username = "Tekia", roles = "purchaseOrderOfficer")
    public void testTrackPurchaseOrderWithPastApprovalDate_ReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.getFirst();
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().minusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Approval date can't be before order date."));

    }

    /** Electronically Generated Purchase Order Integration Tests */
    @Test
    @WithMockUser(username = "Tekia", roles = "OrderReciever")
    public void testSendPurchaseOrderToSupplier_WithValidInput_Successfully() throws Exception {

        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.getFirst();
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());

        // Prepare input for sending purchase order to supplier

        PurchaseOrder approvedPurchaseOrder = purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId()).get();

        SendPurchaseOrderToSupplierInputDS inputDS = new SendPurchaseOrderToSupplierInputDS(
                new PurchaseOrderContactDetailsInputDS("tsegay", approvedPurchaseOrder.getDepartment().getDepartmentId(), "tsegay@gmail.com", "+251979416534", "ReporterContactDetail"),
                approvedPurchaseOrder.getSupplier().getSupplierId());

        mockMvc.perform(post("/purchaseorder/generate-electronically")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderId").value(approvedPurchaseOrder.getOrderId()))
                .andExpect(jsonPath("$[0].supplierId").value(approvedPurchaseOrder.getSupplier().getSupplierId()))
                .andExpect(jsonPath("$[0].dateOfSent").value(approvedPurchaseOrder.getOrderDate().toString()));
    }
    @Test
    @WithMockUser(username = "Tekia", roles = "OrderReciever")
    public void testSendPurchaseOrderToSupplier_NullInputData_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.getFirst();
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());

        // Prepare input for sending purchase order to supplier

        mockMvc.perform(post("/purchaseorder/generate-electronically")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Tekia", roles = "OrderReciever")
    public void testSendPurchaseOrderToSupplier_EmptySupplierId_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.get(0);
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());

        // Prepare input for sending purchase order to supplier

        PurchaseOrder approvedPurchaseOrder = purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId()).get();

        SendPurchaseOrderToSupplierInputDS inputDS = new SendPurchaseOrderToSupplierInputDS(
                new PurchaseOrderContactDetailsInputDS("tsegay", approvedPurchaseOrder.getDepartment().getDepartmentId(), "tsegay@gmail.com", "+251979416534", "ReporterContactDetail"),
                " ");

        mockMvc.perform(post("/purchaseorder/generate-electronically")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "Tekia", roles = "OrderReciever")
    public void testSendPurchaseOrderToSupplier_WithNotApprovedPurchaseOrder_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.get(0);

        SendPurchaseOrderToSupplierInputDS inputDS = new SendPurchaseOrderToSupplierInputDS(
                new PurchaseOrderContactDetailsInputDS("tsegay", purchaseOrder.getDepartment().getDepartmentId(), "tsegay@gmail.com", "+251979416534", "ReporterContactDetail"),
                purchaseOrder.getSupplier().getSupplierId());

        // Simulate that the purchase order is not approved
        mockMvc.perform(post("/purchaseorder/generate-electronically")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Tekia", roles = "OrderReciever")
    public void testSendPurchaseOrderToSupplier_notExistedSupplier_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget( BigDecimal.valueOf(200000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software development", "develop software solution.", savedBudget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(90000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        // Fetch the saved inventory items
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplier.getId()).get();

        // Create requested items
        RequestedItem requestedItem = new RequestedItem(savedInventory1,3);
        RequestedItem requestedItem1 = new RequestedItem(savedInventory2,2);
        RequestedItem requestedItem2 = new RequestedItem(savedInventory3,5);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem1);
        requisition.addItem(requestedItem2);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/purchaseorder/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());

        List<PurchaseOrder> purchaseOrders = purchaseOrderRepository.findAll();
        PurchaseOrder purchaseOrder = purchaseOrders.get(0);
        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", purchaseOrder.getDepartment().getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        mockMvc.perform(post("/purchaseorder/approve")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isOk());

        // Prepare input for sending purchase order to supplier

        PurchaseOrder approvedPurchaseOrder = purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId()).get();


        SendPurchaseOrderToSupplierInputDS inputDS = new SendPurchaseOrderToSupplierInputDS(
                new PurchaseOrderContactDetailsInputDS("tsegay", approvedPurchaseOrder.getDepartment().getDepartmentId(), "tsegay@gmail.com", "+251979416534", "ReporterContactDetail"),
                "Sup-101");

        mockMvc.perform(post("/purchaseorder/generate-electronically")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest());
    }
    /** Contract Integration Tests */
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    @Rollback(false)
    public void testCreateContractSuccessfully() throws Exception{
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testCreateContract_NullInput_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        purchaseOrderRepository.save(purchaseOrder);

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testCreateContract_WithNotApprovedPurchaseOrder_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testCreateContract_WithNullSupplierId_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, null, "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isBadRequest());

    }

    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testCreateContract_EndDateBeforeStartDate_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(25),LocalDate.now().plusDays(13),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Start Date must not be after End Date."));
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testCreateContract_MissingDeliveryTerms_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, null,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Delivery Terms must not be null."));
    }

    /** this is the test for viewing contract details when creating purchase order.*/
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testViewContractDetailSuccessfully() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(230000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(200000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());

        List<Contract> contracts = contractRepository.findAll();
        Contract contract = contracts.get(0);
        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(2),"Air");

        mockMvc.perform(post("/contract/"+contract.getContractId()+"/viewcontractdetails")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contractId").value(contract.getContractId()))
                .andExpect(jsonPath("$.contractTitle").value(contract.getContractTitle()))
                .andExpect(jsonPath("$.paymentTerms").value(contract.getPaymentTerms().toString()))
                .andExpect(jsonPath("$.contractStatus").value("ACTIVE"));
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testViewContractDetails_EmptyContractId_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());


        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/contract/"+" "+"/viewcontractdetails")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    @Rollback(value = false)
    public void testViewContractDetails_WhenMisMatchAmongContractSupplierAndPurchaseOrderSupplier_shouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierContactDetail contactDetailOne = new SupplierContactDetail("Mine", "mine@supplier.com", "+251912123344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));
        SupplierPaymentMethod paymentMethodOne = new SupplierPaymentMethod("Some Bank", "123564", contactDetailOne.getFullName(), contactDetailOne.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier supplierOne = new Supplier("Hp", "IT Supplier", "SUP321", "TIN321", contactDetailOne, List.of(paymentMethodOne), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);
        Supplier savedSupplierOne = supplierRepository.save(supplierOne);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventoryOne = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());
        List<Contract> contracts = contractRepository.findAll();
        Contract contract = contracts.get(0);
        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplierOne.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(2),"Air");

        mockMvc.perform(post("/contract/"+contract.getContractId() +"/viewcontractdetails")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("The supplier of the purchase order must match the supplier of the contract."));
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    public void testViewContractDetails_WithLargePurchaseOrderTotalCostsThanContract_shouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(100000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());

        List<Contract> contracts = contractRepository.findAll();
        Contract contract = contracts.get(0);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(2),"Air");

        mockMvc.perform(post("/contract/"+contract.getContractId()+"/viewcontractdetails")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract manager")
    @Rollback(false)
    public void testViewContractDetails_ContractIdNotFound_ShouldReturnNotFound() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",savedDepartment.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(savedDepartment.getDepartmentId(), purchaseOrderContactDetailsInputDS, savedSupplier.getSupplierId(), List.of(savedRequisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        mockMvc.perform(post("/contract/"+"CC-123"+"/viewcontractdetails")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPurchaseOrderInputDS)))
                .andExpect(status().isBadRequest());
    }

    /** testing for notifing contracts */
    @Test
    @WithMockUser(username = "samrawit", roles = "contract officer")
    @Rollback(false)
    public void testNotifyingContractsSuccessfully() throws Exception{
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusDays(13),BigDecimal.valueOf(120000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/contract/notify")
                        .param("daysBeforeExpirationThreshold", "30")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract officer")
    public void testNotifyContracts_WithNegativethersholdDay_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusDays(13),BigDecimal.valueOf(120000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());

       mockMvc.perform(get("/contract/notify")
               .param("daysBeforeExpirationThreshold", "-5")
               .with(csrf())
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "samrawit", roles = "contract officer")
    @Rollback(value = false)
    public void testNotifyContract_WithNotApprovedContract_ShouldReturnUnauthorized() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(100000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(12000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, savedSupplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusDays(13),BigDecimal.valueOf(120000.0), NET_30, DeliveryTerms.CIF,true,List.of(savedPurchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        mockMvc.perform(post("/contract/create")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputData)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/contract/notify")
                        .param("daysBeforeExpirationThreshold", "30")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /** testing for invoice reconciliation */
    @Test
    @WithMockUser(username = "Zeray",roles = "Account officer")
    public void testReconcileInvoiceSuccessfully() throws Exception{

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);

        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);

        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");
        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").exists())
                .andExpect(jsonPath("$.status").value("MATCHED"))
                .andExpect(jsonPath("$.reconciliationDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.messages").value("Successfully Reconciled with out Discrepancy."));
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WhenDeliveryReceiptIDIsMissing_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);

        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), null,LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WhenInvoiceIDIsMissing_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, null, savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WhenPurchaseOrderIDIsMissing_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), null, savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WhenReconcilationDateIsMissing_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),null);

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WhenAccountantDetailsAreMissing_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(null, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithNonExistedDeliveryReceipt_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), "RC-001",LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithNonExistedInvoice_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, "INV-101", savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithNonExistedPurchaseOrder_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), "ORD-101", savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithPastReconcilationDate_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now().minusDays(10));

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithMisMatchINvoiceAndDeliveryReceipt_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);
        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");


        SupplierContactDetail contactDetail2 = new SupplierContactDetail("Brenna", "brenna@supplier.com", "+251933441122", "Ethiopia");
        Supplier supplier2 = new Supplier("HP", "IT Vendor", "SUP124", "TIN124", contactDetail2, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier2 = supplierRepository.save(supplier2);
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier2.getId()).get();
        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);

        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);

        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");
        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory2,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier2, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithMisMatchINvoiceAndPurchaseOrder_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        SupplierContactDetail contactDetail1 = new SupplierContactDetail("mine", "mine@supplier.com", "+251911224433", "Ethiopia");
        Supplier supplier1 = new Supplier("HP", "IT Vendor", "SUP124", "TIN124", contactDetail1, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier1 = supplierRepository.save(supplier1);

        // Retrieve the saved inventory item

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier1.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier1,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testReconcileInvoice_WithNullInput_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    @Rollback(value = false)
    public void testViewReconcilationHistorySuccessfully() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/total")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconcilationHistory_WhenThereIsNoHistory_ReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");
        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);

        mockMvc.perform(get("/paymentreconciliation/viewhistory/total")
                        .with(csrf())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    @Rollback(value = false)
    public void testViewReconcilationHistoryWithSpeficTimeRangeSuccessfully() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);

        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);

        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");
        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
        PaymentReconciliation paymentReconciliation = paymentReconciliations.get(0);

        mockMvc.perform(get("/paymentreconciliation/viewhistory/paymentDateBased")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("startDate", LocalDate.now().minusDays(30).toString())
                .param("endDate", LocalDate.now().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentId").value(paymentReconciliation.getPaymentId()))
                .andExpect(jsonPath("$[0].invoiceId").value(paymentReconciliation.getInvoice().getInvoiceId()));
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconcilationHistoryWithEmptyHistoryInSpecificTimeRange_returnsBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/paymentDateBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("startDate", LocalDate.now().minusDays(60).toString())
                        .param("endDate", LocalDate.now().minusDays(30).toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("there is no Reconciled Payment Record with payment date is in the specified Range."));
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconcilationHistoryWithInvalidTimeRange_returnsBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/paymentDateBased")
                .param("startDate", LocalDate.now().toString())
                .param("endDate", LocalDate.now().minusDays(20).toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("End date cannot be before Start date."));
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconcilationHistoryWithSameSupplierSuccessfully() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
        PaymentReconciliation paymentReconciliation = paymentReconciliations.get(0);

        mockMvc.perform(get("/paymentreconciliation/viewhistory/supplierBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", paymentReconciliation.getInvoice().getSupplier().getSupplierId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].paymentId").value(paymentReconciliation.getPaymentId()))
                .andExpect(jsonPath("$[0].invoiceId").value(paymentReconciliation.getInvoice().getInvoiceId()));
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconciliationHistoryWithEmptysupplierId_ReturnsBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/supplierBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("SupplierId cannot be null or empty."));
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconciliationHistoryWithNonExistedsupplierId_returnsBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/supplierBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", "SUP-101"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconcilationHistoryWithSameStatusSuccessfully() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
        PaymentReconciliation paymentReconciliation = paymentReconciliations.get(0);

        mockMvc.perform(get("/paymentreconciliation/viewhistory/statusBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("status", paymentReconciliation.getReconciliationStatus().toString()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconciliationHistoryWithEmptyStatus_returnsBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/statusBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("status", ""))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testViewReconciliationHistoryWithNullStatus_returnsBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
        PaymentReconciliation paymentReconciliation = paymentReconciliations.get(0);

        mockMvc.perform(get("/paymentreconciliation/viewhistory/statusBased")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("status", (String) null))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    @Rollback(false)
    public void testNotifyingPaymentApprovalSuccessfully() throws Exception{
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now().plusDays(1), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/total")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/notify")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .param("expectedPaymentDate", LocalDate.now().plusDays(10).toString()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testNotifyingPaymentApproval_WithMissingExpectedPaymentDate_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/viewhistory/total")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/notify")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                .param("expectedPaymentDate", ""))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testNotifyingPaymentApproval_WithPastExpectedPaymentDate_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
        PaymentReconciliation paymentReconciliation = paymentReconciliations.get(0);
        mockMvc.perform(get("/paymentreconciliation/viewhistory/total")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/notify")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("expectedPaymentDate",LocalDate.now().minusDays(10).toString()))
                .andExpect(status().isBadRequest());

    }
    @Test
    @WithMockUser(username = "zeriay", roles = "invoice account Officer")
    public void testNotifyInvoice_WhenNoInvoicesFound_ShouldReturnEmptyList() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(200000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem = new InvoicedItem(savedInventory,10);
        User accountOfficer = new User("mulu berhe", "mulu@gmail.com", "+251979417663", savedDepartment, "developer");
        User savedOfficer = userRepository.save(accountOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), savedSupplier,savedPurchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now().plusMonths(2), savedOfficer," ");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem receivedItem = new DeliveredItem(savedInventory,10);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplier, savedPurchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",savedRequester,"");

        DeliveryReceipt savedDeliveryReceipt =  deliveryReceiptRepository.save(deliveryReceipt);
        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, savedInvoice.getInvoiceId(), savedPurchaseOrder.getOrderId(), savedDeliveryReceipt.getReceiptId(),LocalDate.now());

        mockMvc.perform(post("/paymentreconciliation/reconcile")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDs)))
                .andExpect(status().isOk());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findAll();
        PaymentReconciliation paymentReconciliation = paymentReconciliations.get(0);
        mockMvc.perform(get("/paymentreconciliation/viewhistory/total")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(get("/paymentreconciliation/notify")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("expectedPaymentDate",LocalDate.now().plusDays(10).toString()))
                .andExpect(status().isBadRequest());

    }

    /** this is the test for supplier performance tracking. */
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    @Rollback(false)
    public void testGenerateSupplierPerformanceReport_WithValidInput_Successfully() throws Exception{
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/generateReport")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("supplierId", supplierOne.getSupplierId().toString()))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testGenerateSupplierPerformanceReport_WithNullInput_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/generateReport")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", (String) null))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testGenerateSupplierPerformanceReport_WithMissingSupplierId_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/generateReport")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", " "))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testGenerateSupplierPerformanceReport_WithNonExistingSupplierId_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/generateReport")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", "SUP-101"))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testGenerateSupplierPerformanceReport_WithNotEvaluatedSupplier_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        mockMvc.perform(get("/supplierperformance/generateReport")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", supplierOne.getSupplierId().toString()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    @Rollback(false)
    public void testEvaluateSupplierForRenewalContract_WithValidInput_SuccessfullyRenewsToNewSupplier() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier savedSupplierOne = supplierRepository.save(supplierOne);
        Supplier savedSupplierTwo = supplierRepository.save(supplierTwo);
        Supplier savedSupplierThree = supplierRepository.save(supplierThree);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItemOne = new RequestedItem(savedInventory1,5);
        RequestedItem requestedItemTwo = new RequestedItem(savedInventory2, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", savedDepartment, "Developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),savedRequester,savedDepartment, savedCostCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplierThree, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(savedSupplierTwo, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(savedSupplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(savedDepartment,List.of(savedRequisitionOne),LocalDate.now(), savedSupplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrderOne = purchaseOrderRepository.save(purchaseOrderOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", savedSupplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(savedPurchaseOrderOne),List.of(fileOne),LocalDate.now());
        contractOne.setStatus(ContractStatus.ACTIVE);
        Contract contractTwo = new Contract("contractTwo", savedSupplierOne,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileTwo),LocalDate.now());
        contractTwo.setStatus(ContractStatus.ACTIVE);
        Contract contractThree = new Contract("contractThree", savedSupplierOne,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileThree),LocalDate.now());
        contractThree.setStatus(ContractStatus.ACTIVE);

        contractRepository.save(contractOne);
        contractRepository.save(contractTwo);
        contractRepository.save(contractThree);

        mockMvc.perform(post("/supplierperformance/review")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .param("supplierCatergory", savedSupplierOne.getSupplierCategory().toString()))
                .andExpect(status().isOk());

    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier-performance-Officer")
    public void testEvaluateSupplierForRenewalContract_WithNullInput_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier savedSupplierOne = supplierRepository.save(supplierOne);
        Supplier savedSupplierTwo = supplierRepository.save(supplierTwo);
        Supplier savedSupplierThree = supplierRepository.save(supplierThree);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItemOne = new RequestedItem(savedInventory1,5);
        RequestedItem requestedItemTwo = new RequestedItem(savedInventory2, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", savedDepartment, "Developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),savedRequester,savedDepartment, savedCostCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(savedSupplierTwo, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(savedSupplierThree, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(savedDepartment,List.of(savedRequisitionOne),LocalDate.now(), savedSupplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrderOne = purchaseOrderRepository.save(purchaseOrderOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", savedSupplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(savedPurchaseOrderOne),List.of(fileOne),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", savedSupplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileTwo),LocalDate.now());
        Contract contractThree = new Contract("contractThree", savedSupplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileThree),LocalDate.now());

        contractRepository.save(contractOne);
        contractRepository.save(contractTwo);
        contractRepository.save(contractThree);

        mockMvc.perform(post("/supplierperformance/review")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier-performance-Officer")
    public void testEvaluateSupplierForRenewalContract_WithMissingSupplierCategory_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier savedSupplierOne = supplierRepository.save(supplierOne);
        Supplier savedSupplierTwo = supplierRepository.save(supplierTwo);
        Supplier savedSupplierThree = supplierRepository.save(supplierThree);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItemOne = new RequestedItem(savedInventory1,5);
        RequestedItem requestedItemTwo = new RequestedItem(savedInventory2, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", savedDepartment, "Developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),savedRequester,savedDepartment, savedCostCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(savedSupplierTwo, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(savedSupplierThree, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(savedDepartment,List.of(savedRequisitionOne),LocalDate.now(), savedSupplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrderOne = purchaseOrderRepository.save(purchaseOrderOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", savedSupplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(savedPurchaseOrderOne),List.of(fileOne),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", savedSupplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileTwo),LocalDate.now());
        Contract contractThree = new Contract("contractThree", savedSupplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileThree),LocalDate.now());

        contractRepository.save(contractOne);
        contractRepository.save(contractTwo);
        contractRepository.save(contractThree);

        mockMvc.perform(post("/supplierperformance/review")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierCatergory", " ".toString()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier-performance-Officer")
    public void testEvaluateSupplierForRenewalContract_WhenNoSupplierWithCategory_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier savedSupplierOne = supplierRepository.save(supplierOne);
        Supplier savedSupplierTwo = supplierRepository.save(supplierTwo);
        Supplier savedSupplierThree = supplierRepository.save(supplierThree);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItemOne = new RequestedItem(savedInventory1,5);
        RequestedItem requestedItemTwo = new RequestedItem(savedInventory2, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", savedDepartment, "Developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),savedRequester,savedDepartment, savedCostCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(savedSupplierTwo, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(savedSupplierThree, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(savedDepartment,List.of(savedRequisitionOne),LocalDate.now(), savedSupplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrderOne = purchaseOrderRepository.save(purchaseOrderOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", savedSupplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(savedPurchaseOrderOne),List.of(fileOne),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", savedSupplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileTwo),LocalDate.now());
        Contract contractThree = new Contract("contractThree", savedSupplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileThree),LocalDate.now());

        contractRepository.save(contractOne);
        contractRepository.save(contractTwo);
        contractRepository.save(contractThree);

        mockMvc.perform(post("/supplierperformance/review")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierCatergory", "HouseHoulds".toString()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier-performance-Officer")
    public void testEvaluateSupplierForRenewalContract_WithNoSupplierPerformanceWithIntheCategory_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Machines","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier savedSupplierOne = supplierRepository.save(supplierOne);
        Supplier savedSupplierTwo = supplierRepository.save(supplierTwo);
        Supplier savedSupplierThree = supplierRepository.save(supplierThree);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItemOne = new RequestedItem(savedInventory1,5);
        RequestedItem requestedItemTwo = new RequestedItem(savedInventory2, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", savedDepartment, "Developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),savedRequester,savedDepartment, savedCostCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(savedSupplierThree, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(savedDepartment,List.of(savedRequisitionOne),LocalDate.now(), savedSupplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrderOne = purchaseOrderRepository.save(purchaseOrderOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", savedSupplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(savedPurchaseOrderOne),List.of(fileOne),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", savedSupplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileTwo),LocalDate.now());
        Contract contractThree = new Contract("contractThree", savedSupplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileThree),LocalDate.now());

        contractRepository.save(contractOne);
        contractRepository.save(contractTwo);
        contractRepository.save(contractThree);
        mockMvc.perform(post("/supplierperformance/review")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierCatergory", savedSupplierThree.getSupplierCategory().toString()))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier-performance-Officer")
    public void testEvaluateSupplierForRenewalContract_whenNoContractForSupplierWithIntheCategory_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","+251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        Supplier savedSupplierOne = supplierRepository.save(supplierOne);
        Supplier savedSupplierTwo = supplierRepository.save(supplierTwo);
        Supplier savedSupplierThree = supplierRepository.save(supplierThree);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierOne.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierOne.getId()).get();

        RequestedItem requestedItemOne = new RequestedItem(savedInventory1,5);
        RequestedItem requestedItemTwo = new RequestedItem(savedInventory2, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", savedDepartment, "Developer");
        User savedRequester = userRepository.save(requester);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),savedRequester,savedDepartment, savedCostCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);
        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        SupplierPerformance supplierPerformance = new SupplierPerformance(savedSupplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(savedSupplierTwo, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(savedSupplierThree, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(savedDepartment,List.of(savedRequisitionOne),LocalDate.now(), savedSupplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder savedPurchaseOrderOne = purchaseOrderRepository.save(purchaseOrderOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", savedSupplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(savedPurchaseOrderOne),List.of(fileOne),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", savedSupplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileTwo),LocalDate.now());
        Contract contractThree = new Contract("contractThree", savedSupplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(savedPurchaseOrder),List.of(fileThree),LocalDate.now());

        contractRepository.save(contractOne);
        contractRepository.save(contractTwo);
        contractRepository.save(contractThree);

    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier-performance-Officer")
    public void testViewSupplierPerformanceMetrics_WithValidInput_Successfully() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/viewMetrics")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", supplierOne.getSupplierId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.supplierId").value(supplierOne.getSupplierId()))
                .andExpect(jsonPath("$.supplierQuantitativePerformanceMetrics.deliveryRate").value(80.0))
                .andExpect(jsonPath("$.supplierQuantitativePerformanceMetrics.defectsRate").value(15.0))
                .andExpect(jsonPath("$.supplierQuantitativePerformanceMetrics.accuracyRate").value(75.0))
                .andExpect(jsonPath("$.supplierQuantitativePerformanceMetrics.complianceRate").value(82.0))
                .andExpect(jsonPath("$.supplierQuantitativePerformanceMetrics.costEfficiency").value(88.0))
                .andExpect(jsonPath("$.supplierQualitativePerformanceMetrics.contractAdherence").value(8.0))
                .andExpect(jsonPath("$.supplierQualitativePerformanceMetrics.qualityProducts").value(8.0))
                .andExpect(jsonPath("$.supplierQualitativePerformanceMetrics.technicalExpertise").value(9.0))
                .andExpect(jsonPath("$.supplierQualitativePerformanceMetrics.customerSatisfaction").value(5.0));
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testViewSupplierPerformanceMetrics_WithNullInput_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/viewMetrics")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", (String) null))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testViewSupplierPerformanceMetrics_WithMissingSupplierId_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/viewMetrics")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", " "))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier Id cannot be null or empty."));
    }
    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testViewSupplierPerformanceMetrics_WithNonExistingSupplierId_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance supplierPerformance = new SupplierPerformance(supplierOne, quantitativeMetrics, qualitativeMetrics, LocalDate.now().minusMonths(3));
        SupplierPerformance supplierPerformanceOne = new SupplierPerformance(supplierOne, quantitativeMetricsOne, qualitativeMetricsOne, LocalDate.now().minusMonths(2));
        SupplierPerformance supplierPerformanceTwo = new SupplierPerformance(supplierOne, quantitativeMetricsTwo, qualitativeMetricsTwo, LocalDate.now().minusMonths(1));

        supplierPerformanceRepository.save(supplierPerformance);
        supplierPerformanceRepository.save(supplierPerformanceOne);
        supplierPerformanceRepository.save(supplierPerformanceTwo);

        mockMvc.perform(get("/supplierperformance/viewMetrics")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", "SUP-101"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier Doesn't Found."));
    }

    @Test
    @WithMockUser(username = "yaried", roles = "supplier performance officer")
    public void testViewSupplierPerformanceMetrics_WithNotEvaluatedSupplier_ShouldReturnBadRequest() throws Exception {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierOne = supplierRepository.save(supplier);

        mockMvc.perform(get("/supplierperformance/viewMetrics")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("supplierId", supplierOne.getSupplierId().toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Supplier has not been evaluated yet."));
    }

    /** http integration test for  Procurement report. */
    @Test
    @Rollback(value = false)
    @DirtiesContext
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testGenerateMonthlyApprovedPurchaseOrderReport_ShouldReturnSuccessfully() throws Exception{

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        purchaseOrderRepository.save(purchaseOrder);

        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", savedDepartment, "Developer");
        User savedReporter = userRepository.save(reporter);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail(savedReporter.getFullName(),savedReporter.getEmail(),savedReporter.getPhoneNumber(),savedReporter.getRole());
        String title = "Monthly report.";
        String reportDescription = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportDescription,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        mockMvc.perform(post("/procurementreport/generate_approved_purchaseorder")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reportOfficerInput)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testGenerateMonthlyApprovedPurchaseOrderReportWithNullInput_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        purchaseOrderRepository.save(purchaseOrder);

        mockMvc.perform(post("/procurementreport/generate_approved_purchaseorder")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithMisMatchingActivity_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        purchaseOrderRepository.save(purchaseOrder);

        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", savedDepartment, "Developer");
        User savedReporter = userRepository.save(reporter);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail(savedReporter.getFullName(),savedReporter.getEmail(),savedReporter.getPhoneNumber(),savedReporter.getRole());
        String title = "Monthly report.";
        String reportDescription = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportDescription,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_REQUISITION,LocalDate.now());
        mockMvc.perform(post("/procurementreport/generate_approved_purchaseorder")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportOfficerInput)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Purchase Order Report is null"));
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithNonExistedActivity_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        purchaseRequisitionRepository.save(requisition);


        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", savedDepartment, "Developer");
        User savedReporter = userRepository.save(reporter);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail(savedReporter.getFullName(),savedReporter.getEmail(),savedReporter.getPhoneNumber(),savedReporter.getRole());
        String title = "Monthly report.";
        String reportDescription = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportDescription,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        mockMvc.perform(post("/procurementreport/generate_approved_purchaseorder")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportOfficerInput)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Purchase Order List is empty"));
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithNullProcurementActivity_ShouldReturnBadRequest() throws Exception {
        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        purchaseOrderRepository.save(purchaseOrder);

        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", savedDepartment, "Developer");
        User savedReporter = userRepository.save(reporter);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail(savedReporter.getFullName(),savedReporter.getEmail(),savedReporter.getPhoneNumber(),savedReporter.getRole());
        String title = "Monthly report.";
        String reportDescription = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportDescription,LocalDate.now().minusMonths(1),LocalDate.now(), null,LocalDate.now());
        mockMvc.perform(post("/procurementreport/generate_approved_purchaseorder")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportOfficerInput)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Procurement Activity must not be null."));
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithNullReportedDate_ShouldReturnBadRequest() throws Exception {

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);
        Department department = new Department("Software", "Software Dev", savedBudget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem requestedItem = new RequestedItem(savedInventory,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");

        User savedRequester = userRepository.save(requester);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedRequester, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseOrder purchaseOrder = new PurchaseOrder(savedDepartment, List.of(savedRequisition), LocalDate.now(), savedSupplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        purchaseOrderRepository.save(purchaseOrder);

        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", savedDepartment, "Developer");
        User savedReporter = userRepository.save(reporter);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail(savedReporter.getFullName(),savedReporter.getEmail(),savedReporter.getPhoneNumber(),savedReporter.getRole());
        String title = "Monthly report.";
        String reportDescription = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportDescription,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,null);
        mockMvc.perform(post("/procurementreport/generate_approved_purchaseorder")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reportOfficerInput)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Reported Date must not be null."));
    }
    @Test
    @Rollback(value = false)
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testCreateCustomizedProcurementDashboard_Successfully() throws Exception{
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        purchaseRequisitionRepository.save(requisition);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        User savedReporter = userRepository.save(user);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedReporter);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        reportTemplateRepository.save(reportTemplate);


        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(
                reporterContactDetail,
                "RT-001",
                "Standard Procurement Report Template",
                List.of(ProcurementActivity.PURCHASE_REQUISITION, ProcurementActivity.SUPPLIER_MANAGEMENT),
                List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","totalSupplier","activeSupplier")
        );

        mockMvc.perform(post("/procurementreport/create_customized_dashboard")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testCreateCustomizedDashboard_WithMissMatchingActivities_ReturnsBadRequest() throws Exception{
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.DELIVERY_RECEIPT,ProcurementActivity.PAYMENT_RECONCILIATION),selectedFields);

        mockMvc.perform(post("/procurementreport/create_customized_dashboard")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("there are no delivery receipts stored"));
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testCreateCustomizedProcurementDashboard_WithNullInput_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        PaymentReconciliation reconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        reportTemplateRepository.save(reportTemplate);

        mockMvc.perform(post("/procurementreport/create_customized_dashboard")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testCreateCustomizedProcurementDashboard_WithEmptyInput_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);
        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);
        reportTemplateRepository.save(reportTemplate);

        mockMvc.perform(post("/procurementreport/create_customized_dashboard")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testCreateCustomizedProcurementDashboard_WithNullPocurementActivity_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        PaymentReconciliation reconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,savedReportTemplate.getTemplateId(),savedReportTemplate.getTemplateName(), List.of(),savedReportTemplate.getSelectedFields());

        mockMvc.perform(post("/procurementreport/create_customized_dashboard")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Rollback(false)
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testExportingProcurementReport_WithValidInput_ReturnsSuccessfully() throws Exception{
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        String reportTitle = "Standard Procurement Report";
        String reportDescription = "A standard template for monthly procurement reports.";
        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,savedReportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now(),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT,ProcurementActivity.PURCHASE_ORDER),savedReportTemplate.getSelectedFields(),reportTitle,reportDescription);
        mockMvc.perform(post("/procurementreport/export_procurementdata")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testExportProcrementReport_WithNullInput_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        PaymentReconciliation reconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        String reportTitle = "Standard Procurement Report";
        String reportDescription = "A standard template for monthly procurement reports.";
        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,savedReportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now(),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT,ProcurementActivity.PURCHASE_ORDER),savedReportTemplate.getSelectedFields(),reportTitle,reportDescription);
        mockMvc.perform(post("/procurementreport/export_procurementdata")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(null)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testExportProcurementReport_WithEmptyInput_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        PaymentReconciliation reconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        String reportTitle = "Standard Procurement Report";
        String reportDescription = "A standard template for monthly procurement reports.";
        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,savedReportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now(),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT,ProcurementActivity.PURCHASE_ORDER),savedReportTemplate.getSelectedFields(),reportTitle,reportDescription);
        mockMvc.perform(post("/procurementreport/export_procurementdata")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(" ")))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testExportProcurementReport_WithNullProcurementActivity_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        PaymentReconciliation reconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        String reportTitle = "Standard Procurement Report";
        String reportDescription = "A standard template for monthly procurement reports.";
        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,savedReportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now(),FileFormat.PDF,List.of(),savedReportTemplate.getSelectedFields(),reportTitle,reportDescription);
        mockMvc.perform(post("/procurementreport/export_procurementdata")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest());
    }
    @Test
    @WithMockUser(username = "Hiwet", roles = "ReportManager")
    public void testExportProcurementReport_WithInvalidDateRange_ReturnsBadRequest() throws Exception {
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        // Create and save a budget
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software development");
        department.setDescription("develop software solution.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", savedDepartment, "developer");
        User savedUser = userRepository.save(requester);

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventory2);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventory3);
        requestedItem3.setRequestedQuantity(1);
        requestedItem3.setTotalPrice(requestedItem3.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.addItem(requestedItem2);
        requisition.addItem(requestedItem3);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);

        InvoicedItem invoicedItem1 = new InvoicedItem();
        invoicedItem1.setInventory(savedInventory1);
        invoicedItem1.setInvoicedQuantity(3);
        invoicedItem1.setTotalPrice(invoicedItem1.getTotalPrice());

        InvoicedItem invoicedItem2 = new InvoicedItem();
        invoicedItem2.setInventory(savedInventory1);
        invoicedItem2.setInvoicedQuantity(2);
        invoicedItem2.setTotalPrice(invoicedItem2.getTotalPrice());

        InvoicedItem invoicedItem3 = new InvoicedItem();
        invoicedItem3.setInventory(savedInventory3);
        invoicedItem3.setInvoicedQuantity(1);
        invoicedItem3.setTotalPrice(invoicedItem3.getTotalPrice());

        InvoicedItem savedInvoicedItemOne = invoicedItemRepository.save(invoicedItem1);
        InvoicedItem savedInvoicedItemTwo = invoicedItemRepository.save(invoicedItem2);
        InvoicedItem savedInvoicedItemThree = invoicedItemRepository.save(invoicedItem3);


        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);

        Invoice invoice = new Invoice();
        invoice.setInvoiceDate(LocalDate.now().minusDays(10));
        invoice.setDueDate(LocalDate.now().minusDays(15));
        invoice.setSupplier(savedSupplierModel);
        invoice.setPurchaseOrder(savedPurchaseOrder);
        invoice.addInvoicedItem(savedInvoicedItemOne);
        invoice.addInvoicedItem(savedInvoicedItemTwo);
        invoice.addInvoicedItem(savedInvoicedItemThree);
        invoice.setInvoiceItems(invoice.getInvoiceItems());
        invoice.setDiscount(BigDecimal.valueOf(2000.0));
        invoice.setShippingCost(BigDecimal.valueOf(500.0));
        invoice.setTaxes(BigDecimal.valueOf(1500.0));
        invoice.setCurrency("USD");
        invoice.setPaymentDate(LocalDate.now());
        invoice.setCreatedBy(invoiceUser);
        invoice.setDescription("nwoierw");
        invoice.setTotalCosts(invoice.getTotalCosts());
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveredItem savedDeliveredItem1 = deliveredItemRepository.save(deliveredItem1);
        DeliveredItem savedDeliveredItem2 = deliveredItemRepository.save(deliveredItem2);
        DeliveredItem savedDeliveredItem3 = deliveredItemRepository.save(deliveredItem3);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(savedDeliveredItem1,savedDeliveredItem2,savedDeliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId("REP-001");
        deliveryReceipt.setDeliveryStatus(deliveryReceipt.getDeliveryStatus());
        DeliveryReceipt savedDeliveryReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        PaymentReconciliation paymentReconciliation = new PaymentReconciliation();
        paymentReconciliation.setPaymentId("PAY-001");
        paymentReconciliation.setInvoice(savedInvoice);
        paymentReconciliation.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        PaymentReconciliation reconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);

        User user = new User("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636", savedDepartment, "developer");
        userRepository.save(user);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekia Atsbeha", "tekiatsbeha2034@gmail.com", "+251941797636","developer");
        String reportTitle = "Standard Procurement Report";
        String reportDescription = "A standard template for monthly procurement reports.";
        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,savedReportTemplate.getTemplateId(),LocalDate.now() ,LocalDate.now().minusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT,ProcurementActivity.PURCHASE_ORDER),savedReportTemplate.getSelectedFields(),reportTitle,reportDescription);
        mockMvc.perform(post("/procurementreport/export_procurementdata")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDS)))
                .andExpect(status().isBadRequest());
    }
}
