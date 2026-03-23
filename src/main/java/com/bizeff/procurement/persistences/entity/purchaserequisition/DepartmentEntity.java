package com.bizeff.procurement.persistences.entity.purchaserequisition;

import com.bizeff.procurement.persistences.entity.budget.BudgetEntity;
import com.bizeff.procurement.persistences.entity.purchaseorder.PurchaseOrderEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "department")
public class DepartmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "departmentId", nullable = false, unique = true)
    private String departmentId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;
    @OneToOne(optional = false)
    @JoinColumn(name = "budget_id",nullable = false,unique = true)
    private BudgetEntity budget;

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CostCenterEntity> costCenters = new ArrayList<>();

    @OneToMany(mappedBy = "department",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<UserEntity> userEntity;

    @OneToMany(mappedBy = "departmentEntity",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<PurchaseOrderEntity> purchaseOrders;
    public DepartmentEntity() {}

    public void addCostCenter(CostCenterEntity costCenterEntity){
        if (costCenterEntity != null && !costCenters.contains(costCenterEntity)){
            this.costCenters.add(costCenterEntity);
            costCenterEntity.setDepartment(this);
        }
    }

    /** Getter and Setter.*/
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public BudgetEntity getBudget() {
        return budget;
    }
    public void setBudget(BudgetEntity budget) {
        this.budget = budget;
    }
    public List<CostCenterEntity> getCostCenters() {
        return costCenters;
    }
    public void setCostCenters(List<CostCenterEntity> costCenters) {
        this.costCenters = costCenters;
    }

    public List<UserEntity> getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(List<UserEntity> userEntity) {
        this.userEntity = userEntity;
    }

    public List<PurchaseOrderEntity> getPurchaseOrder() {
        return purchaseOrders;
    }

    public void setPurchaseOrder(List<PurchaseOrderEntity> purchaseOrders) {
        this.purchaseOrders = purchaseOrders;
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "id=" + id +
                ", departmentId='" + departmentId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
