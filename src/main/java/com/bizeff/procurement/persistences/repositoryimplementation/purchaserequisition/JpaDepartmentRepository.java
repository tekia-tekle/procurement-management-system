package com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.DepartmentRepository;
import com.bizeff.procurement.models.purchaserequisition.Department;
import com.bizeff.procurement.persistences.entity.purchaserequisition.CostCenterEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.DepartmentEntity;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataDepartmentRepository;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.BudgetMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.CostCenterMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaserequisition.DepartmentMapper.toDomain;
import static com.bizeff.procurement.persistences.mapper.purchaserequisition.DepartmentMapper.toEntity;

@Repository
public class JpaDepartmentRepository implements DepartmentRepository {
    private SpringDataDepartmentRepository springDataDepartmentRepository;
    public JpaDepartmentRepository(SpringDataDepartmentRepository springDataDepartmentRepository){
        this.springDataDepartmentRepository = springDataDepartmentRepository;
    }
    @Override
    public Department save(Department department) {
        DepartmentEntity departmentEntity = toEntity(department);

        DepartmentEntity savedDepartment = springDataDepartmentRepository.save(departmentEntity);
        return toDomain(savedDepartment);
    }

    @Override
    public Optional<Department> findByDepartmentId(String departmentId) {
        return springDataDepartmentRepository.findByDepartmentId(departmentId).map(departmentEntity -> toDomain(departmentEntity));
    }

    @Override
    public void deleteByDepartmentId(String departmentId) {
        springDataDepartmentRepository.deleteByDepartmentId(departmentId);
    }

    @Override
    public List<Department> findAll() {
        return springDataDepartmentRepository.findAll().stream().map(departmentEntity -> toDomain(departmentEntity)).collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataDepartmentRepository.deleteAll();
    }

    @Override
    public Department update(Department department) {
        if (department == null || department.getDepartmentId() == null) {
            throw new IllegalArgumentException("Department or Department ID cannot be null");
        }
        Optional<DepartmentEntity> existingDepartmentOpt = springDataDepartmentRepository.findByDepartmentId(department.getDepartmentId());

        if (existingDepartmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Department with ID " + department.getDepartmentId() + " does not exist");
        }
        DepartmentEntity existingDepartment = existingDepartmentOpt.get();
        // Update fields as necessary
        existingDepartment.setName(department.getName());
        existingDepartment.setDescription(department.getDescription());
        existingDepartment.setBudget(BudgetMapper.toEntity(department.getBudget()));
        // Update cost centers
        existingDepartment.setCostCenters(
            department.getCostCenters().stream()
                .map(costCenter -> {
                    CostCenterEntity costCenterEntity = CostCenterMapper.toEntity(costCenter);
                    existingDepartment.addCostCenter(costCenterEntity);
                    return costCenterEntity;
                }).collect(Collectors.toList()));
        // Save the updated entity
        DepartmentEntity updatedDepartment = springDataDepartmentRepository.save(existingDepartment);
        return toDomain(updatedDepartment);
    }
}
