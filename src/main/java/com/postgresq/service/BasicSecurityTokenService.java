package com.postgresq.service;

import com.postgresq.model.SecurityTokens;

public class BasicSecurityTokenService {

    private static final SecurityTokens TOKENS = new SecurityTokens();

    public static void addToken(String token, String userName) {
        if (token == null || userName == null) {
            throw new IllegalArgumentException("Token and user name must not be null");
        }
        if (TOKENS.isValidToken(token)) {
            throw new IllegalArgumentException("Token already exists: " + token);
        }
        TOKENS.addToken(token, userName);
    }

    public static boolean isValidToken(String token, String userName) {
        if (token == null || userName == null) {
            return false;
        }
        if (!TOKENS.isValidToken(token)) {
            return false;
        }
        String tokenUser = TOKENS.getUserByToken(token);
        return userName.equals(tokenUser);

    }

    public static boolean removeToken(String token) {
        if (token == null) {
            return false;
        }
        return TOKENS.removeToken(token);
    }
}
