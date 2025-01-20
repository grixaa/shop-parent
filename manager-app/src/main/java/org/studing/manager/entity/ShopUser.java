package org.studing.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user", schema = "user_management")
public class ShopUser {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    Integer id;

    @Column(name = "c_username")
    String username;

    @Column(name = "c_password")
    String password;

    @ManyToMany
    @JoinTable(
        schema =  "user_management",
        name = "t_user_authority",
        joinColumns = @JoinColumn(name = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "id_authority"))
    List<Authority> authorities;
}
