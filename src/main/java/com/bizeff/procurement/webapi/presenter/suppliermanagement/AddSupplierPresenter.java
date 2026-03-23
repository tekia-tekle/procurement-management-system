package com.bizeff.procurement.webapi.presenter.suppliermanagement;

import com.bizeff.procurement.domaininterfaces.outputboundary.suppliermanagement.AddSupplierOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.AddSupplierOutputDS;
import com.bizeff.procurement.webapi.viewmodel.suppliermanagement.AddedSupplierViewModel;
import org.springframework.stereotype.Component;

@Component
public class AddSupplierPresenter implements AddSupplierOutputBoundary {

    private AddedSupplierViewModel addedSupplierViewModel;
    public AddSupplierPresenter(AddedSupplierViewModel addedSupplierViewModel){
        this.addedSupplierViewModel = addedSupplierViewModel;
    }

    @Override
    public void presentAddedSupplier(AddSupplierOutputDS output) {
        this.addedSupplierViewModel = new AddedSupplierViewModel(
                output.getSupplierId(),
                output.getSupplierName(),
                output.getSupplierCategory(),
                output.getTaxIdentificationNumber(),
                output.getRegistrationNumber(),
                output.getContactDetail(),
                output.getPaymentMethods(),
                output.getExistedItems(),
                output.isActive(),
                output.getRegistrationDate().toString(),
                output.getLastUpdated().toString());
    }

    public AddedSupplierViewModel getVendorOfficerViewModel() {
        return addedSupplierViewModel;
    }

    @Override
    public String toString() {
        return "AddSupplierPresenter{" +
                "addedSupplierViewModel=" + addedSupplierViewModel +
                '}';
    }
}
