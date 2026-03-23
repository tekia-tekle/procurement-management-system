package com.bizeff.procurement.usecases.procurementReport;

import com.bizeff.procurement.domaininterfaces.inputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.GenerateMonthlyApprovedPurchaseOrderReportInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.procurementReport.ReporterContactDetail;
import com.bizeff.procurement.domaininterfaces.outputboundary.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.procurementreport.GenerateMonthlyApprovedPurchaseOrderReportOutPutDS;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.procurementreport.ProcurementReportRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaseorder.PurchaseOrderRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.PurchaseRequisitionRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.UserRepository;
import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.suppliermanagement.SupplierRepository;
import com.bizeff.procurement.models.enums.PurchaseOrderStatus;
import com.bizeff.procurement.models.procurementreport.ProcurementReport;
import com.bizeff.procurement.models.procurementreport.PurchaseOrderReport;
import com.bizeff.procurement.models.purchaseorder.PurchaseOrder;
import com.bizeff.procurement.models.purchaserequisition.PurchaseRequisition;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.services.procurementreport.ProcurementReportGenerationServices;

import java.util.List;
import java.util.stream.Collectors;

import static com.bizeff.procurement.services.invoicepaymentreconciliation.ContractPaymentTermsEnforcementService.validateDateOrder;
import static com.bizeff.procurement.services.supplymanagement.SupplierMaintenanceService.*;

public class GenerateMonthlyApprovedPurchaseOrderReportUseCase implements GenerateMonthlyApprovedPurchaseOrderReportInputBoundary {
    private PurchaseOrderRepository purchaseOrderRepository;
    private SupplierRepository supplierRepository;
    private DepartmentRepository departmentRepository;
    private ProcurementReportRepository procurementReportRepository;
    private PurchaseRequisitionRepository purchaseRequisitionRepository;
    private UserRepository userRepository;
    private ProcurementReportGenerationServices procurementReportGenerationServices;
    private GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary generateMonthlyApprovedPurchaseOrderReportOutputBoundary;
    public GenerateMonthlyApprovedPurchaseOrderReportUseCase(
            ProcurementReportRepository procurementReportRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            SupplierRepository supplierRepository,
            PurchaseRequisitionRepository purchaseRequisitionRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            ProcurementReportGenerationServices procurementReportGenerationServices,
            GenerateMonthlyApprovedPurchaseOrderReportOutputBoundary generateMonthlyApprovedPurchaseOrderReportOutputBoundary) {
        this.procurementReportRepository = procurementReportRepository;
        this.supplierRepository = supplierRepository;
        this.purchaseRequisitionRepository = purchaseRequisitionRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.procurementReportGenerationServices = procurementReportGenerationServices;
        this.generateMonthlyApprovedPurchaseOrderReportOutputBoundary = generateMonthlyApprovedPurchaseOrderReportOutputBoundary;
    }

    @Override
    public GenerateMonthlyApprovedPurchaseOrderReportOutPutDS generateMonthlyApprovedPurchaseOrderReport(GenerateMonthlyApprovedPurchaseOrderReportInputDS inputDS){
        validateInputDS(inputDS);
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll();
        if (purchaseOrderList == null || purchaseOrderList.isEmpty()){
            throw new IllegalArgumentException("Purchase Order List is empty");
        }

        List<PurchaseOrder> approvedPurchaseOrderList = purchaseOrderList.stream()
                .filter(purchaseOrder -> purchaseOrder.getPurchaseOrderStatus().equals(PurchaseOrderStatus.APPROVED))
                .collect(Collectors.toList());
        if (approvedPurchaseOrderList == null || approvedPurchaseOrderList.isEmpty()){
            throw new IllegalArgumentException("there is no approved purchase order.");
        }

        for (PurchaseOrder purchaseOrder : approvedPurchaseOrderList) {
            boolean matchedSupplier = supplierRepository.findAll().stream().anyMatch(supplier -> supplier.getSupplierId().equals(purchaseOrder.getSupplier().getSupplierId()));
            if (!matchedSupplier){
                throw new IllegalArgumentException("Supplier not found for the given purchase order.");
            }
            boolean matchedDepartment = departmentRepository.findAll().stream().anyMatch(department -> department.getDepartmentId().equals(purchaseOrder.getDepartment().getDepartmentId()));
            if (!matchedDepartment){
                throw new IllegalArgumentException("Department not found for the given purchase order.");
            }
            List<PurchaseRequisition> requisitionList = purchaseOrder.getRequisitionList();
            if (requisitionList == null || requisitionList.isEmpty()){
                throw new IllegalArgumentException("Requisition list is empty for the given purchase order.");
            }
            for (PurchaseRequisition requisition : requisitionList) {
                boolean matchedRequisition = purchaseRequisitionRepository.findAll().stream().anyMatch(requisition1 -> requisition1.getRequisitionId().equals(requisition.getRequisitionId()));
                if (!matchedRequisition){
                    throw new IllegalArgumentException("Requisition not found for the given purchase order.");
                }
            }

            procurementReportGenerationServices.getPurchaseOrders().add(purchaseOrder);
            procurementReportGenerationServices.getRequisitions().addAll(requisitionList);
            procurementReportGenerationServices.getSuppliers().add(purchaseOrder.getSupplier());
        }
        User user = userRepository.findByEmail(inputDS.getReporterContactDetail().getEmail()).orElseThrow(()-> new IllegalArgumentException("there is no user that is stored with the given email."));
        ProcurementReport procurementReport = procurementReportGenerationServices.generateTimeBasedProcurementReport(user,List.of(inputDS.getProcurementActivity()),inputDS.getStartDate(),inputDS.getEndDate(),inputDS.getReportedDate(),inputDS.getReportTitle(),inputDS.getReportDescription());
        ProcurementReport savedProcurementReport = procurementReportRepository.save(procurementReport);
        if (savedProcurementReport.getPurchaseOrderReport() == null){
            throw new IllegalArgumentException("Purchase Order Report is null");
        }
        PurchaseOrderReport purchaseOrderReport = savedProcurementReport.getPurchaseOrderReport();

        GenerateMonthlyApprovedPurchaseOrderReportOutPutDS outputDS = new GenerateMonthlyApprovedPurchaseOrderReportOutPutDS(
                savedProcurementReport.getReportId(),
                savedProcurementReport.getTitle(),
                savedProcurementReport.getReportDescription(),
                inputDS.getStartDate(),
                inputDS.getEndDate(),
                purchaseOrderReport.getTotalPurchaseOrders(),
                purchaseOrderReport.getTotalPurchaseOrderCosts(),
                purchaseOrderReport.getPurchaseOrderTotalSpendPerSupplier(),
                purchaseOrderReport.getPurchaseOrdersForSupplier(),
                purchaseOrderReport.getPurchaseOrdersByDepartment(),
                purchaseOrderReport.getPurchaseOrderTotalSpendPerDepartment());

        generateMonthlyApprovedPurchaseOrderReportOutputBoundary.generateMonthlyApprovedPurchaseOrderReport(outputDS);
        return outputDS;
    }

    private void validateInputDS(GenerateMonthlyApprovedPurchaseOrderReportInputDS inputDS){
        if (inputDS == null){
            throw new IllegalArgumentException("Input Data Source is null");
        }
        validateReporterContractDetail(inputDS.getReporterContactDetail());
        validateDateOrder(inputDS.getStartDate(),inputDS.getEndDate());
        validateString(inputDS.getReportTitle(),"Report Title");
        validateDate(inputDS.getReportedDate(),"Reported Date");
        validateDateOrder(inputDS.getEndDate(),inputDS.getReportedDate());
        validateNotNull(inputDS.getProcurementActivity(),"Procurement Activity");
    }
    public void validateReporterContractDetail(ReporterContactDetail reporterContactDetail){
        if (reporterContactDetail == null){
            throw new IllegalArgumentException("Reporter Contact Detail is null");
        }
        if (reporterContactDetail.getFullName() == null || reporterContactDetail.getFullName().trim().isEmpty()){
            throw new IllegalArgumentException("Reporter Contact Detail is required and must have a valid name.");
        }
        if (reporterContactDetail.getEmail() == null || reporterContactDetail.getEmail().trim().isEmpty()){
            throw new IllegalArgumentException("Reporter Contact Detail is required and must have a valid email.");
        }
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!reporterContactDetail.getEmail().matches(emailRegex)){
            throw new IllegalArgumentException("Reporter Contact Detail is required and must have a valid email.");
        }
        if (reporterContactDetail.getPhoneNumber() == null || reporterContactDetail.getPhoneNumber().trim().isEmpty()){
            throw new IllegalArgumentException("Reporter Contact Detail is required and must have a valid phone number.");
        }
        String phoneRegex = "^\\+\\d{1,3}\\d{4,14}$";
        if (!reporterContactDetail.getPhoneNumber().matches(phoneRegex)){
            throw new IllegalArgumentException("Reporter Contact Detail is required and must have a valid phone number.");
        }
        if (reporterContactDetail.getRole() == null || reporterContactDetail.getRole().trim().isEmpty()){
            throw new IllegalArgumentException("Reporter Contact Detail is required and must have a valid role.");
        }
    }
}
