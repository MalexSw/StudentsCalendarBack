package com.postgresq.model;

import java.util.HashMap;
import java.util.Map;

public class SecurityTokens {

    private static final Map<String, String> TOKENS = new HashMap<>();

    public boolean isValidToken(String token) {
        return TOKENS.containsKey(token);
    }

    public void addToken(String token, String value) {
        TOKENS.put(token, value);
    }

    public boolean removeToken(String token) {
        return TOKENS.remove(token) != null;
    }

    public String getUserByToken(String token) {
        return TOKENS.get(token);
    }

}
