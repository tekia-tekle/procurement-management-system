package com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition;

import com.bizeff.procurement.models.purchaserequisition.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    User update(User user);
    Optional<User>findByUserId(String userId);
    Optional<User>findByPhoneNumber(String phoneNumber);
    Optional<User>findByEmail(String email);
    List<User>findByDepartmentId(String departmentId);
    void deleteByUserId(String userId);
    void deleteByPhoneNumber(String phoneNumber);
    void deleteByEmail(String email);
    List<User> findAll();
    void deleteAll();

}
