package com.arwest.developer.profile_app_api.service;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import com.arwest.developer.profile_app_api.io.model.Role;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface AccountService {

    AppUser saveUser(String name, String username, String email);
    AppUser findByUsername(String username);
    AppUser findByEmail(String email);
    List<AppUser>userList();
    Role findUserRoleByName(String role);
    Role saveRole(Role role);
    void updateUser(AppUser appUser);
    AppUser updateUser(AppUser user, HashMap<String, String> request);
    AppUser findUserById(Long id);
    void deleteUser(AppUser appUser);
    void resetPassword(AppUser appUser);
    List<AppUser> getUserListByUsername(String username);
    AppUser simpleSaveUser(AppUser user);
    String saveUserImage(MultipartFile multipartFile, Long userImageId);
    void updateUserPassword(AppUser appUser, String newpassword);

}
