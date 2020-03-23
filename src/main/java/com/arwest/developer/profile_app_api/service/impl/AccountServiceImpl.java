package com.arwest.developer.profile_app_api.service.impl;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import com.arwest.developer.profile_app_api.io.model.Role;
import com.arwest.developer.profile_app_api.io.model.UserRole;
import com.arwest.developer.profile_app_api.io.repository.AppUserRepository;
import com.arwest.developer.profile_app_api.io.repository.RoleRepository;
import com.arwest.developer.profile_app_api.service.AccountService;
import com.arwest.developer.profile_app_api.unitility.Constants;
import com.arwest.developer.profile_app_api.unitility.EmailConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountService accountService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private AppUserRepository appUserRepository;
    private EmailConstructor emailConstructor;
    private JavaMailSender mailSender;
    private RoleRepository roleRepository;

    @Autowired
    public AccountServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, AppUserRepository appUserRepository,
                              EmailConstructor emailConstructor, JavaMailSender mailSender,
                              RoleRepository roleRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.appUserRepository = appUserRepository;
        this.emailConstructor = emailConstructor;
        this.mailSender = mailSender;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public AppUser saveUser(String name, String username, String email) {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        AppUser appUser = new AppUser();
        appUser.setPassword(encryptedPassword);
        appUser.setName(name);
        appUser.setUsername(username);
        appUser.setEmail(email);
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(appUser, accountService.findUserRoleByName("USER")));
        appUser.setUserRoles(userRoles);
        appUserRepository.save(appUser);
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Constants.TEMP_USER.toPath());
            String fileName = appUser.getId() + ".png";
            Path path = Paths.get(Constants.USER_FOLDER + fileName);
            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mailSender.send(emailConstructor.constructNewUserEmail(appUser, password));
        return appUser;
    }
    @Override
    public AppUser findByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
    @Override
    public AppUser findByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> userList() {
        return appUserRepository.findAll();
    }

    @Override
    public Role findUserRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public void updateUser(AppUser appUser) {

        String password = appUser.getPassword();
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        appUser.setPassword(encryptedPassword);
        appUserRepository.save(appUser);
        mailSender.send(emailConstructor.constructUpdateUserProfileEmail(appUser));
    }

    @Override
    public AppUser findUserById(Long id) {
        return appUserRepository.findUserById(id);
    }

    @Override
    public void deleteUser(AppUser appUser) {
        appUserRepository.delete(appUser);
    }

    @Override
    public void resetPassword(AppUser appUser) {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        appUser.setPassword(encryptedPassword);
        appUserRepository.save(appUser);
        mailSender.send(emailConstructor.constructResetPasswordEmail(appUser, password));
    }

    @Override
    public List<AppUser> getUserListByUsername(String username) {
        return appUserRepository.findByUsernameContaining(username);
    }

    @Override
    public AppUser simpleSaveUser(AppUser user) {
         appUserRepository.save(user);
         mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));
         return user;
    }
    @Override
    public String saveUserImage(MultipartFile multipartFile, Long userImageId) {
        /*
         * MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)
         * request; Iterator<String> it = multipartRequest.getFileNames(); MultipartFile
         * multipartFile = multipartRequest.getFile(it.next());
         */
        byte[] bytes;
        try {
            Files.deleteIfExists(Paths.get(Constants.USER_FOLDER + "/" + userImageId + ".png"));
            bytes = multipartFile.getBytes();
            Path path = Paths.get(Constants.USER_FOLDER + userImageId + ".png");
            Files.write(path, bytes);
            return "User picture saved to server";
        } catch (IOException e) {
            return "User picture Saved";
        }
    }
    @Override
    public AppUser updateUser(AppUser user, HashMap<String, String> request) {
        String name = request.get("name");
        // String username = request.get("username");
        String email = request.get("email");
        String bio = request.get("bio");
        user.setName(name);
        // appUser.setUsername(username);
        user.setEmail(email);
        user.setBio(bio);
        appUserRepository.save(user);
        mailSender.send(emailConstructor.constructUpdateUserProfileEmail(user));
        return user;

    }

    @Override
    public void updateUserPassword(AppUser appUser, String newpassword) {
        String encryptedPassword = bCryptPasswordEncoder.encode(newpassword);
        appUser.setPassword(encryptedPassword);
        appUserRepository.save(appUser);
        mailSender.send(emailConstructor.constructResetPasswordEmail(appUser, newpassword));
    }

}
