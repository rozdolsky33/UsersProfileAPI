package com.arwest.developer.profile_app_api.service;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import com.arwest.developer.profile_app_api.io.model.Role;

import java.util.List;

public interface AccountService {

    void saveUser(AppUser appUser);
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    List<AppUser>userList();
    Role findUserRoleByName(String role);
    Role saveRole(Role role);
    void updateUser(AppUser appUser);
    AppUser findUserById(Long id);
    void deleteUser(AppUser appUser);
    void resetPassword(AppUser appUser);
    List<AppUser> getUserListByUsername(String username);
    AppUser simpleSaveUser(AppUser user);

}
