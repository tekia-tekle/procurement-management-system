package com.bizeff.procurement.persistences.repositoryimplementation.purchaserequisition;

import com.bizeff.procurement.domaininterfaces.repositoriesinterfaces.purchaserequisition.UserRepository;
import com.bizeff.procurement.models.purchaserequisition.User;
import com.bizeff.procurement.persistences.entity.purchaserequisition.UserEntity;
import com.bizeff.procurement.persistences.jparepository.purchaserequisition.SpringDataUserRepository;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.DepartmentMapper;
import com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper.toDomain;
import static com.bizeff.procurement.persistences.mapper.purchaserequisition.UserMapper.toEntity;

@Repository
public class JpaUserRepository implements UserRepository {
    private SpringDataUserRepository springDataUserRepository;
    public JpaUserRepository(SpringDataUserRepository springDataUserRepository){
        this.springDataUserRepository =  springDataUserRepository;
    }
    @Override
    public User save(User user) {
        UserEntity userEntity = toEntity(user);

        UserEntity savedEntity = springDataUserRepository.save(userEntity);

        return UserMapper.toDomain(savedEntity);
    }
    @Override
    public Optional<User> findByUserId(String userId){
        return springDataUserRepository.findByUserId(userId).map(userEntity -> toDomain(userEntity));
    }
    @Override
    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return springDataUserRepository.findByPhoneNumber(phoneNumber).map(entity->toDomain(entity));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataUserRepository.findByEmail(email).map(userEntity -> toDomain(userEntity));
    }

    @Override
    public List<User>findByDepartmentId(String departmentId){
        return springDataUserRepository.findByDepartment_DepartmentId(departmentId).stream()
                .map(userEntity -> toDomain(userEntity)).collect(Collectors.toList());
    }
    @Override
    public void deleteByUserId(String userId){
        springDataUserRepository.deleteByUserId(userId);
    }
    @Override
    public void deleteByPhoneNumber(String phoneNumber) {
        springDataUserRepository.deleteByPhoneNumber(phoneNumber);
    }

    @Override
    public void deleteByEmail(String email) {
        springDataUserRepository.deleteByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return springDataUserRepository.findAll().stream().map(userEntity -> toDomain(userEntity)).collect(Collectors.toList());
    }
    @Override
    public void deleteAll(){
        springDataUserRepository.deleteAll();
    }

    @Override
    public User update(User user) {
        if (user == null || user.getUserId() == null) {
            throw new IllegalArgumentException("User or User ID cannot be null");
        }
        Optional<UserEntity> existingUserOpt = springDataUserRepository.findByUserId(user.getUserId());
        if (!existingUserOpt.isPresent()) {
            throw new IllegalArgumentException("User with ID " + user.getUserId() + " not found");
        }
        UserEntity existingUser = existingUserOpt.get();
        existingUser.setFullName(user.getFullName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setDepartment(DepartmentMapper.toEntity(user.getDepartment()));
        existingUser.setRole(user.getRole());
        UserEntity updatedUser = springDataUserRepository.save(existingUser);
        return UserMapper.toDomain(updatedUser);
    }
}
