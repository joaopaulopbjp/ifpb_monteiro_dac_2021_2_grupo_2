package com.dac.ecommerce.livros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dac.ecommerce.livros.model.user.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Role findByRole(String role);

}
