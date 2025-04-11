package com.neo.vibecheck.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class AccessCodes {
    private long userId;
    private String accessToken;
    private String refreshToken;
}
