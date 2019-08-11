package com.example.demo;

import com.example.demo.entities.AppRole;
import com.example.demo.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

@SpringBootApplication
public class SecurityServiceApplication {

    public static void main(String[] args)
    {
        SpringApplication.run(SecurityServiceApplication.class, args);


    }


    @Bean
    CommandLineRunner start(AccountService accountService){
        return  args -> {
            /*accountService.save(new AppRole(null,"USER"));
            accountService.save(new AppRole(null,"ADMIN"));
            Stream.of("user1","user2","user3").forEach(e->{
                accountService.saveUser(e,"1234","1234");
            });

       */
           // accountService.addRoleToUser("user1","ADMIN");

        };
           }


    @Bean
    BCryptPasswordEncoder getBcBCryptPasswordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
