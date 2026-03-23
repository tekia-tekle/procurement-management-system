package com.bizeff.procurement.webapi.presenter.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.UpdateSupplierContactDetailOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.UpdateSupplierContactDetailedOutputDS;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.UpdatedVendorDetailViewModel;
import org.springframework.stereotype.Component;

@Component
public class UpdatedSupplierDetailPresenter implements UpdateSupplierContactDetailOutputBoundary {

    private UpdatedVendorDetailViewModel updatedVendorDetailViewModel;
    public UpdatedSupplierDetailPresenter(UpdatedVendorDetailViewModel updatedVendorDetailViewModel){
        this.updatedVendorDetailViewModel = updatedVendorDetailViewModel;
    }
    @Override
    public void presentUpdatedSupplier(UpdateSupplierContactDetailedOutputDS supplierContactDetailedOutputDS){
        UpdatedVendorDetailViewModel updatedVendorDetailViewModel1 = new UpdatedVendorDetailViewModel(supplierContactDetailedOutputDS.getSupplierId(),
                supplierContactDetailedOutputDS.getSupplierName(),
                supplierContactDetailedOutputDS.getSupplierCategory(),
                supplierContactDetailedOutputDS.getTaxIdentificationNumber(),
                supplierContactDetailedOutputDS.getRegistrationNumber(),
                supplierContactDetailedOutputDS.getRegistrationDate().toString(),
                supplierContactDetailedOutputDS.getContactDetail(),
                supplierContactDetailedOutputDS.getPaymentMethods(),
                supplierContactDetailedOutputDS.getExistedItems(),
                supplierContactDetailedOutputDS.getUpdatedDate().toString(),
                supplierContactDetailedOutputDS.getUpdated());

        this.updatedVendorDetailViewModel = updatedVendorDetailViewModel1;
    }
    public UpdatedVendorDetailViewModel getUpdatedVendorDetailViewModel() {
        return updatedVendorDetailViewModel;
    }
    @Override
    public String toString() {
        return "UpdatedSupplierDetailPresenter{" +
                "updatedVendorDetailViewModel=" + updatedVendorDetailViewModel +
                '}';
    }
}
