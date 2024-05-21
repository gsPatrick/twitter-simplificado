package br.com.patrick.domain.controller.token;

import br.com.patrick.domain.dtos.token.LoginRequest;
import br.com.patrick.domain.dtos.token.LoginResponse;
import br.com.patrick.domain.service.token.TokenService;
import br.com.patrick.domain.service.user.basic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private UserService service;

    @Autowired
    private TokenService tokenService;

    private final JwtEncoder jwtEncoder;

    public TokenController(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> Login (@RequestBody LoginRequest loginRequest) {
       return tokenService.Login(loginRequest);
    }

}
