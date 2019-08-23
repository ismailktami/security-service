package com.example.demo.web;

import com.example.demo.entities.AppUser;
import com.example.demo.service.AccountService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private AccountService accountService;
    @PostMapping(value ="/register" )
    public AppUser register(@RequestBody UserForm userForm){
    return accountService.saveUser(userForm.getUsername(),userForm.getPassword(),userForm.getConfirmedPassword());
    }

    @GetMapping("/users")
    public List<AppUser> getUsers(){
        return accountService.getUsers();
    }


    @PostMapping("/users/{username}")
    public AppUser BloquerUser(@PathVariable("username") String username){
        return this.accountService.BloquerUser(username);

    }
}
@Data
class UserForm{

    private String username;
    private String password;
    private String confirmedPassword;
}