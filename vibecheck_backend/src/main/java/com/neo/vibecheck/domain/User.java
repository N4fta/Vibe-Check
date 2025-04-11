package com.neo.vibecheck.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;

    private String email;

    private String username;

    private String password;

    private String profilePicture;

    private LocalDate birthDate;

    private boolean admin;

}