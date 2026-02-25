package com.dto;

public class AuthedRequest {

    private String token;
    private String username;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isValid() {
        return token != null && !token.isEmpty() && username != null && !username.isEmpty();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
