package com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.CostCenterRepository;
import com.bizeff.procurement.models.purchaserequisition.CostCenter;
import com.bizeff.procurement.persistences.entity.purchaserequisition.CostCenterEntity;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataCostCenterRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaserequisition.CostCenterMapper.toEntity;
import static com.bizeff.procurement.persistences.mapper.purchaserequisition.CostCenterMapper.toModel;
public class JpaCostCenterRepository implements CostCenterRepository {
    private SpringDataCostCenterRepository springDataCostCenterRepository;
    public JpaCostCenterRepository(SpringDataCostCenterRepository springDataCostCenterRepository){
        this.springDataCostCenterRepository = springDataCostCenterRepository;
    }
    @Override
    public CostCenter save(CostCenter costCenter) {
        CostCenterEntity costCenterEntity = toEntity(costCenter);
        CostCenterEntity savedEntity = springDataCostCenterRepository.save(costCenterEntity);

        return toModel(savedEntity);
    }

    @Override
    public Optional<CostCenter> findByCostCenterId(String costCenterId) {

        return springDataCostCenterRepository.findByCostCenterId(costCenterId).map(costCenterEntity -> toModel(costCenterEntity));
    }

    @Override
    public void deleteByCostCenterId(String costCenterId) {

        springDataCostCenterRepository.deleteByCostCenterId(costCenterId);
    }

    @Override
    public List<CostCenter> findAll() {

        return springDataCostCenterRepository.findAll().stream().map(costCenterEntity -> toModel(costCenterEntity)).collect(Collectors.toList());
    }
    @Override
    public List<CostCenter>findByDepartmentId(String departmentId){
        return springDataCostCenterRepository.findByDepartment_DepartmentId(departmentId).stream()
                .map(costCenterEntity -> toModel(costCenterEntity)).collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataCostCenterRepository.deleteAll();
    }

    @Override
    public CostCenter update(CostCenter costCenter) {
        if(costCenter.getCostCenterId() == null){
            throw new IllegalArgumentException("Cost Center ID cannot be null");
        }
        Optional<CostCenterEntity> existingCostCenterOpt = springDataCostCenterRepository.findByCostCenterId(costCenter.getCostCenterId());
        if (!existingCostCenterOpt.isPresent()) {
            throw new IllegalArgumentException("Cost Center with ID " + costCenter.getCostCenterId() + " does not exist");
        }
        CostCenterEntity existingCostCenter = existingCostCenterOpt.get();
        // Update fields as necessary
        existingCostCenter.setName(costCenter.getName());
        existingCostCenter.setDescription(costCenter.getDescription());
        existingCostCenter.setAllocatedBudget(costCenter.getAllocatedBudget());
        existingCostCenter.setUsedBudget(costCenter.getUsedBudget());
        existingCostCenter.setRemainingBudget(costCenter.getRemainingBudget());
        // Save the updated entity
        CostCenterEntity updatedCostCenter = springDataCostCenterRepository.save(existingCostCenter);
        return toModel(updatedCostCenter);
    }
}
