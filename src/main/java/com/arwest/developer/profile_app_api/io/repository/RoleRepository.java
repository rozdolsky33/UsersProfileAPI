package com.arwest.developer.profile_app_api.io.repository;

import com.arwest.developer.profile_app_api.io.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleByName(String name);

}
