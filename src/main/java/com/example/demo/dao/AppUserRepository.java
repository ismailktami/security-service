package com.example.demo.dao;

import com.example.demo.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    public AppUser findByUsername(String username);
}
