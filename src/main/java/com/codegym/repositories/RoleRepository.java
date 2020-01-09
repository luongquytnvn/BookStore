package com.codegym.repositories;


import com.codegym.models.signinSignup.Role;
import com.codegym.models.signinSignup.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
