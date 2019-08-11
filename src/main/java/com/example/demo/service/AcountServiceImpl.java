package com.example.demo.service;

import com.example.demo.dao.AppRoleRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppRole;
import com.example.demo.entities.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AcountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AcountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public AppUser saveUser(String username, String password, String confirmedPasssword) {
    AppUser user=appUserRepository.findByUsername(username);
    if(user!=null) throw new RuntimeException("User already exisit");
    if(!password.equals(confirmedPasssword)) throw new RuntimeException("Your password of confirrmation is incorrect");

    AppUser appUser=new AppUser();
    appUser.setUsername(username);
    appUser.setPassword(bCryptPasswordEncoder.encode(password));
    appUser.setActived(true);
    appUserRepository.save(appUser);
    addRoleToUser(username,"USER");
    return appUser;
    }

    @Override
    public AppRole save(AppRole role) {
        return appRoleRepository.save(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        appUserRepository.findByUsername(username).getRoles().add(appRoleRepository.findByRoleName(rolename));
    }




}
