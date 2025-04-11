package com.neo.vibecheck.configuration.auth.token;

public interface AccessTokenEncoder {
    String encode(AccessToken accessToken);
}
