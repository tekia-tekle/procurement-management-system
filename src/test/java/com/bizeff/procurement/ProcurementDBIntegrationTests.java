package com.bizeff.procurement;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.budgetandcostcontrol.BudgetRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.contractsmanagement.ContractRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.invoicepaymentreconciliation.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.*;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.*;
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
import com.bizeff.procurement.models.procurementreport.PurchaseRequisitionReport;
import com.bizeff.procurement.models.procurementreport.ReportTemplate;
import com.bizeff.procurement.models.procurementreport.SupplierReport;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.*;
import com.bizeff.procurement.models.supplymanagement.Inventory;
import com.bizeff.procurement.models.supplymanagement.Supplier;
import com.bizeff.procurement.models.supplymanagement.SupplierContactDetail;
import com.bizeff.procurement.models.supplymanagement.SupplierPaymentMethod;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierPerformance;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQualitativePerformanceMetrics;
import com.bizeff.procurement.models.supplyperformancemanagement.SupplierQuantitativePerformanceMetrics;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.models.enums.PaymentMethodType.*;
import static com.bizeff.procurement.models.enums.PaymentTerms.NET_30;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProcurementApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
public class ProcurementDBIntegrationTests {
    @Autowired
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RequestedItemRepository requestedItemRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private CostCenterRepository costCenterRepository;
    @Autowired
    private BudgetRepository budgetRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    private ContractRepository contractRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoicedItemRepository invoicedItemRepository;
    @Autowired private DeliveryReceiptRepository deliveryReceiptRepository;
    @Autowired private DeliveredItemRepository deliveredItemRepository;
    @Autowired private PaymentReconciliationRepository paymentReconciliationRepository;
    @Autowired private SupplierPerformanceRepository supplierPerformanceRepository;

    @Autowired private ProcurementReportRepository procurementReportRepository;
    @Autowired private ReportTemplateRepository reportTemplateRepository;
    @Autowired private SupplierPerformanceReportRepository supplierPerformanceReportRepository;
    @Autowired private PurchaseOrderReportRepository purchaseOrderReportRepository;
    @Autowired private ContractReportRepository contractReportRepository;
    @Autowired private InvoiceReportRepository invoiceReportRepository;
    @Autowired private DeliveryReceiptReportRepository deliveryReceiptReportRepository;
    @Autowired private PaymentReconciliationReportRepository paymentReconciliationReportRepository;
    @Autowired private PurchaseRequisitionReportRepository purchaseRequisitionReportRepository;
    @Autowired private SupplierReportRepository supplierReportRepository;


    @BeforeEach
    void SetUp(){
        budgetRepository.deleteAll();
        departmentRepository.deleteAll();
        costCenterRepository.deleteAll();
        inventoryRepository.deleteAll();
        purchaseRequisitionRepository.deleteAll();
        userRepository.deleteAll();
        supplierRepository.deleteAll();
        purchaseOrderRepository.deleteAll();
        contractRepository.deleteAll();
        invoiceRepository.deleteAll();
        deliveryReceiptRepository.deleteAll();
        paymentReconciliationRepository.deleteAll();
        supplierPerformanceRepository.deleteAll();
        procurementReportRepository.deleteAll();
        reportTemplateRepository.deleteAll();
        supplierPerformanceReportRepository.deleteAll();
        purchaseOrderReportRepository.deleteAll();
        contractReportRepository.deleteAll();
        invoiceReportRepository.deleteAll();
        deliveryReceiptReportRepository.deleteAll();
        paymentReconciliationReportRepository.deleteAll();
        purchaseRequisitionReportRepository.deleteAll();
        supplierReportRepository.deleteAll();

    }

    /** save and find budget, */
    @Test
    public void testSaveAndFindBudget_Successfully(){
        Budget budget = new Budget();
        budget.setBudgetId("BG001");
        budget.setTotalBudget( BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.updateBudgetStatus();
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        Budget savedBudget = budgetRepository.save(budget);

        Optional<Budget> foundBudget = budgetRepository.findByBudgetId(savedBudget.getBudgetId());
        assertTrue(foundBudget.isPresent());
    }
    /** Try to save with duplication of budget id. */
    @Test
    public void testSaveBudget_WithDuplicateBudgetId_throwExeption(){
        Budget budget = new Budget();

        budget.setBudgetId("BG006");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        budgetRepository.save(budget);

        Budget budget1 = new Budget();
        budget1.setBudgetId("BG006");  // <-------- budget with budgetId:BG006 is already is existed before. we can't duplicate budget id.
        budget1.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget1.setCurrency("USD");
        budget1.setStartDate(LocalDate.now());
        budget1.setEndDate(LocalDate.now().plusMonths(1));
        budget1.setTotalRemainingBudget(budget1.getTotalBudget());
        budget1.updateBudgetStatus();

        assertThrows(DataIntegrityViolationException.class,()->budgetRepository.save(budget1));
    }

    /** Update Budget. */
    @Test
    public void testUpdateBudget_Successfully(){
        Budget budget = new Budget();
        budget.setBudgetId("BG002");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        savedBudget.setTotalBudget(BigDecimal.valueOf(15000.00));
        savedBudget.setCurrency("EUR");
        savedBudget.setStartDate(LocalDate.now().minusDays(5));
        savedBudget.setEndDate(LocalDate.now().plusMonths(2));
        savedBudget.updateBudgetStatus();

        Budget updatedBudget = budgetRepository.update(savedBudget);

        assertEquals(BigDecimal.valueOf(15000.00), updatedBudget.getTotalBudget());
        assertEquals("EUR", updatedBudget.getCurrency());
    }

    /** Try to update budget with non existed budget id. */
    @Test
    public void testUpdateBudget_WithNonExistedBudgetId_throwExeption(){
        Budget budget = new Budget();
        budget.setBudgetId("BG006");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();
        assertThrows(InvalidDataAccessApiUsageException.class,()->budgetRepository.update(budget));
    }

    /** Delete Budget. */
    @Test
    public void testDeleteBudget_Successfully(){

        Budget budget = new Budget();
        budget.setBudgetId("BG00");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        budgetRepository.deleteByBudgetId(savedBudget.getBudgetId());
        Optional<Budget> foundBudget = budgetRepository.findByBudgetId(savedBudget.getBudgetId());

        assertTrue(foundBudget.isEmpty());

    }

    /** Getting all saved Budgets. */
    @Test
    public void testGettingBudgetByStatus_Successfully(){
        Budget budget = new Budget();

        budget.setBudgetId("BG00");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.setBudgetStatus(BudgetStatus.ACTIVE);

        budgetRepository.save(budget);

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG003");
        budgetOne.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetOne.setCurrency("USD");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budget.getTotalBudget());
        budgetOne.setBudgetStatus(BudgetStatus.ACTIVE);

        budgetRepository.save(budgetOne);

        /** Budget two.*/
        Budget budgetTwo = new Budget();

        budgetTwo.setBudgetId("BG004");
        budgetTwo.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetTwo.setCurrency("USD");
        budgetTwo.setStartDate(LocalDate.now());
        budgetTwo.setEndDate(LocalDate.now().plusMonths(1));
        budgetTwo.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetTwo.setBudgetStatus(BudgetStatus.INACTIVE);

        budgetRepository.save(budgetTwo);

        List<Budget> savedBudgets = budgetRepository.findAll();

        assertEquals(3,savedBudgets.size());

        List<Budget> activeBudgets = budgetRepository.findByStatus(BudgetStatus.ACTIVE);
        assertEquals(2, activeBudgets.size());
    }

    /** Get budget by status. */
    @Test
    public void testFindAllBudget_Successfully(){
        Budget budget = new Budget();
        budget.setBudgetId("BG003");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        budgetRepository.save(budget);

        /** Budget two.*/
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG004");
        budgetOne.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetOne.setCurrency("USD");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        budgetRepository.save(budgetOne);

        List<Budget> savedBudgets = budgetRepository.findAll();
        assertEquals(2, savedBudgets.size());
    }

    /** try to save budget with null total budget. */
    @Test
    public void testSaveBudgetWithnullTotalBudget_throwException(){

        Budget budget = new Budget();

        budget.setBudgetId("BG005");
        budget.setTotalBudget(null);
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.updateBudgetStatus();

        assertThrows(DataIntegrityViolationException.class,()->budgetRepository.save(budget));
    }

    /** try to save budget with negative total budget. */
    @Test
    public void testSaveBudgetWithNegativeTotalBudget_throwException(){

        Budget budget = new Budget();

        budget.setBudgetId("BG006");
        budget.setTotalBudget(BigDecimal.valueOf(-10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.updateBudgetStatus();

        assertThrows(ConstraintViolationException.class,()->budgetRepository.save(budget));

    }
    @Test
    public void testSaveBudget_withNullBudgetId_throwException(){

        Budget budget = new Budget();

        budget.setBudgetId(null);
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        assertThrows(ConstraintViolationException.class,()->budgetRepository.save(budget));

    }

    @Test
    public void testSaveBudget_withBlankBudgetId_throwException(){
        Budget budget = new Budget();

        budget.setBudgetId(" ");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.updateBudgetStatus();

        assertThrows(ConstraintViolationException.class,()->budgetRepository.save(budget));
    }
    /** try to save budget whem start date of the budget is after end date. */
    @Test
    public void testSaveBudget_whenStartDateAfterEndDate_throwException(){

        Budget budget = new Budget();

        budget.setBudgetId("BG007");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().minusDays(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        assertThrows(ConstraintViolationException.class,()->budgetRepository.save(budget));

    }

    /** try to get budget with non existed id. */
    @Test
    public void testGetbudget_WithNonExistedId_throwempty(){

        Budget budget = new Budget();

        budget.setBudgetId("BG008");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.updateBudgetStatus();
        String budgetId = IdGenerator.generateId("BG");

        Optional<Budget>foundBudget = budgetRepository.findByBudgetId(budgetId);
        assertTrue(foundBudget.isEmpty());
    }
    @Test
    public void testSaveDepartmentAndFindByID_Successfully(){

        Budget budget = new Budget();

        budget.setBudgetId("BG009");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department();
        department.setDepartmentId("DI001");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);

        Department savedDepartment = departmentRepository.save(department);

        Optional<Department> existedDepartment = departmentRepository.findByDepartmentId(savedDepartment.getDepartmentId()); // <--- finding by department id.

        assertEquals(savedDepartment.getDepartmentId(), existedDepartment.get().getDepartmentId());
    }

    /** try to save department with duplicate department id. */
    @Test
    public void testSaveDepartment_WithDuplicateDeparmtentId_throwExeption(){
        Budget budget = new Budget();
        budget.setBudgetId("BG009");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        Budget budgetOne = new Budget();

        budgetOne.setBudgetId("BG100");
        budgetOne.setTotalBudget(BigDecimal.valueOf(15000.00));
        budgetOne.setCurrency("Birr");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudgetOne = budgetRepository.save(budgetOne);


        Department department = new Department();
        department.setDepartmentId("DI001");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        departmentRepository.save(department);

        Department departmentOne = new Department();
        departmentOne.setDepartmentId("DI001");
        departmentOne.setName("Software Development");
        departmentOne.setDescription("we are new department in malam Engineering plc.");
        departmentOne.setBudget(savedBudgetOne);
        assertThrows(DataIntegrityViolationException.class, ()->departmentRepository.save(departmentOne));

    }

    /** test for creating two differente department with same budget. */
    @Test
    public void testsSaveDifferentDepartment_WithSameBudget_throwException(){
        Budget budgetOne = new Budget();

        budgetOne.setBudgetId("BG100");
        budgetOne.setTotalBudget(BigDecimal.valueOf(15000.00));
        budgetOne.setCurrency("Birr");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudgetOne = budgetRepository.save(budgetOne);


        Department department = new Department();
        department.setDepartmentId("DI001");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudgetOne);
        departmentRepository.save(department);

        Department departmentOne = new Department();
        departmentOne.setDepartmentId("DI002");
        departmentOne.setName("Software Development");
        departmentOne.setDescription("we are new department in malam Engineering plc.");
        departmentOne.setBudget(savedBudgetOne);
        assertThrows(DataIntegrityViolationException.class, ()->departmentRepository.save(departmentOne));
    }
    @Test
    public void testDeleteDepartment_Successfully(){
        Budget budget = new Budget();
        budget.setBudgetId("BG100");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department();
        department.setDepartmentId("DI002");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);

        Department savedDepartment = departmentRepository.save(department);

        departmentRepository.deleteByDepartmentId(savedDepartment.getDepartmentId());

        Optional<Department> existedDepartment = departmentRepository.findByDepartmentId(savedDepartment.getDepartmentId());
        assertTrue(existedDepartment.isEmpty());
    }

    @Test
    public void testFindAllDepartments_Successfully(){

        Budget budget = new Budget();
        budget.setBudgetId("BG101");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        /** Budget two.*/
        Budget budgetOne = new Budget();

        budgetOne.setBudgetId("BG102");
        budgetOne.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetOne.setCurrency("USD");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget1 = budgetRepository.save(budgetOne);

        Department departmentOne = new Department();
        departmentOne.setDepartmentId("DI003");
        departmentOne.setName("Software Development");
        departmentOne.setDescription("we are new department in malam Engineering plc.");
        departmentOne.setBudget(savedBudget1);

        Department departmentTwo = new Department();
        departmentTwo.setDepartmentId("DI004");
        departmentTwo.setName("Software Development");
        departmentTwo.setDescription("we are new department in malam Engineering plc.");
        departmentTwo.setBudget(savedBudget);

        departmentRepository.save(departmentOne);
        departmentRepository.save(departmentTwo);

        List<Department> departmentList = departmentRepository.findAll();
        assertEquals(2, departmentList.size());
    }

    /** try to update department with valid data. */
    @Test
    public void testUpdateDepartment_Successfully(){

        Budget budget = new Budget();
        budget.setBudgetId("BG101");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department();
        department.setDepartmentId("DI001");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG102");
        budgetOne.setTotalBudget(BigDecimal.valueOf(100000.00));
        budgetOne.setCurrency("EUR");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(3));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudgetOne = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        // Update the department details
        savedDepartment.setName("Software Development Team");
        savedDepartment.setDescription("we are new department in malam Engineering plc. we are team of software developers.");
        savedDepartment.setBudget(savedBudgetOne);
        savedDepartment.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(50000.00));
        Department updatedDepartment = departmentRepository.update(savedDepartment);
        assertEquals("Software Development Team", updatedDepartment.getName());
        assertEquals("we are new department in malam Engineering plc. we are team of software developers.", updatedDepartment.getDescription());
    }

    @Test
    public void testUpdateDepartment_WithNonExistedId_ThrowException(){
        Budget budget = new Budget();
        budget.setBudgetId("BG101");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budget);

        Department department = new Department();
        department.setDepartmentId("DI001");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);

        assertThrows(InvalidDataAccessApiUsageException.class, ()-> departmentRepository.update(department));
    }

    /** try to save Department with missing Elements.*/
    @Test
    public void testSaveDepartment_withNullBudget_throwException(){

        Department department = new Department();
        department.setDepartmentId("DI005");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(null);

        assertThrows(DataIntegrityViolationException.class,()->departmentRepository.save(department));
    }
    @Test
    public void testGetDepartment_WithNonExistedId_ThrowException(){

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG103");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();

        department.setDepartmentId("DI006");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);

        departmentRepository.save(department);

        String departmentId = IdGenerator.generateId("DP");

        Optional<Department> foundDepartment = departmentRepository.findByDepartmentId(departmentId);
        assertTrue(foundDepartment.isEmpty());
    }
    @Test
    public void testGetDepartments_WhenemptyRepository_returnEcxception(){
        List<Department> totalDepartments = departmentRepository.findAll();
        assertTrue(totalDepartments.isEmpty());
    }

    /** this is the testing for User  classes. */
    @Test
    public void testSaveUserAndFindByUserID_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Optional<User> foundUser = userRepository.findByUserId(savedUser.getUserId());
        assertTrue(foundUser.isPresent());
        assertEquals("UR001", foundUser.get().getUserId());
        assertEquals(requester.getFullName(), foundUser.get().getFullName());
        assertEquals(requester.getEmail(), foundUser.get().getEmail());
    }

    /** try to save with invalid email address. */
    @Test
    public void testSaveUser_WithInvalideEmail_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        assertThrows(ConstraintViolationException.class,()->userRepository.save(requester));
    }

    /** try to save user with invalid phone regular expression. */
    @Test
    public void testSaveUser_WithInvalidePhone_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("0979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        assertThrows(ConstraintViolationException.class,()->userRepository.save(requester));
    }

    /** try to save same user with in different department. */
    @Test
    public void testSaveSameUser_InDifferentDepartment_throwException(){

        Budget budget = new Budget();
        budget.setBudgetId("BG101");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        /** Budget two.*/
        Budget budgetOne = new Budget();

        budgetOne.setBudgetId("BG102");
        budgetOne.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetOne.setCurrency("USD");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget1 = budgetRepository.save(budgetOne);

        Department departmentOne = new Department();
        departmentOne.setDepartmentId("DI003");
        departmentOne.setName("Software Development");
        departmentOne.setDescription("we are new department in malam Engineering plc.");
        departmentOne.setBudget(savedBudget1);

        Department departmentTwo = new Department();
        departmentTwo.setDepartmentId("DI004");
        departmentTwo.setName("Software Development");
        departmentTwo.setDescription("we are new department in malam Engineering plc.");
        departmentTwo.setBudget(savedBudget);

        Department department1 = departmentRepository.save(departmentOne);
        Department department2 = departmentRepository.save(departmentTwo);
        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(department1);
        requester.setRole("developer");
        userRepository.save(requester);

        User user = new User();
        user.setUserId("UR001");
        user.setFullName("Tekia Tekle");
        user.setEmail("tekia2034@gmail.com");
        user.setPhoneNumber("+251979417636");
        user.setDepartment(department2);
        user.setRole("developer");

        assertThrows(DataIntegrityViolationException.class,()-> userRepository.save(user));
    }

    /** try to save duplicate user id in the same department. */
    @Test
    public void testSaveUser_WithDuplicateUserId_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG102");
        budgetOne.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetOne.setCurrency("USD");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget1 = budgetRepository.save(budgetOne);

        Department departmentTwo = new Department();
        departmentTwo.setDepartmentId("DI004");
        departmentTwo.setName("Software Development");
        departmentTwo.setDescription("we are new department in malam Engineering plc.");
        departmentTwo.setBudget(savedBudget1);

        Department department2 = departmentRepository.save(departmentTwo);
        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(department2);
        requester.setRole("developer");
        userRepository.save(requester);

        User user = new User();
        user.setUserId("UR001");
        user.setFullName("mulu Tekle");
        user.setEmail("tekia2021@gmail.com");
        user.setPhoneNumber("+2519794173236");
        user.setDepartment(department2);
        user.setRole("developer");

        assertThrows(DataIntegrityViolationException.class,()->userRepository.save(user));
    }

    /** getting User ByEmail Address. */
    @Test
    public void testFindingUserByEmail_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());
        assertTrue(foundUser.isPresent());
    }
    @Test
    public void testFindingUserByPhoneNumber_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Optional<User> foundUser = userRepository.findByPhoneNumber(savedUser.getPhoneNumber());
        assertTrue(foundUser.isPresent());
    }

    @Test
    public void testDeleteUserById_Successfully() {
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        userRepository.deleteByUserId(savedUser.getUserId());

        Optional<User> foundUser = userRepository.findByUserId(savedUser.getUserId());
        assertTrue(foundUser.isEmpty());
    }
    @Test
    public void testDeleteUserByEmail_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);
        userRepository.deleteByEmail(savedUser.getEmail());

        Optional<User> foundUser = userRepository.findByEmail(savedUser.getEmail());
        assertTrue(foundUser.isEmpty());
    }
    @Test
    public void testDeleteUserByPhoneNumber_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        userRepository.deleteByPhoneNumber(savedUser.getPhoneNumber());
        Optional<User> foundUser = userRepository.findByPhoneNumber(savedUser.getPhoneNumber());
        assertTrue(foundUser.isEmpty());
    }

    /** finding users that are in the same department. */
    @Test
    @Rollback(value = false)
    public void testFindingUsers_withSameDepartment_Successfully(){
        Budget budget = new Budget();
        budget.setBudgetId("BG101");
        budget.setTotalBudget(BigDecimal.valueOf(10000.00));
        budget.setCurrency("USD");
        budget.setStartDate(LocalDate.now());
        budget.setEndDate(LocalDate.now().plusMonths(1));
        budget.setTotalRemainingBudget(budget.getTotalBudget());
        budget.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budget);

        /** Budget two.*/
        Budget budgetOne = new Budget();

        budgetOne.setBudgetId("BG102");
        budgetOne.setTotalBudget(BigDecimal.valueOf(10000.00));
        budgetOne.setCurrency("USD");
        budgetOne.setStartDate(LocalDate.now());
        budgetOne.setEndDate(LocalDate.now().plusMonths(1));
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget1 = budgetRepository.save(budgetOne);

        Department departmentOne = new Department();
        departmentOne.setDepartmentId("DI003");
        departmentOne.setName("Software Development");
        departmentOne.setDescription("we are new department in malam Engineering plc.");
        departmentOne.setBudget(savedBudget1);

        Department departmentTwo = new Department();
        departmentTwo.setDepartmentId("DI004");
        departmentTwo.setName("Software Development");
        departmentTwo.setDescription("we are new department in malam Engineering plc.");
        departmentTwo.setBudget(savedBudget);

        Department department1 = departmentRepository.save(departmentOne);
        Department department2 = departmentRepository.save(departmentTwo);
        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(department1);
        requester.setRole("developer");
        userRepository.save(requester);

        User user = new User();
        user.setUserId("UR002");
        user.setFullName("mulu mulu");
        user.setEmail("tekia2043@gmail.com");
        user.setPhoneNumber("+251979417633");
        user.setDepartment(department2);
        user.setRole("developer");
        userRepository.save(user);

        List<User> totalUser = userRepository.findAll();
        assertEquals(2,totalUser.size()); // <--------------- total user lists.

        List<User> userInSameDepartment = userRepository.findByDepartmentId(department1.getDepartmentId());
        assertEquals(1, userInSameDepartment.size()); // <------------ user in the same department.
    }

    /** Getting the total user that are saved before. */
    @Test
    public void testGetTotalUsers_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");

        User requesterOne = new User();
        requesterOne.setUserId("UR002");
        requesterOne.setFullName("Tekle Tekia");
        requesterOne.setEmail("tekia@gmail.com");
        requesterOne.setPhoneNumber("+251979416763");
        requesterOne.setDepartment(savedDepartment);
        requesterOne.setRole("developer");
        userRepository.save(requesterOne);
        userRepository.save(requester);

        List<User> savedUsers = userRepository.findAll();
        assertEquals(2,savedUsers.size());
    }
    @Test
    public void testGetUser_WithNonExistedId_returnEmpty(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        userRepository.save(requester);

        Optional<User> foundUser = userRepository.findByUserId("UR1");
        assertTrue(foundUser.isEmpty());
    }
    @Test
    public void testGetUser_WithNonExistedEmail_returnEmpty(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        userRepository.save(requester);

        Optional<User> foundUser = userRepository.findByEmail("tekia@gmail.com");
        assertTrue(foundUser.isEmpty());
    }
    @Test
    public void testGetUser_WithNonExistedPhoneNumber_returnEmpty(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG14");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI7");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        userRepository.save(requester);

        Optional<User> foundUser = userRepository.findByPhoneNumber("+251979417663");
        assertTrue(foundUser.isEmpty());
    }
    @Test
    public void testSaveCostCenterAndFindByID_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        departmentRepository.save(department);

        Optional<CostCenter> foundCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId());
        assertTrue(foundCostCenter.isPresent());
    }

    /** test Update user detail successfully. */
    @Test
    public void testUpdateUser_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");   
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        savedUser.setFullName("Zenebe Tekle");
        savedUser.setEmail("zenebetekle@gmail.com");
        savedUser.setPhoneNumber("+251979417636");
        savedUser.setDepartment(savedDepartment);
        savedUser.setRole("sales");
        userRepository.update(savedUser);

        Optional<User> foundUser = userRepository.findByUserId(savedUser.getUserId());
        assertTrue(foundUser.isPresent());

        assertEquals("Zenebe Tekle", foundUser.get().getFullName());
        assertEquals("zenebetekle@gmail.com", foundUser.get().getEmail());
        assertEquals("+251979417636", foundUser.get().getPhoneNumber());
        assertEquals("sales", foundUser.get().getRole());

    }

    /** trye to test update user with non existed id. */
    @Test
    public void testUpdateUser_WithNonExistedId_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budgetOne);

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");

        assertThrows(InvalidDataAccessApiUsageException.class, ()-> userRepository.update(requester));
    }

    /** test costCenter saving successfully. */
    @Test
    public void testSaveCostCenter_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        departmentRepository.save(department);

        Optional<CostCenter> foundCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId());
        assertTrue(foundCostCenter.isPresent());
    }

    /** test when try to save with duplicate costCenter Id. */
    @Test
    public void testSaveCostCenter_WithDuplicateCostCenterId_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);
        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        CostCenter newCostCenter = new CostCenter();
        newCostCenter.setCostCenterId("CC001");
        newCostCenter.setName("new procurement");
        newCostCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        departmentRepository.save(department);

        // Attempting to save a CostCenter with a duplicate ID should throw an exception
        newCostCenter.setDepartment(department);


        assertThrows(DataIntegrityViolationException.class, ()-> costCenterRepository.save(newCostCenter));
    }

    /** finding CostCenters by departmentId. */
    @Test
    public void testFindCostCenters_ByDepartment_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalRemainingBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        Budget budget = new Budget();

        budget.setBudgetId("BG105");
        budget.setTotalBudget(BigDecimal.valueOf(20000.0));
        budget.setStartDate(LocalDate.of(2025, 1, 1));
        budget.setEndDate(LocalDate.of(2025,10,31));
        budget.setCurrency("USD");

        budget.setTotalRemainingBudget(budget.getTotalRemainingBudget());
        budget.updateBudgetStatus();
        Budget savedBudget1 = budgetRepository.save(budget);


        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CCC01");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        CostCenter costCenterOne = new CostCenter();
        costCenterOne.setCostCenterId("CC001");
        costCenterOne.setName("Procurement");
        costCenterOne.setDescription("Manages acquisitions");

        CostCenter costCenterTwo = new CostCenter();
        costCenterTwo.setCostCenterId("CC011");
        costCenterTwo.setName("Procurement");
        costCenterTwo.setDescription("Manages acquisitions");


        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenterOne,BigDecimal.valueOf(10000.0));
        department.allocateBudgetToCostCenter(costCenterTwo,BigDecimal.valueOf(5000.0));
        Department savedDepartment = departmentRepository.save(department);

        Department department1 = new Department();

        department1.setDepartmentId("DI008");
        department1.setName("Networking");
        department1.setDescription("giving Networking Facility.");
        department1.setBudget(savedBudget1);
        department1.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(1000.0));
        departmentRepository.save(department1);

        List<CostCenter> totalCostCenterList = costCenterRepository.findAll();
        assertEquals(3, totalCostCenterList.size());

        List<CostCenter> costCentersWithSameDepartmentModel = costCenterRepository.findByDepartmentId(savedDepartment.getDepartmentId());
        assertEquals(2, costCentersWithSameDepartmentModel.size());
    }
    @Test
    public void testDeleteCostCenter_WithValidId_Successfully(){
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
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();
        departmentRepository.deleteByDepartmentId(department.getDepartmentId());
        costCenterRepository.deleteByCostCenterId(savedCostCenter.getCostCenterId());

        Optional<CostCenter> foundCostCenter = costCenterRepository.findByCostCenterId(savedCostCenter.getCostCenterId());
        assertTrue(foundCostCenter.isEmpty());
    }
    @Test
    public void testGetAllCostCenter_Successfully(){
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
        costCenter.setCostCenterId("CCC01");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        CostCenter costCenterOne = new CostCenter();
        costCenterOne.setCostCenterId("CC-001");
        costCenterOne.setName("Procurement");
        costCenterOne.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenterOne,BigDecimal.valueOf(10000.0));
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(5000.0));
        departmentRepository.save(department);

        List<CostCenter> foundCostCenters = costCenterRepository.findAll();
        assertEquals(2, foundCostCenters.size());
    }

    /** test for updating cost center with valid data. */
    @Test
    public void testUpdateCostCenter_Successfully(){
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
        costCenter.setCostCenterId("CCC01");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(5000.0));
        Department savedDepartment = departmentRepository.save(department);

        Optional<CostCenter> foundCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId());

        CostCenter savedCostCenter = foundCostCenter.get();
        savedCostCenter.setName("Updated Procurement");
        savedCostCenter.setDescription("Updated Manages acquisitions");
        savedCostCenter.setDepartment(savedDepartment);

        costCenterRepository.update(savedCostCenter);

        Optional<CostCenter> updatedCostCenter = costCenterRepository.findByCostCenterId(savedCostCenter.getCostCenterId());
        assertEquals("Updated Procurement", updatedCostCenter.get().getName());
        assertEquals("Updated Manages acquisitions", updatedCostCenter.get().getDescription());


    }

    /** test updating non-existed costCenter .*/
    @Test
    public void testUpdateCostCenter_WithNonExistedId_throwException(){
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
        costCenter.setCostCenterId("CCC01");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        CostCenter costCenterOne = new CostCenter();
        costCenterOne.setCostCenterId("CC-001");
        costCenterOne.setName("Procurement");
        costCenterOne.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenterOne,BigDecimal.valueOf(10000.0));
        departmentRepository.save(department);

        assertThrows(IllegalArgumentException.class, ()-> costCenterRepository.update(costCenter));
    }

    /** this is the negative test for the cost center entity. */
    @Test
    public void testGetCostCenter_WithNonExistedId_returnEmpty(){
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
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI-101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        departmentRepository.save(department);

        Optional<CostCenter> foundCostCenter = costCenterRepository.findByCostCenterId("CCC101");
        assertTrue(foundCostCenter.isEmpty());
    }
    @Test
    public void testGetAllCostCenters_WithEmptyRepository_returnEmpty(){
        List<CostCenter> savedCostCenters = costCenterRepository.findAll();
        assertTrue(savedCostCenters.isEmpty());
    }
    /** this is the test for the inventory testing. */
    @Test
    public void testSaveInventoryAndFindById_Successfully(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Optional<Inventory> foundInventory = inventoryRepository.findByItemIdAndSupplierId(savedInventory.getItemId(), savedSupplier.getId());
        assertTrue(foundInventory.isPresent());
    }
    @Test
    public void testSaveInventoryWithDuplicateId_ThrowException(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-1");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Inventory item2 = new Inventory();
        item2.setItemId(item.getItemId());
        item2.setItemName("Laptop");
        item2.setQuantityAvailable(3);
        item2.setUnitPrice(BigDecimal.valueOf(10000.0));
        item2.setItemCategory("Electronics");
        item2.setExpiryDate(LocalDate.now().plusYears(2));
        item2.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,item2), LocalDate.now());

        assertThrows(DataIntegrityViolationException.class,()-> supplierRepository.save(supplier));
    }
    @Test
    public void testSaveInventoryWithNullId_ThrowException(){
        Inventory item = new Inventory();
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");
        assertThrows(DataIntegrityViolationException.class,()-> inventoryRepository.save(item));
    }
    @Test
    public void testSaveInventoryWithNegativeQuantity_ThrowException(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-1");
        item.setItemName("Laptop");
        item.setQuantityAvailable(-3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");
        assertThrows(ConstraintViolationException.class,()-> inventoryRepository.save(item));
    }

    @Test
    public void testSaveInventoryWithNegativeUnitPrice_ThrowException(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-1");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(-10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");
        assertThrows(ConstraintViolationException.class,()-> inventoryRepository.save(item));
    }
    @Test
    public void testSaveInventoryWithNullUnitPrice_ThrowException(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-1");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(null);
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");
        assertThrows(DataIntegrityViolationException.class,()-> inventoryRepository.save(item));
    }
    @Test
    public void testSaveExpiredInventory_throwException(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-1");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().minusDays(1));
        item.setSpecification("Intel Core i7");
        assertThrows(ConstraintViolationException.class,()-> inventoryRepository.save(item));
    }
    @Test
    public void testDeleteInventory_WithExistedId_Successfully(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);
        supplierRepository.deleteBySupplierId(savedSupplier.getSupplierId());
        inventoryRepository.deleteByItemId(item.getItemId());
        Optional<Inventory> foundInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId());
        assertTrue(foundInventory.isEmpty());
    }
    @Test
    public void testGetAllSavedInventory_Successfully(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-00211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Inventory itemOne = new Inventory();
        itemOne.setItemId("ITEM-002");
        itemOne.setItemName("Laptop");
        itemOne.setQuantityAvailable(3);
        itemOne.setUnitPrice(BigDecimal.valueOf(10000.0));
        itemOne.setItemCategory("Electronics");
        itemOne.setExpiryDate(LocalDate.now().plusYears(2));
        itemOne.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,itemOne), LocalDate.now());
        supplierRepository.save(supplier);

        List<Inventory> savedInventories = inventoryRepository.findAll();
        assertEquals(2,savedInventories.size());
    }
    @Test
    public void testFindAllInvetoryByExpiringDate_Successfully(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-11");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(12));
        item.setSpecification("Intel Core i7");

        Inventory itemOne = new Inventory();
        itemOne.setItemId("ITEM-12");
        itemOne.setItemName("Laptop");
        itemOne.setQuantityAvailable(3);
        itemOne.setUnitPrice(BigDecimal.valueOf(10000.0));
        itemOne.setItemCategory("Electronics");
        itemOne.setExpiryDate(LocalDate.now().plusYears(2));
        itemOne.setSpecification("Intel Core i7");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,itemOne), LocalDate.now());

        supplierRepository.save(supplier);

        List<Inventory> inventoryList = inventoryRepository.findByExpiryDateIsAfter(LocalDate.now().plusYears(5));

        assertEquals(1,inventoryList.size());
    }

    /** test for updating Inventory. */
    @Test
    public void testUpdateInventory_Successfully() {
        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        savedInventory.setItemName( "HP_LAPTOP");
        savedInventory.setQuantityAvailable(5);
        savedInventory.setUnitPrice(BigDecimal.valueOf(20000.0));
        savedInventory.setItemCategory("Electronics");
        savedInventory.setExpiryDate(LocalDate.now().plusYears(2));
        savedInventory.setSpecification("Intel Core i9");
        inventoryRepository.update(savedInventory);

        assertEquals("HP_LAPTOP", savedInventory.getItemName());
        assertEquals(5, savedInventory.getQuantityAvailable());
        assertEquals(BigDecimal.valueOf(20000.0), savedInventory.getUnitPrice());
        assertEquals("Electronics", savedInventory.getItemCategory());
        assertEquals(LocalDate.now().plusYears(2), savedInventory.getExpiryDate());
        assertEquals("Intel Core i9", savedInventory.getSpecification());
    }

    /** try to update inventory with non-existed Itemid. */
    @Test
    public void testUpdateInventory_WithNonExistedItemId_throwException(){
        Inventory item = new Inventory();
        item.setItemId("ITEM-11");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(12));
        item.setSpecification("Intel Core i7");

        assertThrows(IllegalArgumentException.class, ()-> inventoryRepository.update(item));
    }


    /** this is the test for the purchase requisition. */
    @Test
    public void testSavePurchaseRequisitionAndFindByIdSuccessfully() {
        // arrange
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);
        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(2);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        // act
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(purchaseRequisition);

        Optional<PurchaseRequisition> foundRequisition = purchaseRequisitionRepository.findByRequisitionId(savedRequisition.getRequisitionId());

        // Then
        assertTrue(foundRequisition.isPresent());
        assertEquals(savedRequisition.getRequisitionId(), foundRequisition.get().getRequisitionId());// find by data.
    }
    @Test
    public void testSaveRequisition_WithDuplicateRequisitionId_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter  = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);


        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(2);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-001");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        //assert
        assertThrows(DataIntegrityViolationException.class, ()->purchaseRequisitionRepository.save(purchaseRequisition1));
    }
    @Test
    public void testSaveRequisition_whenDuplicateRequisitionNumber_throwException(){
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter  = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);


        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        //when
        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(2);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-001");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        //assert
        assertThrows(DataIntegrityViolationException.class, ()->purchaseRequisitionRepository.save(purchaseRequisition1));

    }
    @Test
    public void testSaveRequisition_WithNullUser_throwException(){
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(2);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(costCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(null);
        purchaseRequisition1.setCostCenter(costCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition1.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition1.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        //assert

        assertThrows(DataIntegrityViolationException.class, ()->purchaseRequisitionRepository.save(purchaseRequisition1));
    }
    @Test
    public void testSaveRequisition_WithNullDepartment_throwException(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);
        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter  = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();
        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(2);


        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-001");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(null);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        //assert
        assertThrows(DataIntegrityViolationException.class, ()->purchaseRequisitionRepository.save(purchaseRequisition1));
    }
    @Test
    public void testFindRequisitionByRequisitionNumber_Successfully(){
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);
        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(2);

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(purchaseRequisition);

        //assert
        Optional<PurchaseRequisition>  foundRequisition = purchaseRequisitionRepository.findByRequisitionNumber(savedRequisition.getRequisitionNumber());

        assertTrue(foundRequisition.isPresent());
    }
    @Test
    @Rollback(value = false)
    public void testFindRequisitionByRequisitionStatus_Successfully(){
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(1000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter  = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Inventory itemOne = new Inventory();
        itemOne.setItemId("ITEM-0011");
        itemOne.setItemName("Laptop");
        itemOne.setQuantityAvailable(3);
        itemOne.setUnitPrice(BigDecimal.valueOf(10000.0));
        itemOne.setItemCategory("Electronics");
        itemOne.setExpiryDate(LocalDate.now().plusYears(2));
        itemOne.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item,itemOne), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(itemOne.getItemId(),savedSupplier.getId()).get();
        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(1);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        RequestedItem requestedItem = new RequestedItem();
        requestedItem.setInventory(savedInventory1);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());
        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.addItem(itemToBeRequested);
        purchaseRequisition.addItem(requestedItem);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.APPROVED);
        purchaseRequisition1.setItems(purchaseRequisition1.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        purchaseRequisitionRepository.save(purchaseRequisition1);

        List<PurchaseRequisition> totalSavedRequisition = purchaseRequisitionRepository.findAll();
        assertEquals(2,totalSavedRequisition.size());

        List<PurchaseRequisition> approvedRequiaitions =  purchaseRequisitionRepository.findByRequisitionStatus(RequisitionStatus.APPROVED);
        assertEquals(1,approvedRequiaitions.size());
    }
    @Test
    public void testFindRequisitionByPriorityLevel_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter  = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).orElseThrow();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(1);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        purchaseRequisitionRepository.save(purchaseRequisition1);

        List<PurchaseRequisition> requisitionsWithSamePriority = purchaseRequisitionRepository.findByPriorityLevel(PriorityLevel.HIGH);
        assertEquals(2, requisitionsWithSamePriority.size());

    }
    @Test
    public void testFindRequisitionByTimeRange_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(1);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(3));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(3));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.getItems().add(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        purchaseRequisitionRepository.save(purchaseRequisition1);

        List<PurchaseRequisition> purchaseRequisitionsWithInTheRange = purchaseRequisitionRepository.findByRequisitionDateBetween(LocalDate.now().minusDays(1), LocalDate.now().plusMonths(2));
        assertEquals(2, purchaseRequisitionsWithInTheRange.size());
    }
    @Test
    public void testFindRequisitionsByUserId_Successfully(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(1);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition1.getRequisitionDate());
        purchaseRequisition1.addItem(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition1.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        purchaseRequisitionRepository.save(purchaseRequisition1);

        List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository.findByRequestedByUserId(requester.getUserId());
        assertEquals(2, purchaseRequisitionList.size());

    }
    @Test
    public void testFindRequisitionByDepartmentId_Successfully(){
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");


        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(1);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.getItems().add(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition1.getRequisitionDate());
        purchaseRequisition1.addItem(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition1.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        purchaseRequisitionRepository.save(purchaseRequisition1);

        List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository.findByDepartmentEntityDepartmentId(department.getDepartmentId());

        assertEquals(2, purchaseRequisitionList.size());

    }
    @Test
    public void testFindRequisitionByCostCenterId_Successfully(){
        requestedItemRepository.deleteAll();
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG104");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC001");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI007");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR001");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setRequestedQuantity(1);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
        purchaseRequisition.setRequisitionId("REQ-001");
        purchaseRequisition.setRequisitionNumber("REQ-001");
        purchaseRequisition.setRequisitionDate(LocalDate.now());
        purchaseRequisition.setDepartment(savedDepartment);
        purchaseRequisition.setRequestedBy(savedUser);
        purchaseRequisition.setCostCenter(savedCostCenter);
        purchaseRequisition.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition.setJustification("Need for project work");
        purchaseRequisition.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition.addItem(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition.setItems(purchaseRequisition.getItems());

        PurchaseRequisition purchaseRequisition1 = new PurchaseRequisition();
        purchaseRequisition1.setRequisitionId("REQ-002");
        purchaseRequisition1.setRequisitionNumber("REQ-002");
        purchaseRequisition1.setRequisitionDate(LocalDate.now());
        purchaseRequisition1.setDepartment(savedDepartment);
        purchaseRequisition1.setRequestedBy(savedUser);
        purchaseRequisition1.setCostCenter(savedCostCenter);
        purchaseRequisition1.setPriorityLevel(PriorityLevel.HIGH);
        purchaseRequisition1.setExpectedDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseRequisition1.setJustification("Need for project work");
        purchaseRequisition1.setUpdatedDate(purchaseRequisition.getRequisitionDate());
        purchaseRequisition1.addItem(itemToBeRequested);
        purchaseRequisition1.setRequisitionStatus(RequisitionStatus.PENDING);
        purchaseRequisition1.setItems(purchaseRequisition1.getItems());

        purchaseRequisitionRepository.save(purchaseRequisition);

        purchaseRequisitionRepository.save(purchaseRequisition1);

        List<PurchaseRequisition> purchaseRequisitionList = purchaseRequisitionRepository.findByCostCenterEntityCostCenterId(costCenter.getCostCenterId());

        assertEquals(2,purchaseRequisitionList.size());
    }
    @Test
    public void testDeletePurchaseRequisitionByRequisitionId_Successfully() {

        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG108");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC003");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");


        Department department = new Department();
        department.setDepartmentId("DI009");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR003");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");

        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setRequestedQuantity(3);
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());
        PurchaseRequisition purchaseRequisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        purchaseRequisition.addItem(itemToBeRequested);
        purchaseRequisition.setItems(purchaseRequisition.getItems());
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);

        // act
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(purchaseRequisition);

        // When
        purchaseRequisitionRepository.deleteByRequisitionId(savedRequisition.getRequisitionId());

        // Then
        Optional<PurchaseRequisition> deletedRequisition = purchaseRequisitionRepository.findByRequisitionId(savedRequisition.getRequisitionId());
        assertFalse(deletedRequisition.isPresent());
    }

    @Test
    @Rollback(value = false)
    public void testUpdatePurchaseRequisition_Successfully() {
        // Given
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG109");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();
        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC004");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI010");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(10000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR004");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");

        User savedUser = userRepository.save(requester);

        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();
        RequestedItem itemToBeRequested = new RequestedItem();
        itemToBeRequested.setRequestedQuantity(3);
        itemToBeRequested.setInventory(savedInventory);
        itemToBeRequested.setTotalPrice(itemToBeRequested.getTotalPrice());

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        purchaseRequisition.addItem(itemToBeRequested);
        purchaseRequisition.setItems(purchaseRequisition.getItems());
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);

        // act
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(purchaseRequisition);

        // When
        savedRequisition.setRequisitionNumber("REQ-001-UPDATED");
        savedRequisition.setRequisitionDate(LocalDate.now().minusDays(1));
        savedRequisition.setPriorityLevel(PriorityLevel.MEDIUM);
        savedRequisition.setExpectedDeliveryDate(LocalDate.now().plusMonths(2));
        savedRequisition.setJustification("Updated justification for project work");
        savedRequisition.setRequisitionStatus(RequisitionStatus.APPROVED);

        // Then
        PurchaseRequisition updatedRequisition = purchaseRequisitionRepository.update(savedRequisition);
        assertEquals(RequisitionStatus.APPROVED, updatedRequisition.getRequisitionStatus());
        assertEquals("REQ-001-UPDATED", updatedRequisition.getRequisitionNumber());
        assertEquals(LocalDate.now().minusDays(1), updatedRequisition.getRequisitionDate());
        assertEquals(PriorityLevel.MEDIUM, updatedRequisition.getPriorityLevel());
        assertEquals(LocalDate.now().plusMonths(2), updatedRequisition.getExpectedDeliveryDate());
        assertEquals("Updated justification for project work", updatedRequisition.getJustification());
    }

    /** this is negative testing for the purchase requisition repository imeplementation. */
    @Test
    public void testSaveRequisitionUnsuccessfully_DueToNullRequisition(){
        assertThrows(InvalidDataAccessApiUsageException.class,()->purchaseRequisitionRepository.save(null));
    }

    @Test
    public void testSaveRequisition_Failure_DueToNegativeQuantity(){
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG110");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.setTotalRemainingBudget(budgetOne.getTotalBudget());
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC005");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI101");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter,BigDecimal.valueOf(13000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR005");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);


        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem(savedInventory,-3);

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        purchaseRequisition.addItem(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);

        assertThrows(ConstraintViolationException.class,()->purchaseRequisitionRepository.save(purchaseRequisition));//Price cannot be zero for non-zero quantity.
    }
    @Test
    public void testFindingRequisition_WithNonExistedRequisition_Successfully(){
        //given
        Budget budgetOne = new Budget();
        budgetOne.setBudgetId("BG111");
        budgetOne.setTotalBudget(BigDecimal.valueOf(20000.0));
        budgetOne.setStartDate(LocalDate.of(2025, 1, 1));
        budgetOne.setEndDate(LocalDate.of(2025,10,31));
        budgetOne.setCurrency("USD");
        budgetOne.updateBudgetStatus();

        Budget savedBudget = budgetRepository.save(budgetOne);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC006");
        costCenter.setName("Procurement");
        costCenter.setDescription("Manages acquisitions");

        Department department = new Department();
        department.setDepartmentId("DI102");
        department.setName("Software Development");
        department.setDescription("we are new department in malam Engineering plc.");
        department.setBudget(savedBudget);
        department.allocateBudgetToCostCenter(costCenter, BigDecimal.valueOf(15000.0));
        Department savedDepartment = departmentRepository.save(department);

        CostCenter savedCostCenter = costCenterRepository.findByCostCenterId(costCenter.getCostCenterId()).get();

        User requester = new User();
        requester.setUserId("UR006");
        requester.setFullName("Tekia Tekle");
        requester.setEmail("tekia2034@gmail.com");
        requester.setPhoneNumber("+251979417636");
        requester.setDepartment(savedDepartment);
        requester.setRole("developer");
        User savedUser = userRepository.save(requester);


        Inventory item = new Inventory();
        item.setItemId("ITEM-0010");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        Supplier savedSupplier = supplierRepository.save(supplier);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplier.getId()).get();

        RequestedItem itemToBeRequested = new RequestedItem(savedInventory,3);

        PurchaseRequisition purchaseRequisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        purchaseRequisition.addItem(itemToBeRequested);
        purchaseRequisition.setRequisitionStatus(RequisitionStatus.PENDING);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(purchaseRequisition);
        assertNotNull(savedRequisition.getRequisitionId());

        String requisitionId = IdGenerator.generateId("REQ");
        //when and then
        Optional<PurchaseRequisition> foundRequisition = purchaseRequisitionRepository.findByRequisitionId(requisitionId);
        assertFalse(foundRequisition.isPresent());
    }


    /** this is the test for supplier repository implementation. */
    @Test
    @Rollback(value = false)
    public void testSaveSupplierAndFindBySupplierId_Successfully(){
        //given
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        //when
        Supplier savedVendor = supplierRepository.save(supplier);

        Optional<Supplier> foundSupplier = supplierRepository.findBySupplierId(savedVendor.getSupplierId());
        assertTrue(foundSupplier.isPresent());

    }
    @Test
    public void testSaveSupplier_WithDuplicatedSupplierId_throwException(){
        //given
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", supplierContactDetail.getFullName(),supplierContactDetail.getPhone() ,List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD,NET_30, "USD", BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");
        Supplier supplier = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item), LocalDate.now());

        supplierRepository.save(supplier);

        Supplier supplierModel1 = new Supplier();
        supplierModel1.setSupplierId(supplier.getSupplierId());
        supplierModel1.setSupplierName("Cisco");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1021");
        supplierModel1.setRegistrationNumber("CI-13-2025");
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());
        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));
    }

    @Test
    public void testSaveSupplier_WithNullSupplierName_throwException(){

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-001");
        supplierModel1.setSupplierName(null);
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1021");
        supplierModel1.setRegistrationNumber("CI-13-2025");
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());
        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));

    }
    @Test
    public void testSaveSupplier_WithNullSupplierCategory_throwExcpetion(){

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel1 = new Supplier();
        supplierModel1.setSupplierId("SUP-001");
        supplierModel1.setSupplierName("Cisco");
        supplierModel1.setSupplierCategory(null);
        supplierModel1.setTaxIdentificationNumber("CI1021");
        supplierModel1.setRegistrationNumber("CI-13-2025");
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());

        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));

    }
    @Test
    public void testSaveSupplier_WithNullTINNumber_throwException(){

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-001");
        supplierModel1.setSupplierName("Cisco");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber(null);
        supplierModel1.setRegistrationNumber("CI-13-2025");
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());

        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));

    }
    @Test
    public void testSaveSupplier_WithDuplicatedTINNumber_throwException(){

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();
        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());

        supplierRepository.save(supplierModel);

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-002");
        supplierModel1.setSupplierName("IBM");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1021");
        supplierModel1.setRegistrationNumber("CI-13-025");
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());

        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));
    }
    @Test
    public void testSaveSupplier_WithNullRegistrationNumber_throwException(){

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-001");
        supplierModel1.setSupplierName("Cisco");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1021");
        supplierModel1.setRegistrationNumber(null);
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());
        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));

    }
    @Test
    public void testSaveSupplier_WithDuplicatedRegistrationNumber_throwException(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        supplierRepository.save(supplierModel);

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-002");
        supplierModel1.setSupplierName("IBM");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1022");
        supplierModel1.setRegistrationNumber("CI-13-2025");
        supplierModel1.setSupplierContactDetail(supplierContactDetail);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());

        assertThrows(DataIntegrityViolationException.class,()->supplierRepository.save(supplierModel1));
    }
    @Test
    public void testFindSupplierByCategory_Successfully(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979412531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierRepository.save(supplierModel);

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-002");
        supplierModel1.setSupplierName("IBM");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1022");
        supplierModel1.setRegistrationNumber("CI-13-025");
        supplierModel1.setSupplierContactDetail(supplierContactDetailOne);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.getExistedItems().add(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierRepository.save(supplierModel1);

        List<Supplier> supplierModelList = supplierRepository.findBySupplierCategory(supplierModel1.getSupplierCategory());
        assertEquals(2, supplierModelList.size());
    }
    @Test
    public void testFindActiveSuppliers_Successfully(){

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979412531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        supplierModel.setActive(true);
        supplierRepository.save(supplierModel);

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-002");
        supplierModel1.setSupplierName("IBM");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1022");
        supplierModel1.setRegistrationNumber("CI-13-025");
        supplierModel1.setSupplierContactDetail(supplierContactDetailOne);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now());
        supplierModel1.setExistedItems(supplierModel1.getExistedItems());

        supplierModel1.setActive(false);
        supplierRepository.save(supplierModel1);

        List<Supplier> totalSupplierListModels = supplierRepository.findAll();
        assertEquals(2, totalSupplierListModels.size());

        List<Supplier> activeSupplierListModels = supplierRepository.findByIsActiveTrue();
        assertEquals(1, activeSupplierListModels.size());

    }
    @Test
    public void testFindSuppliersByRegistrationDate_Successfully(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierContactDetail supplierContactDetailOne = new SupplierContactDetail("mulu berhe", "mulu@cisco.com", "+251979412531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());

        supplierRepository.save(supplierModel);

        Supplier supplierModel1 = new Supplier();

        supplierModel1.setSupplierId("SUP-002");
        supplierModel1.setSupplierName("IBM");
        supplierModel1.setSupplierCategory("Vendor");
        supplierModel1.setTaxIdentificationNumber("CI1012");
        supplierModel1.setRegistrationNumber("CI-13-025");
        supplierModel1.setSupplierContactDetail(supplierContactDetailOne);
        supplierModel1.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel1.addInventory(item);
        supplierModel1.setRegistrationDate(LocalDate.now().minusMonths(2));
        supplierRepository.save(supplierModel1);

        List<Supplier> supplierModelList = supplierRepository.findByRegistrationDateBefore(LocalDate.now().minusDays(2)); // supplierModel that are
        assertEquals(1, supplierModelList.size());

        List<Supplier> supplierModels = supplierRepository.findByRegistrationDateAfter(LocalDate.now().minusDays(2));
        assertEquals(1, supplierModels.size());
    }
    @Test
    public void testFindSupplierByTINNumber_successfully(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Optional<Supplier> foundSupplier = supplierRepository.findByTaxIdentificationNumber(savedSupplierModel.getTaxIdentificationNumber());
        assertTrue(foundSupplier.isPresent());
    }
    @Test
    public void testFindSupplierByRegistrationNumber_Successfully(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Optional<Supplier> foundSupplier = supplierRepository.findByRegistrationNumber(savedSupplierModel.getRegistrationNumber());
        assertTrue(foundSupplier.isPresent());

    }
    @Test
    public void testFindSupplier_WithNonExistedSupplierId_throwException(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());

        supplierRepository.save(supplierModel);

        Optional<Supplier> foundSupplier = supplierRepository.findBySupplierId("SUP-003");
        assertTrue(foundSupplier.isEmpty());
    }
    @Test
    public void testFindSupplier_WithNonExistedTIN_Number_throwException(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());

        supplierRepository.save(supplierModel);

        Optional<Supplier>foundSupplier =  supplierRepository.findByTaxIdentificationNumber("CI-1011");
        assertTrue(foundSupplier.isEmpty());
    }
    @Test
    public void testFindSupplier_WithNOtExistedRegistrationNumber_ThrowException(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        supplierRepository.save(supplierModel);

        Optional<Supplier> foundSupplier = supplierRepository.findByRegistrationNumber("CI-13-20");
        assertTrue(foundSupplier.isEmpty());
    }
    @Test
    @Rollback(value = false)
    public void testDeleteSupplier_WithValidSupplierId_Successfully(){
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();
        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        supplierRepository.deleteBySupplierId(savedSupplierModel.getSupplierId());
        System.out.println(savedSupplierModel);
        Optional<Supplier> foundSupplier = supplierRepository.findBySupplierId(savedSupplierModel.getSupplierId());

        assertFalse(foundSupplier.isPresent());
    }
    @Test
    public void testShowIngSupplierRepository_WhenNoSupplierSavedBefore_ReturnEmpty(){
        List<Supplier> totalSupplierListModel = supplierRepository.findAll();
        assertTrue(totalSupplierListModel.isEmpty());
    }

    @Test
    public void testSaveSupplier_WithContactDetailsIsNull_throwException() {
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565", "Tekia Tekle","+251979417636", List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(null);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        assertThrows(NullPointerException.class,()->supplierRepository.save(supplierModel));
    }

    @Test
    public void testSaveSupplier_WithInvalideEmailExpression_throwException() {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegaycisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        assertThrows(InvalidDataAccessApiUsageException.class,()->supplierRepository.save(supplierModel));
    }
    @Test

    public void testSaveSupplier_WithPhoneNumberWithOutCountryCode_throwException() {
        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "0979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Inventory item = new Inventory();
        item.setItemId("ITEM-211");
        item.setItemName("Laptop");
        item.setQuantityAvailable(3);
        item.setUnitPrice(BigDecimal.valueOf(10000.0));
        item.setItemCategory("Electronics");
        item.setExpiryDate(LocalDate.now().plusYears(2));
        item.setSpecification("Intel Core i7");

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        assertThrows(InvalidDataAccessApiUsageException.class,()->supplierRepository.save(supplierModel));
    }

    /** test of purchase order repository implementation . */
    @Test
    @Rollback(value = false)
    public void testSavePurchaseOrderAndFindByOrderId_Successfully(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder =  purchaseOrderRepository.save(purchaseOrder);
        Optional<PurchaseOrder> foundPurchaseOrder = purchaseOrderRepository.findByOrderId(savedPurchaseOrder.getOrderId());
        assertTrue(foundPurchaseOrder.isPresent());
    }
    @Test
    public void testSavePurchaseOrder_WithNullOrderId_throwException(){
        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId(null);
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        assertThrows(DataIntegrityViolationException.class, ()->purchaseOrderRepository.save(purchaseOrder));
    }
    @Test
    public void testSavePurchaseOrder_WithDuplicatedOrderId_throwException(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(),savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);
        purchaseOrderRepository.save(purchaseOrder);

        PurchaseOrder purchaseOrderOne = new PurchaseOrder();
        purchaseOrderOne.setOrderId("PO-001");
        purchaseOrderOne.setDepartment( savedDepartment);
        purchaseOrderOne.addRequisition(requisition);
        purchaseOrderOne.setRequisitionList(purchaseOrder.getRequisitionList());
        purchaseOrderOne.setOrderDate(LocalDate.now());
        purchaseOrderOne.setSupplier(savedSupplierModel);
        purchaseOrderOne.setShippingMethod("Air");
        purchaseOrderOne.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrderOne.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        assertThrows(DataIntegrityViolationException.class, ()->purchaseOrderRepository.save(purchaseOrderOne));

    }
    @Test
    public void testSavePurchaseOrder_WithNullDepartmentEntity_throwException(){

        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);
        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

        Budget budget = new Budget( BigDecimal.valueOf(50000), LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31), "USD");
        Budget savedBudget = budgetRepository.save(budget);

        CostCenter costCenter = new CostCenter();
        costCenter.setCostCenterId("CC-101");
        costCenter.setName("IT equipments");
        costCenter.setDescription("Manages IT Equipments");

//        CostCenter savedCostCenter = costCenterRepository.save(costCenter);
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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( null);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        assertThrows(DataIntegrityViolationException.class, ()->purchaseOrderRepository.save(purchaseOrder));
    }
    @Test
    public void testSavePurchaseOrder_WithEmptyRequisition_throwException(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplierModel.getId()).orElseThrow();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
//        purchaseOrder.addRequisition(requisition);
        purchaseOrder.setRequisitionList(purchaseOrder.getRequisitionList());
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        assertThrows(ConstraintViolationException.class,()->purchaseOrderRepository.save(purchaseOrder));
    }
    @Test
    public void testSavePurchaseOrder_WithNullSupplierEntity_throwException(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();
        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplierModel.getId()).get();
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(),savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(),savedSupplierModel.getId()).get();


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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(null); // <-------
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        assertThrows(DataIntegrityViolationException.class, ()->purchaseOrderRepository.save(purchaseOrder));
    }
    @Test
    public void testFindPurchaseOrderByDepartmentId_Successfull(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findByDepartmentId(purchaseOrder.getDepartment().getDepartmentId());
        assertEquals(2, purchaseOrderList.size());
    }
    @Test
    public void testFindPurchaseOrdersWithSameSupplier_Successfully(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(),savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(),savedSupplierModel.getId()).get();

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findBySupplierId(purchaseOrder.getSupplier().getSupplierId());
        assertEquals(2,purchaseOrderList.size());
    }
    @Test
    public void testFindPurchaseOrderByStatus_Successfully(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();


        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> totalPurchaseOrderLists = purchaseOrderRepository.findAll();
        assertEquals(2,totalPurchaseOrderLists.size()); // <-------- showing the total saved purchase orders

        List<PurchaseOrder> approvedPurchaseOrderList = purchaseOrderRepository.findByPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        assertEquals(1,approvedPurchaseOrderList.size()); // <-------- shows only the approved purchase order.
    }
    @Test
    public void testFindByOrderDateRange_Successfully(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(),savedSupplierModel.getId()).get();
        /** About Requisition. */

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now().minusMonths(2));
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);
        List<PurchaseOrder> totalPurchaseOrderLists = purchaseOrderRepository.findAll();
        assertEquals(2,totalPurchaseOrderLists.size());

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findByOrderDateBetween(LocalDate.now().minusDays(20), LocalDate.now().plusDays(10));
        assertEquals(1, purchaseOrderList.size());
    }
    @Test
    public void testFindPurchaseOrderByDeliveryDateRange_Successfully(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());

        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();

        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(1));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> totalPurchaseOrderLists = purchaseOrderRepository.findAll();
        assertEquals(2,totalPurchaseOrderLists.size());

        List<PurchaseOrder>purchaseOrderList = purchaseOrderRepository.findByDeliveryDateBetween(LocalDate.now(),LocalDate.now().plusMonths(3));
        assertEquals(1, purchaseOrderList.size());

    }
    @Test
    public void testFindPurchaseOrder_WithOrderedBeforeSpecificDate_Successfully(){

        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        requisition.addItem(requestedItem);
        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findByOrderDateBefore(LocalDate.now().plusDays(10));
        assertEquals(2,purchaseOrderList.size());
    }
    @Test
    public void testFindPurchaseOrders_OrderedAfterSpecificeDate_Successfully(){

        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now().minusDays(20));
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(2));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findByOrderDateAfter(LocalDate.now().minusDays(10));
        assertEquals(1,purchaseOrderList.size());
    }

    @Test
    public void testDeletePurchaseOrderSuccessfully(){


        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        purchaseOrderRepository.deleteByOrderId(purchaseOrder1.getOrderId()); // <----- delete purchase order successfully.

        Optional<PurchaseOrder> foundPurchaseOrder = purchaseOrderRepository.findByOrderId(purchaseOrder1.getOrderId());
        assertFalse(foundPurchaseOrder.isPresent());
    }

    @Test
    public void testFindPurchaseOrder_WithNonExistedOrderId_returnEmpty(){

        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());
        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");

        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);
        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        Optional<PurchaseOrder> foundPurchaseOrder = purchaseOrderRepository.findByOrderId("ORD-001");
        assertTrue(foundPurchaseOrder.isEmpty());
    }
    @Test
    public void testFindPurchaseOrder_WithNotExistedDepartmentId_returnEmpty(){

        /** About SupplierModel. */
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier();

        supplierModel.setSupplierId("SUP-001");
        supplierModel.setSupplierName("Cisco");
        supplierModel.setSupplierCategory("Vendor");
        supplierModel.setTaxIdentificationNumber("CI1021");
        supplierModel.setRegistrationNumber("CI-13-2025");
        supplierModel.setSupplierContactDetail(supplierContactDetail);
        supplierModel.setSupplierPaymentMethods(List.of(supplierPaymentMethod));
        supplierModel.addInventory(item);
        supplierModel.addInventory(item1);
        supplierModel.addInventory(item2);
        supplierModel.setRegistrationDate(LocalDate.now());
        supplierModel.setExistedItems(supplierModel.getExistedItems());

        Supplier savedSupplierModel =  supplierRepository.save(supplierModel);

        Inventory savedInventory = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        /** About Requisition. */

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
        requestedItem.setInventory(savedInventory);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.setRequisitionId("REQ-001");
        requisition.addItem(requestedItem);

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseRequisition requisitionOne = new PurchaseRequisition("REQ-002", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisitionOne.setRequisitionId("REQ-002");
        requisitionOne.addItem(requestedItem);

        PurchaseRequisition savedRequisitionOne = purchaseRequisitionRepository.save(requisitionOne);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("PO-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder purchaseOrder1 = new PurchaseOrder();
        purchaseOrder1.setOrderId("PO-002");
        purchaseOrder1.setDepartment(savedDepartment);
        purchaseOrder1.addRequisition(savedRequisitionOne);
        purchaseOrder1.setOrderDate(LocalDate.now());
        purchaseOrder1.setSupplier(savedSupplierModel);
        purchaseOrder1.setShippingMethod("SHIPP");
        purchaseOrder1.setPurchaseOrderStatus(PurchaseOrderStatus.APPROVED);
        purchaseOrder1.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder1.setApproved(true);
        purchaseOrder1.setShipped(true);

        purchaseOrderRepository.save(purchaseOrder);
        purchaseOrderRepository.save(purchaseOrder1);

        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findByDepartmentId("DP-001");
        assertTrue(purchaseOrderList.isEmpty());
    }

    /** this is the testing for contracts repository implementation. */
    @Test
    @Rollback(value = false)
    public void testSaveContractAndFindByContractId_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");
        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CON-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.setStatus(ContractStatus.ACTIVE);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());

        Contract savedContract = contractRepository.save(contract);

        Optional<Contract> foundContract =  contractRepository.findByContractId(savedContract.getContractId());
        assertTrue(foundContract.isPresent());
    }
    @Test
    public void testSaveContracts_WithNullContractId_throwException(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");


        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId(null);
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.setStatus(ContractStatus.ACTIVE);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());

        assertThrows(DataIntegrityViolationException.class,()->contractRepository.save(contract));
    }
    @Test
    public void testSaveContractWithDuplicateContractId_throwException(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contractOne = new Contract("Contract with Cisco", savedSupplierModel, LocalDate.now().plusDays(10), LocalDate.now().plusMonths(10), NET_30, DeliveryTerms.CIF, BigDecimal.valueOf(45000.0), true, List.of(savedPurchaseOrder), List.of(contractFile),LocalDate.now().minusDays(3));
        contractOne.setContractId("CO-001");

        contractRepository.save(contractOne);
        
        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.setStatus(ContractStatus.ACTIVE);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());

        assertThrows(DataIntegrityViolationException.class,()->contractRepository.save(contract));
    }
    @Test
    public void testSaveContract_WithEmptyPurchaseOrder_throwException(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

       Supplier savedSupplierModel = supplierRepository.save(supplierModel);

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
        requestedItem.setInventory(item);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(requisition);
        purchaseOrder.setRequisitionList(purchaseOrder.getRequisitionList());
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.setStatus(ContractStatus.ACTIVE);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());

        assertThrows(ConstraintViolationException.class,()->contractRepository.save(contract));

    }
    @Test
    public void testFindContractBySupplierId_Successfully(){Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.setStatus(ContractStatus.ACTIVE);
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());
        Contract savedContract = contractRepository.save(contract);

        List<Contract> contracts = contractRepository.findBySupplierId(savedContract.getSupplier().getSupplierId());
        assertEquals(1,contracts.size());
    }
    @Test
    public void testFindContractByStatus_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.setStatus(ContractStatus.ACTIVE);
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());

        contractRepository.save(contract);

        List<Contract> contracts = contractRepository.findByStatus(ContractStatus.ACTIVE);
        assertEquals(1,contracts.size());
    }
    @Test
    public void testDeleteContractsSuccessfully(){

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");
        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.setStatus(ContractStatus.ACTIVE);
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());
        Contract savedContract = contractRepository.save(contract);

        contractRepository.deleteByContractId(savedContract.getContractId());

        Optional<Contract> foundContract = contractRepository.findByContractId(savedContract.getContractId());
        assertTrue(foundContract.isEmpty());
    }
    @Test
    public void testSaveContractUnsuccessfullyDueToNullValue(){
        assertThrows(NullPointerException.class,()->contractRepository.save(null));
    }
    @Test
    public void testSaveContractsFailDuetoEndTimeBeforeStartTime(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(requisition);
        purchaseOrder.setRequisitionList(purchaseOrder.getRequisitionList());
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().minusMonths(1));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.setStatus(ContractStatus.ACTIVE);
        contract.addPurchaseOrder(purchaseOrder);
        contract.setPurchaseOrders(contract.getPurchaseOrders());
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());

        assertThrows(InvalidDataAccessApiUsageException.class,()->contractRepository.save(contract));

    }
    @Test
    public void testFindConrtactFail_DueTo_NonExistedId(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        PurchaseRequisition requisition = new PurchaseRequisition("REQ-001", LocalDate.now(), savedUser, savedDepartment, savedCostCenter, PriorityLevel.HIGH, LocalDate.now().plusMonths(1), "Need for project work");
        requisition.addItem(requestedItem);

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
        ContractFile contractFile = new ContractFile("contract one", "pdf", "/files/sample.pdf", LocalDate.now());

        Contract contract = new Contract();

        contract.setContractId("CO-001");
        contract.setContractTitle("Contract with Cisco");
        contract.setSupplier(savedSupplierModel);
        contract.setStartDate(LocalDate.now());
        contract.setEndDate(LocalDate.now().plusMonths(10));
        contract.setPaymentTerms(NET_30);
        contract.setDeliveryTerms(DeliveryTerms.CIF);
        contract.setTotalCost(BigDecimal.valueOf(45000.0));
        contract.setRenewable(true);
        contract.setRenewalDate(LocalDate.now().plusDays(20));
        contract.setStatus(ContractStatus.ACTIVE);
        contract.addPurchaseOrder(savedPurchaseOrder);
        contract.addAttachment(contractFile);
        contract.setAttachments(contract.getAttachments());
        contract.setCreatedDate(LocalDate.now());
        contractRepository.save(contract);

        Optional<Contract> foundContract = contractRepository.findByContractId("CC-001");
        assertTrue(foundContract.isEmpty());
    }

    /** this is the testing for Invoice repository implementation. */
    @Test
    @Rollback(value = false)
    public void testSaveInvoiceAndFindById_Successfully(){
        invoicedItemRepository.deleteAll();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");

        invoice.setInvoiceId("INV-001");
        Invoice savedInvoice = invoiceRepository.save(invoice);

        Optional<Invoice> foundInvoice = invoiceRepository.findByInvoiceId(savedInvoice.getInvoiceId());
        assertTrue(foundInvoice.isPresent());
    }
    @Test
    public void testSaveInvoiceWithNullInvoiceId_throwExcpetion(){
        invoicedItemRepository.deleteAll();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");

        invoice.setInvoiceId(null);
        assertThrows(DataIntegrityViolationException.class,()->invoiceRepository.save(invoice));
    }
    @Test
    public void testSaveInvoice_WithDuplicateInvoiceId_throwException(){
        invoicedItemRepository.deleteAll();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");

        invoice.setInvoiceId("INV-001");
        Invoice invoiceOne = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(5), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");

        invoiceOne.setInvoiceId("INV-001");
        invoiceRepository.save(invoice);

        assertThrows(DataIntegrityViolationException.class,()->invoiceRepository.save(invoiceOne));
    }
    @Test
    public void testSaveInvoice_WithNullSupplier_throwEcxeption(){
        invoicedItemRepository.deleteAll();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15),null,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-001");

        assertThrows(DataIntegrityViolationException.class,()->invoiceRepository.save(invoice));
    }
    @Test
    public void testSaveInvoice_WithNullPurchaseOrder_throwException(){
        invoicedItemRepository.deleteAll();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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

        purchaseOrderRepository.save(purchaseOrder);
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,null,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        assertThrows(DataIntegrityViolationException.class, ()->invoiceRepository.save(invoice));
    }
    @Test
    public void testFindInvoiceByOrderId_Successfully(){
        invoicedItemRepository.deleteAll();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        Optional<Invoice> foundInvoice = invoiceRepository.findByPurchaseOrderId(savedInvoice.getPurchaseOrder().getOrderId());
        assertTrue(foundInvoice.isPresent());
    }
    @Test
    public void testFindInvoicesByUserId_successsfully(){
        invoicedItemRepository.deleteAll();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventoryOne = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventoryTwo = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventoryThree = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        requestedItem.setInventory(savedInventoryOne);
        requestedItem.setRequestedQuantity(3);
        requestedItem.setTotalPrice(requestedItem.getTotalPrice());

        RequestedItem requestedItem2 = new RequestedItem();
        requestedItem2.setInventory(savedInventoryTwo);
        requestedItem2.setRequestedQuantity(2);
        requestedItem2.setTotalPrice(requestedItem2.getTotalPrice());

        RequestedItem requestedItem3 = new RequestedItem();
        requestedItem3.setInventory(savedInventoryThree);
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventoryOne,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventoryTwo,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventoryThree,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        List<Invoice> foundInvoice = invoiceRepository.findInvoiceByUserId(savedInvoice.getCreatedBy().getUserId());
        assertEquals(1, foundInvoice.size());
    }
    @Test
    public void testFindInvoiceWithInSpecificTimeRange_Successfully(){
        invoicedItemRepository.deleteAll();

        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
        Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        List<Invoice> foundInvoice = invoiceRepository.findByInvoiceDateBetween(LocalDate.now().minusDays(20),LocalDate.now().plusMonths(1));
        assertEquals(1, foundInvoice.size());
    }
    @Test
    public void deleteInvoice_WithValidInvoiceId_Successfully(){
        invoicedItemRepository.deleteAll();

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

        PurchaseRequisition savedRequisition = purchaseRequisitionRepository.save(requisition);;

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        Invoice savedInvoice = invoiceRepository.save(invoice);

        invoiceRepository.deleteByInvoiceId(savedInvoice.getInvoiceId());

        Optional<Invoice> foundInvoice = invoiceRepository.findByInvoiceId(savedInvoice.getInvoiceId());
        assertTrue(foundInvoice.isEmpty());
    }
    /** this is the test for delivery receipt repository implementation. */
    @Test
    @Rollback(value = false)
    public void testSaveDeliveryReceiptsAndFindByReeiptId_Successfully(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        DeliveryReceipt savedDeliveryReceipts = deliveryReceiptRepository.save(deliveryReceipt);

        Optional<DeliveryReceipt> deliveredReceipt = deliveryReceiptRepository.findByReceiptId(savedDeliveryReceipts.getReceiptId());
        assertTrue(deliveredReceipt.isPresent());
    }
    @Test
    public void testSaveDeliveryReceipts_WithNullReceiptId_throwException(){
        deliveredItemRepository.deleteAll();
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

        PurchaseRequisition savedRequsition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedRequsition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setReceiptId(null);

        assertThrows(DataIntegrityViolationException.class,()->deliveryReceiptRepository.save(deliveryReceipt));
    }
    @Test
    public void testSaveDeliveryReceipt_WithDuplicatedReceiptId_throwException(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        DeliveryReceipt deliveryReceiptOne = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceiptOne.setReceiptId("REP-001");
        deliveryReceiptRepository.save(deliveryReceipt);

        assertThrows(DataIntegrityViolationException.class,()->deliveryReceiptRepository.save(deliveryReceiptOne));
    }
    @Test
    public void testSaveDeliveryReceipt_withNullSupplier_throwException(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(item,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(item1,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(item2,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(null,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        assertThrows(DataIntegrityViolationException.class,()->deliveryReceiptRepository.save(deliveryReceipt));
    }
    @Test
    public void testSaveDeliveryReceipt_WithNullPurchaseOrder_throwException(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,null,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        assertThrows(DataIntegrityViolationException.class, ()->deliveryReceiptRepository.save(deliveryReceipt));
    }
    @Test
    public void testSaveDeliveryReceipt_WithEmptyReceivedItem_returnEmpty(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        DeliveryReceipt savedReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        Optional<DeliveryReceipt> foundReceipt = deliveryReceiptRepository.findByReceiptId(savedReceipt.getReceiptId());
        assertTrue(foundReceipt.get().getReceivedItems().isEmpty());

    }
    @Test
    public void testFindDeliveryReceiptBySupplierId_Successfully(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        DeliveryReceipt savedReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        List<DeliveryReceipt> deliveryReceiptList = deliveryReceiptRepository.findBySupplierId(savedReceipt.getSupplier().getSupplierId());
        assertEquals(1, deliveryReceiptList.size());
    }
    @Test
    public void testFindDeliveryReceiptsByPurchaseOrderId_Successfully(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        DeliveryReceipt savedReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        List<DeliveryReceipt> deliveryReceiptList = deliveryReceiptRepository.findByOrderId(savedReceipt.getPurchaseOrder().getOrderId());
        assertEquals(1, deliveryReceiptList.size());
    }
    @Test
    public void testFindDeliveryReceiptsByUser_Successfully(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        DeliveryReceipt savedReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        List<DeliveryReceipt> deliveryReceiptList = deliveryReceiptRepository.findByUser(savedReceipt.getReceivedBy().getUserId());
        assertEquals(1, deliveryReceiptList.size());
    }
    @Test
    public void testFindDeliveryReceiptByStatus_Successfully(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");
        deliveryReceipt.setDeliveryStatus(DeliveryStatus.DELIVERED);
        deliveryReceiptRepository.save(deliveryReceipt);

        List<DeliveryReceipt> deliveryReceiptList = deliveryReceiptRepository.findByStatus(DeliveryStatus.DELIVERED);
        assertEquals(1, deliveryReceiptList.size());
    }
    @Test
    public void testFindDeliveryReceipts_WhichDeliveredBeforeSpecificDate_Successfully(){
        deliveredItemRepository.deleteAll();
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

       Inventory savedInventory1 = inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).get();
       Inventory savedInventory2 = inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).get();
       Inventory savedInventory3 = inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).get();

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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        deliveryReceiptRepository.save(deliveryReceipt);

        List<DeliveryReceipt> deliveryReceiptList = deliveryReceiptRepository.findByDeliveryDateBefore(LocalDate.now());
        assertEquals(1, deliveryReceiptList.size());
    }
    @Test
    public void deleteDeliveryReceipt_WithValidReceiptId_Successfully(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        DeliveryReceipt savedReceipt = deliveryReceiptRepository.save(deliveryReceipt);

        deliveryReceiptRepository.deleteByReceiptId(savedReceipt.getReceiptId());

        Optional<DeliveryReceipt> foundDeliveryReceipt = deliveryReceiptRepository.findByReceiptId(savedReceipt.getReceiptId());
        assertTrue(foundDeliveryReceipt.isEmpty());
    }
    @Test
    public void testSaveDeliveryReceiptsFailDuetoNegativeAmount(){
        deliveredItemRepository.deleteAll();
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
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,-3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        assertThrows(ConstraintViolationException.class,()->deliveryReceiptRepository.save(deliveryReceipt));
    }
    @Test
    public void testFindDeliveryReceiptsFailDuetoNonExistedId(){
        deliveredItemRepository.deleteAll();
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

        PurchaseRequisition savedPurchaseRequisition = purchaseRequisitionRepository.save(requisition);

        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setOrderId("ORD-001");
        purchaseOrder.setDepartment( savedDepartment);
        purchaseOrder.addRequisition(savedPurchaseRequisition);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setSupplier(savedSupplierModel);
        purchaseOrder.setShippingMethod("Air");
        purchaseOrder.setDeliveryDate(LocalDate.now().plusMonths(4));
        purchaseOrder.setPurchaseOrderStatus(PurchaseOrderStatus.PENDING);

        PurchaseOrder savedPurchaseOrder = purchaseOrderRepository.save(purchaseOrder);
        InvoicedItem invoicedItem1 = new InvoicedItem(savedInventory1,3);
        InvoicedItem invoicedItem2 = new InvoicedItem(savedInventory2,2);
        InvoicedItem invoicedItem3 = new InvoicedItem(savedInventory3,1);

        User invoiceOfficer = new User("New Officer","officer.invoice@gmial.com","+251987327646",savedDepartment,"Officer");
        User invoiceUser = userRepository.save(invoiceOfficer);
        Invoice invoice = new Invoice(LocalDate.now().minusDays(10),LocalDate.now().minusDays(15), savedSupplierModel,savedPurchaseOrder,List.of(invoicedItem1,invoicedItem2,invoicedItem3),BigDecimal.valueOf(2000.0),BigDecimal.valueOf(500.0),BigDecimal.valueOf(1500.0),"USD",LocalDate.now(),invoiceUser," ");
        invoice.setInvoiceId("INV-012");

        invoiceRepository.save(invoice);

        DeliveredItem deliveredItem1 = new DeliveredItem(savedInventory1,3);
        DeliveredItem deliveredItem2 = new DeliveredItem(savedInventory2,2);
        DeliveredItem deliveredItem3 = new DeliveredItem(savedInventory3,1);
        DeliveryReceipt deliveryReceipt = new DeliveryReceipt(savedSupplierModel,savedPurchaseOrder,List.of(deliveredItem1,deliveredItem2,deliveredItem3),LocalDate.now().minusDays(1),"Addiss Ababa,Ethiopia",savedUser,"received");

        deliveryReceiptRepository.save(deliveryReceipt);

        Optional<DeliveryReceipt>existedDeliveryReceipts = deliveryReceiptRepository.findByReceiptId("REC001");
        assertFalse(existedDeliveryReceipts.isPresent());
    }

    /** this is the test for Payment Reconciliation Repository impmentation. */
    @Test
    @Rollback(value = false)
    public void testSavePaymentReconciliationAndFindByPaymentId_Successfully(){

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

        Optional<PaymentReconciliation> foundReconciledPayment =  paymentReconciliationRepository.findByPaymentId(reconciledPayment.getPaymentId());
        assertTrue(foundReconciledPayment.isPresent());

    }
    @Test
    public void testSavePaymentReconciliation_WithNullPaymentId_throwException(){
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
        purchaseOrder.setDepartment(savedDepartment);
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
        paymentReconciliation.setPaymentId(null);
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

        assertThrows(DataIntegrityViolationException.class, ()->paymentReconciliationRepository.save(paymentReconciliation));
    }
    @Test
    public void testSavePaymentReconciliation_WithDuplicatePaymentId_throwException(){

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


        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setPaymentId("PAY-001");
        paymentReconciliation1.setInvoice(savedInvoice);
        paymentReconciliation1.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation1.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation1.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation1.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation1.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation1.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation1.setPaymentDate(LocalDate.now());
        paymentReconciliation1.setReconciliationDate(LocalDate.now());
        paymentReconciliation1.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation1.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);

        assertThrows(DataIntegrityViolationException.class,()->paymentReconciliationRepository.save(paymentReconciliation1));
    }
    @Test
    public void testSavePaymentReconciliation_WithNullInvoice_throwException(){

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
        paymentReconciliation.setInvoice(null);
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

        assertThrows(DataIntegrityViolationException.class,()->paymentReconciliationRepository.save(paymentReconciliation));
    }
    @Test
    public void testSavePaymentReconciliation_WithNullPurchaseOrder_throwException(){

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
        paymentReconciliation.setPurchaseOrder(null);
        paymentReconciliation.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        assertThrows(DataIntegrityViolationException.class,()->paymentReconciliationRepository.save(paymentReconciliation));
    }
    @Test
    public void testSavePaymentReconciliation_WithNullDeliveryReceipt_throwException(){

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
        paymentReconciliation.setDeliveryReceipt(null);
        paymentReconciliation.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation.setPaymentDate(LocalDate.now());
        paymentReconciliation.setReconciliationDate(LocalDate.now());
        paymentReconciliation.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        assertThrows(DataIntegrityViolationException.class,()->paymentReconciliationRepository.save(paymentReconciliation));
    }
    @Test
    public void testFindPaymentReconciliationsByInvoiceId_Successfully(){

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

        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setPaymentId("PAY-002");
        paymentReconciliation1.setInvoice(savedInvoice);
        paymentReconciliation1.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation1.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation1.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation1.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation1.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation1.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation1.setPaymentDate(LocalDate.now());
        paymentReconciliation1.setReconciliationDate(LocalDate.now());
        paymentReconciliation1.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation1.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);
        paymentReconciliationRepository.save(paymentReconciliation1);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findByInvoiceId(savedInvoice.getInvoiceId());
        assertEquals(2,paymentReconciliationList.size());
    }
    @Test
    public void testFindPaymentReconciliationByOrderId_Successfully(){

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


        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setPaymentId("PAY-002");
        paymentReconciliation1.setInvoice(savedInvoice);
        paymentReconciliation1.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation1.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation1.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation1.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation1.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation1.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation1.setPaymentDate(LocalDate.now());
        paymentReconciliation1.setReconciliationDate(LocalDate.now());
        paymentReconciliation1.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation1.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);
        paymentReconciliationRepository.save(paymentReconciliation1);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findByOrderId(savedPurchaseOrder.getOrderId());
        assertEquals(2,paymentReconciliationList.size());
    }
    @Test
    public void testFindPaymentReconciliationsByDeliveryReceiptsId_Successfully(){

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


        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setPaymentId("PAY-002");
        paymentReconciliation1.setInvoice(savedInvoice);
        paymentReconciliation1.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation1.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation1.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation1.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation1.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation1.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation1.setPaymentDate(LocalDate.now());
        paymentReconciliation1.setReconciliationDate(LocalDate.now());
        paymentReconciliation1.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation1.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);
        paymentReconciliationRepository.save(paymentReconciliation1);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findByReceiptId(savedDeliveryReceipt.getReceiptId());
        assertEquals(2,paymentReconciliationList.size());
    }
    @Test
    public void testFindPaymentReconciliationsByStatus_Successfully(){

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

        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setPaymentId("PAY-002");
        paymentReconciliation1.setInvoice(savedInvoice);
        paymentReconciliation1.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation1.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation1.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation1.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation1.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation1.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation1.setPaymentDate(LocalDate.now());
        paymentReconciliation1.setReconciliationDate(LocalDate.now());
        paymentReconciliation1.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation1.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);
        paymentReconciliationRepository.save(paymentReconciliation1);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findByStatus(ReconciliationStatus.UNDERPAID);
        assertEquals(2,paymentReconciliationList.size());
    }
    @Test
    public void testFindPaymentReconciliations_ReconciledBeforeSpecificDate_Successfully(){

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

        PaymentReconciliation paymentReconciliation1 = new PaymentReconciliation();
        paymentReconciliation1.setPaymentId("PAY-002");
        paymentReconciliation1.setInvoice(savedInvoice);
        paymentReconciliation1.setPurchaseOrder(savedPurchaseOrder);
        paymentReconciliation1.setDeliveryReceipt(savedDeliveryReceipt);
        paymentReconciliation1.setInvoiceAmount(savedInvoice.getTotalCosts());
        paymentReconciliation1.setExpectedAmount(BigDecimal.valueOf(45000.0));
        paymentReconciliation1.setActualPaidAmount(savedDeliveryReceipt.getTotalCost());
        paymentReconciliation1.setCurrency(savedInvoice.getCurrency());
        paymentReconciliation1.setPaymentDate(LocalDate.now().minusDays(15));
        paymentReconciliation1.setReconciliationDate(LocalDate.now().minusDays(5));
        paymentReconciliation1.setReconciliationStatus(ReconciliationStatus.UNDERPAID);
        paymentReconciliation1.setDiscrepancyAmount(paymentReconciliation.getExpectedAmount().subtract(paymentReconciliation.getActualPaidAmount()));

        paymentReconciliationRepository.save(paymentReconciliation);
        paymentReconciliationRepository.save(paymentReconciliation1);

        List<PaymentReconciliation> paymentReconciliationList = paymentReconciliationRepository.findAll();
        assertEquals(2, paymentReconciliationList.size());

        List<PaymentReconciliation> paymentReconciliations = paymentReconciliationRepository.findByReconciliationDateBefore(LocalDate.now().minusDays(3));
        assertEquals(1,paymentReconciliations.size());

    }
    @Test
    public void testDeletePaymentReconciliationByPaymentId_Successfully(){

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

        PaymentReconciliation savedReconciledPayment = paymentReconciliationRepository.save(paymentReconciliation);

        paymentReconciliationRepository.deleteByPaymentId(savedReconciledPayment.getPaymentId());

        Optional<PaymentReconciliation> foundReconciledPayment = paymentReconciliationRepository.findByPaymentId(savedReconciledPayment.getPaymentId());
        assertTrue(foundReconciledPayment.isEmpty());
    }
    /** this is the testing for SupplierPerformance Repository Implementation. */
    @Test
    public void testSaveSupplierPerformanceAndFindById_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance savedPerformance = supplierPerformanceRepository.save(performance);
        Optional<SupplierPerformance> foundPerformance = supplierPerformanceRepository.findById(savedPerformance.getId());
        assertTrue(foundPerformance.isPresent());
        assertEquals(SupplierPerformanceRate.EXCELLENT, savedPerformance.getSupplierPerformanceRate());
        assertEquals(LocalDate.now(), savedPerformance.getEvaluationDate());

    }
    @Test
    public void testSaveSupplierPerformance_WithNullSupplier_ThrowException(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(null);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        assertThrows(DataIntegrityViolationException.class,()->supplierPerformanceRepository.save(performance));
    }
    @Test
    public void testFindSupplierPerformnacesBySupplierID_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");


        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance performance1 = new SupplierPerformance();
        performance1.setSupplier(savedSupplierModel);
        performance1.setQualitativePerformanceMetrics(qualitativeMetricsOne);
        performance1.setQuantitativePerformanceMetrics(quantitativeMetricsOne);
        performance1.setEvaluationDate(LocalDate.now().minusDays(20));
        performance1.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance1.setEvaluatorComments("upGrade the quality");

        supplierPerformanceRepository.save(performance1);
        supplierPerformanceRepository.save(performance);

        List<SupplierPerformance> performanceList = supplierPerformanceRepository.findBySupplierId(savedSupplierModel.getSupplierId());
        assertEquals(2, performanceList.size());
    }
    @Test
    @Rollback(value = false)
    public void testFindSupplierPerformanceByPerformanceRate_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance performance1 = new SupplierPerformance();
        performance1.setSupplier(savedSupplierModel);
        performance1.setQualitativePerformanceMetrics(qualitativeMetricsOne);
        performance1.setQuantitativePerformanceMetrics(quantitativeMetricsOne);
        performance1.setEvaluationDate(LocalDate.now().minusDays(20));
        performance1.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance1.setEvaluatorComments("upGrade the quality");

        supplierPerformanceRepository.save(performance1);
        supplierPerformanceRepository.save(performance);

        List<SupplierPerformance> performanceList = supplierPerformanceRepository.findByPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        assertEquals(2, performanceList.size());
    }
    @Test
    public void testFindSupplierPerformancesEvaluatedAfterSpecificDate_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance performance1 = new SupplierPerformance();
        performance1.setSupplier(savedSupplierModel);
        performance1.setQualitativePerformanceMetrics(qualitativeMetricsOne);
        performance1.setQuantitativePerformanceMetrics(quantitativeMetricsOne);
        performance1.setEvaluationDate(LocalDate.now().minusDays(20));
        performance1.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance1.setEvaluatorComments("upGrade the quality");

        supplierPerformanceRepository.save(performance1);
        supplierPerformanceRepository.save(performance);

        List<SupplierPerformance> performanceList = supplierPerformanceRepository.findByEvaluationDateAfter(LocalDate.now().minusDays(10));
        assertEquals(1, performanceList.size());
    }
    @Test
    public void testFindSupplierPerformancesEvaluatedBeforeSpecificDate_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");

        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        inventoryRepository.findByItemIdAndSupplierId(item.getItemId(), savedSupplierModel.getId()).orElseThrow();
        inventoryRepository.findByItemIdAndSupplierId(item1.getItemId(), savedSupplierModel.getId()).orElseThrow();
        inventoryRepository.findByItemIdAndSupplierId(item2.getItemId(), savedSupplierModel.getId()).orElseThrow();

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);


        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance performance1 = new SupplierPerformance();
        performance1.setSupplier(savedSupplierModel);
        performance1.setQualitativePerformanceMetrics(qualitativeMetricsOne);
        performance1.setQuantitativePerformanceMetrics(quantitativeMetricsOne);
        performance1.setEvaluationDate(LocalDate.now().minusDays(20));
        performance1.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance1.setEvaluatorComments("upGrade the quality");

        supplierPerformanceRepository.save(performance1);
        supplierPerformanceRepository.save(performance);

        List<SupplierPerformance> performanceList = supplierPerformanceRepository.findByEvaluationDateBefore(LocalDate.now().minusDays(10));
        assertEquals(1, performanceList.size());
    }
    @Test
    public void testDeleteSupplierPerformanceById_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);

        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance savedPerformance = supplierPerformanceRepository.save(performance);

        supplierPerformanceRepository.deleteById(savedPerformance.getId());
        Optional<SupplierPerformance> foundPerformance = supplierPerformanceRepository.findById(savedPerformance.getId());
        assertTrue(foundPerformance.isEmpty());
    }
    @Test
    public void testDeletePerformanceBySupplierId_Successfully(){
        Inventory item = new Inventory("sn101", "Laptop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "new generations");
        Inventory item1 = new Inventory("sn102", "Desktop", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusYears(5), "Hp models");
        Inventory item2 = new Inventory("sn103", "Switch", 10, BigDecimal.valueOf(10000.0), "Electronics", LocalDate.now().plusMonths(10), "new brand with 32 ports");


        SupplierContactDetail supplierContactDetail = new SupplierContactDetail("tsegay berhe", "tsegay@cisco.com", "+251979421531", "Kenya");
        SupplierPaymentMethod supplierPaymentMethod = new SupplierPaymentMethod("Brhan Bank", "09129284395565",supplierContactDetail.getFullName(),supplierContactDetail.getPhone(),  List.of(CREDIT_CARD, BANK_TRANSFER, PAYPAL), CREDIT_CARD, NET_30, "USD",  BigDecimal.valueOf(10000.0));

        Supplier supplierModel = new Supplier("IBM", "Vendor", "17636", "412434", supplierContactDetail, List.of(supplierPaymentMethod), List.of(item, item1, item2), LocalDate.now());
        supplierModel.setSupplierId("SUP-001");

        Supplier savedSupplierModel = supplierRepository.save(supplierModel);
        SupplierQuantitativePerformanceMetrics quantitativePerformanceMetrics = new SupplierQuantitativePerformanceMetrics(95, 2, 96, 90, 85, 1, 0);
        SupplierQualitativePerformanceMetrics qualitativePerformanceMetrics = new SupplierQualitativePerformanceMetrics(9, 8, 7, 9, 8, 10);

        SupplierQuantitativePerformanceMetrics quantitativeMetricsOne = new SupplierQuantitativePerformanceMetrics(95, 2, 90, 94, 92, 85, 14);

        SupplierQualitativePerformanceMetrics qualitativeMetricsOne = new SupplierQualitativePerformanceMetrics(4, 3, 10, 8, 9, 9);

        SupplierPerformance performance = new SupplierPerformance();
        performance.setSupplier(savedSupplierModel);
        performance.setQuantitativePerformanceMetrics(quantitativePerformanceMetrics);
        performance.setQualitativePerformanceMetrics(qualitativePerformanceMetrics);
        performance.setEvaluationDate(LocalDate.now());
        performance.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance.setEvaluatorComments("Keep It Up.");

        SupplierPerformance performance1 = new SupplierPerformance();
        performance1.setSupplier(savedSupplierModel);
        performance1.setQualitativePerformanceMetrics(qualitativeMetricsOne);
        performance1.setQuantitativePerformanceMetrics(quantitativeMetricsOne);
        performance1.setEvaluationDate(LocalDate.now().minusDays(20));
        performance1.setSupplierPerformanceRate(SupplierPerformanceRate.EXCELLENT);
        performance1.setEvaluatorComments("upGrade the quality");

        supplierPerformanceRepository.save(performance1);
        supplierPerformanceRepository.save(performance);

        supplierPerformanceRepository.deleteBySupplierId(savedSupplierModel.getSupplierId());

        List<SupplierPerformance> performanceList = supplierPerformanceRepository.findBySupplierId(savedSupplierModel.getSupplierId());
        assertEquals(0, performanceList.size());
    }

    //this is the testing for ReportAnalysis Repository implementation.
    @Test
    @Rollback(value = false)
    public void testSaveProcurementReportWithVarietyOfActivitiesSuccessfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                        .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                        .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                        .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setReportId("RP-001");
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setSupplierReport(supplierReport);
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        ProcurementReport savedReport = procurementReportRepository.save(procurementReport);
        assertEquals(procurementReport.getReportId(), savedReport.getReportId());
        assertNotNull(savedReport.getId());
        assertNotNull(purchaseRequisitionReportRepository.findById(savedReport.getRequisitionReport().getId()));
        assertNotNull(supplierReportRepository.findById(savedReport.getSupplierReport().getId()));
        assertEquals(supplierReport.getActiveSuppliers(), savedReport.getSupplierReport().getActiveSuppliers());
        assertEquals(purchaseRequisitionReport.getTotalRequisitions(), savedReport.getRequisitionReport().getTotalRequisitions());
    }
    @Test
    public void testFindProcurementReportByReportIdSuccessfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setReportId("RP-001");
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setSupplierReport(supplierReport);
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        ProcurementReport savedReport = procurementReportRepository.save(procurementReport);

        Optional<ProcurementReport> foundReport = procurementReportRepository.findByReportId(savedReport.getReportId());
        assertTrue(foundReport.isPresent());
        assertEquals(procurementReport.getReportId(), foundReport.get().getReportId());
    }
    @Test
    public void testDeleteProcurementReportSuccessfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(Supplier::getSupplierCategory, supplier -> 1, Integer::sum));

        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setReportId("RP-001");
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setSupplierReport(supplierReport);
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        ProcurementReport savedReport = procurementReportRepository.save(procurementReport);


        Optional<ProcurementReport> foundReport = procurementReportRepository.findByReportId(savedReport.getReportId());
        assertTrue(foundReport.isPresent());

        procurementReportRepository.deleteById(savedReport.getId());
        Optional<ProcurementReport> deletedReport = procurementReportRepository.findById(savedReport.getId());
        assertFalse(deletedReport.isPresent());
    }
    @Test
    public void testSaveProcurementReportUnsuccessfullyDuetoNullValue(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        assertThrows(InvalidDataAccessApiUsageException.class, () -> procurementReportRepository.save(null));
    }
    @Test
    public void testSaveProcurementReportUnsuccessfullyDuetoMissingElement(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(Supplier::getSupplierCategory, supplier -> 1, Integer::sum));

        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setSupplierReport(supplierReport);
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        assertThrows(DataIntegrityViolationException.class, () -> procurementReportRepository.save(procurementReport));
    }
    @Test
    public void testFindProcurementReportUnsuccessfullyDuetoNon_ExistedReport(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setReportId("RP-001");
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setSupplierReport(supplierReport);
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        ProcurementReport savedReport = procurementReportRepository.save(procurementReport);

        Optional<ProcurementReport> foundReport = procurementReportRepository.findByReportId("Non-Existed-ID");
        assertFalse(foundReport.isPresent());
    }

    @Test
    public void testFindAllProcurementReportSuccessfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setReportId("RP-001");
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        ProcurementReport procurementReport2 = new ProcurementReport();
        procurementReport2.setReportId("RP-002");
        procurementReport2.setTitle("Monthly Procurement Report");
        procurementReport2.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport2.setCreatedBy(savedUser);
        procurementReport2.setCreatedAt(LocalDate.now());
        procurementReport2.setSupplierReport(supplierReport);

        ProcurementReport savedReport = procurementReportRepository.save(procurementReport);
        ProcurementReport savedReport2 = procurementReportRepository.save(procurementReport2);

        List<ProcurementReport> reports = procurementReportRepository.findAll();
        assertEquals(2, reports.size());

        Optional<ProcurementReport> foundReport = procurementReportRepository.findByReportId(savedReport.getReportId());
        assertTrue(foundReport.isPresent());

        Optional<ProcurementReport> foundReport2 = procurementReportRepository.findByReportId(savedReport2.getReportId());
        assertTrue(foundReport2.isPresent());

    }
    @Test
    public void deleteAllProcurementReportSuccessfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        ProcurementReport procurementReport = new ProcurementReport();
        procurementReport.setReportId("RP-001");
        procurementReport.setTitle("Monthly Procurement Report");
        procurementReport.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport.setCreatedBy(savedUser);
        procurementReport.setCreatedAt(LocalDate.now());
        procurementReport.setRequisitionReport(purchaseRequisitionReport);

        ProcurementReport procurementReport2 = new ProcurementReport();
        procurementReport2.setReportId("RP-002");
        procurementReport2.setTitle("Monthly Procurement Report");
        procurementReport2.setReportDescription("This report provides an overview of the procurement activities for the month of January 2025.");
        procurementReport2.setCreatedBy(savedUser);
        procurementReport2.setCreatedAt(LocalDate.now());
        procurementReport2.setSupplierReport(supplierReport);

        ProcurementReport savedReport = procurementReportRepository.save(procurementReport);
        ProcurementReport savedReport2 = procurementReportRepository.save(procurementReport2);

        List<ProcurementReport> reports = procurementReportRepository.findAll();
        assertEquals(2, reports.size());

        procurementReportRepository.deleteAll();

        List<ProcurementReport> deletedReports = procurementReportRepository.findAll();
        assertEquals(0, deletedReports.size());
    }

    @Test
    @Rollback(false)
    public void testSaveReportTemplate_WithValidDataSuccessfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        System.out.println(savedReportTemplate);
        assertNotNull(savedReportTemplate.getId());
        assertEquals(savedReportTemplate.getTemplateName(), "Standard Procurement Report Template");
        assertEquals(savedReportTemplate.getCreatedBy().getFullName(), savedUser.getFullName());
        assertEquals(savedReportTemplate.getCreatedAt(), LocalDate.now());
    }
    @Test
    public void testSaveReportTemplate_WithNullData_ThrowException(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        System.out.println(savedReportTemplate);
        assertNotNull(savedReportTemplate.getId());
        assertEquals(savedReportTemplate.getTemplateName(), "Standard Procurement Report Template");
        assertEquals(savedReportTemplate.getCreatedBy().getFullName(), savedUser.getFullName());
        assertEquals(savedReportTemplate.getCreatedAt(), LocalDate.now());

    }
    @Test
    public void testSaveReportTemplate_WithMissingElement_ThrowException(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        System.out.println(savedReportTemplate);
        assertNotNull(savedReportTemplate.getId());
        assertEquals(savedReportTemplate.getTemplateName(), "Standard Procurement Report Template");
        assertEquals(savedReportTemplate.getCreatedBy().getFullName(), savedUser.getFullName());
        assertEquals(savedReportTemplate.getCreatedAt(), LocalDate.now());
    }
    @Test
    public void testFindReportTemplateByReportId_Successfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        System.out.println(savedReportTemplate);
        assertNotNull(savedReportTemplate.getId());
        assertEquals(savedReportTemplate.getTemplateName(), "Standard Procurement Report Template");
        assertEquals(savedReportTemplate.getCreatedBy().getFullName(), savedUser.getFullName());
        assertEquals(savedReportTemplate.getCreatedAt(), LocalDate.now());
    }
    @Test
    public void testFindReportTemplateById_Successfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        Optional<ReportTemplate> reportTemplateOptional = reportTemplateRepository.findBytemplateId(savedReportTemplate.getTemplateId());
        assertTrue(reportTemplateOptional.isPresent());
        assertEquals(reportTemplateOptional.get().getTemplateName(), "Standard Procurement Report Template");
    }
    @Test
    public void testFindReportTemplate_WithNullTemplateId_throwException(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

       reportTemplateRepository.save(reportTemplate);
        Optional<ReportTemplate> reportTemplateOptional = reportTemplateRepository.findBytemplateId(null);
        assertTrue(reportTemplateOptional.isEmpty());

    }
    @Test
    public void testFindReportTemplate_WithNonExistedTemplateId_throwException(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(Supplier::getSupplierCategory, supplier -> 1, Integer::sum));

        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        Optional<ReportTemplate> reportTemplateOptional = reportTemplateRepository.findBytemplateId("RT-999");
        assertTrue(reportTemplateOptional.isEmpty());
    }
    @Test
    public void testDeleteReportTemplateById_Successfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        Optional<ReportTemplate> reportTemplateOptional = reportTemplateRepository.findBytemplateId(savedReportTemplate.getTemplateId());
        assertTrue(reportTemplateOptional.isPresent());
        reportTemplateRepository.deleteById(savedReportTemplate.getId());
        Optional<ReportTemplate> deletedTemplate = reportTemplateRepository.findBytemplateId(savedReportTemplate.getTemplateId());
        assertFalse(deletedTemplate.isPresent());
    }
    @Test
    @Rollback(false)
    public void testDeleteReportTemplate_WithNonExistedTemplateId_throwException(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(Supplier::getSupplierCategory, supplier -> 1, Integer::sum));

        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");

        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);
        reportTemplateRepository.save(reportTemplate);
        assertEquals(1, reportTemplateRepository.findAll().size());
        reportTemplateRepository.deleteBytemplateId("nonExistedId");
        assertEquals(1, reportTemplateRepository.findAll().size());
    }
    @Test
    public void testFindAllReportTemplate_Successfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        List<String> selectedFields2 = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);
        ReportTemplate reportTemplate2 = new ReportTemplate();
        reportTemplate2.setTemplateId("RT-002");
        reportTemplate2.setTemplateName("Advanced Procurement Report Template");
        reportTemplate2.setTemplateDescription("An advanced template for monthly procurement reports.");
        reportTemplate2.setCreatedBy(savedUser);
        reportTemplate2.setCreatedAt(LocalDate.now());
        reportTemplate2.setSelectedFields(selectedFields2);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        ReportTemplate savedReportTemplate2 = reportTemplateRepository.save(reportTemplate2);

        List<ReportTemplate> reportTemplates = reportTemplateRepository.findAll();

        assertEquals(2, reportTemplates.size());


    }

    @Test
    public void testDeleteAllReportTemplate_Successfully(){
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

        Long totalSuppliers = supplierRepository.findAll().stream().count();
        Long activeSuppliers = supplierRepository.findAll().stream().map(Supplier::isActive).collect(Collectors.toList()).stream().count();
        Map<String, Integer> supplierCounByCategory = supplierRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Supplier::getSupplierCategory,
                        supplier -> 1,
                        Integer::sum // merge function if names repeat
                ));
        List<Supplier> supplierDetails = supplierRepository.findAll();
        SupplierReport supplierReport = new SupplierReport();
        supplierReport.setTotalSuppliers(totalSuppliers);
        supplierReport.setActiveSuppliers(activeSuppliers);
        supplierReport.setSuppliersByCategory(supplierCounByCategory);

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

        List<PurchaseRequisition> requisitions = purchaseRequisitionRepository.findAll();
        PurchaseRequisitionReport purchaseRequisitionReport = new PurchaseRequisitionReport();
        purchaseRequisitionReport.setTotalRequisitions(requisitions.stream().count());

        purchaseRequisitionReport.setTotalSpendingAmount(requisitions.stream()
                .map(PurchaseRequisition::getTotalEstimatedCosts).reduce(BigDecimal.ZERO, BigDecimal::add));

        purchaseRequisitionReport.setTotalRequestedItems(requisitions.stream().mapToLong(req -> req.getItems().size()).sum());

        purchaseRequisitionReport.setRequisitionsByPriority(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getPriorityLevel, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionStatusMap(requisitions.stream()
                .collect(Collectors.toMap(PurchaseRequisition::getRequisitionStatus, req -> 1, Integer::sum)));

        purchaseRequisitionReport.setRequisitionsByDepartment(requisitions.stream()
                .collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), req -> 1, Integer::sum)));

        purchaseRequisitionReport.setTotalSpendingPerDepartment(requisitions.stream().
                collect(Collectors.toMap(req -> req.getDepartment().getDepartmentId(), PurchaseRequisition::getTotalEstimatedCosts, BigDecimal::add)));

        List<String> selectedFields = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        List<String> selectedFields2 = List.of("totalRequisitions","totalSpendingAmount","requisitionsByPriority","requisitionsByDepartment","requisitionsByStatus","totalSupplier","activeSupplier");
        ReportTemplate reportTemplate = new ReportTemplate();
        reportTemplate.setTemplateId("RT-001");
        reportTemplate.setTemplateName("Standard Procurement Report Template");
        reportTemplate.setTemplateDescription("A standard template for monthly procurement reports.");
        reportTemplate.setCreatedBy(savedUser);
        reportTemplate.setCreatedAt(LocalDate.now());
        reportTemplate.setSelectedFields(selectedFields);
        ReportTemplate reportTemplate2 = new ReportTemplate();
        reportTemplate2.setTemplateId("RT-002");
        reportTemplate2.setTemplateName("Advanced Procurement Report Template");
        reportTemplate2.setTemplateDescription("An advanced template for monthly procurement reports.");
        reportTemplate2.setCreatedBy(savedUser);
        reportTemplate2.setCreatedAt(LocalDate.now());
        reportTemplate2.setSelectedFields(selectedFields2);

        ReportTemplate savedReportTemplate = reportTemplateRepository.save(reportTemplate);
        ReportTemplate savedReportTemplate2 = reportTemplateRepository.save(reportTemplate2);

        List<ReportTemplate> reportTemplates = reportTemplateRepository.findAll();

        assertEquals(2, reportTemplates.size());

        reportTemplateRepository.deleteAll();
        List<ReportTemplate> deletedTemplates = reportTemplateRepository.findAll();
        assertEquals(0, deletedTemplates.size());
    }
}
