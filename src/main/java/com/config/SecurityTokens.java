package com.config;

import java.util.List;

public class SecurityTokens {

    protected static List<String> tokens = new java.util.ArrayList<>();

    public static void addToken(String token) {
        tokens.add(token);
    }

    public static boolean isValidToken(String token) {
        return tokens.contains(token);
    }

    public static void removeToken(String token) {
        tokens.remove(token);
    }
}
