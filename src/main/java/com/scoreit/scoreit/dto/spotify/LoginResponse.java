package com.scoreit.scoreit.dto.spotify;

public class LoginResponse {
    private String access_token;

    public LoginResponse(String access_token) {
        this.access_token = access_token;
    }

    public LoginResponse() {
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
}