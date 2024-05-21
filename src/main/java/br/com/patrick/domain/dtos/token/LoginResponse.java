package br.com.patrick.domain.dtos.token;

public record LoginResponse(String accessToken, Long expiresIn) {
}
