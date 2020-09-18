package io.javalin.core.security;

import io.javalin.http.Context;

/**
 * Auth credentials for basic HTTP authorization.
 * Contains the Base64 decoded [username] and [password] from the Authorization header.
 * @see Context#basicAuthCredentials()
 */
public class BasicAuthCredentials {

    private final String username;
    private final String password;

    public BasicAuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
