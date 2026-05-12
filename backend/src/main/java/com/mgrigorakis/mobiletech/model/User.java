package com.mgrigorakis.mobiletech.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
@Entity
public class User extends BaseModel {
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @Column(name = "email", unique = true, nullable = false, length = 320)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
}
