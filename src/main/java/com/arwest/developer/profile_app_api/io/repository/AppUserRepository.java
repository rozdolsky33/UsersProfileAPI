package com.arwest.developer.profile_app_api.io.repository;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    AppUser findAppUserById(Long id);
    List<AppUser> findByUsernameContaining(String username);
}
