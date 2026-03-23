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
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.CreateContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.NotifyExpiringContractOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.contractsmanagements.ViewContractDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ReconcileInvoiceOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.CreateCustomizedProcurementDashboardOutPutBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.ExportProcurementDataOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.ApprovePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.CreatePurchaseOrderOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaseorder.SendPurchaseOrderToSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.CreateRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.TrackRequisitionOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.purchaserequisition.ViewPendingRequisitionsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.AddSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.UpdateSupplierContactDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.ViewSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.GenerateSupplierPerformanceReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.ViewSupplierPerformanceMerticsOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.CreateContractOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.NotifyExpiringContractOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.contractsmanagement.ViewContractDetailOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalOutPutDS;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ReconclieInvoiceOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.invoicepaymentreconciliation.ViewReconciledPaymentRecordOutPutDS;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.CreateCustomizedProcurementDashboardOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.ExportedProcurementDataOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutPutDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.ApprovePurchaseOrderOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.CreatePurchaseOrderOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaseorder.SendPurchaseOrderToSupplierOutPutDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.CreateRequisitionOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.TrackRequisitionOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.purchaserequisition.ViewPendingRequisitionsOutputData;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.AddSupplierOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.UpdateSupplierContactDetailedOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.ViewSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.GenerateSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.ViewSupplierPerformanceMetricsOutputDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.DeliveryReceiptRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.InvoiceRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.PaymentReconciliationRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ProcurementReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ReportTemplateRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.TemplateBasedReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.CostCenterRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.UserRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.InventoryRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.supplierperformance.SupplierPerformanceRepository;
import com.bizeff.procurement.models.IdGenerator;
import com.bizeff.procurement.models.budget.Budget;
import com.bizeff.procurement.models.budget.BudgetStatus;
import com.bizeff.procurement.models.contracts.Contract;
import com.bizeff.procurement.models.contracts.ContractFile;
import com.bizeff.procurement.models.enums.*;
import com.bizeff.procurement.models.invoicepaymentreconciliation.*;
import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.models.purchaseorder.OrderedItem;
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
import com.bizeff.procurement.services.contract.StoreAndTrackContractServices;
import com.bizeff.procurement.services.invoicepaymentreconciliation.InvoiceReconciliationServices;
import com.bizeff.procurement.services.invoicepaymentreconciliation.PaymentReconciliationMaintainingService;
import com.bizeff.procurement.services.procurementreport.ProcurementReportCustomizationServices;
import com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderEnsuringServices;
import com.bizeff.procurement.services.purchaseorder.PurchaseOrderTrackingStatusService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionCatalogValidationService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionMaintenancesService;
import com.bizeff.procurement.services.purchaserequisition.PurchaseRequisitionTrackingStatusService;
import com.bizeff.procurement.services.supplierperformancetracking.StoringSupplierPerformanceHistoryService;
import com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService;
import com.bizeff.procurement.services.supplymanagement.SupplierPerformanceEvaluationServices;
import com.bizeff.procurement.usecases.contractmanagement.CreateContractUseCase;
import com.bizeff.procurement.usecases.contractmanagement.NotifyExpiringContractUseCase;
import com.bizeff.procurement.usecases.contractmanagement.ViewContractDetailsUseCase;
import com.bizeff.procurement.usecases.invoicepaymentreconciliation.NotifySupplierForPaymentApprovalUseCase;
import com.bizeff.procurement.usecases.invoicepaymentreconciliation.ReconcileInvoiceUseCase;
import com.bizeff.procurement.usecases.invoicepaymentreconciliation.ViewReconciledPaymentRecordUseCase;
import com.bizeff.procurement.usecases.procurementReport.CreateCustomizedProcurementDashboardUseCase;
import com.bizeff.procurement.usecases.procurementReport.ExportProcurementDataUseCase;
import com.bizeff.procurement.usecases.procurementReport.GenerateMonthlyApprovedPurchaseOrderReportUseCase;
import com.bizeff.procurement.usecases.purchaseorder.ApprovePurchaseOrderUseCase;
import com.bizeff.procurement.usecases.purchaseorder.CreatePurchaseOrderUseCase;
import com.bizeff.procurement.usecases.purchaseorder.SendPurchaseOrderToSupplierUseCase;
import com.bizeff.procurement.usecases.purchaserequisition.CreatePurchaseRequisitionUseCase;
import com.bizeff.procurement.usecases.purchaserequisition.TrackPurchaseRequisitionUseCase;
import com.bizeff.procurement.usecases.purchaserequisition.ViewPendingRequisitionsUseCase;
import com.bizeff.procurement.usecases.suppliermanagement.AddSupplierUseCase;
import com.bizeff.procurement.usecases.suppliermanagement.UpdateSupplierDetailUseCase;
import com.bizeff.procurement.usecases.suppliermanagement.ViewSupplierPerformancesUseCase;
import com.bizeff.procurement.usecases.supplierperformancetracking.EvaluateSupplierForContractRenewalUseCase;
import com.bizeff.procurement.usecases.supplierperformancetracking.GenerateSupplierPerformanceReportUseCase;
import com.bizeff.procurement.usecases.supplierperformancetracking.ViewSupplierPerformanceMetricsUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.bizeff.procurement.models.enums.PaymentMethodType.*;
import static com.bizeff.procurement.models.enums.PaymentTerms.NET_30;
import static com.bizeff.procurement.models.enums.RequisitionStatus.APPROVED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class ProcurementUseCaseTest {
    private PurchaseRequisitionMaintenancesService maintainRequisitionService;
    private PurchaseRequisitionTrackingStatusService purchaseRequisitionTrackingStatusService;
    private PurchaseRequisitionCatalogValidationService purchaseRequisitionCatalogValidationService;
    private CreatePurchaseRequisitionUseCase createPurchaseRequisitionUseCase;
    private TrackPurchaseRequisitionUseCase requisitionApproverUseCase;
    private ViewPendingRequisitionsUseCase viewPendingRequisitionsUseCase;
    private InventoryRepository inventoryRepository;
    private PurchaseRequisitionRepository requisitionRepository;
    private DepartmentRepository departmentRepository;
    private UserRepository userRepository;
    private CostCenterRepository costCenterRepository;
    private BudgetRepository budgetRepository;
    private TrackRequisitionOutputBoundary trackRequisitionOutputBoundary;
    private CreateRequisitionOutputBoundary createRequisitionOutputBoundary;
    private ViewPendingRequisitionsOutputBoundary viewPendingRequisitionsOutputBoundary;

    /** About Supplier. */
    private SupplierMaintenanceService supplierMaintenanceService;
    private SupplierPerformanceEvaluationServices supplierPerformanceEvaluationServices;
    private AddSupplierUseCase addSupplierUseCase;
    private ViewSupplierPerformancesUseCase viewSupplierPerformancesUseCase;
    private UpdateSupplierDetailUseCase updateSupplierDetailUseCase;
    private SupplierRepository supplierRepository;
    private SupplierPerformanceRepository supplierPerformanceRepository;
    private AddSupplierOutputBoundary addSupplierOutputBoundary;
    private ViewSupplierPerformanceReportOutputBoundary viewSupplierPerformanceReportOutputBoundary;
    private UpdateSupplierContactDetailOutputBoundary updateSupplierDetailOutputBoundary;

    /** Purchase order. */
    private PurchaseOrderEnsuringServices purchaseOrderEnsuringServices;
    private PurchaseOrderTrackingStatusService purchaseOrderTrackingStatusService;
    private PurchaseOrderRepository purchaseOrderRepository;
    private CreatePurchaseOrderUseCase createPurchaseOrderUseCase;
    private ApprovePurchaseOrderUseCase approvePurchaseOrderUseCase;
    private SendPurchaseOrderToSupplierUseCase sendPurchaseOrderToSupplierUseCase;
    private CreatePurchaseOrderOutputBoundary createPurchaseOrderOutputBoundary;
    private ApprovePurchaseOrderOutputBoundary approvePurchaseOrderOutputBoundary;
    private SendPurchaseOrderToSupplierOutputBoundary sendPurchaseOrderToSupplierOutputBoundary;

    /** Contract .*/
    private CreateContractUseCase createContractUseCase;
    private ViewContractDetailsUseCase viewContractDetailsUseCase;
    private NotifyExpiringContractUseCase notifyExpiringContractUseCase;
    private StoreAndTrackContractServices storeAndTrackContractServices;
    private AlertingContractsServices alertingContractsServices;
    private ContractRepository contractRepository;
    private CreateContractOutputBoundary createContractOutputBoundary;
    private ViewContractDetailOutputBoundary viewContractDetailOutputBoundary;
    private NotifyExpiringContractOutputBoundary notifyExpiringContractOutputBoundary;
    /** Payment Reconciliation Service. */
    private ReconcileInvoiceUseCase  reconcileInvoiceUseCase;
    private NotifySupplierForPaymentApprovalUseCase notifySupplierForPaymentApprovalUseCase;
    private ViewReconciledPaymentRecordUseCase viewReconciledPaymentRecordUseCase;
    private InvoiceReconciliationServices invoiceReconciliationServices;
    private PaymentReconciliationMaintainingService paymentReconciliationMaintainingService;
    private PaymentReconciliationRepository paymentReconciliationRepository;
    private InvoiceRepository invoiceRepository;
    private DeliveryReceiptRepository deliveryReceiptRepository;
    private ReconcileInvoiceOutputBoundary reconcileInvoiceOutputBoundary;
    private NotifySupplierForPaymentApprovalOutputBoundary notifySupplierForPaymentApprovalOutputBoundary;
    private ViewReconciledPaymentRecordOutputBoundary viewReconciledPaymentRecordOutputBoundary;

    /** Supplier Performance tracking. */
    private StoringSupplierPerformanceHistoryService supplierPerformanceHistoryService;
    private GenerateSupplierPerformanceReportUseCase generateSupplierPerformanceReportUseCase;
    private ViewSupplierPerformanceMetricsUseCase viewSupplierPerformanceMetricsUseCase;
    private EvaluateSupplierForContractRenewalUseCase evaluateSupplierForContractRenewalUseCase;
    private GenerateSupplierPerformanceReportOutputBoundary generateSupplierPerformanceReportOutputBoundary;
    private ViewSupplierPerformanceMerticsOutputBoundary viewSupplierPerformanceMerticsOutputBoundary;
    private EvaluateSupplierForContractRenewalOutputBoundary evaluateSupplierForContractRenewalOutputBoundary;

    /** Reporting Procurement activity. */
    private ProcurementReportGenerationServices procurementReportGenerationServices;
    private ProcurementReportCustomizationServices procurementReportCustomizationServices;
    private GenerateMonthlyApprovedPurchaseOrderReportUseCase generateMonthlyApprovedPurchaseOrderReportUseCase;
    private CreateCustomizedProcurementDashboardUseCase createCustomizedProcurementDashboardUseCase;
    private ExportProcurementDataUseCase exportProcurementDataUseCase;
    private GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary generateMonthlyApprovedPurchaseOrderReportOutputBoundary;
    private CreateCustomizedProcurementDashboardOutPutBoundary createCustomizedProcurementDashboardOutPutBoundary;
    private ExportProcurementDataOutputBoundary exportProcurementDataOutputBoundary;
    private TemplateBasedReportRepository templateBasedReportRepository;
    private ProcurementReportRepository procurementReportRepository;
    private ReportTemplateRepository reportTemplateRepository;
    @BeforeEach
    void setUp() {
        templateBasedReportRepository = mock(TemplateBasedReportRepository.class);
        requisitionRepository = mock(PurchaseRequisitionRepository.class);
        departmentRepository = mock(DepartmentRepository.class);
        costCenterRepository = mock(CostCenterRepository.class);
        userRepository = mock(UserRepository.class);
        inventoryRepository = mock(InventoryRepository.class);
        supplierRepository = mock(SupplierRepository.class);
        supplierPerformanceRepository = mock(SupplierPerformanceRepository.class);
        purchaseOrderRepository = mock(PurchaseOrderRepository.class);
        contractRepository = mock(ContractRepository.class);
        paymentReconciliationRepository = mock(PaymentReconciliationRepository.class);
        invoiceRepository = mock(InvoiceRepository.class);
        deliveryReceiptRepository = mock(DeliveryReceiptRepository.class);
        procurementReportRepository = mock(ProcurementReportRepository.class);
        reportTemplateRepository = mock(ReportTemplateRepository.class);
    }

    // this the testing for purchase requisition use cases.
    @Test
    public void testCreateRequisition_WithMultipleItems_Successfully() {
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(5000.0));

        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        RequisitionContactDetailsInputDS requesterContactDetail = new RequisitionContactDetailsInputDS("Tekia Tekle","tekia2034@gmail.com","+251979417636",department.getDepartmentId(),"java developer");

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("sn101", "Laptop",  2, BigDecimal.valueOf(10000.0), "Electronics",LocalDate.now().plusMonths(10),"new generations"),
                new ItemsInputDS("sn102", "Desktop", 3, BigDecimal.valueOf(10000.0), "Electronics",LocalDate.now().plusMonths(10),"Hp models"));

        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterContactDetail,department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment", supplier.getSupplierId());

        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(userRepository.findByPhoneNumber("+251979417636")).thenReturn(Optional.of(requester));
        when(costCenterRepository.findByCostCenterId(costCenter.getCostCenterId())).thenReturn(Optional.of(costCenter));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        // act
        CreateRequisitionOutputDS outPut = createPurchaseRequisitionUseCase.createRequisition(inputData);

        // assert
        assertNotNull(outPut);
        assertEquals(BigDecimal.valueOf(50000.0), outPut.getTotalEstimatedCosts());
        assertEquals("REQ101",outPut.getRequisitionNumber());
        assertEquals(department.getDepartmentId(), outPut.getDepartmentId());
        assertEquals(requester.getFullName(),outPut.getRequestedBy());
        assertEquals(2, outPut.getRequestedItems().size());

        verify(requisitionRepository, times(1)).save(any(PurchaseRequisition.class));
        verify(createRequisitionOutputBoundary,times(1)).presentRequestedRequisitions(any(CreateRequisitionOutputDS.class));
    }
    @Test
    public void testCreateRequisition_WithSingleItem_Successfully() {
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);

        // existed department, cost center, requester.
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.updateBudgetStatus();
        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(5000.0));
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        //input data structure.
        List<ItemsInputDS> items = List.of(new ItemsInputDS("sn101", "Laptop",  2, BigDecimal.valueOf(10000.0), "Electronics",LocalDate.now().plusMonths(10),"new generations"));
        RequisitionContactDetailsInputDS contactDetails = new RequisitionContactDetailsInputDS("Tekia Tekle","tekia2034@gmail.com","+251979417636",department.getDepartmentId(),"developer");
        CreateRequisitionInputDS input = new CreateRequisitionInputDS(contactDetails, department.getDepartmentId(), costCenter.getCostCenterId(), "REQ456",LocalDate.now(),items,PriorityLevel.HIGH,LocalDate.now().plusMonths(3),"Office chair", supplier.getSupplierId());

        when(departmentRepository.findByDepartmentId(input.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(input.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(userRepository.findByPhoneNumber(input.getRequester().getPhone())).thenReturn(Optional.of(requester));
        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        //act
        CreateRequisitionOutputDS outPut = createPurchaseRequisitionUseCase.createRequisition(input);

        //assert
        assertNotNull(outPut);
        assertEquals(BigDecimal.valueOf(20000.0), outPut.getTotalEstimatedCosts());
        assertEquals("REQ456",outPut.getRequisitionNumber());

        verify(requisitionRepository, times(1)).save(any(PurchaseRequisition.class));
        verify(createRequisitionOutputBoundary,times(1)).presentRequestedRequisitions(any(CreateRequisitionOutputDS.class));
    }
    @Test
    public void testCreateRequisition_WithNoItems_ThrowExceptions() {
        // arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        RequisitionContactDetailsInputDS requesterDetail = new RequisitionContactDetailsInputDS("Tekia","tekia@gmail.com","+251979417636",department.getDepartmentId(),"developer");

        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterDetail, department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), List.of(), PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);


        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(userRepository.findByPhoneNumber(inputData.getRequisitionNumber())).thenReturn(Optional.of(requester));

        //act and assert.
        assertThrows(NullPointerException.class, () -> createPurchaseRequisitionUseCase.createRequisition(inputData));

        verify(requisitionRepository, never()).save(any(PurchaseRequisition.class));
        verify(createRequisitionOutputBoundary,never()).presentRequestedRequisitions(any(CreateRequisitionOutputDS.class));
    }
    @Test
    public void testCreateRequisition_WithMissingRequesterInfo_ThrowExceptions(){

        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        List<ItemsInputDS> items = List.of(new ItemsInputDS("1", "Chair",  1, BigDecimal.valueOf(150.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic"));
        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( null, department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);

        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));

        //act and assert.
        assertThrows(NullPointerException.class,()->createPurchaseRequisitionUseCase.createRequisition(inputData));
        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testCreateRequisition_WithInvalidEmail_ThrowExceptions(){
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        RequisitionContactDetailsInputDS requesterDetail = new RequisitionContactDetailsInputDS("Tekia","tekia.com","+251979417636", department.getDepartmentId(),"developer");
        List<ItemsInputDS> items = List.of(new ItemsInputDS("1", "Chair",  1, BigDecimal.valueOf(150.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic"));
        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterDetail, department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);

        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));

        //act and assert.
        assertThrows(IllegalArgumentException.class,()->createPurchaseRequisitionUseCase.createRequisition(inputData));

        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testCreateRequisition_WithInvalidPhone_ThrowExceptions(){
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        RequisitionContactDetailsInputDS requesterDetail = new RequisitionContactDetailsInputDS("Tekia","tekia@gmail.com","056640896315", department.getDepartmentId(),"developer");
        List<ItemsInputDS> items = List.of(new ItemsInputDS("1", "Chair",  1, BigDecimal.valueOf(150.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic"));
        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterDetail, department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);

        when(requisitionRepository.findByRequisitionId(inputData.getRequisitionNumber())).thenReturn(Optional.empty());
        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));

        // act and assert
        assertThrows(IllegalArgumentException.class,()->createPurchaseRequisitionUseCase.createRequisition(inputData));

        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testCreateRequisition_WithNullInputData_ThrowExceptions() {
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        RequisitionContactDetailsInputDS requesterDetail = new RequisitionContactDetailsInputDS("Tekia","tekia@gmail.com","056640896315", department.getDepartmentId(),"developer");
        List<ItemsInputDS> items = List.of(new ItemsInputDS("1", "Chair",  1, BigDecimal.valueOf(150.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic"));
        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterDetail, department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);

        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));

        //act and assert
        assertThrows(NullPointerException.class, () -> createPurchaseRequisitionUseCase.createRequisition(null));

        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testCreateRequisition_NegativeQuantity_ThrowExceptions() {
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        List<ItemsInputDS> items = List.of(new ItemsInputDS("1", "Table",  -5, BigDecimal.valueOf(300), "Wooden",LocalDate.now().plusYears(3),"new specification"));
        RequisitionContactDetailsInputDS requesterDetail = new RequisitionContactDetailsInputDS("Tekia","tekia@gmail.com","+251979417636", department.getDepartmentId(), "developer");
        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterDetail, requesterDetail.getDepartmentId(), costCenter.getCostCenterId(), "REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);

        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(userRepository.findByPhoneNumber(inputData.getRequester().getPhone())).thenReturn(Optional.of(requester));

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> createPurchaseRequisitionUseCase.createRequisition(inputData));

        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testCreateRequisition_WithZeroQuantity_ThrowExceptions() {
        //arrange
        maintainRequisitionService = new PurchaseRequisitionMaintenancesService();
        createRequisitionOutputBoundary = mock(CreateRequisitionOutputBoundary.class);
        createPurchaseRequisitionUseCase = new CreatePurchaseRequisitionUseCase(requisitionRepository,userRepository,departmentRepository, costCenterRepository,supplierRepository,maintainRequisitionService,createRequisitionOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);

        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        List<ItemsInputDS> items = List.of(new ItemsInputDS("1", "Notebook",  0, BigDecimal.valueOf(10), "Office equipment",LocalDate.now().plusDays(10),"A4"));
        RequisitionContactDetailsInputDS requesterDetail = new RequisitionContactDetailsInputDS("Tekia","tekia@gmail.com","+251979417636", department.getDepartmentId(), "developer");
        CreateRequisitionInputDS inputData = new CreateRequisitionInputDS( requesterDetail, department.getDepartmentId(), costCenter.getCostCenterId(),"REQ101",LocalDate.now(), items, PriorityLevel.HIGH, LocalDate.now().plusDays(7), "Office Equipment",null);

        when(requisitionRepository.save(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(inputData.getDepartment())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(inputData.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(userRepository.findByPhoneNumber(inputData.getRequester().getPhone())).thenReturn(Optional.of(requester));

        //act and assert
        assertThrows(IllegalArgumentException.class, () -> createPurchaseRequisitionUseCase.createRequisition(inputData));

        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }

    /** this is the testing for tracking requisition status. */
    @Test
    public void testApproveRequisition_WithValidItems_Successfully() {
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        trackRequisitionOutputBoundary = mock(TrackRequisitionOutputBoundary.class);
        requisitionApproverUseCase = new TrackPurchaseRequisitionUseCase(requisitionRepository, purchaseRequisitionCatalogValidationService, purchaseRequisitionTrackingStatusService,trackRequisitionOutputBoundary, inventoryRepository);

        Budget budget = new Budget(BigDecimal.valueOf(7000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(20), "New Department");
        requisition.addItem(requestedItemOne);
        requisition.addItem(requestedItemTwo);
        // Prepare input for use case
        TrackRequisitionInputDS inputData = new TrackRequisitionInputDS(new RequisitionContactDetailsInputDS("Daniel", "daniel@gmail.com", "+251979417636", "Bizeff", "Department Manager"), requisition.getRequisitionId());

        // Store requisition in memory simulation
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(5000.0));
        purchaseRequisitionTrackingStatusService.createDepartment(department);

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);

        when(requisitionRepository.update(any(PurchaseRequisition.class))).thenAnswer(invocation->invocation.getArgument(0));

        when((requisitionRepository.findByRequisitionId(inputData.getRequisitionId()))).thenReturn(Optional.of(requisition));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(costCenter.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(userRepository.findByPhoneNumber(requester.getPhoneNumber())).thenReturn(Optional.of(requester));
        when(inventoryRepository.findByItemId(inventoryOne.getItemId())).thenReturn(Optional.of(inventoryOne));
        when(inventoryRepository.findByItemId(inventoryTwo.getItemId())).thenReturn(Optional.of(inventoryTwo));

        // act
        TrackRequisitionOutputDS output = requisitionApproverUseCase.trackRequisition(inputData);

        // assert
        assertNotNull(output);
        assertEquals(RequisitionStatus.APPROVED, output.getStatus());

        verify(requisitionRepository, times(1)).findByRequisitionId(requisition.getRequisitionId());
    }
    @Test
    public void testApprovalRequisition_WithInvalidItems_Failure(){
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        trackRequisitionOutputBoundary = mock(TrackRequisitionOutputBoundary.class);

        requisitionApproverUseCase = new TrackPurchaseRequisitionUseCase(requisitionRepository, purchaseRequisitionCatalogValidationService, purchaseRequisitionTrackingStatusService,trackRequisitionOutputBoundary, inventoryRepository);

        // Prepare input for use case
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        Inventory itemOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory itemTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(itemOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(itemTwo, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(20), "New Department");
        requisition.addItem(requestedItemOne);
        requisition.addItem(requestedItemTwo);

        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(7000.0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(costCenter.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when((requisitionRepository.findByRequisitionId(requisition.getRequisitionId()))).thenReturn(Optional.of(requisition));

        TrackRequisitionInputDS inputData = new TrackRequisitionInputDS(new RequisitionContactDetailsInputDS("Daniel", "daniel@gmail.com", "+251979417636", "Bizeff", "Department Manager"),requisition.getRequisitionId());

        // act and assert
        assertThrows(IllegalArgumentException.class,()->requisitionApproverUseCase.trackRequisition(inputData));
        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testApproveRequisition_Failure_NonExistedRequisitionId(){
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        trackRequisitionOutputBoundary = mock(TrackRequisitionOutputBoundary.class);
        requisitionApproverUseCase = new TrackPurchaseRequisitionUseCase(requisitionRepository, purchaseRequisitionCatalogValidationService, purchaseRequisitionTrackingStatusService,trackRequisitionOutputBoundary, inventoryRepository);

        // Prepare input for use case
        TrackRequisitionInputDS inputData = new TrackRequisitionInputDS(new RequisitionContactDetailsInputDS("Daniel", "daniel@gmail.com", "+251979417636", "Bizeff", "Department Manager"), "REQ123");
        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        Inventory itemOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory itemTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(itemOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(itemTwo, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(20), "New Department");
        requisition.addItem(requestedItemOne);
        requisition.addItem(requestedItemTwo);

        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(7000.0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(costCenter.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when((requisitionRepository.findByRequisitionId(requisition.getRequisitionId()))).thenReturn(Optional.of(requisition));

        //act and assert
        assertThrows(NoSuchElementException.class,()->requisitionApproverUseCase.trackRequisition(inputData));
        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
    }
    @Test
    public void testApprovalRequisition_WithInsufficientBudgetButUrgent_ReturnPendingStatus(){ // the status set to pending since it has at least one highly Urgent item in the requisition, but the budget is insufficient to approve the status.
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        trackRequisitionOutputBoundary = mock(TrackRequisitionOutputBoundary.class);
        requisitionApproverUseCase = new TrackPurchaseRequisitionUseCase(requisitionRepository, purchaseRequisitionCatalogValidationService, purchaseRequisitionTrackingStatusService,trackRequisitionOutputBoundary, inventoryRepository);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.updateBudgetStatus();

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        CostCenter costCenter1 = new CostCenter("front End","Front End development");
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(20), "New Department");
        requisition.addItem(requestedItemOne);
        requisition.addItem(requestedItemTwo);

        // Prepare input for use case
        TrackRequisitionInputDS inputData = new TrackRequisitionInputDS(new RequisitionContactDetailsInputDS("Daniel", "daniel@gmail.com", "+251979417636", "Bizeff", "Department Manager"), requisition.getRequisitionId());

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(4000.0));
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(5000.0));

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);

        when(requisitionRepository.update(any(PurchaseRequisition.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(requisitionRepository.findByRequisitionId(inputData.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(inventoryRepository.findByItemId("item1")).thenReturn(Optional.of(inventoryOne));
        when(inventoryRepository.findByItemId("item2")).thenReturn(Optional.of(inventoryTwo));

        //act
        TrackRequisitionOutputDS output = requisitionApproverUseCase.trackRequisition(inputData);

        //assert
        assertEquals(RequisitionStatus.PENDING,output.getStatus());
        verify(trackRequisitionOutputBoundary,times(1)).presentApprovedRequisitions(any(TrackRequisitionOutputDS.class));
    }
    @Test
    public void testApprovalRequisition_WithInsufficientBudgetAndNotUrgentItems_WillRejected(){ // the status set to rejected since it has at least one highly Urgent item in the requisition, but the budget is insufficient to approve the status.
        //arrange
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        trackRequisitionOutputBoundary = mock(TrackRequisitionOutputBoundary.class);
        requisitionApproverUseCase = new TrackPurchaseRequisitionUseCase(requisitionRepository, purchaseRequisitionCatalogValidationService, purchaseRequisitionTrackingStatusService,trackRequisitionOutputBoundary, inventoryRepository);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.updateBudgetStatus();

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        CostCenter costCenter1 = new CostCenter("front End","Front End development");
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(4000.0));
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(5000.0));

        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter,PriorityLevel.MEDIUM, LocalDate.now().plusDays(20), "New Department");
        requisition.addItem(requestedItemOne);
        requisition.addItem(requestedItemTwo);

        // Prepare input for use case
        TrackRequisitionInputDS inputData = new TrackRequisitionInputDS(new RequisitionContactDetailsInputDS("Daniel", "daniel@gmail.com", "+251979417636", "Bizeff", "Department Manager"), requisition.getRequisitionId());

        when(requisitionRepository.update(any(PurchaseRequisition.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(costCenter.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(userRepository.findByPhoneNumber(requester.getPhoneNumber())).thenReturn(Optional.of(requester));
        when(inventoryRepository.findByItemId("item1")).thenReturn(Optional.of(inventoryOne));
        when(inventoryRepository.findByItemId("item2")).thenReturn(Optional.of(inventoryTwo));

        //act
        TrackRequisitionOutputDS output = requisitionApproverUseCase.trackRequisition(inputData);

        //assert.
        assertEquals(RequisitionStatus.REJECTED,output.getStatus());
        verify(trackRequisitionOutputBoundary,times(1)).presentApprovedRequisitions(any(TrackRequisitionOutputDS.class));
    }
    @Test
    public void testApprovalRequisition_WithNullInputData_ThrowExceptions(){
        //arrange
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        trackRequisitionOutputBoundary = mock(TrackRequisitionOutputBoundary.class);
        requisitionApproverUseCase = new TrackPurchaseRequisitionUseCase(requisitionRepository, purchaseRequisitionCatalogValidationService, purchaseRequisitionTrackingStatusService,trackRequisitionOutputBoundary, inventoryRepository);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        String departmentId = department.getDepartmentId();
        CostCenter costCenter = new CostCenter("backend","back End delelopment");
        String costCenterId = costCenter.getCostCenterId();
        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(20), "New Department");
        requisition.addItem(requestedItemOne);
        requisition.addItem(requestedItemTwo);

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(7000.0));
        when(departmentRepository.findByDepartmentId(departmentId)).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(costCenterId)).thenReturn(Optional.of(costCenter));

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition.getRequisitionId(),requisition);

        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));

        //act and assert
        assertThrows(NullPointerException.class,()->requisitionApproverUseCase.trackRequisition(null));

        verify(requisitionRepository,never()).save(any(PurchaseRequisition.class));
        verify(trackRequisitionOutputBoundary,never()).presentApprovedRequisitions(any(TrackRequisitionOutputDS.class));
    }

    /** this is the testing for viewing pending requisitions. */
    @Test
    public void testViewAllPendingRequisition_WithAllPending_Successfully() {
        // Mock Data
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        viewPendingRequisitionsOutputBoundary = mock(ViewPendingRequisitionsOutputBoundary.class);
        viewPendingRequisitionsUseCase = new ViewPendingRequisitionsUseCase(requisitionRepository,viewPendingRequisitionsOutputBoundary,purchaseRequisitionTrackingStatusService);

        Inventory inventory = new Inventory("1", "Chair",  10, BigDecimal.valueOf(600.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic");
        RequestedItem requestedItem = new RequestedItem(inventory,10);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        CostCenter costCenter1 = new CostCenter("front end","front End delelopment");

        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ456",LocalDate.now(),requester,department, costCenter1, PriorityLevel.HIGH, LocalDate.now().plusDays(12), "null");
        requisition1.addItem(requestedItem);

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(20), "New Department");
        requisition2.addItem(requestedItemOne);
        requisition2.addItem(requestedItemTwo);

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(4500.0));
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(5000.0));


        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition1.getRequisitionId(),requisition1);
        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition2.getRequisitionId(),requisition2);

        when(requisitionRepository.update(any(PurchaseRequisition.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(requisitionRepository.findByRequisitionId(requisition1.getRequisitionId())).thenReturn(Optional.of(requisition1));
        when(requisitionRepository.findByRequisitionId(requisition2.getRequisitionId())).thenReturn(Optional.of(requisition2));

        List<PurchaseRequisition> requisitionList = List.of(requisition1, requisition2);

        // Mock repository responses

        when(requisitionRepository.findAll()).thenReturn(requisitionList);
        when(requisitionRepository.findByDepartmentEntityDepartmentId(department.getDepartmentId())).thenReturn(requisitionList);
        // Call method
        List<ViewPendingRequisitionsOutputData> result = viewPendingRequisitionsUseCase.viewAllPendingRequisition(department.getDepartmentId());

        // Verify the expected results
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify repository interactions
        verify(requisitionRepository, times(1)).findAll();
        verify(requisitionRepository, times(2)).update(any(PurchaseRequisition.class)); // Saves updated statuses
        verify(viewPendingRequisitionsOutputBoundary, times(1)).presentPendingRequisition(result);
    }
    @Test
    public void testViewAllPendingRequisition_WithMixedRequisitionLists_Successfully() {
        // Mock Data
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        viewPendingRequisitionsOutputBoundary = mock(ViewPendingRequisitionsOutputBoundary.class);
        viewPendingRequisitionsUseCase = new ViewPendingRequisitionsUseCase(requisitionRepository,viewPendingRequisitionsOutputBoundary,purchaseRequisitionTrackingStatusService);

        Inventory inventory = new Inventory("1", "Chair",  10, BigDecimal.valueOf(500.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic");
        RequestedItem requestedItem = new RequestedItem(inventory,10);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        CostCenter costCenter1 = new CostCenter("front end","front End delelopment");

        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ456",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusDays(12), "null");
        requisition1.addItem(requestedItem);

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.LOW, LocalDate.now().plusDays(20), "New Department");
        requisition2.addItem(requestedItemOne);
        requisition2.addItem(requestedItemTwo);

        purchaseRequisitionTrackingStatusService.createDepartment(department);

        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(6000.0));
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(4000.0));


        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition1.getRequisitionId(),requisition1);
        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition2.getRequisitionId(), requisition2);

        // Mock repository behaviors
        when(requisitionRepository.update(any(PurchaseRequisition.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(requisitionRepository.findByRequisitionId(requisition1.getRequisitionId())).thenReturn(Optional.of(requisition1));
        when(requisitionRepository.findByRequisitionId(requisition2.getRequisitionId())).thenReturn(Optional.of(requisition2));

        List<PurchaseRequisition> requisitionList = List.of(requisition1, requisition2);

        // Mock repository responses

        when(requisitionRepository.findAll()).thenReturn(requisitionList);
        when(requisitionRepository.findByDepartmentEntityDepartmentId(department.getDepartmentId())).thenReturn(requisitionList);
        // Call method
        List<ViewPendingRequisitionsOutputData> result = viewPendingRequisitionsUseCase.viewAllPendingRequisition(department.getDepartmentId());


        // Verify the expected results
        assertNotNull(result);
        assertEquals(1, result.size()); // Only one should be pending

        // Verify repository interactions
        verify(requisitionRepository, times(1)).findAll();
        verify(requisitionRepository, times(2)).update(any(PurchaseRequisition.class)); // one is approved and one is pending.
        verify(viewPendingRequisitionsOutputBoundary, times(1)).presentPendingRequisition(result);
    }
    @Test
    public void testViewPendingRequisition_WhenNoRequisitionInRepo_Unsuccessfully(){
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        viewPendingRequisitionsOutputBoundary = mock(ViewPendingRequisitionsOutputBoundary.class);
        viewPendingRequisitionsUseCase = new ViewPendingRequisitionsUseCase(requisitionRepository,viewPendingRequisitionsOutputBoundary,purchaseRequisitionTrackingStatusService);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        CostCenter costCenter1 = new CostCenter("front end","front End delelopment");

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(5000.0));
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(4000.0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(costCenterRepository.findByCostCenterId(costCenter.getCostCenterId())).thenReturn(Optional.of(costCenter));
        when(costCenterRepository.findByCostCenterId(costCenter1.getCostCenterId())).thenReturn(Optional.of(costCenter1));

        when(requisitionRepository.findAll()).thenReturn(List.of());
        when(requisitionRepository.findByDepartmentEntityDepartmentId(department.getDepartmentId())).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class,()-> viewPendingRequisitionsUseCase.viewAllPendingRequisition(department.getDepartmentId()));

        verify(requisitionRepository).findAll();
        verify(viewPendingRequisitionsOutputBoundary,never()).presentPendingRequisition(any());
    }
    @Test
    public void testViewPendingRequisition_WhenNoPendingRequisition_ThrowExceptions(){
        purchaseRequisitionCatalogValidationService = new PurchaseRequisitionCatalogValidationService();
        purchaseRequisitionTrackingStatusService = new PurchaseRequisitionTrackingStatusService();
        viewPendingRequisitionsOutputBoundary = mock(ViewPendingRequisitionsOutputBoundary.class);
        viewPendingRequisitionsUseCase = new ViewPendingRequisitionsUseCase(requisitionRepository,viewPendingRequisitionsOutputBoundary,purchaseRequisitionTrackingStatusService);

        Inventory inventory = new Inventory("1", "Chair",  10, BigDecimal.valueOf(500.0), "Office equipments",LocalDate.now().plusYears(5),"Ergonomic");
        RequestedItem requestedItem = new RequestedItem(inventory,10);

        Budget budget = new Budget(BigDecimal.valueOf(10000.0),LocalDate.now().minusDays(10),LocalDate.now().plusDays(20),"USD");
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        Department department = new Department("Bizeff","developing software based on bizeff",budget);
        CostCenter costCenter = new CostCenter("backend","back End delelopment");

        CostCenter costCenter1 = new CostCenter("front end","front End delelopment");

        User requester = new User("Tekia Tekle","tekia2034@gmail.com","+251979417636",department,"developer");

        PurchaseRequisition requisition1 = new PurchaseRequisition("REQ456",LocalDate.now(),requester,department, costCenter, PriorityLevel.LOW, LocalDate.now().plusDays(12), "null");
        requisition1.addItem(requestedItem);

        Inventory inventoryOne = new Inventory("item1", "Laptop",  3, BigDecimal.valueOf(1000), "Laptops",LocalDate.now().plusYears(3),"New Specification");
        Inventory inventoryTwo = new Inventory("item2", "Monitor",  3, BigDecimal.valueOf(1000), "Computer Devices",LocalDate.now().plusYears(3),"New Specification");

        RequestedItem requestedItemOne = new RequestedItem(inventoryOne,3);
        RequestedItem requestedItemTwo = new RequestedItem(inventoryTwo, 3);
        PurchaseRequisition requisition2 = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.LOW, LocalDate.now().plusDays(20), "New Department");
        requisition2.addItem(requestedItemOne);
        requisition2.addItem(requestedItemTwo);

        purchaseRequisitionTrackingStatusService.createDepartment(department);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(6000.0));
        department.allocateBudgetToCostCenter(costCenter1,BigDecimal.valueOf(2000.0));

        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition1.getRequisitionId(),requisition1);
        purchaseRequisitionTrackingStatusService.getAllRequisitions().put(requisition2.getRequisitionId(), requisition2);

        when(requisitionRepository.update(any(PurchaseRequisition.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(requisitionRepository.findByRequisitionId(requisition1.getRequisitionId())).thenReturn(Optional.of(requisition1));
        when(requisitionRepository.findByRequisitionId(requisition2.getRequisitionId())).thenReturn(Optional.of(requisition2));

        List<PurchaseRequisition> requisitionList = List.of(requisition1, requisition2);

        // Mock repository responses

        when(requisitionRepository.findAll()).thenReturn(requisitionList);
        when(requisitionRepository.findByDepartmentEntityDepartmentId(department.getDepartmentId())).thenReturn(requisitionList);
        // Call method
        List<PurchaseRequisition>pendingLists = requisitionRepository.findAll().stream().filter(req->req.getRequisitionStatus().equals(RequisitionStatus.PENDING)).collect(Collectors.toList());


        when(requisitionRepository.findByRequisitionStatus(RequisitionStatus.PENDING)).thenReturn(pendingLists);

        viewPendingRequisitionsUseCase.viewAllPendingRequisition(department.getDepartmentId());

        verify(requisitionRepository,times(2)).findAll();
        verify(viewPendingRequisitionsOutputBoundary, times(1)).presentPendingRequisition(any());

    }
    @Test
    public void testAddSupplier_WithValidFields_Successfully() {
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        // inputs.
        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("BANK_TRANSFER", "CREDIT_CARD"), "BANK_TRANSFER",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "NET_30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(
                new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());


        when(supplierRepository.save(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        AddSupplierOutputDS result = addSupplierUseCase.addSupplier(addSupplierInputDS);

        //assert.
        assertNotNull(result);
        assertEquals("malam",result.getSupplierName());
        assertEquals("It industry", result.getSupplierCategory());

        verify(supplierRepository, times(1)).save(any(Supplier.class));
    }
    @Test
    public void testAddSupplier_WithNullInputData_throwException() {
        assertThrows(NullPointerException.class, () -> addSupplierUseCase.addSupplier(null));

        verify(supplierRepository,never()).save(any(Supplier.class));

    }
    @Test
    public void testAddSupplier_WithInvalidEmail_ThrowExceptions() {
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        // inputs.

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "emaildomain.com", "+123456789", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("Bank Transfer", "Credit Card"), "Bank Transfer",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "Net 30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());


        when(supplierRepository.save(any(Supplier.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(supplierRepository.findBySupplierId("V003")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> addSupplierUseCase.addSupplier(addSupplierInputDS));

        verify(supplierRepository,never()).save(any(Supplier.class));

    }
    @Test
    public void testAddSupplier_WithInvalidPhoneNumber_ThrowExceptions() {
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        // inputs.

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "0934845689", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("Bank Transfer", "Credit Card"), "Bank Transfer",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "Net 30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        when(supplierRepository.save(any(Supplier.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(supplierRepository.findBySupplierId("V004")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> addSupplierUseCase.addSupplier(addSupplierInputDS));

        verify(supplierRepository,never()).save(any(Supplier.class));
    }
    @Test
    public void testAddSupplier_WithEmptyItems_ThrowException() {
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+251934845689", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("Bank Transfer", "Credit Card"), "Bank Transfer",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "Net 30", new BigDecimal("50000.00"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),List.of(),LocalDate.now());

        when(supplierRepository.save(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("sup001")).thenReturn(Optional.empty());


        assertThrows(IllegalArgumentException.class, () -> addSupplierUseCase.addSupplier(addSupplierInputDS));

        verify(supplierRepository,never()).save(any(Supplier.class));
        verify(addSupplierOutputBoundary,never()).presentAddedSupplier(any(AddSupplierOutputDS.class));
    }
    @Test
    public void testAddSupplier_WithExpiredItems_ThrowExceptions() {
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        // inputs.

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "+251934845689", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("Bank Transfer", "Credit Card"), "Bank Transfer",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "Net 30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().minusYears(1),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().minusYears(1),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        when(supplierRepository.save(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("V005")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> addSupplierUseCase.addSupplier(addSupplierInputDS));

        verify(supplierRepository,never()).save(any(Supplier.class));
    }
    @Test
    public void testAddSupplier_WithInvalideItems_ThrowExceptions() { //it contains invalid item which means negative unit price.
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        // inputs.

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "0934845689", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("Bank Transfer", "Credit Card"), "Bank Transfer",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "Net 30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(-1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("malam","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        when(supplierRepository.save(any(Supplier.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        when(supplierRepository.findBySupplierId("V005")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> addSupplierUseCase.addSupplier(addSupplierInputDS));
        verify(supplierRepository,never()).save(any(Supplier.class));
    }
    @Test
    public void testAddSupplier_WithEmptySupplierName_ThrowExceptions() {
        supplierMaintenanceService = new SupplierMaintenanceService();
        addSupplierOutputBoundary = mock(AddSupplierOutputBoundary.class);
        addSupplierUseCase  = new AddSupplierUseCase(supplierRepository,supplierMaintenanceService,addSupplierOutputBoundary);

        // inputs.

        SupplierContactDetailsInputDS supplierContactDetailsInputDS =  new SupplierContactDetailsInputDS("VendorName", "email@domain.com", "0934845689", "123 Street");
        SupplierPaymentMethodsInputDS supplierPaymentMethodsInputDS = new SupplierPaymentMethodsInputDS(List.of("Bank Transfer", "Credit Card"), "Bank Transfer",supplierContactDetailsInputDS.getFullName(),supplierContactDetailsInputDS.getPhone(), "First National Bank", "12345678901234", "USD", "Net 30", new BigDecimal("50000.00"));

        List<ItemsInputDS> items = Arrays.asList(new ItemsInputDS("1", "Laptop",  2, BigDecimal.valueOf(1000), "Electronics",LocalDate.now().plusYears(10),"16GB RAM"),
                new ItemsInputDS("2", "Mouse", 3, BigDecimal.valueOf(20), "Computer Devices",LocalDate.now().plusYears(10),"Wireless"));

        AddSupplierInputDS addSupplierInputDS = new AddSupplierInputDS("","It industry","TIN-987654321","REG-2024-4567",supplierContactDetailsInputDS,List.of(supplierPaymentMethodsInputDS),items,LocalDate.now());

        when(supplierRepository.save(any(Supplier.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> addSupplierUseCase.addSupplier(addSupplierInputDS));
        verify(supplierRepository,never()).save(any(Supplier.class));
    }
    @Test
    public void testViewSupllierPerformanceReport_withValidData_Successfully() {
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));


        Supplier supplier1 = new Supplier("Microsoft","Electronics","21323","MS365",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Lion International Bank","101293049950", "ME Me","2510987654433",List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0))),
                List.of(item,item2),LocalDate.now().minusMonths(5));
        Supplier supplier2 = new Supplier("Lenevo","Electronics","098594676","LN10232",new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai"),
                List.of(new SupplierPaymentMethod("Lion International Bank","101293049950", "Me Me","251978465232",List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0))),
                List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);


        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier1.getSupplierId(), supplier1);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier2.getSupplierId(), supplier2);

        List<SupplierPerformance> resault1 = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(),quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        List<SupplierPerformance> resault2 = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier1.getSupplierId(),quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        List<SupplierPerformance> resault3 = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier2.getSupplierId(),quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        SupplierPerformance performance1 = resault1.getFirst();
        SupplierPerformance performance2 = resault2.getFirst();
        SupplierPerformance performance3 = resault3.getFirst();

        ViewSupplierPerformanceReportInputDS viewSupplierPerformanceReportInputDS = new ViewSupplierPerformanceReportInputDS("Electronics",LocalDate.now().minusMonths(3),LocalDate.now());

        when(supplierRepository.findAll()).thenReturn(List.of(supplier, supplier1, supplier2));
        when(supplierPerformanceRepository.findAll()).thenReturn(List.of(performance1,performance2,performance3));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierRepository.findBySupplierId(supplier1.getSupplierId())).thenReturn(Optional.of(supplier1));
        when(supplierRepository.findBySupplierId(supplier2.getSupplierId())).thenReturn(Optional.of(supplier2));

        when(supplierPerformanceRepository.findById(performance1.getId())).thenReturn(Optional.of(performance1));
        when(supplierPerformanceRepository.findById(performance2.getId())).thenReturn(Optional.of(performance2));
        when(supplierPerformanceRepository.findById(performance3.getId())).thenReturn(Optional.of(performance3));

        List<ViewSupplierPerformanceReportOutputDS> supplierPerformanceReportOutputDS = viewSupplierPerformancesUseCase.viewSupplierPerformances(viewSupplierPerformanceReportInputDS);

        assertEquals(3,supplierPerformanceReportOutputDS.size());
        assertEquals(supplier1.getSupplierId(),supplierPerformanceReportOutputDS.getFirst().getSupplierId());
        assertEquals(supplier.getSupplierId(),supplierPerformanceReportOutputDS.get(1).getSupplierId());
        assertEquals(supplier2.getSupplierId(),supplierPerformanceReportOutputDS.get(2).getSupplierId());

        assertEquals(97.57,supplierPerformanceReportOutputDS.getFirst().getTotalSupplierPerformanceScore());
        assertEquals(96.43,supplierPerformanceReportOutputDS.get(1).getTotalSupplierPerformanceScore());
        assertEquals(84.27,supplierPerformanceReportOutputDS.get(2).getTotalSupplierPerformanceScore());

    }
    @Test
    void testViewSupplierPerformancereport_WithNoSuppliers_ThrowException() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        when(supplierRepository.findAll()).thenReturn(Collections.emptyList());

        ViewSupplierPerformanceReportInputDS inputDS = new ViewSupplierPerformanceReportInputDS("Electronics", LocalDate.now(), LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> viewSupplierPerformancesUseCase.viewSupplierPerformances(inputDS));
    }
    @Test
    void testViewSupplierPerformanceReport_WithNoMatchingCategory_ThrowException() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        when(supplierRepository.findAll()).thenReturn(Collections.singletonList(supplier));

        ViewSupplierPerformanceReportInputDS inputDS = new ViewSupplierPerformanceReportInputDS("Furniture", LocalDate.now(), LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> viewSupplierPerformancesUseCase.viewSupplierPerformances(inputDS));
    }
    @Test
    void testViewSupplierPerformanceReport_WithNoPerformanceRecords_ThrowException() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        when(supplierRepository.findAll()).thenReturn(Collections.singletonList(supplier));
        when(supplierPerformanceRepository.findAll()).thenReturn(Collections.emptyList());

        ViewSupplierPerformanceReportInputDS inputDS = new ViewSupplierPerformanceReportInputDS("Electronics", LocalDate.now(), LocalDate.now());
        assertThrows(IllegalArgumentException.class, () -> viewSupplierPerformancesUseCase.viewSupplierPerformances(inputDS));
    }
    @Test
    void testViewSupplierPerformanceReport_WithNoMatchingDateRange_throwException() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        ViewSupplierPerformanceReportInputDS inputDS = new ViewSupplierPerformanceReportInputDS("Electronics", LocalDate.of(2024, 5, 1), LocalDate.of(2024, 5, 15));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics= new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance performance = new SupplierPerformance(supplier, quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));

        when(supplierRepository.findAll()).thenReturn(Collections.singletonList(supplier));
        when(supplierPerformanceRepository.findAll()).thenReturn(Collections.singletonList(performance));

        when(supplierRepository.findBySupplierId("Sup100")).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));


        assertThrows(IllegalArgumentException.class, () -> viewSupplierPerformancesUseCase.viewSupplierPerformances(inputDS));
    }
    @Test
    public void testCalculateSupplierPerformance_WithValidSupplierId_Successfully() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance performance = new SupplierPerformance(supplier,quantitativeMetrics,qualitativeMetrics,LocalDate.now().minusMonths(1));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierPerformanceRepository.findAll()).thenReturn(List.of(performance));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(),quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));

        double score = viewSupplierPerformancesUseCase.calculateSupplierPerformance(performance);

        assertEquals(84.27, score);
    }
    @Test
    public void testCalculateSupplierPerformance_WithNullPerformanceRecord_ThrowExceptions() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance performance = new SupplierPerformance(supplier, quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierPerformanceRepository.findAll()).thenReturn(List.of(performance));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        assertThrows(NullPointerException.class, () -> viewSupplierPerformancesUseCase.calculateSupplierPerformance(null));
    }
    @Test
    public void testCalculateSupplierPerformance_WithNoPerformanceRecord_throwExceptions() {
        viewSupplierPerformanceReportOutputBoundary = mock(ViewSupplierPerformanceReportOutputBoundary.class);
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformancesUseCase = new ViewSupplierPerformancesUseCase(supplierRepository,supplierPerformanceRepository,supplierPerformanceEvaluationServices,viewSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier( "IBM", "Electronics", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        SupplierPerformance performance = new SupplierPerformance(supplier, quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        SupplierPerformance performance1 = new SupplierPerformance();
        performance1.setSupplier(supplier);
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierPerformanceRepository.findAll()).thenReturn(List.of(performance));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        assertThrows(IllegalArgumentException.class, () -> viewSupplierPerformancesUseCase.calculateSupplierPerformance(performance1));
    }

    // test for updating the vendor details and payment methods
    @Test
    public void testUpdateSuppliersDetailsSuccessfully(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD", "BANK_TRANSFER", "PAYPAL"), "CREDIT_CARD",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), "Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));
        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS("VIN001","MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433",List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("VIN001")).thenReturn(Optional.of(supplier));

        UpdateSupplierContactDetailedOutputDS outPut =  updateSupplierDetailUseCase.updateSupplierDetails(input);

        assertEquals("Enat Bank",outPut.getPaymentMethods().getFirst().getBankName());
        assertEquals("Kenya",outPut.getContactDetail().getAddress().trim());
        assertEquals(BigDecimal.valueOf(50000.0),outPut.getPaymentMethods().getFirst().getCreditLimit());
        verify(supplierRepository,times(1)).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,times(1)).presentUpdatedSupplier(outPut);

    }
    @Test
    public void testUpdateSupplierDetail_WithNullInputData_throwException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950", "Me Me","+2510987654433", List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL, NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("VIN001")).thenReturn(Optional.of(supplier));

        assertThrows(NullPointerException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(null));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }
    @Test
    public void testUpdateSupplierDetails_WithNonExistedSupplier_throwException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD", "BANK_TRANSFER", "PAYPAL"), "CREDIT_CARD",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), "Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433",List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("VIN001")).thenReturn(Optional.of(supplier));
        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }
    @Test
    public void testUpdateSupplierDetail_WithNullSupplierContactInputDS_throwException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD", "BANK_TRANSFER", "PAYPAL"), "CREDIT_CARD","Tekia Tekle","+251979417636", "Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Tekia Tekle","+251979157364", List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL, NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));
        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"MicroSoft","Software","ms365","sm10101",null,List.of(supplierPaymentMethod),LocalDate.now());

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }
    @Test
    public void testUpdateSupplierDetail_WithInvalidEmailExpression_ThrowException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegaycisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD", "BANK_TRANSFER", "PAYPAL"), "CREDIT_CARD",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), "Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433",List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS(supplier.getSupplierId(),"MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }
    @Test
    public void testUpdateSupplierDetail_WithInvalidPhoneNumber_ThrowException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegay@cisco.com", "0979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD", "BANK_TRANSFER", "PAYPAL"), "CREDIT_CARD",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), "Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));
        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS("SUP001","MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433",List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));

    }
    @Test
    public void testUpdateSupplierDetail_WithEmptyAcceptedPaymentMethod_ThrowException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of(), "CREDIT_CARD",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),"Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));
        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS("SUP001","MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433",List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }
    @Test
    public void testUpdateSupplierDetail_WithPreferedPaymentMethodNotInAcceptedList_ThrowException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD","BANK_TRANSFER","PAYPAL"),"CASH",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),"Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(50000.0));
        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS("SUP001","MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433", List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),CASH,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }
    @Test
    public void testUpdateSupplierDetail_WithInvalidCreditLimit_ThrowException(){
        supplierMaintenanceService = new SupplierMaintenanceService();
        updateSupplierDetailOutputBoundary = mock(UpdateSupplierContactDetailOutputBoundary.class);
        updateSupplierDetailUseCase = new UpdateSupplierDetailUseCase(supplierRepository,supplierMaintenanceService,updateSupplierDetailOutputBoundary);

        SupplierContactDetailsInputDS supplierContactDetail = new SupplierContactDetailsInputDS("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethodsInputDS supplierPaymentMethod = new SupplierPaymentMethodsInputDS(List.of("CREDIT_CARD", "BANK_TRANSFER", "PAYPAL"), "CREDIT_CARD",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), "Enat Bank", "09129284395565", "USD", "NET_30", BigDecimal.valueOf(-50000.0));
        UpdateSupplierContactDetailInputDS input = new UpdateSupplierContactDetailInputDS("SUP001","MicroSoft","Software","ms365","sm10101",supplierContactDetail,List.of(supplierPaymentMethod),LocalDate.now());

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        Supplier supplier = new Supplier("MicroSoft","Software","ms365","sm10101",new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai"),
                List.of(new SupplierPaymentMethod("Brhan Bank", "101293049950","Me is Me","+2510987654433",List.of(CREDIT_CARD,BANK_TRANSFER,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(10000.0))),
                List.of(item1,item),LocalDate.now().minusMonths(3));

        when(supplierRepository.update(any(Supplier.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findBySupplierId("SUP001")).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class,()->updateSupplierDetailUseCase.updateSupplierDetails(input));
        verify(supplierRepository,never()).update(any(Supplier.class));
        verify(updateSupplierDetailOutputBoundary,never()).presentUpdatedSupplier(any(UpdateSupplierContactDetailedOutputDS.class));
    }

    // this is the testing for purchase order use cases
    @Test
    public void testCreatePurchaseOrder_WithValidField_Successfully(){
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        createPurchaseOrderOutputBoundary = mock(CreatePurchaseOrderOutputBoundary.class);
        createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderRepository,requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        /** this is the approved Requisition .*/
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",department.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(), purchaseOrderContactDetailsInputDS, supplier.getSupplierId(), List.of(requisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        CreatePurchaseOrderOutputDS createPurchaseOrderOutputDS = createPurchaseOrderUseCase.createOrder(createPurchaseOrderInputDS);

        //assert
        assertEquals(supplier.getSupplierId(),createPurchaseOrderOutputDS.getSupplierId());
        assertEquals(List.of(requisition.getRequisitionId()),createPurchaseOrderOutputDS.getOrderedRequisitionIds());
        assertEquals(department.getDepartmentId(),createPurchaseOrderOutputDS.getDepartmentId());
    }
    @Test
    public void testCreatePurchaseOrder_WithNotApprovedRequisition_throwException(){ // this test tests when we try to create Purchase order if the requisition is not approved.
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        createPurchaseOrderOutputBoundary = mock(CreatePurchaseOrderOutputBoundary.class);
        createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderRepository,requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        /** the requisition is in pending status. */

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",department.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(), purchaseOrderContactDetailsInputDS, supplier.getSupplierId(), List.of(requisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        assertEquals(RequisitionStatus.PENDING,requisition.getRequisitionStatus());
        assertThrows(IllegalArgumentException.class,()->createPurchaseOrderUseCase.createOrder(createPurchaseOrderInputDS)); // since the requisition is by default pending status.
    }
    @Test
    public void testCreatePurchaseOrder_WithNotExistedSupplier_throwException(){ // when we try to create new purchase order with not existed supplier.
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        createPurchaseOrderOutputBoundary = mock(CreatePurchaseOrderOutputBoundary.class);
        createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderRepository,requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",department.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(), purchaseOrderContactDetailsInputDS, supplier.getSupplierId(), List.of(requisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,()->createPurchaseOrderUseCase.createOrder(createPurchaseOrderInputDS));
    }
    @Test
    public void testCreateOrder_WithNullInput_throwException() { // when we try to create new purchase order with null elements.
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        createPurchaseOrderOutputBoundary = mock(CreatePurchaseOrderOutputBoundary.class);
        createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderRepository,requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.empty());

        assertThrows(NullPointerException.class, () -> createPurchaseOrderUseCase.createOrder(null));
    }
    @Test
    public void testCreateOrderWithDeliveryDateBeforeOrderDate() { // this try to create purchase order with delivery date is before Order time.
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        createPurchaseOrderOutputBoundary = mock(CreatePurchaseOrderOutputBoundary.class);
        createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderRepository,requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,3);

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",department.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(), purchaseOrderContactDetailsInputDS, supplier.getSupplierId(), List.of(requisition.getRequisitionId()),LocalDate.now(),LocalDate.now().minusMonths(4),"Air");
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        assertThrows(IllegalArgumentException.class, () -> createPurchaseOrderUseCase.createOrder(createPurchaseOrderInputDS));
    }
    @Test
    public void testCreateOrder_WthMixedRequisitionStatus_Successfully(){
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        createPurchaseOrderOutputBoundary = mock(CreatePurchaseOrderOutputBoundary.class);
        createPurchaseOrderUseCase = new CreatePurchaseOrderUseCase(purchaseOrderRepository,requisitionRepository,departmentRepository,supplierRepository,createPurchaseOrderOutputBoundary,purchaseOrderEnsuringServices);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        RequestedItem requestedItem1 = new RequestedItem(item,1);
        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-101", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        purchaseOrderEnsuringServices.getDepartmentMap().put(department.getDepartmentId(),department);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisition.getRequisitionId(),requisition);
        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        purchaseOrderEnsuringServices.getPurchaseRequisitionMap().put(requisitionOne.getRequisitionId(),requisitionOne);

        PurchaseOrderContactDetailsInputDS purchaseOrderContactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("zeray",department.getDepartmentId(),"zeray@malam.com","+251971324481","developer");
        CreatePurchaseOrderInputDS createPurchaseOrderInputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(), purchaseOrderContactDetailsInputDS, supplier.getSupplierId(), List.of(requisition.getRequisitionId()),LocalDate.now(),LocalDate.now().plusMonths(4),"Air");
        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when((requisitionRepository.findByRequisitionId(requisition.getRequisitionId()))).thenReturn(Optional.of(requisition));
        when((requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId()))).thenReturn(Optional.of(requisitionOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        CreatePurchaseOrderOutputDS createPurchaseOrderOutputDS = createPurchaseOrderUseCase.createOrder(createPurchaseOrderInputDS);

        // since the status for the requisitionOne is pending while approved for the requisition.
        assertEquals(1,createPurchaseOrderOutputDS.getOrderedRequisitionIds().size());
    }
    @Test
    public void testApprovePurchaseOrder_WithValidField_Successfully() {
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", department.getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        when(purchaseOrderRepository.update(any(PurchaseOrder.class))).thenAnswer(invocation->invocation.getArgument(0));

        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        approvePurchaseOrderUseCase.trackPurchaseOrderStatus(input);

        ApprovePurchaseOrderOutputDS output = new ApprovePurchaseOrderOutputDS(purchaseOrder.getOrderId(), true,LocalDate.now().plusDays(12), input.getManagerDetails().getName());
        assertTrue( output.isApproved());
    }
    @Test
    public void testApprovePurchaseOrder_WithNotActiveBudget_UnSuccessfully() {
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", department.getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        assertThrows(IllegalArgumentException.class,()-> approvePurchaseOrderUseCase.trackPurchaseOrderStatus(input));
        verify(purchaseOrderRepository,never()).update(any(PurchaseOrder.class));
    }
    @Test
    public void testApprovePurchaseOrder_WithApprovalDateBeforeOrderDate_UnSuccessfully() {
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", department.getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().minusDays(60));

        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        assertThrows(IllegalArgumentException.class,()-> approvePurchaseOrderUseCase.trackPurchaseOrderStatus(input));

    }
    @Test
    public void testApprovePurchaseOrder_WhenApprovalDateAfterDeliveryDate_Unsuccessfully(){
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", department.getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusMonths(10));

        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        assertThrows(IllegalArgumentException.class,()->approvePurchaseOrderUseCase.trackPurchaseOrderStatus(input));
    }
    @Test
    public void testApprovePurchaseOrder_WithTotalCostGreaterThanBudget_Unsuccessfully(){
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(25000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(20000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,4);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434"
                , supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", department.getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), purchaseOrder.getOrderId(), LocalDate.now().plusDays(30));

        when(purchaseOrderRepository.update(any(PurchaseOrder.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        ApprovePurchaseOrderOutputDS outputDS = approvePurchaseOrderUseCase.trackPurchaseOrderStatus(input);

        assertFalse(outputDS.isApproved());
    }
    @Test
    public void testApprovePurchaseOrder_WithNullInput_Unsuccessfully(){
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        when(purchaseOrderRepository.update(any(PurchaseOrder.class))).thenAnswer(invocation->invocation.getArgument(0));

        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        assertThrows(NullPointerException.class,()->approvePurchaseOrderUseCase.trackPurchaseOrderStatus(null));

        verify(purchaseOrderRepository,never()).update(any(PurchaseOrder.class));
        verify(approvePurchaseOrderOutputBoundary,never()).presentPurchaseOrderWithStatus(any(ApprovePurchaseOrderOutputDS.class));
    }
    @Test
    public void testApprovePurchaseOrder_WithNonExistedPurchaseOrder_Unsuccessfully(){
        purchaseOrderTrackingStatusService = new PurchaseOrderTrackingStatusService();
        departmentRepository = mock(DepartmentRepository.class);
        budgetRepository = mock(BudgetRepository.class);
        approvePurchaseOrderOutputBoundary = mock(ApprovePurchaseOrderOutputBoundary.class);
        approvePurchaseOrderUseCase = new ApprovePurchaseOrderUseCase(purchaseOrderRepository,departmentRepository,budgetRepository,purchaseOrderTrackingStatusService,approvePurchaseOrderOutputBoundary);
        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item,2);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        requisition.setRequisitionStatus(RequisitionStatus.APPROVED); /** this is the approved Requisition .*/

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        when(purchaseOrderRepository.update(any(PurchaseOrder.class))).thenAnswer(invocation->invocation.getArgument(0));

        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(budgetRepository.findByBudgetId(budget.getBudgetId())).thenReturn(Optional.of(budget));

        String orderId = IdGenerator.generateId("PO-");

        ApprovePurchaseOrderInputDS input = new ApprovePurchaseOrderInputDS(new PurchaseOrderContactDetailsInputDS("Daniel", department.getDepartmentId(),"daniel@gmail.com","+251979310625","Manager"), orderId, LocalDate.now().plusDays(10));

        assertThrows(IllegalArgumentException.class,()-> approvePurchaseOrderUseCase.trackPurchaseOrderStatus(input));

        verify(purchaseOrderRepository,never()).update(any(PurchaseOrder.class));
        verify(approvePurchaseOrderOutputBoundary,never()).presentPurchaseOrderWithStatus(any());
    }
    @Test
    public void testSendPurchaseOrderToSupplier_Success() {
        // Arrange
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        sendPurchaseOrderToSupplierOutputBoundary = mock(SendPurchaseOrderToSupplierOutputBoundary.class);
        sendPurchaseOrderToSupplierUseCase = new SendPurchaseOrderToSupplierUseCase(purchaseOrderRepository, supplierRepository, purchaseOrderEnsuringServices, sendPurchaseOrderToSupplierOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter("IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434",
                supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        SendPurchaseOrderToSupplierInputDS inputDS = new SendPurchaseOrderToSupplierInputDS(
                new PurchaseOrderContactDetailsInputDS("tsegay", department.getDepartmentId(), "tsegay@gmail.com", "+251979416534", "ReporterContactDetail"),
                purchaseOrder.getOrderId());

        when(supplierRepository.findBySupplierId(inputDS.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(List.of(purchaseOrder));

        // Act
        List<SendPurchaseOrderToSupplierOutPutDS> outputDS = sendPurchaseOrderToSupplierUseCase.sendPurchaseOrderToSupplier(inputDS);

        // Assert
        assertNotNull(outputDS);
        verify(sendPurchaseOrderToSupplierOutputBoundary,times(1)).presentElectronicallyTransferredPurchaseOrders(outputDS);
    }
    @Test
    void testSendPurchaseOrderToSupplier_WithNotApprovedPurchaseOrder_ThrowException() {
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        sendPurchaseOrderToSupplierOutputBoundary = mock(SendPurchaseOrderToSupplierOutputBoundary.class);
        sendPurchaseOrderToSupplierUseCase = new SendPurchaseOrderToSupplierUseCase(purchaseOrderRepository, supplierRepository, purchaseOrderEnsuringServices, sendPurchaseOrderToSupplierOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(),  List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrderEnsuringServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        purchaseOrderEnsuringServices.getPurchaseOrderMap().put(purchaseOrder.getOrderId(), purchaseOrder);

        SendPurchaseOrderToSupplierInputDS input = new SendPurchaseOrderToSupplierInputDS(new PurchaseOrderContactDetailsInputDS("Sender", department.getDepartmentId(), "sender@example.com", "+251912345678", "Manager"),
                purchaseOrder.getSupplier().getSupplierId());

        when(supplierRepository.findBySupplierId(input.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(List.of(purchaseOrder));

        assertThrows(IllegalArgumentException.class, () -> sendPurchaseOrderToSupplierUseCase.sendPurchaseOrderToSupplier(input));
        verify(sendPurchaseOrderToSupplierOutputBoundary,never()).presentElectronicallyTransferredPurchaseOrders(anyList());
    }
    @Test
    void testSendPurchaseOrderToSupplier_WithNOTExistedSupplier_ThrowException() {
        purchaseOrderEnsuringServices = new PurchaseOrderEnsuringServices();
        sendPurchaseOrderToSupplierOutputBoundary = mock(SendPurchaseOrderToSupplierOutputBoundary.class);
        sendPurchaseOrderToSupplierUseCase = new SendPurchaseOrderToSupplierUseCase(purchaseOrderRepository, supplierRepository, purchaseOrderEnsuringServices, sendPurchaseOrderToSupplierOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        SendPurchaseOrderToSupplierInputDS input = new SendPurchaseOrderToSupplierInputDS(new PurchaseOrderContactDetailsInputDS("Sender", department.getDepartmentId(), "sender@example.com", "+251912345678", "Manager"), purchaseOrder.getSupplier().getSupplierId());
        when(supplierRepository.findBySupplierId(input.getSupplierId())).thenReturn(Optional.empty());
        when(purchaseOrderRepository.findBySupplierId(input.getSupplierId())).thenReturn(List.of(purchaseOrder));

        assertThrows(IllegalArgumentException.class, () -> sendPurchaseOrderToSupplierUseCase.sendPurchaseOrderToSupplier(input));
        verify(sendPurchaseOrderToSupplierOutputBoundary, never()).presentElectronicallyTransferredPurchaseOrders(anyList());

    }

    /**this testing is focused on contract management use case.*/
    @Test
    public void testCreateContractsSuccessfully(){
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        createContractOutputBoundary = mock(CreateContractOutputBoundary.class);
        createContractUseCase = new CreateContractUseCase(contractRepository,supplierRepository,purchaseOrderRepository,storeAndTrackContractServices,createContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);


        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, supplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30,DeliveryTerms.CIF,true,List.of(purchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));


        CreateContractOutputDS outPut = createContractUseCase.createContracts(inputData);

        assertNotNull(outPut);
        assertEquals("Bid for national bank.",outPut.getContractTitle());
        verify(contractRepository,times(1)).save(any(Contract.class));
    }
    @Test
    public void testCreateContracts_With_NullInput_ThrowException(){
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        createContractOutputBoundary = mock(CreateContractOutputBoundary.class);
        createContractUseCase = new CreateContractUseCase(contractRepository,supplierRepository,purchaseOrderRepository,storeAndTrackContractServices,createContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);


        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30,"USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        assertThrows(NullPointerException.class,()->createContractUseCase.createContracts(null));
        verify(contractRepository,never()).save(any(Contract.class));
    }
    @Test
    public void testCreateContracts_WithNonExistedSupplier_ThrowException(){
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        createContractOutputBoundary = mock(CreateContractOutputBoundary.class);
        createContractUseCase = new CreateContractUseCase(contractRepository,supplierRepository,purchaseOrderRepository,storeAndTrackContractServices,createContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);


        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod( "Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER,NET_30, "USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        String supplierId = IdGenerator.generateId("SUP");
        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, supplierId, "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30,DeliveryTerms.CIF,true,List.of(purchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        assertThrows(IllegalArgumentException.class,()->createContractUseCase.createContracts(inputData));
        verify(contractRepository,never()).save(any(Contract.class));
    }
    @Test
    public void testCreateContracts_With_nonExistedPurchaseOrder(){
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        createContractOutputBoundary = mock(CreateContractOutputBoundary.class);
        createContractUseCase = new CreateContractUseCase(contractRepository,supplierRepository,purchaseOrderRepository,storeAndTrackContractServices,createContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);


        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER,NET_30, "USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        String orderId = IdGenerator.generateId("PO");

        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, supplier.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30,DeliveryTerms.CIF,true,List.of(orderId),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        assertThrows(IllegalArgumentException.class,()->createContractUseCase.createContracts(inputData));

        verify(contractRepository,never()).save(any(Contract.class));
    }
    @Test
    public void testCreateContracts_WithMissMatchingAmongSupplierAndPurchaseOrder_Unsuccessfully(){
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        createContractOutputBoundary = mock(CreateContractOutputBoundary.class);
        createContractUseCase = new CreateContractUseCase(contractRepository,supplierRepository,purchaseOrderRepository,storeAndTrackContractServices,createContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(40000));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        RequestedItem requestedItem = new RequestedItem(item,10);
        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);


        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD", BigDecimal.valueOf(10000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item), LocalDate.now());

        Supplier supplier1 = new Supplier("IBM","Vendor","IBM10123","IBM101010",contactDetail, List.of(paymentMethod),List.of(item),LocalDate.now());
        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        when(contractRepository.save(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(supplierRepository.findAll()).thenReturn(List.of(supplier, supplier1));
        when(supplierRepository.findBySupplierId(supplier1.getSupplierId())).thenReturn(Optional.of(supplier1));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        ContractsContactDetailsInputDS contactDetailsInputDS = new ContractsContactDetailsInputDS("Samrawit","samrawit@malam.com","+251914412347","Sales");
        CreateContractInputDS inputData = new CreateContractInputDS(contactDetailsInputDS, supplier1.getSupplierId(), "Bid for national bank.",LocalDate.now().plusDays(5),LocalDate.now().plusMonths(3),BigDecimal.valueOf(1000000.0), NET_30,DeliveryTerms.CIF,true,List.of(purchaseOrder.getOrderId()),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        assertThrows(IllegalArgumentException.class,()->createContractUseCase.createContracts(inputData));
        verify(contractRepository,never()).save(any(Contract.class));
    }

    /** test for viewing the Contract details when creating purchase Order.*/
    @Test
    public void testViewContractDetail_Successfuuly() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplier.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        ViewContractDetailOutputDS result = viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(contracts.getContractId(),inputDS);
        verify(contractRepository,times(1)).update(contracts);
        verify(viewContractDetailOutputBoundary,times(1)).presentContractDetails(result);
    }
    @Test
    public void testViewContractDetail_WithEmptyContractId_ThrowEmptyList() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplier.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        assertThrows(IllegalArgumentException.class,()->viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(" ",inputDS));
        verify(contractRepository,never()).update(any(Contract.class));
        verify(viewContractDetailOutputBoundary,never()).presentContractDetails(any(ViewContractDetailOutputDS.class));
    }
    @Test
    public void testViewContratDetail_withNullContractId_ThrowException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));


        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplier.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        assertThrows(IllegalArgumentException.class,()->viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(null,inputDS));
        verify(contractRepository,never()).update(any(Contract.class));
        verify(viewContractDetailOutputBoundary,never()).presentContractDetails(any(ViewContractDetailOutputDS.class));

    }
    @Test
    public void testViewContractDetail_WithNullPurchaseOrderInput_throwException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplier.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        assertThrows(NullPointerException.class,()->viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(contracts.getContractId(),null));
        verify(contractRepository,never()).update(any(Contract.class));
        verify(viewContractDetailOutputBoundary,never()).presentContractDetails(any(ViewContractDetailOutputDS.class));

    }
    @Test
    public void testViewContractDetail_WithNonExistedContract_ThrowException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId("CON-101")).thenReturn(Optional.empty());
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplier.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        assertThrows(IllegalArgumentException.class,()->viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder("CON-101",inputDS));
        verify(contractRepository,never()).update(contracts);
        verify(viewContractDetailOutputBoundary,never()).presentContractDetails(any(ViewContractDetailOutputDS.class));

    }
    @Test
    public void testViewContractDetail_WithExistedPurchaseOrder_ThrowException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        List<String> requisitionIds = purchaseOrder.getRequisitionList().stream().map(PurchaseRequisition::getRequisitionId).collect(Collectors.toList());
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(purchaseOrder.getDepartment().getDepartmentId(),contactDetailsInputDS,purchaseOrder.getSupplier().getSupplierId(), requisitionIds, purchaseOrder.getOrderDate(), purchaseOrder.getDeliveryDate(),purchaseOrder.getShippingMethod());
        ViewContractDetailOutputDS result = viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(contracts.getContractId(),inputDS);
        verify(contractRepository,times(1)).update(contracts);
        verify(viewContractDetailOutputBoundary,times(1)).presentContractDetails(result);
    }
    @Test
    public void testViewContractDetail_WithMissMatchaMongContractSupplierAndPurchaseOrderSupplier_ThrowException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierContactDetail contactDetailOne = new SupplierContactDetail("Mine", "mine@supplier.com", "+251912123344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));
        SupplierPaymentMethod paymentMethodOne = new SupplierPaymentMethod("Some Bank", "123564", contactDetailOne.getFullName(), contactDetailOne.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));
        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());
        Supplier supplierOne = new Supplier("Hp", "IT Supplier", "SUP321", "TIN321", contactDetailOne, List.of(paymentMethodOne), List.of(item,itemOne), LocalDate.now());
        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplierOne.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        ViewContractDetailOutputDS result = viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(contracts.getContractId(),inputDS);
        assertEquals(result.getMessage(),"The supplier of the purchase order must match the supplier of the contract.");
        verify(contractRepository,times(1)).update(contracts);
        verify(viewContractDetailOutputBoundary,times(1)).presentContractDetails(result);
    }
    @Test
    public void testViewContractDetail_WhenTotalCostOfContractIsLessThanTotalCostOfPurchaseOrder_ThrowException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(50000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PurchaseOrderContactDetailsInputDS contactDetailsInputDS = new PurchaseOrderContactDetailsInputDS("John Doe", "john@example.com", "+251912345678", "Software", "Developer");
        CreatePurchaseOrderInputDS inputDS = new CreatePurchaseOrderInputDS(department.getDepartmentId(),contactDetailsInputDS,supplier.getSupplierId(), List.of(requisition.getRequisitionId(),requisitionOne.getRequisitionId()), LocalDate.now(), LocalDate.now().plusMonths(2),  "Air");
        ViewContractDetailOutputDS result = viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(contracts.getContractId(),inputDS);
        assertEquals(result.getMessage(),"The total cost of the purchase orders exceeds the contract's total cost.");
        verify(contractRepository,times(1)).update(contracts);
        verify(viewContractDetailOutputBoundary,times(1)).presentContractDetails(result);
    }
    @Test
    public void testViewContractDetail_NullInput_throwException() {
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        viewContractDetailOutputBoundary = mock(ViewContractDetailOutputBoundary.class);
        viewContractDetailsUseCase = new ViewContractDetailsUseCase(contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,storeAndTrackContractServices,viewContractDetailOutputBoundary);
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 5);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        storeAndTrackContractServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contracts = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(150000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(90000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contracts.getContractId())).thenReturn(Optional.of(contracts));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findAll()).thenReturn(Arrays.asList(contracts,contractOne));
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(departmentRepository.findByDepartmentId(department.getDepartmentId())).thenReturn(Optional.of(department));
        when(requisitionRepository.findByRequisitionId(requisition.getRequisitionId())).thenReturn(Optional.of(requisition));
        when(requisitionRepository.findByRequisitionId(requisitionOne.getRequisitionId())).thenReturn(Optional.of(requisitionOne));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(purchaseOrderRepository.findByOrderId(purchaseOrderOne.getOrderId())).thenReturn(Optional.of(purchaseOrderOne));

        when(purchaseOrderRepository.save(any(PurchaseOrder.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(contractRepository.update(any(Contract.class))).thenAnswer(invocation -> invocation.getArgument(0));

        assertThrows(IllegalArgumentException.class, () -> viewContractDetailsUseCase.viewContractDetailWhenCreatingPurchaseOrder(null,null));
        verify(contractRepository,never()).update(any(Contract.class));
        verify(viewContractDetailOutputBoundary,never()).presentContractDetails(any());

    }

    // this is the testing  for expiring contract notifier.
    @Test
    public void testNotifyExpiringContract_Successfully(){
        alertingContractsServices = new AlertingContractsServices();
        notifyExpiringContractOutputBoundary = mock(NotifyExpiringContractOutputBoundary.class);
        notifyExpiringContractUseCase = new NotifyExpiringContractUseCase(contractRepository,alertingContractsServices,notifyExpiringContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 10);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);


        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));

        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));

        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contract = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusDays(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusDays(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        contractOne.setStatus(ContractStatus.ACTIVE);
        contract.setStatus(ContractStatus.ACTIVE);

        when(contractRepository.findAll()).thenReturn(Arrays.asList(contract,contractOne));
        alertingContractsServices.getAllContracts().put(contract.getContractId(),contract);
        alertingContractsServices.getAllContracts().put(contractOne.getContractId(),contractOne);

        List<NotifyExpiringContractOutputDS> outPut = notifyExpiringContractUseCase.notifyContracts(18);
        assertEquals(2,outPut.size());

    }
    @Test
    public void testNotifyExpiringContracts_WhithEmptyRepository_ThrowException(){
        alertingContractsServices = new AlertingContractsServices();
        notifyExpiringContractOutputBoundary = mock(NotifyExpiringContractOutputBoundary.class);
        notifyExpiringContractUseCase = new NotifyExpiringContractUseCase(contractRepository,alertingContractsServices,notifyExpiringContractOutputBoundary);

        when(contractRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NullPointerException.class,()->notifyExpiringContractUseCase.notifyContracts(18));
    }
    @Test
    public void testNotifyExpiringContracts_WhenNoExpiringContracts_ThrowException(){
        alertingContractsServices = new AlertingContractsServices();
        notifyExpiringContractOutputBoundary = mock(NotifyExpiringContractOutputBoundary.class);
        notifyExpiringContractUseCase = new NotifyExpiringContractUseCase(contractRepository,alertingContractsServices,notifyExpiringContractOutputBoundary);

        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        Inventory item = new Inventory("SN001", "Laptop", 10, BigDecimal.valueOf(10000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem = new RequestedItem(item, 10);

        Inventory itemOne = new Inventory("SN002", "DeskTop", 2, BigDecimal.valueOf(5000), "Electronics", LocalDate.now().plusYears(3), "Spec");
        RequestedItem requestedItem1 = new RequestedItem(itemOne,2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItem1);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        SupplierContactDetail contactDetail = new SupplierContactDetail("Mike", "mike@supplier.com", "+251911223344", "Nairobi");
        SupplierPaymentMethod paymentMethod = new SupplierPaymentMethod("Some Bank", "123456", contactDetail.getFullName(), contactDetail.getPhone(), List.of(BANK_TRANSFER), BANK_TRANSFER, NET_30, "USD",BigDecimal.valueOf(20000));

        Supplier supplier = new Supplier("Dell", "IT Vendor", "SUP123", "TIN123", contactDetail, List.of(paymentMethod), List.of(item,itemOne), LocalDate.now());

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplier,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contract = new Contract("contractOne", supplier,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractOne = new Contract("contractTwo", supplier,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        contractOne.setStatus(ContractStatus.ACTIVE);
        contract.setStatus(ContractStatus.ACTIVE);

        when(contractRepository.findAll()).thenReturn(Arrays.asList(contract,contractOne));
        alertingContractsServices.getAllContracts().put(contract.getContractId(),contract);
        alertingContractsServices.getAllContracts().put(contractOne.getContractId(),contractOne);

        assertThrows(IllegalArgumentException.class,()->notifyExpiringContractUseCase.notifyContracts(8));
    }

    /** this is the testing for invoice payment reconciliation. */
    @Test
    public void testReconcileInvoice_WithNODiscrepancy_Successfully() {

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),LocalDate.now());

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        ReconclieInvoiceOutputDS outputResult = reconcileInvoiceUseCase.reconcileInvoice(inputDs);

        assertEquals(ReconciliationStatus.MATCHED, outputResult.getStatus());

        verify(paymentReconciliationRepository,times(1)).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WithDiscrepancyAndUnderPaid_Successfully() {

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

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
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,4);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(1000.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,2);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),LocalDate.now());


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        ReconclieInvoiceOutputDS outputResult = reconcileInvoiceUseCase.reconcileInvoice(inputDs);

        assertEquals(ReconciliationStatus.UNDERPAID, outputResult.getStatus());
        verify(paymentReconciliationRepository,times(1)).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WithDiscrepancyAndOVERPaid_Successfully() {

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

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

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1500.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),LocalDate.now());


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        ReconclieInvoiceOutputDS outputResult = reconcileInvoiceUseCase.reconcileInvoice(inputDs);

        assertEquals(ReconciliationStatus.OVERPAID, outputResult.getStatus());
        verify(paymentReconciliationRepository,times(1)).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WithInvalidInput_throwExcpetion(){ // invalid Accountant Contact Detail.

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

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
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1500.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails(" ","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        assertThrows(IllegalArgumentException.class, ()->reconcileInvoiceUseCase.reconcileInvoice(inputDs));

        verify(paymentReconciliationRepository,never()).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary,never()).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WithMissingInputElements_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem =  new RequestedItem(item,3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1500.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");

        ReconcileInvoiceInputDS inputDsOne = new ReconcileInvoiceInputDS(accountantContactDetails, null, purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());
        ReconcileInvoiceInputDS inputDsTwo = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), null, deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());
        ReconcileInvoiceInputDS inputDsThree = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), null,LocalDate.now());


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        assertThrows(IllegalArgumentException.class, ()->reconcileInvoiceUseCase.reconcileInvoice(inputDsOne));
        assertThrows(IllegalArgumentException.class, ()->reconcileInvoiceUseCase.reconcileInvoice(inputDsTwo));
        assertThrows(IllegalArgumentException.class, ()->reconcileInvoiceUseCase.reconcileInvoice(inputDsThree));

        verify(paymentReconciliationRepository,never()).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary,never()).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WithNullInput_throwException() {

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

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
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1500.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,3);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");


        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));

        assertThrows(IllegalArgumentException.class, () -> reconcileInvoiceUseCase.reconcileInvoice(null));
    }
    @Test
    public void testReconcileInvoice_WithnonExistedOrder_throwException() {

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

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
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,2);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");


        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");
        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());

        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reconcileInvoiceUseCase.reconcileInvoice(inputDs));
        verify(paymentReconciliationRepository,never()).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary, never()).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WhenNotInvoiced_throwException(){
        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

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
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item,2);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");
        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());

        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.empty());
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.of(deliveryReceipt));

        assertThrows(IllegalArgumentException.class, ()->reconcileInvoiceUseCase.reconcileInvoice(inputDs));
        verify(paymentReconciliationRepository,never()).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary,never()).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }
    @Test
    public void testReconcileInvoice_WhenNotDelivered_throwException() {

        invoiceReconciliationServices = new InvoiceReconciliationServices();
        reconcileInvoiceOutputBoundary = mock(ReconcileInvoiceOutputBoundary.class);
        reconcileInvoiceUseCase = new ReconcileInvoiceUseCase(invoiceReconciliationServices, paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,purchaseOrderRepository,reconcileInvoiceOutputBoundary);

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department("Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        RequestedItem requestedItem = new RequestedItem(item, 3);
        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder = new PurchaseOrder( department, List.of(requisition), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem = new InvoicedItem(item,3);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(20),LocalDate.now().minusDays(5), supplier,purchaseOrder,List.of(invoicedItem),BigDecimal.valueOf(1000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(500.0),"Birr",LocalDate.now(), new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer")," ");

        DeliveredItem receivedItem = new DeliveredItem(item, 2);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(supplier, purchaseOrder, List.of(receivedItem), LocalDate.now().minusDays(10),"",requester,"");

        AccountantContactDetails accountantContactDetails = new AccountantContactDetails("New Officer","officer.invoice@gmial.com","+251987327646","Officer");
        ReconcileInvoiceInputDS inputDs = new ReconcileInvoiceInputDS(accountantContactDetails, invoice.getInvoiceId(), purchaseOrder.getOrderId(), deliveryReceipt.getReceiptId(),deliveryReceipt.getDeliveryDate());

        invoiceReconciliationServices.addPurchaseOrder(purchaseOrder);
        invoiceReconciliationServices.addInvoice(invoice);
        invoiceReconciliationServices.addDeliveryReceipt(deliveryReceipt);

        when(paymentReconciliationRepository.save(any(PaymentReconciliation.class))).thenAnswer(invocation->invocation.getArgument(0));
        when(purchaseOrderRepository.findByOrderId(purchaseOrder.getOrderId())).thenReturn(Optional.of(purchaseOrder));
        when(invoiceRepository.findByInvoiceId(invoice.getInvoiceId())).thenReturn(Optional.of(invoice));
        when(deliveryReceiptRepository.findByReceiptId(deliveryReceipt.getReceiptId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> reconcileInvoiceUseCase.reconcileInvoice(inputDs));
        verify(paymentReconciliationRepository,never()).save(any(PaymentReconciliation.class));
        verify(reconcileInvoiceOutputBoundary,never()).presentReconciledInvoice(any(ReconclieInvoiceOutputDS.class));
    }

    /** this is the testing for Viewing Reconciled Record History. */
    @Test
    public void testViewReconciliationTotalHistory_Success() {
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        viewReconciledPaymentRecordUseCase.viewReconciliationTotalHistory();
        verify(viewReconciledPaymentRecordOutputBoundary, times(1)).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciliationTotalHistory_WhenReconciledRecordRepositorEmpty_throwException() {
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, () -> viewReconciledPaymentRecordUseCase.viewReconciliationTotalHistory());
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciliationTotalHistory_whenNoPaymentReconcilationRecorededBefore_throwException(){

        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        assertThrows(IllegalArgumentException.class, ()->viewReconciledPaymentRecordUseCase.viewReconciliationTotalHistory());
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryByTimeRange_WithValidRecord_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

        Budget budget = new Budget( BigDecimal.valueOf(250000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Department department = new Department( "Software development", "develop software solution.", budget);
        CostCenter costCenter = new CostCenter( "IT equipments", "Manages IT Equipments");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(150000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        RequestedItem requestedItem1 = new RequestedItem(item,3);
        RequestedItem requestedItem2 = new RequestedItem(item1,2);
        RequestedItem requestedItem3 = new RequestedItem(item2,1);

        User requester = new User("Tekia Tekle", "tekia2034@gmail.com", "+251979417636", department, "developer");

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(10),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(2),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(5),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        List<ViewReconciledPaymentRecordOutPutDS> paymentRecordOutPutDS =  viewReconciledPaymentRecordUseCase.viewReconciliationHistoryByTimeRange(LocalDate.now().minusDays(10), LocalDate.now().plusDays(15));

        assertEquals(3,paymentRecordOutPutDS.size());
        verify(viewReconciledPaymentRecordOutputBoundary,times(1)).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryByTimeRange_WhenPaymentReconcilationRepositoryEmpty_throwExcepton(){

        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().minusDays(1),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(4),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(5),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
         paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of());
        assertThrows(IllegalArgumentException.class, ()->viewReconciledPaymentRecordUseCase.viewReconciliationHistoryByTimeRange(LocalDate.now().minusDays(5), LocalDate.now().plusDays(15)));
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryByTimeRange_whenNoReconciledRecordInTheRange_throwException(){

        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));
    }
    @Test
    public void testViewReconciledRecordHistoryByTimeRange_WithInvalidDate_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        assertThrows(IllegalArgumentException.class, ()->viewReconciledPaymentRecordUseCase.viewReconciliationHistoryByTimeRange(LocalDate.now().plusDays(10),LocalDate.now().minusDays(10)));
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryBySupplier_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");


        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        List<ViewReconciledPaymentRecordOutPutDS> reconciledPaymentRecordOutPutDS = viewReconciledPaymentRecordUseCase.viewReconciledPaymentRecord_WithSameSupplier(supplier.getSupplierId());

        assertEquals(3,reconciledPaymentRecordOutPutDS.size());
        verify(viewReconciledPaymentRecordOutputBoundary,times(1)).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryBySupplier_whenEmptyRepository_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, ()->viewReconciledPaymentRecordUseCase.viewReconciledPaymentRecord_WithSameSupplier(supplier.getSupplierId()));
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryBySupplier_whenRecordNotFoundINRepository_throowException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        assertThrows(IllegalArgumentException.class, ()->viewReconciledPaymentRecordUseCase.viewReconciledPaymentRecord_WithSameSupplier(supplier.getSupplierId()));
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryBySupplier_whenNoPaymentReconcilationWithSupplier_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        System.out.println(supplier.getSupplierId());
        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        assertThrows(IllegalArgumentException.class, ()-> viewReconciledPaymentRecordUseCase.viewReconciledPaymentRecord_WithSameSupplier("supplierId"));
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }
    @Test
    public void testViewReconciledRecordHistoryBySupplier_WithInvalidInput_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        viewReconciledPaymentRecordOutputBoundary = mock(ViewReconciledPaymentRecordOutputBoundary.class);
        viewReconciledPaymentRecordUseCase = new ViewReconciledPaymentRecordUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,viewReconciledPaymentRecordOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        assertThrows(IllegalArgumentException.class, ()->viewReconciledPaymentRecordUseCase.viewReconciledPaymentRecord_WithSameSupplier(" "));
        verify(viewReconciledPaymentRecordOutputBoundary,never()).presentReconciledPaymentRecordHistory(anyList());
    }

    /** testing for Notifying supplier Payment Approval Date. */
    @Test
    public void testNotifySupplier_AllPaymentRecord_Successfully(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        List<NotifySupplierForPaymentApprovalOutPutDS> outPutDSResult = notifySupplierForPaymentApprovalUseCase.notifySupplier(LocalDate.now().plusDays(5));
        for (NotifySupplierForPaymentApprovalOutPutDS outPutDS:outPutDSResult){
            System.out.println(outPutDS);
        }
        assertEquals(3,outPutDSResult.size());
        verify(notifySupplierForPaymentApprovalOutputBoundary,times(3)).presentNotification(outPutDSResult);
    }
    @Test
    public void testNotifySupplier_withMixedInvoiceLists_returnSpecific() {
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(10),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder3,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(7),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);


        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        List<NotifySupplierForPaymentApprovalOutPutDS> outPutDSResult = notifySupplierForPaymentApprovalUseCase.notifySupplier(LocalDate.now().plusDays(5));

        assertEquals(1,outPutDSResult.size());
        verify(notifySupplierForPaymentApprovalOutputBoundary, times(1)).presentNotification(outPutDSResult);

    }
    @Test
    public void testNotifySupplier_WithNoInvoicesUnderNotification_returnEmpty() {
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(10),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(10),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder3,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now().plusDays(7),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);
        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        assertThrows(IllegalArgumentException.class,()->notifySupplierForPaymentApprovalUseCase.notifySupplier(LocalDate.now().plusDays(5)));
        verify(notifySupplierForPaymentApprovalOutputBoundary, never()).presentNotification(anyList());
    }
    @Test
    public void testNotifySupplier_WithEmptyRepository_throwException() {

        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));

        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder3,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of());

        assertThrows(IllegalArgumentException.class, () -> notifySupplierForPaymentApprovalUseCase.notifySupplier(LocalDate.now().plusDays(5)));
        verify(notifySupplierForPaymentApprovalOutputBoundary,never()).presentNotification(anyList());
    }
    @Test
    public void testNotifySupplier_WithNullInput_throwException() {

        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder3,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        assertThrows(IllegalArgumentException.class, () -> notifySupplierForPaymentApprovalUseCase.notifySupplier(null));
        verify(notifySupplierForPaymentApprovalOutputBoundary,never()).presentNotification(anyList());
    }
    @Test
    public void testSupplier_WithInvalidInput_throwException(){
        paymentReconciliationMaintainingService = new PaymentReconciliationMaintainingService();
        notifySupplierForPaymentApprovalOutputBoundary = mock(NotifySupplierForPaymentApprovalOutputBoundary.class);
        notifySupplierForPaymentApprovalUseCase = new NotifySupplierForPaymentApprovalUseCase(paymentReconciliationMaintainingService,paymentReconciliationRepository,notifySupplierForPaymentApprovalOutputBoundary);

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

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.addItem(requestedItem1);

        PurchaseRequisition requisitionTwo = new PurchaseRequisition("REQ-002", LocalDate.now(), requester, department, costCenter, PriorityLevel.MEDIUM, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionTwo.addItem(requestedItem2);

        PurchaseRequisition requisitionThree = new PurchaseRequisition("REQ-003", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(2), "Need for project work");
        requisitionThree.addItem(requestedItem3);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier( "IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        PurchaseOrder purchaseOrder1 = new PurchaseOrder( department, List.of(requisitionOne), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder2 = new PurchaseOrder( department, List.of(requisitionTwo), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));
        PurchaseOrder purchaseOrder3 = new PurchaseOrder( department, List.of(requisitionThree), LocalDate.now(), supplier, "Air", LocalDate.now().plusMonths(4));


        InvoicedItem invoicedItem1 = new InvoicedItem(item,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(item1,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(item2,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",department,"Officer");
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder1,List.of(invoicedItem1),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice1 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder2,List.of(invoicedItem2),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");
        Invoice invoice2 = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), supplier,purchaseOrder3,List.of(invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceOfficer," ");

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);

        DeliveryReceipt deliveryOne= new DeliveryReceipt(supplier,purchaseOrder1,List.of(deliveredItem1),LocalDate.now().minusDays(5),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryTwo= new DeliveryReceipt(supplier,purchaseOrder2,List.of(deliveredItem2),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",requester,"received");
        DeliveryReceipt deliveryThree= new DeliveryReceipt(supplier,purchaseOrder3,List.of(deliveredItem3),LocalDate.now().plusDays(1),"Addiss Ababa,Ethiopia",requester,"received");

        // adding the purchase order.
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder1.getOrderId(),purchaseOrder1);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder2.getOrderId(),purchaseOrder2);
        paymentReconciliationMaintainingService.getPurchaseOrderMap().put(purchaseOrder3.getOrderId(),purchaseOrder3);

        // adding the invoice
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice.getInvoiceId(), invoice);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice1.getInvoiceId(), invoice2);
        paymentReconciliationMaintainingService.getInvoiceMap().put(invoice2.getInvoiceId(), invoice2);

        //adding the delivery
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryOne.getReceiptId(), deliveryOne);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryTwo.getReceiptId(), deliveryTwo);
        paymentReconciliationMaintainingService.getDeliveryReceiptMap().put(deliveryThree.getReceiptId(), deliveryThree);

        // adding the paymentReconciliation.
        PaymentReconciliation reconciliationOne = paymentReconciliationMaintainingService.addReconciledRecord(invoice,purchaseOrder1,deliveryOne);
        PaymentReconciliation reconciliationTwo = paymentReconciliationMaintainingService.addReconciledRecord(invoice1,purchaseOrder2,deliveryTwo);
        PaymentReconciliation reconciliationThree = paymentReconciliationMaintainingService.addReconciledRecord(invoice2,purchaseOrder3,deliveryThree);

        when(paymentReconciliationRepository.findAll()).thenReturn(List.of(reconciliationOne,reconciliationTwo,reconciliationThree));

        when(paymentReconciliationRepository.findByPaymentId(reconciliationOne.getPaymentId())).thenReturn(Optional.of(reconciliationOne));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationTwo.getPaymentId())).thenReturn(Optional.of(reconciliationTwo));
        when(paymentReconciliationRepository.findByPaymentId(reconciliationThree.getPaymentId())).thenReturn(Optional.of(reconciliationThree));

        assertThrows(IllegalArgumentException.class, () -> notifySupplierForPaymentApprovalUseCase.notifySupplier(LocalDate.now().minusDays(3)));
        verify(notifySupplierForPaymentApprovalOutputBoundary, never()).presentNotification(anyList());
    }

    /** this is the test for Supplier performance mmetrics evaluation. */
    @Test
    public void testGenerateSupplierPerformanceReport_WithValidFields_Successfully(){
        supplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();
        generateSupplierPerformanceReportOutputBoundary = mock(GenerateSupplierPerformanceReportOutputBoundary.class);
        generateSupplierPerformanceReportUseCase = new GenerateSupplierPerformanceReportUseCase(supplierPerformanceRepository,supplierRepository,supplierPerformanceHistoryService,generateSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        when(supplierPerformanceRepository.save(any(SupplierPerformance.class))).thenAnswer(invocation -> invocation.getArgument(1));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findBySupplierId(performance.getSupplier().getSupplierId())).thenReturn(List.of(performance));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        List<GenerateSupplierPerformanceReportOutputDS> supplierPerformanceReportOutputDSList = generateSupplierPerformanceReportUseCase.generatePerformanceReport(performance.getSupplier().getSupplierId());

        assertEquals(1,supplierPerformanceReportOutputDSList.size());

        verify(generateSupplierPerformanceReportOutputBoundary,times(1)).presentSupplierPerformanceReport(anyList());

    }
    @Test
    public void testGenerateSupplierPerformanceReport_WithInvalidInput_throwException(){

        supplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();
        generateSupplierPerformanceReportOutputBoundary = mock(GenerateSupplierPerformanceReportOutputBoundary.class);
        generateSupplierPerformanceReportUseCase = new GenerateSupplierPerformanceReportUseCase(supplierPerformanceRepository,supplierRepository,supplierPerformanceHistoryService,generateSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        assertThrows(IllegalArgumentException.class,()->generateSupplierPerformanceReportUseCase.generatePerformanceReport(" "));
    }
    @Test
    public void testGenerateSupplierPerformanceReport_WithNonExistedSupplier_throwException(){
        supplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();
        generateSupplierPerformanceReportOutputBoundary = mock(GenerateSupplierPerformanceReportOutputBoundary.class);
        generateSupplierPerformanceReportUseCase = new GenerateSupplierPerformanceReportUseCase(supplierPerformanceRepository,supplierRepository,supplierPerformanceHistoryService,generateSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);
        supplierPerformanceHistoryService.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        String supplierId = IdGenerator.generateId("SUP");
        assertThrows(IllegalArgumentException.class,()->generateSupplierPerformanceReportUseCase.generatePerformanceReport(supplierId));
    }
    @Test
    public void testGenerateSupplierPerformanceReport_WhenSupplierNotEvaluated_ThrowException(){ // performance metrics was not added yet.
        supplierPerformanceHistoryService = new StoringSupplierPerformanceHistoryService();
        generateSupplierPerformanceReportOutputBoundary = mock(GenerateSupplierPerformanceReportOutputBoundary.class);
        generateSupplierPerformanceReportUseCase = new GenerateSupplierPerformanceReportUseCase(supplierPerformanceRepository,supplierRepository,supplierPerformanceHistoryService,generateSupplierPerformanceReportOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceHistoryService.getSupplierMap().put(supplier.getSupplierId(), supplier);

        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        assertThrows(IllegalArgumentException.class,()->generateSupplierPerformanceReportUseCase.generatePerformanceReport(supplier.getSupplierId()));

    }

    /** this is the test for viewing the performance Metrics. */
    @Test
    public void testViewSupplierPerformanceMetrics_Successfully(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceMerticsOutputBoundary = mock(ViewSupplierPerformanceMerticsOutputBoundary.class);
        viewSupplierPerformanceMetricsUseCase = new ViewSupplierPerformanceMetricsUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,viewSupplierPerformanceMerticsOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));


        List<SupplierPerformance> performanceList = supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        SupplierPerformance performance = performanceList.getFirst();
        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        when(supplierPerformanceRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(performanceList);
        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        ViewSupplierPerformanceMetricsOutputDS  supplierPerformanceMetricsOutputDS = viewSupplierPerformanceMetricsUseCase.viewPerformanceMetrics(performance.getSupplier().getSupplierId());

        assertEquals(supplier.getSupplierId(),supplierPerformanceMetricsOutputDS.getSupplierId());
        assertEquals(supplierQualitativePerformanceMetrics,supplierPerformanceMetricsOutputDS.getSupplierQualitativePerformanceMetrics());
        assertEquals(supplierQuantitativePerformanceMetrics,supplierPerformanceMetricsOutputDS.getSupplierQuantitativePerformanceMetrics());

        verify(viewSupplierPerformanceMerticsOutputBoundary,times(1)).presentSupplierPerformanceMetrics(any(ViewSupplierPerformanceMetricsOutputDS.class));
    }
    @Test
    public void testViewSupplierPerformanceMetrics_WithNotValidInput_ThrowException(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceMerticsOutputBoundary = mock(ViewSupplierPerformanceMerticsOutputBoundary.class);
        viewSupplierPerformanceMetricsUseCase = new ViewSupplierPerformanceMetricsUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,viewSupplierPerformanceMerticsOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));


        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        assertThrows(IllegalArgumentException.class,()->viewSupplierPerformanceMetricsUseCase.viewPerformanceMetrics(" "));
        verify(viewSupplierPerformanceMerticsOutputBoundary, never()).presentSupplierPerformanceMetrics(any(ViewSupplierPerformanceMetricsOutputDS.class));
    }
    @Test
    public void testViewSupplierPerformanceMetrics_WithNotExistedSupplier_ThrowException(){ // there is no supplier in the service when we find by the id.
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceMerticsOutputBoundary = mock(ViewSupplierPerformanceMerticsOutputBoundary.class);
        viewSupplierPerformanceMetricsUseCase = new ViewSupplierPerformanceMetricsUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,viewSupplierPerformanceMerticsOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));

        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        String supplierId = IdGenerator.generateId("SUP");

        assertThrows(IllegalArgumentException.class,()->viewSupplierPerformanceMetricsUseCase.viewPerformanceMetrics(supplierId));

        verify(viewSupplierPerformanceMerticsOutputBoundary, never()).presentSupplierPerformanceMetrics(any(ViewSupplierPerformanceMetricsOutputDS.class));
    }
    @Test
    public void testViewSupplierPerformanceMetrics_WhenSupplierNotFoundInRepository_ThrowException(){ // there is supplier in the map but not saved to the repository.
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceMerticsOutputBoundary = mock(ViewSupplierPerformanceMerticsOutputBoundary.class);
        viewSupplierPerformanceMetricsUseCase = new ViewSupplierPerformanceMetricsUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,viewSupplierPerformanceMerticsOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);

        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.of(performance));

        assertThrows(IllegalArgumentException.class,()->viewSupplierPerformanceMetricsUseCase.viewPerformanceMetrics(supplier.getSupplierId()));

        verify(viewSupplierPerformanceMerticsOutputBoundary, never()).presentSupplierPerformanceMetrics(any(ViewSupplierPerformanceMetricsOutputDS.class));
    }
    @Test
    public void testViewSupplierPerformanceMetrics_WhenPerformanceNotAllocatedToSupplier_throwException(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceMerticsOutputBoundary = mock(ViewSupplierPerformanceMerticsOutputBoundary.class);
        viewSupplierPerformanceMetricsUseCase = new ViewSupplierPerformanceMetricsUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,viewSupplierPerformanceMerticsOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));


        assertThrows(IllegalArgumentException.class,()->viewSupplierPerformanceMetricsUseCase.viewPerformanceMetrics(supplier.getSupplierId()));
        verify(viewSupplierPerformanceMerticsOutputBoundary, never()).presentSupplierPerformanceMetrics(any(ViewSupplierPerformanceMetricsOutputDS.class));
    }
    @Test
    public void testViewSupplierPerformanceMetrics_WithAllocatedPerformanceScoreButNotSavedToRepository_throwException(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        viewSupplierPerformanceMerticsOutputBoundary = mock(ViewSupplierPerformanceMerticsOutputBoundary.class);
        viewSupplierPerformanceMetricsUseCase = new ViewSupplierPerformanceMetricsUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,viewSupplierPerformanceMerticsOutputBoundary);

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30,"USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());

        SupplierQuantitativePerformanceMetrics supplierQuantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics supplierQualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance(supplier,supplierQuantitativePerformanceMetrics ,supplierQualitativePerformanceMetrics ,LocalDate.now());

        performance.setEvaluationDate(LocalDate.now().minusMonths(3));
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);

        supplierPerformanceEvaluationServices.getSupplierMap().put(supplier.getSupplierId(), supplier);
        when(supplierRepository.findBySupplierId(supplier.getSupplierId())).thenReturn(Optional.of(supplier));


        supplierPerformanceEvaluationServices.addSupplierPerformance(supplier.getSupplierId(), supplierQuantitativePerformanceMetrics,supplierQualitativePerformanceMetrics,LocalDate.now().minusMonths(3));

        when(supplierPerformanceRepository.findById(performance.getId())).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,()->viewSupplierPerformanceMetricsUseCase.viewPerformanceMetrics(supplier.getSupplierId()));
        verify(viewSupplierPerformanceMerticsOutputBoundary, never()).presentSupplierPerformanceMetrics(any(ViewSupplierPerformanceMetricsOutputDS.class));
    }

    /** testing for Using performance Data in order to renew contract . */
    @Test
    public void testEvaluateSupplierForContractRenewal_Successfully(){
        // arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);
        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);
        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(),List.of(CREDIT_CARD,CASH,PAYPAL),PAYPAL,NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);
        when(supplierRepository.findBySupplierCategory(supplierOne.getSupplierCategory())).thenReturn(List.of(supplierOne,supplierTwo, supplierThree));
        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));


        // adding performance to each supplier.

        SupplierPerformance result1 = new SupplierPerformance(supplierOne,quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        SupplierPerformance result2 = new SupplierPerformance(supplierTwo,quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        SupplierPerformance result3 = new SupplierPerformance(supplierThree,quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        when(supplierPerformanceRepository.findBySupplierId(result1.getSupplier().getSupplierId())).thenReturn(List.of(result1));
        when(supplierPerformanceRepository.findBySupplierId(result2.getSupplier().getSupplierId())).thenReturn(List.of(result2));
        when(supplierPerformanceRepository.findBySupplierId(result3.getSupplier().getSupplierId())).thenReturn(List.of(result3));

        when(supplierPerformanceRepository.findById(result1.getId())).thenReturn(Optional.of(result1));
        when(supplierPerformanceRepository.findById(result2.getId())).thenReturn(Optional.of(result2));
        when(supplierPerformanceRepository.findById(result3.getId())).thenReturn(Optional.of(result3));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());

        Contract contractTwo = new Contract("contractTwo", supplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        when(contractRepository.findAll()).thenReturn(List.of(contractOne,contractTwo,contractThree));
        when(contractRepository.findBySupplierId(contractOne.getSupplier().getSupplierId())).thenReturn(List.of(contractOne));
        when(contractRepository.findBySupplierId(contractTwo.getSupplier().getSupplierId())).thenReturn(List.of(contractTwo));
        when(contractRepository.findBySupplierId(contractThree.getSupplier().getSupplierId())).thenReturn(List.of(contractThree));

        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        List<EvaluateSupplierForContractRenewalOutputDS> result = evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(supplierOne.getSupplierCategory());
        assertEquals(3,result.size());
        verify(evaluateSupplierForContractRenewalOutputBoundary,times(1)).presentRenewalContract(result);
    }
    @Test
    public void testEvaluateSupplierForContractRenewal_WithInvalidaInput_throwException(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier
        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));
        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));
        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);


        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierTwo.getSupplierId(), supplierTwo);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierThree.getSupplierId(), supplierThree);


        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));

        // adding performance to each supplier.

        List<SupplierPerformance> resault1 = supplierPerformanceEvaluationServices.addSupplierPerformance(supplierOne.getSupplierId(),quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        List<SupplierPerformance> resault2 = supplierPerformanceEvaluationServices.addSupplierPerformance(supplierTwo.getSupplierId(),quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        List<SupplierPerformance> resault3 = supplierPerformanceEvaluationServices.addSupplierPerformance(supplierThree.getSupplierId(),quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        SupplierPerformance performance1 = resault1.getFirst();
        SupplierPerformance performance2 = resault2.getFirst();
        SupplierPerformance performance3 = resault3.getFirst();

        when(supplierPerformanceRepository.findById(performance1.getId())).thenReturn(Optional.of(performance1));
        when(supplierPerformanceRepository.findById(performance2.getId())).thenReturn(Optional.of(performance2));
        when(supplierPerformanceRepository.findById(performance3.getId())).thenReturn(Optional.of(performance3));

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());

        Contract contractTwo = new Contract("contractTwo", supplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        storeAndTrackContractServices.getContract().put(contractOne.getContractId(),contractOne);
        storeAndTrackContractServices.getContract().put(contractTwo.getContractId(),contractTwo);
        storeAndTrackContractServices.getContract().put(contractThree.getContractId(),contractThree);

        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        assertThrows(IllegalArgumentException.class,()-> evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(" "));
        verify(evaluateSupplierForContractRenewalOutputBoundary,never()).presentRenewalContract(anyList());

    }
    @Test
    public void testEvaluateSupplierForContractRenewal_WithNoSupplier_throwException(){
        // arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);


        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        // adding performance to each supplier.

        SupplierPerformance resault1 = new SupplierPerformance(supplierOne,quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        SupplierPerformance resault2 = new SupplierPerformance(supplierTwo,quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        SupplierPerformance resault3 = new SupplierPerformance(supplierThree,quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        when(supplierPerformanceRepository.findById(resault1.getId())).thenReturn(Optional.of(resault1));
        when(supplierPerformanceRepository.findById(resault2.getId())).thenReturn(Optional.of(resault2));
        when(supplierPerformanceRepository.findById(resault3.getId())).thenReturn(Optional.of(resault3));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());

        Contract contractTwo = new Contract("contractTwo", supplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        assertThrows(IllegalArgumentException.class,()->evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal("Electronics"));
        verify(evaluateSupplierForContractRenewalOutputBoundary,never()).presentRenewalContract(anyList());
    }
    @Test
    public void testEvaluateSupplierForContractRenewal_WithNotEvaluatedSupplier_throwException(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));


        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));

        // adding performance to each supplier.


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());

        Contract contractTwo = new Contract("contractTwo", supplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        assertThrows(IllegalArgumentException.class,()->evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(supplierOne.getSupplierCategory()));
        verify(evaluateSupplierForContractRenewalOutputBoundary,never()).presentRenewalContract(anyList());

    }
    @Test
    public void testEvaluateSupplierForContractRenewal_WhenEvaluationScoreNotSavedInRepository_ThrowException(){
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);


        // all about supplier
        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);


        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierOne.getSupplierId(), supplierOne);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierTwo.getSupplierId(), supplierTwo);
        supplierPerformanceEvaluationServices.getSupplierMap().put(supplierThree.getSupplierId(), supplierThree);


        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));

        // adding performance to each supplier.

        supplierPerformanceEvaluationServices.addSupplierPerformance(supplierOne.getSupplierId(),quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        supplierPerformanceEvaluationServices.addSupplierPerformance(supplierTwo.getSupplierId(),quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        supplierPerformanceEvaluationServices.addSupplierPerformance(supplierThree.getSupplierId(),quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        // all about Purchase order.
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        //all about contract.

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", supplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());


        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        assertThrows(IllegalArgumentException.class,()->evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(supplierOne.getSupplierCategory()));
        verify(evaluateSupplierForContractRenewalOutputBoundary,never()).presentRenewalContract(anyList());
    }
    @Test
    public void testEvaluateSupplierForContractRenewal_WhenNoContractFoundWithTheSupplierCategory_ThrowExpection(){
        // arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);


        // all about supplier
        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));


        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Software","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        when(supplierRepository.findBySupplierCategory(supplierTwo.getSupplierCategory())).thenReturn(List.of(supplierOne, supplierTwo, supplierThree));
        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));

        // adding performance to each supplier.
        SupplierPerformance result1 = new SupplierPerformance(supplierOne,quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        SupplierPerformance result2 = new SupplierPerformance(supplierTwo,quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        SupplierPerformance result3 = new SupplierPerformance(supplierThree,quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        when(supplierPerformanceRepository.findBySupplierId(result1.getSupplier().getSupplierId())).thenReturn(List.of(result1));
        when(supplierPerformanceRepository.findBySupplierId(result2.getSupplier().getSupplierId())).thenReturn(List.of(result2));
        when(supplierPerformanceRepository.findBySupplierId(result3.getSupplier().getSupplierId())).thenReturn(List.of(result3));

        when(supplierPerformanceRepository.findById(result1.getId())).thenReturn(Optional.of(result1));
        when(supplierPerformanceRepository.findById(result2.getId())).thenReturn(Optional.of(result2));
        when(supplierPerformanceRepository.findById(result3.getId())).thenReturn(Optional.of(result3));

        //all about purchase order.
        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        //all about contract.
        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),false,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());

        Contract contractTwo = new Contract("contractTwo", supplierOne,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),false,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),false,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(List.of(contractOne,contractTwo));
        when(contractRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(List.of(contractThree));
        when(contractRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Collections.emptyList());

        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        assertThrows(IllegalArgumentException.class,()->evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(supplierTwo.getSupplierCategory()));
        verify(evaluateSupplierForContractRenewalOutputBoundary,never()).presentRenewalContract(anyList());
    }
    @Test
    public void testEvaluateSupplierForContractRenewal_whenContractsAreNotRenewable_ReturnEmpty(){
        // arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition
        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);


        // all about supplier
        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);

        when(supplierRepository.findBySupplierCategory(supplierOne.getSupplierCategory())).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));

        // adding performance to each supplier.

        SupplierPerformance result1 = new SupplierPerformance(supplierOne,quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        result1.getSupplierPerformanceRate(); //result1.getSupplierPerformanceRate();
        SupplierPerformance result2 = new SupplierPerformance(supplierTwo,quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        result2.getSupplierPerformanceRate();
        SupplierPerformance result3 = new SupplierPerformance(supplierThree,quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));
        result3.getSupplierPerformanceRate();

        when(supplierPerformanceRepository.findBySupplierId(result1.getSupplier().getSupplierId())).thenReturn(List.of(result1));
        when(supplierPerformanceRepository.findBySupplierId(result2.getSupplier().getSupplierId())).thenReturn(List.of(result2));
        when(supplierPerformanceRepository.findBySupplierId(result3.getSupplier().getSupplierId())).thenReturn(List.of(result3));

        when(supplierPerformanceRepository.findById(result1.getId())).thenReturn(Optional.of(result1));
        when(supplierPerformanceRepository.findById(result2.getId())).thenReturn(Optional.of(result2));
        when(supplierPerformanceRepository.findById(result3.getId())).thenReturn(Optional.of(result3));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierOne,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),false,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());

        Contract contractTwo = new Contract("contractTwo", supplierTwo,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),false,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),false,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());

        when(contractRepository.findAll()).thenReturn(List.of(contractOne, contractTwo, contractThree));
        when(contractRepository.findBySupplierId(contractOne.getSupplier().getSupplierId())).thenReturn(List.of(contractOne, contractTwo, contractThree));
        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findBySupplierId(contractTwo.getSupplier().getSupplierId())).thenReturn(List.of(contractTwo));
        when(contractRepository.findBySupplierId(contractThree.getSupplier().getSupplierId())).thenReturn(List.of(contractThree));

        List<EvaluateSupplierForContractRenewalOutputDS> outputDSList = evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(supplierOne.getSupplierCategory());

        assertEquals(0, outputDSList.size());
        verify(evaluateSupplierForContractRenewalOutputBoundary,times(1)).presentRenewalContract(outputDSList);
    }
    @Test
    public void testEvaluateSupplierForRenewalContract_WhenNoRenewal_returnAsItIs(){
        // arrange
        supplierPerformanceEvaluationServices = new SupplierPerformanceEvaluationServices();
        storeAndTrackContractServices = new StoreAndTrackContractServices();
        evaluateSupplierForContractRenewalOutputBoundary = mock(EvaluateSupplierForContractRenewalOutputBoundary.class);
        evaluateSupplierForContractRenewalUseCase = new EvaluateSupplierForContractRenewalUseCase(supplierPerformanceEvaluationServices,supplierPerformanceRepository,supplierRepository,contractRepository,storeAndTrackContractServices,evaluateSupplierForContractRenewalOutputBoundary);

        // all about budget and department
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();
        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("John Doe", "john@example.com", "+251912345678", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);


        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        SupplierQuantitativePerformanceMetrics quantitativeMetrics = new SupplierQuantitativePerformanceMetrics(90, 5, 85, 92, 88, 95, 4);

        SupplierQualitativePerformanceMetrics qualitativeMetrics = new SupplierQualitativePerformanceMetrics(8, 9, 7, 9, 8, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsTwo = new SupplierQuantitativePerformanceMetrics(80, 15, 75, 82, 88, 85, 24);

        SupplierQualitativePerformanceMetrics qualitativeMetricsTwo = new SupplierQualitativePerformanceMetrics(8, 9, 9, 8, 10, 5);


        when(supplierRepository.findBySupplierCategory(supplierOne.getSupplierCategory())).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(supplierRepository.findBySupplierId(supplierOne.getSupplierId())).thenReturn(Optional.of(supplierOne));
        when(supplierRepository.findBySupplierId(supplierTwo.getSupplierId())).thenReturn(Optional.of(supplierTwo));
        when(supplierRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(Optional.of(supplierThree));

        // adding performance to each supplier.

        SupplierPerformance result1 = new SupplierPerformance(supplierOne,quantitativeMetrics, qualitativeMetrics,LocalDate.now().minusMonths(1));
        SupplierPerformance result2 = new SupplierPerformance(supplierTwo,quantitativeMetricsOne, qualitativeMetricsOne,LocalDate.now().minusMonths(1));
        SupplierPerformance result3 = new SupplierPerformance(supplierThree,quantitativeMetricsTwo, qualitativeMetricsTwo,LocalDate.now().minusMonths(1));

        when(supplierPerformanceRepository.findBySupplierId(result1.getSupplier().getSupplierId())).thenReturn(List.of(result1));
        when(supplierPerformanceRepository.findBySupplierId(result2.getSupplier().getSupplierId())).thenReturn(List.of(result2));
        when(supplierPerformanceRepository.findBySupplierId(result3.getSupplier().getSupplierId())).thenReturn(List.of(result3));

        when(supplierPerformanceRepository.findById(result1.getId())).thenReturn(Optional.of(result1));
        when(supplierPerformanceRepository.findById(result2.getId())).thenReturn(Optional.of(result2));
        when(supplierPerformanceRepository.findById(result3.getId())).thenReturn(Optional.of(result3));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);


        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        ContractFile fileOne = new ContractFile("attachementOne", "pdf", "/files/one.pdf", LocalDate.now());
        ContractFile fileTwo = new ContractFile("attachementTwo", "pdf", "/files/two.pdf", LocalDate.now());
        ContractFile fileThree = new ContractFile("attachementThree", "pdf", "/files/three.pdf", LocalDate.now());

        Contract contractOne = new Contract("contractOne", supplierThree,LocalDate.now().plusDays(2),LocalDate.now().plusMonths(3), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(10000.0),true,List.of(purchaseOrderOne),List.of(fileOne,fileTwo),LocalDate.now());
        Contract contractTwo = new Contract("contractTwo", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
        Contract contractThree = new Contract("contractThree", supplierThree,LocalDate.now().plusDays(3),LocalDate.now().plusMonths(5), NET_30,DeliveryTerms.FOB,BigDecimal.valueOf(130000.0),true,List.of(purchaseOrder),List.of(fileOne,fileTwo,fileThree),LocalDate.now());
;
        when(contractRepository.findAll()).thenReturn(List.of(contractOne,contractTwo,contractThree));
        when(contractRepository.findBySupplierId(supplierThree.getSupplierId())).thenReturn(List.of(contractOne,contractTwo,contractThree));

        when(contractRepository.findByContractId(contractOne.getContractId())).thenReturn(Optional.of(contractOne));
        when(contractRepository.findByContractId(contractTwo.getContractId())).thenReturn(Optional.of(contractTwo));
        when(contractRepository.findByContractId(contractThree.getContractId())).thenReturn(Optional.of(contractThree));

        List<EvaluateSupplierForContractRenewalOutputDS> outputDSList = evaluateSupplierForContractRenewalUseCase.evaluateSupplierforContractRenewal(supplierThree.getSupplierCategory());

        assertEquals(3,outputDSList.size());

        verify(evaluateSupplierForContractRenewalOutputBoundary,times(1)).presentRenewalContract(outputDSList);
    }

    // test for generating Monthly Approved Purchase Order report
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReportSuccessfully(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));

        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(purchaseOrderOne,purchaseOrder));
        when(supplierRepository.findAll()).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisitionOne,requisition));
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));
        List<OrderedItem> orderedItems = new ArrayList<>();
        orderedItems.addAll(purchaseOrderOne.getOrderedItems());
        orderedItems.addAll(purchaseOrder.getOrderedItems());

        GenerateMonthlyApprovedPurchaseOrderReportOutPutDS approvedPurchaseOrderReportOutPutDS = generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(reportOfficerInput);
        System.out.println(approvedPurchaseOrderReportOutPutDS);
        assertEquals(approvedPurchaseOrderReportOutPutDS.getReportTitle(), "Monthly report.");
        assertEquals(approvedPurchaseOrderReportOutPutDS.getReportDescription(), "MonthlyApprovedPurchaseOrderReport");
        assertEquals(approvedPurchaseOrderReportOutPutDS.getStartDate(),LocalDate.now().minusMonths(1));
        assertEquals(approvedPurchaseOrderReportOutPutDS.getEndDate(),LocalDate.now());

        verify(procurementReportRepository,times(1)).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,times(1)).generateMonthlyApprovedPurchaseOrderReport(approvedPurchaseOrderReportOutPutDS);
    }
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithnullInput_throwsException(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(purchaseOrderOne,purchaseOrder));
        when(supplierRepository.findAll()).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisitionOne,requisition));
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));

        assertThrows(IllegalArgumentException.class,()->generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(null));
        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,never()).generateMonthlyApprovedPurchaseOrderReport(any(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS.class));
    }
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithNoPurchaseOrder_throwsException(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of());
        when(supplierRepository.findAll()).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisitionOne,requisition));
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));

        assertThrows(IllegalArgumentException.class,()->generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(reportOfficerInput));
        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,never()).generateMonthlyApprovedPurchaseOrderReport(any(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS.class));

    }
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WithNoApprovedPurchaseOrder_throwsException(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));

        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(purchaseOrderOne,purchaseOrder));
        when(supplierRepository.findAll()).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisitionOne,requisition));
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));

        assertThrows(IllegalArgumentException.class,()->generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(reportOfficerInput));

        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,never()).generateMonthlyApprovedPurchaseOrderReport(any(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS.class));

    }
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WhenNOMatchedSupplier_throwsException(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(purchaseOrderOne,purchaseOrder));
        when(supplierRepository.findAll()).thenReturn(List.of());
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisitionOne,requisition));
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));

        assertThrows(IllegalArgumentException.class,()->generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(reportOfficerInput));
        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,never()).generateMonthlyApprovedPurchaseOrderReport(any(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS.class));

    }
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WhenNoMatchedRequisition_throwsException(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(purchaseOrderOne,purchaseOrder));
        when(supplierRepository.findAll()).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(departmentRepository.findAll()).thenReturn(List.of(department));
        when(requisitionRepository.findAll()).thenReturn(List.of());
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));

        assertThrows(IllegalArgumentException.class,()->generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(reportOfficerInput));
        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,never()).generateMonthlyApprovedPurchaseOrderReport(any(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS.class));

    }
    @Test
    public void testGenerateMonthlyApprovedPurchaseOrderReport_WhenNoDepartmentMatched_throwsException(){
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        generateMonthlyApprovedPurchaseOrderReportOutputBoundary = mock(GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary.class);
        generateMonthlyApprovedPurchaseOrderReportUseCase = new GenerateMonthlyApprovedPurchaseOrderReportUseCase(procurementReportRepository, purchaseOrderRepository,supplierRepository,requisitionRepository,departmentRepository,userRepository,procurementReportGenerationServices,generateMonthlyApprovedPurchaseOrderReportOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","developer");
        String title = "Monthly report.";
        String reportType = "MonthlyApprovedPurchaseOrderReport";
        GenerateMonthlyApprovedPurchaseOrderReportInputDS reportOfficerInput = new GenerateMonthlyApprovedPurchaseOrderReportInputDS(reporterContactDetail,title,reportType,LocalDate.now().minusMonths(1),LocalDate.now(), ProcurementActivity.PURCHASE_ORDER,LocalDate.now());
        Budget budget = new Budget(BigDecimal.valueOf(250000.0), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        budget.updateBudgetStatus();

        Department department = new Department("Software", "Software Dev", budget);
        CostCenter costCenter = new CostCenter("CC1", "IT Center");
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(190000.0));

        // all about requisition

        Inventory item = new Inventory("sn001", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new generations");
        Inventory item1 = new Inventory("sn002", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "Hp models");
        Inventory item2 = new Inventory("sn003", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        RequestedItem requestedItemOne = new RequestedItem(item,5);
        RequestedItem requestedItemTwo = new RequestedItem(item1, 2);

        User requester = new User("berhe","Berhe@outlook.com","+251900883478", department, "Developer");

        PurchaseRequisition requisition = new PurchaseRequisition("REQ001", LocalDate.now(), requester, department, costCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Project work");
        requisition.addItem(requestedItemOne);
        requisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ123",LocalDate.now(),requester,department, costCenter, PriorityLevel.HIGH,LocalDate.now().plusDays(20),"new department.");
        requisitionOne.addItem(requestedItemTwo);
        requisitionOne.setRequisitionStatus(RequisitionStatus.APPROVED);

        // all about supplier

        SupplierContactDetail supplierOneContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierTwoContactDetail = new SupplierContactDetail("MicroSoft","microsoft@gmail.com","+2510987654433","Dubai");
        SupplierContactDetail supplierThreeContactDetail = new SupplierContactDetail("Lenevo","lenvouser@gmail.com","++251978465232","Dubai");

        SupplierPaymentMethod supplierOnePaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierOneContactDetail.getFullName(),supplierOneContactDetail.getPhone(), List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD", BigDecimal.valueOf(10000.0));
        SupplierPaymentMethod supplierTwoPaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierTwoContactDetail.getFullName(), supplierTwoContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30,"USD",BigDecimal.valueOf(20000.0));
        SupplierPaymentMethod supplierThreePaymentMethod = new SupplierPaymentMethod("Lion International Bank","101293049950", supplierThreeContactDetail.getFullName(), supplierThreeContactDetail.getPhone(), List.of(CREDIT_CARD,CASH,PAYPAL),CASH, NET_30, "USD",BigDecimal.valueOf(20000.0));

        Supplier supplierOne = new Supplier( "IBM", "Electronics", "17636", "412434", supplierOneContactDetail, List.of(supplierOnePaymentMethod), List.of(item1,item2),LocalDate.now().minusMonths(5));

        Supplier supplierTwo = new Supplier("Microsoft","Electronics","21323","MS365",supplierTwoContactDetail,List.of(supplierTwoPaymentMethod), List.of(item,item2),LocalDate.now().minusMonths(5));

        Supplier supplierThree = new Supplier("Lenevo","Electronics","098594676","LN10232",supplierThreeContactDetail,List.of(supplierThreePaymentMethod) , List.of(item,item1,item2),LocalDate.now().minusMonths(5));


        PurchaseOrder purchaseOrderOne = new PurchaseOrder(department,List.of(requisitionOne),LocalDate.now(), supplierOne,"Air",LocalDate.now().plusDays(12));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);

        PurchaseOrder purchaseOrder = new PurchaseOrder(department, List.of(requisition), LocalDate.now(), supplierOne, "Air", LocalDate.now().plusMonths(2));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");

        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(purchaseOrderRepository.findAll()).thenReturn(List.of(purchaseOrderOne,purchaseOrder));
        when(supplierRepository.findAll()).thenReturn(List.of(supplierOne,supplierTwo,supplierThree));
        when(departmentRepository.findAll()).thenReturn(List.of());
        when(requisitionRepository.findAll()).thenReturn(List.of(requisitionOne,requisition));
        when(userRepository.findByEmail(reporterContactDetail.getEmail())).thenReturn(Optional.of(reporter));

        assertThrows(IllegalArgumentException.class,()->generateMonthlyApprovedPurchaseOrderReportUseCase.generateMonthlyApprovedPurchaseOrderReport(reportOfficerInput));
        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(generateMonthlyApprovedPurchaseOrderReportOutputBoundary,never()).generateMonthlyApprovedPurchaseOrderReport(any(GenerateMonthlyApprovedPurchaseOrderReportOutPutDS.class));

    }

    // this is the test for creating customizedProcurement Dashboard.
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WithValidInput_Successfully(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields());

        CreateCustomizedProcurementDashboardOutputDS outputDS = createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(inputDS);
        assertEquals(reportTemplate.getTemplateId(), outputDS.getDashboardId());

        assertEquals(2L, outputDS.getReportData().get("totalRequisitions"));

        assertEquals(BigDecimal.valueOf(20000.0).setScale(2), outputDS.getReportData().get("totalSpendingAmount"));

        assertEquals(Map.of(APPROVED,2L), outputDS.getReportData().get("requisitionsByStatus"));

        assertEquals(Map.of(department.getDepartmentId(),2L), outputDS.getReportData().get("requisitionsByDepartment"));

        assertEquals(Map.of(PriorityLevel.HIGH,2L), outputDS.getReportData().get("requisitionsByPriority"));

        assertEquals(1L, outputDS.getReportData().get("activeSupplier"));
        assertEquals(1L, outputDS.getReportData().get("totalSupplier"));

        assertEquals(List.of(ProcurementActivity.PURCHASE_REQUISITION, ProcurementActivity.SUPPLIER_MANAGEMENT), outputDS.getProcurementActivities());

        verify(reportTemplateRepository,times(1)).findBytemplateId(reportTemplate.getTemplateId());
        verify(createCustomizedProcurementDashboardOutPutBoundary,times(1)).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));
    }
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WithNullInput_throwsException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        assertThrows(IllegalArgumentException.class,()-> createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(null));

        verify(reportTemplateRepository,never()).save(any(ReportTemplate.class));
        verify(createCustomizedProcurementDashboardOutPutBoundary,never()).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));
    }
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WhenReporterNotExisted_throwExceptions(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.empty());
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields());

        assertThrows(IllegalArgumentException.class,()-> createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(inputDS));
        verify(reportTemplateRepository,never()).save(any(ReportTemplate.class));
        verify(createCustomizedProcurementDashboardOutPutBoundary,never()).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));

    }
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WhenReportTemplateNotExisted_createNewReportTemplate(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.ofNullable(null));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields());

        CreateCustomizedProcurementDashboardOutputDS outputDS = createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(inputDS);
        assertEquals(reportTemplate.getTemplateId(), outputDS.getDashboardId());
        assertEquals(2L, outputDS.getReportData().get("totalRequisitions"));
        assertEquals(BigDecimal.valueOf(20000.0).setScale(2), outputDS.getReportData().get("totalSpendingAmount"));
        assertEquals(Map.of(PriorityLevel.HIGH, 2L), outputDS.getReportData().get("requisitionsByPriority"));
        assertEquals(Map.of(department.getDepartmentId(), 2L), outputDS.getReportData().get("requisitionsByDepartment"));
        assertEquals(Map.of(APPROVED, 2L), outputDS.getReportData().get("requisitionsByStatus"));

        verify(reportTemplateRepository,times(1)).save(any(ReportTemplate.class));
        verify(createCustomizedProcurementDashboardOutPutBoundary,times(1)).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));
    }
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WhenReportTemplateExisted_updateReportTemplate(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByStatus","activeSupplier"),LocalDate.now(),user);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier","requisitionItems","suppliersByCategory","existedItems");
        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),selectedFields);

        CreateCustomizedProcurementDashboardOutputDS outputDS = createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(inputDS);

        assertEquals(reportTemplate.getTemplateId(), outputDS.getDashboardId());
        assertEquals(2L, outputDS.getReportData().get("totalRequisitions"));
        assertEquals(BigDecimal.valueOf(20000.0).setScale(2), outputDS.getReportData().get("totalSpendingAmount"));
        assertEquals(Map.of(PriorityLevel.HIGH, 2L), outputDS.getReportData().get("requisitionsByPriority"));
        assertEquals(Map.of(department.getDepartmentId(), 2L), outputDS.getReportData().get("requisitionsByDepartment"));
        assertEquals(Map.of(APPROVED, 2L), outputDS.getReportData().get("requisitionsByStatus"));
        assertEquals(1L, outputDS.getReportData().get("activeSupplier"));
        assertEquals(1L, outputDS.getReportData().get("totalSupplier"));

        verify(reportTemplateRepository,times(1)).update(any(ReportTemplate.class));
        verify(createCustomizedProcurementDashboardOutPutBoundary,times(1)).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));
    }
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WhennoPrcocurementActivities_throwException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByStatus","activeSupplier"),LocalDate.now(),user);

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier","requisitionItems","suppliersByCategory","existedItems");
        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));

        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),selectedFields);

        assertThrows(IllegalArgumentException.class, () -> createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(inputDS));
        verify(reportTemplateRepository,never()).save(any(ReportTemplate.class));
        verify(createCustomizedProcurementDashboardOutPutBoundary,never()).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));

    }
    @Test
    public void testCreateCustomizedProcurementPerformanceDashboard_WhenMismatchedActivitiesAndSelectedFields_throwException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        createCustomizedProcurementDashboardOutPutBoundary = mock(CreateCustomizedProcurementDashboardOutPutBoundary.class);
        createCustomizedProcurementDashboardUseCase = new CreateCustomizedProcurementDashboardUseCase(templateBasedReportRepository,reportTemplateRepository, supplierPerformanceRepository,paymentReconciliationRepository,deliveryReceiptRepository,invoiceRepository,contractRepository,purchaseOrderRepository,supplierRepository,requisitionRepository,userRepository,procurementReportCustomizationServices,createCustomizedProcurementDashboardOutPutBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        CreateCustomizedProcurementDashboardInputDS inputDS = new CreateCustomizedProcurementDashboardInputDS(reporterContactDetail,reportTemplate.getTemplateId(),reportTemplate.getTemplateName(), List.of(ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields());
        assertThrows(IllegalArgumentException.class, () -> createCustomizedProcurementDashboardUseCase.createCustomizedProcurementDashboard(inputDS));
        verify(reportTemplateRepository,never()).save(any(ReportTemplate.class));
        verify(createCustomizedProcurementDashboardOutPutBoundary,never()).presentCustomizedProcurementDashboard(any(CreateCustomizedProcurementDashboardOutputDS.class));

    }

    // test for exporting procurement report
    @Test
    public void testExportProcurementReport_WithValidInputDS_Successfully(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields(),"Monthly Report","Monthly procurement report");
        ExportedProcurementDataOutputDS outputDS = exportProcurementDataUseCase.exportProcurementData(inputDS);
        assertEquals("Monthly Report", outputDS.getReportTitle());
        assertEquals(FileFormat.PDF, outputDS.getFileFormat());
        assertEquals(reporter.getFullName(), outputDS.getExportedBy());

        verify(procurementReportRepository,times(1)).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,times(1)).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));
    }
    @Test
    public void testExportProcurementReport_WhenReportTemplateNotFound_AddNewReportTemplateSuccessfully(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.empty());
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields(),"Monthly Report","Monthly procurement report");
        ExportedProcurementDataOutputDS outputDS = exportProcurementDataUseCase.exportProcurementData(inputDS);
        verify(procurementReportRepository,times(1)).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,times(1)).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));
    }
    @Test
    public void testExportProcurementReport_WhenReportTemplateFoundButMismatchedSelectedFields_UpdateReportTemplateSuccessfully(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByStatus"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),selectedFields,"Monthly Report","Monthly procurement report");
        ExportedProcurementDataOutputDS outputDS = exportProcurementDataUseCase.exportProcurementData(inputDS);

        verify(procurementReportRepository,times(1)).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,times(1)).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));
    }
    @Test
    public void testExportProcurementReport_WhenNoProcurementActivitiesFound_ThrowException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));

        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields(),"Monthly Report","Monthly procurement report");
        assertThrows(IllegalArgumentException.class,()->exportProcurementDataUseCase.exportProcurementData(inputDS));
        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,never()).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));

    }
    @Test
    public void testExportProcurementReport_WhenMismatchedActivitiesAndSelectedFields_ThrowException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_ORDER,ProcurementActivity.SUPPLIER_PERFORMANCE),reportTemplate.getSelectedFields(),"Monthly Report","Monthly procurement report");
        assertThrows(IllegalArgumentException.class,()->exportProcurementDataUseCase.exportProcurementData(inputDS));

        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,never()).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));

    }
    @Test
    public void testExportProcurementReport_WithNullInputDS_ThrowException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);
        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.of(reporter));
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields(),"Monthly Report","Monthly procurement report");
        assertThrows(IllegalArgumentException.class,()->exportProcurementDataUseCase.exportProcurementData(null));

        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,never()).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));

    }
    @Test
    public void testExportProcurementReport_WhenReporterNotFound_ThrowException(){
        procurementReportCustomizationServices = new ProcurementReportCustomizationServices();
        procurementReportGenerationServices = new ProcurementReportGenerationServices();
        exportProcurementDataOutputBoundary = mock(ExportProcurementDataOutputBoundary.class);
        exportProcurementDataUseCase = new ExportProcurementDataUseCase(templateBasedReportRepository,procurementReportRepository,reportTemplateRepository,requisitionRepository,purchaseOrderRepository,supplierRepository,supplierPerformanceRepository,paymentReconciliationRepository,contractRepository,invoiceRepository,deliveryReceiptRepository,userRepository,procurementReportGenerationServices,procurementReportCustomizationServices,exportProcurementDataOutputBoundary);

        ReporterContactDetail reporterContactDetail = new ReporterContactDetail("Tekie","Tekie@outlook.com","+251909083478","Mekelle");

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
        User reporter = new User("Tekie","Tekie@outlook.com","+251909083478", department, "Developer");
        ReportTemplate reportTemplate = new ReportTemplate("template1", "template1", "template1",List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier"),LocalDate.now(),user);

        when(reportTemplateRepository.save(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(reportTemplateRepository.update(any(ReportTemplate.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(procurementReportRepository.save(any(ProcurementReport.class))).thenAnswer(invocation -> invocation.getArgument(0));

        when(userRepository.findByPhoneNumber(reporterContactDetail.getPhoneNumber())).thenReturn(Optional.empty());
        when(reportTemplateRepository.findBytemplateId(reportTemplate.getTemplateId())).thenReturn(Optional.of(reportTemplate));
        when(requisitionRepository.findAll()).thenReturn(List.of(requisition2,requisition));
        when(supplierRepository.findAll()).thenReturn(List.of(supplier));

        ExportProcurementDataInputDS inputDS = new ExportProcurementDataInputDS(reporterContactDetail,reportTemplate.getTemplateId(),LocalDate.now().minusMonths(1),LocalDate.now().plusMonths(1),FileFormat.PDF,List.of(ProcurementActivity.PURCHASE_REQUISITION,ProcurementActivity.SUPPLIER_MANAGEMENT),reportTemplate.getSelectedFields(),"Monthly Report","Monthly procurement report");
        assertThrows(IllegalArgumentException.class,()->exportProcurementDataUseCase.exportProcurementData(inputDS));

        verify(procurementReportRepository,never()).save(any(ProcurementReport.class));
        verify(exportProcurementDataOutputBoundary,never()).presentExportedProcurementData(any(ExportedProcurementDataOutputDS.class));
    }
}