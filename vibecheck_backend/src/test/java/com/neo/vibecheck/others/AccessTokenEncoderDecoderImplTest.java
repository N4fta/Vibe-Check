package com.neo.vibecheck.others;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.neo.vibecheck.configuration.auth.token.AccessToken;
import com.neo.vibecheck.configuration.auth.token.exception.InvalidAccessTokenException;
import com.neo.vibecheck.configuration.auth.token.impl.AccessTokenEncoderDecoderImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
class AccessTokenEncoderDecoderImplTest {

    @Autowired
    private AccessTokenEncoderDecoderImpl accessTokenEncoderDecoder;

    @Test
    void encodeAndDecodeAccessToken() {
        AccessToken accessToken = new AccessToken("subject", 1L, List.of("ROLE_USER"));
        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);
        AccessToken decodedToken = accessTokenEncoderDecoder.decode(encodedToken);

        assertThat(decodedToken.getSubject()).isEqualTo(accessToken.getSubject());
        assertThat(decodedToken.getUserId()).isEqualTo(accessToken.getUserId());
        assertThat(decodedToken.getRoles()).isEqualTo(accessToken.getRoles());
    }

    @Test
    void encodeAccessTokenWithNullUserId() {
        AccessToken accessToken = new AccessToken("subject", null, List.of("ROLE_USER"));
        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);
        AccessToken decodedToken = accessTokenEncoderDecoder.decode(encodedToken);

        assertThat(decodedToken.getSubject()).isEqualTo(accessToken.getSubject());
        assertThat(decodedToken.getUserId()).isNull();
        assertThat(decodedToken.getRoles()).isEqualTo(accessToken.getRoles());
    }

    @Test
    void encodeAccessTokenWithEmptyRoles() {
        AccessToken accessToken = new AccessToken("subject", 1L, List.of());
        String encodedToken = accessTokenEncoderDecoder.encode(accessToken);
        AccessToken decodedToken = accessTokenEncoderDecoder.decode(encodedToken);

        assertThat(decodedToken.getSubject()).isEqualTo(accessToken.getSubject());
        assertThat(decodedToken.getUserId()).isEqualTo(accessToken.getUserId());
        assertThat(decodedToken.getRoles()).isEmpty();
    }

    @Test
    void decodeInvalidAccessToken() {
        String invalidToken = "invalid.token";
        assertThatThrownBy(() -> accessTokenEncoderDecoder.decode(invalidToken))
                .isInstanceOf(InvalidAccessTokenException.class);
    }
}