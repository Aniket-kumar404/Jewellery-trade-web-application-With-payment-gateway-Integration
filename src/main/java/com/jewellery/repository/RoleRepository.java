package com.jewellery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jewellery.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

}
