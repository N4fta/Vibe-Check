package com.neo.vibecheck.data.entity;

import java.time.LocalDate;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Length(min = 3, max = 40)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(min = 3, max = 20)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Length(min = 59, max = 70)
    @Column(name = "hashed_password")
    private String password;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotNull
    @Column(name = "admin_role")
    private boolean admin;
}
