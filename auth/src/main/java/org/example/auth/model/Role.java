package org.example.auth.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String privilege;
    private String info;

    public Long getId() {
        return id;
    }

    public String getInfo() {
        return info;
    }

    public String getName() {
        return name;
    }

    public String getPrivilege() {
        return privilege;
    }

    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();
}
