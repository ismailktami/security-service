package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor @Getter @Setter
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean actived;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<AppRole> roles=new ArrayList<>();


}
