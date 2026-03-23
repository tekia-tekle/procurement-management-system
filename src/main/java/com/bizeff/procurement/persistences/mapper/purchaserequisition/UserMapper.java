package com.bizeff.procurement.persistences.mapper.purchaserequisition;


import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.persistences.entity.purchaserequisition.DepartmentEntity;
import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;

public class UserMapper {

    public static User toDomain(UserEntity entity) {
        if (entity == null) return null;

        User user = new User();
        user.setId(entity.getId());
        user.setUserId(entity.getUserId());
        user.setFullName(entity.getFullName());
        user.setEmail(entity.getEmail());
        user.setPhoneNumber(entity.getPhoneNumber());
        user.setDepartment(DepartmentMapper.toDomain(entity.getDepartment()));
        user.setRole(entity.getRole());
        return user;
    }


    public static UserEntity toEntity(User domain){
        if (domain == null) return null;

        UserEntity userEntity = new UserEntity();
        if (domain.getId() != null){
            userEntity.setId(domain.getId());
        }
        userEntity.setId(domain.getId());
        userEntity.setUserId(domain.getUserId());
        userEntity.setFullName(domain.getFullName());
        userEntity.setEmail(domain.getEmail());
        userEntity.setPhoneNumber(domain.getPhoneNumber());
        if (domain.getDepartment() != null){
            DepartmentEntity departmentEntity =  DepartmentMapper.toEntity(domain.getDepartment());
            userEntity.setDepartment(departmentEntity);
        }
        userEntity.setRole(domain.getRole());
        return userEntity;
    }
}

