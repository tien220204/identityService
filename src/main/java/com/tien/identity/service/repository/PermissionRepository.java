package com.tien.identity.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tien.identity.service.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
