package com.neo.vibecheck.others;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.configuration.auth.AuthenticationRequestFilter;
import com.neo.vibecheck.configuration.auth.RequestAuthenticatedUserProvider;
import com.neo.vibecheck.configuration.auth.token.impl.AccessTokenEncoderDecoderImpl;
import com.neo.vibecheck.configuration.security.PasswordEncoderConfig;
import com.neo.vibecheck.data.jpa.UserRepositoryJpa;
import com.neo.vibecheck.domain.services.UserService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class SpringContextTest {

    @Autowired
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    @Autowired
    private AuthenticationRequestFilter authenticationRequestFilter;

    @Autowired
    private RequestAuthenticatedUserProvider requestAuthenticatedUserProvider;

    @Autowired
    private PasswordEncoderConfig passwordEncoderConfig;

    @Autowired
    private UserRepositoryJpa userRepositoryJpa;

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        assertThat(accessTokenEncoderDecoder).isNotNull();
        assertThat(authenticationRequestFilter).isNotNull();
        assertThat(requestAuthenticatedUserProvider).isNotNull();
        assertThat(passwordEncoderConfig).isNotNull();
        assertThat(userRepositoryJpa).isNotNull();
        assertThat(userService).isNotNull();
    }
}