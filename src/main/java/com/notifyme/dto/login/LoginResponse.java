package com.notifyme.dto.login;

public record LoginResponse(String accessToken, Long expiresIn) {
}
