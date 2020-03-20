package com.arwest.developer.profile_app_api.service.impl;

import com.arwest.developer.profile_app_api.io.model.AppUser;
import com.arwest.developer.profile_app_api.io.model.Role;
import com.arwest.developer.profile_app_api.io.repository.AppUserRepository;
import com.arwest.developer.profile_app_api.io.repository.RoleRepository;
import com.arwest.developer.profile_app_api.service.AccountService;
import com.arwest.developer.profile_app_api.unitility.EmailConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

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
    public void saveUser(AppUser appUser) {
        String password = RandomStringUtils.randomAlphanumeric(10);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        appUser.setPassword(encryptedPassword);
        appUserRepository.save(appUser);
        mailSender.send(emailConstructor.constructNewUserEmail(appUser, password));
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
}
