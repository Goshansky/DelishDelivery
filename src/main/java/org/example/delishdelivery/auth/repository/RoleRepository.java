package org.example.delishdelivery.auth.repository;

import org.example.delishdelivery.auth.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String roleUser);
}
