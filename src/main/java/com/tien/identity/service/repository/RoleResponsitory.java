package com.tien.identity.service.repository;


import com.tien.identity.service.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleResponsitory extends JpaRepository<Role,String> {
}
