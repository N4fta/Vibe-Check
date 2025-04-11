package com.neo.vibecheck.configuration.auth.token;

public interface AccessTokenDecoder {
    AccessToken decode(String accessTokenEncoded);
}
