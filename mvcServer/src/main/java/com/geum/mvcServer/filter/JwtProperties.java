package com.geum.mvcServer.filter;

public interface JwtProperties {

    String SECRET = "JWT_TOKEN_SECRET_KEY";
    int EXPIRATION_TIME = 864000000;
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
