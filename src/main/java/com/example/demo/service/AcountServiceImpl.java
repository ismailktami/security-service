package com.example.demo.service;

import com.example.demo.dao.AppRoleRepository;
import com.example.demo.dao.AppUserRepository;
import com.example.demo.entities.AppRole;
import com.example.demo.entities.AppUser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


    @Override
    public List<AppUser> getUsers() {
        List<AppUser> users=new ArrayList<>();

        for (AppUser appUser : this.appUserRepository.findAll()) {
                appUser.getRoles().forEach(r->{
                    if(r.getRoleName().equals("USER") && appUser.getRoles().size()==1)
                        users.add(appUser);

                });
        }
        return users;
    }

    @Override
    public AppUser BloquerUser(String username) {
            /*AppUser user=new AppUser();
            user=appUserRepository.findByUsername(username);
            user.setActived(false);
            */
             appUserRepository.findByUsername(username).setActived(false);
             return null;
    }

    @Override
    public AppUser DebloquerUser(String username) {
        return null;
    }
}
