package com.homeinventory.homeinventory.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.homeinventory.homeinventory.user.User;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Role")
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    @SequenceGenerator(
            name = "role_sequence",
            sequenceName = "role_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "role_name",
            nullable = false
    )
    private String roleName;

    @OneToMany(
            mappedBy = "role",
            orphanRemoval = true,
            cascade = {CascadeType.ALL}
    )
    private List<User> users = new ArrayList<>();

    public Role() {
    }

    public Role(Long id) {
        this.id = id;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @JsonIgnore
    public List<User> getUsers() {
        return users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
