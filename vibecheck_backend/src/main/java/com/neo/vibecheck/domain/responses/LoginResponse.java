package com.neo.vibecheck.domain.responses;

import com.neo.vibecheck.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class LoginResponse {
    private int status;
    private String message;
    private User result;
}
