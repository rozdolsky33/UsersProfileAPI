package com.arwest.developer.profile_app_api.io.repository;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    AppUser findByUsername(String username);

    AppUser findByEmail(String userEmail);

    @Query("SELECT appUser FROM AppUser appUser WHERE appUser.id=:id")
    AppUser findUserById(@Param("id") Long id);

    List<AppUser> findByUsernameContaining(String username);
}
