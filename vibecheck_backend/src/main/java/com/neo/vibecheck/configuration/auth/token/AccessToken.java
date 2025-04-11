package com.neo.vibecheck.configuration.auth.token;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

@EqualsAndHashCode
@Getter
@Builder
public class AccessToken {
    private final String subject;
    private final Long userId;
    private final Set<String> roles;

    public AccessToken(String subject, Long userId, Collection<String> roles) {
        this.subject = subject;
        this.userId = userId;
        this.roles = roles != null ? Set.copyOf(roles) : Collections.emptySet();
    }
}
