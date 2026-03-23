package com.bizeff.procurement.webapi.presenter.supplierPerformancetracking;

import com.bizeff.procurement.domaininterfaces.outputboundary.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputBoundary;
import com.bizeff.procurement.domaininterfaces.outputds.supplierperformancetracking.EvaluateSupplierForContractRenewalOutputDS;
import com.bizeff.procurement.webapi.viewmodel.supplierPerformancetracking.EvaluatedSupplierForContractRenewalViewModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EvaluateSupplierForContractRenewalPresenter implements EvaluateSupplierForContractRenewalOutputBoundary{
    private List<EvaluatedSupplierForContractRenewalViewModel> evaluatedSupplierForContractRenewalViewModels;

    public EvaluateSupplierForContractRenewalPresenter(List<EvaluatedSupplierForContractRenewalViewModel> evaluatedSupplierForContractRenewalViewModels) {
        this.evaluatedSupplierForContractRenewalViewModels = evaluatedSupplierForContractRenewalViewModels;
    }

    @Override
    public void presentRenewalContract(List<EvaluateSupplierForContractRenewalOutputDS> evaluateSupplierForContractRenewalOutputs) {
        List<EvaluatedSupplierForContractRenewalViewModel> evaluatedSupplierForContractRenewalViewModelList = new ArrayList<>();

        for (EvaluateSupplierForContractRenewalOutputDS evaluateSupplierForContractRenewalOutputDS : evaluateSupplierForContractRenewalOutputs) {
            EvaluatedSupplierForContractRenewalViewModel evaluatedSupplierForContractRenewalViewModel = new EvaluatedSupplierForContractRenewalViewModel(
                    evaluateSupplierForContractRenewalOutputDS.getContractId(),
                    evaluateSupplierForContractRenewalOutputDS.getOldSupplierId(),
                    evaluateSupplierForContractRenewalOutputDS.getNewSupplierId(),
                    evaluateSupplierForContractRenewalOutputDS.getRenewed());

            // Add the view model to the list
            evaluatedSupplierForContractRenewalViewModelList.add(evaluatedSupplierForContractRenewalViewModel);
        }
        this.evaluatedSupplierForContractRenewalViewModels = evaluatedSupplierForContractRenewalViewModelList;
    }

    public List<EvaluatedSupplierForContractRenewalViewModel> getEvaluatedSupplierForContractRenewalViewModels() {
        return evaluatedSupplierForContractRenewalViewModels;
    }

    @Override
    public String toString() {
        return "EvaluateSupplierForContractRenewalPresenter{" +
                "evaluatedSupplierForContractRenewalViewModels=" + evaluatedSupplierForContractRenewalViewModels +
                '}';
    }
}
