package com.bizeff.procurement.domaininterfaces.inputboundary.suppliermanagement;
import com.bizeff.procurement.domaininterfaces.inputds.suppliermanagement.AddSupplierInputDS;
import com.bizeff.procurement.domaininterfaces.outputds.suppliermanagement.AddSupplierOutputDS;
public interface AddSupplierInputBoundary {
    AddSupplierOutputDS addSupplier(AddSupplierInputDS inputData);
}
