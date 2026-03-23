package com.bizeff.procurement.webapi.controller;

import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.AddSupplierInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.UpdateSupplierContactDetailInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement.ViewSupplierPerformanceReportInputBoundary;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.AddSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.UpdateSupplierContactDetailInputDS;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.ViewSupplierPerformanceReportInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.AddSupplierOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.UpdateSupplierContactDetailedOutputDS;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.ViewSupplierPerformanceReportOutputDS;
import com.bizeff.procurement.webapi.presenter.suppliermanagement.AddSupplierPresenter;
import com.bizeff.procurement.webapi.presenter.suppliermanagement.UpdatedSupplierDetailPresenter;
import com.bizeff.procurement.webapi.presenter.suppliermanagement.ViewSupplierPerformancePresenter;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.AddedSupplierViewModel;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.UpdatedVendorDetailViewModel;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.ViewSupplierPerformanceViewModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    private AddSupplierInputBoundary addSupplierInputBoundary;
    private AddSupplierPresenter addSupplierPresenter;
    private UpdateSupplierContactDetailInputBoundary vendorsDetailsInputBoundary;
    private UpdatedSupplierDetailPresenter updatedSupplierDetailPresenter;
    private ViewSupplierPerformanceReportInputBoundary vendorsPerformanceInputBoundary;
    private ViewSupplierPerformancePresenter viewSupplierPerformancePresenter;
    public SupplierController(AddSupplierInputBoundary addSupplierInputBoundary,
                              AddSupplierPresenter addSupplierPresenter,
                              UpdateSupplierContactDetailInputBoundary vendorsDetailsInputBoundary,
                              UpdatedSupplierDetailPresenter updatedSupplierDetailPresenter,
                              ViewSupplierPerformanceReportInputBoundary vendorsPerformanceInputBoundary,
                              ViewSupplierPerformancePresenter viewSupplierPerformancePresenter) {
        this.addSupplierInputBoundary = addSupplierInputBoundary;
        this.addSupplierPresenter = addSupplierPresenter;
        this.vendorsDetailsInputBoundary = vendorsDetailsInputBoundary;
        this.updatedSupplierDetailPresenter = updatedSupplierDetailPresenter;
        this.vendorsPerformanceInputBoundary = vendorsPerformanceInputBoundary;
        this.viewSupplierPerformancePresenter = viewSupplierPerformancePresenter;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addSupplier(@RequestBody AddSupplierInputDS supplierInputDS){

        AddSupplierOutputDS createdVendor = addSupplierInputBoundary.addSupplier(supplierInputDS);

        addSupplierPresenter.presentAddedSupplier(createdVendor);

        AddedSupplierViewModel vendorOfficerViewModel = addSupplierPresenter.getVendorOfficerViewModel();

        return ResponseEntity.ok(vendorOfficerViewModel);
    }
    @PostMapping("/update")
    public ResponseEntity<?> updateVendorDetail(@RequestBody UpdateSupplierContactDetailInputDS supplierContactDetailInputDS){

        UpdateSupplierContactDetailedOutputDS updatedVendorDetatil = vendorsDetailsInputBoundary.updateSupplierDetails(supplierContactDetailInputDS);

        updatedSupplierDetailPresenter.presentUpdatedSupplier(updatedVendorDetatil);

        UpdatedVendorDetailViewModel updatedVendorDetailViewModel = updatedSupplierDetailPresenter.getUpdatedVendorDetailViewModel();

        return ResponseEntity.ok(updatedVendorDetailViewModel);
    }
    @GetMapping("/view-performance")
    public ResponseEntity<List<ViewSupplierPerformanceViewModel>> viewSupplierPerfotmances(
            @RequestParam String category,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){

        ViewSupplierPerformanceReportInputDS inputDS = new ViewSupplierPerformanceReportInputDS(category,startDate,endDate);
        List<ViewSupplierPerformanceReportOutputDS> supplierPErformanceLists = vendorsPerformanceInputBoundary.viewSupplierPerformances(inputDS);
        viewSupplierPerformancePresenter.presentSuppliersBasedOnPerformance(supplierPErformanceLists);
        List<ViewSupplierPerformanceViewModel> viewSupplierPerformanceViewModel = viewSupplierPerformancePresenter.getViewSupplierPerformanceViewModels();

        return  ResponseEntity.ok(viewSupplierPerformanceViewModel);
    }
}
