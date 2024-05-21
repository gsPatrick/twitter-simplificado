    package br.com.patrick.domain.service.token;


    import br.com.patrick.domain.dtos.token.LoginRequest;
    import br.com.patrick.domain.dtos.token.LoginResponse;
    import br.com.patrick.domain.model.Role;
    import br.com.patrick.domain.service.user.basic.UserService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.authentication.BadCredentialsException;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.oauth2.jwt.JwtClaimsSet;
    import org.springframework.security.oauth2.jwt.JwtEncoder;
    import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
    import org.springframework.stereotype.Service;

    import java.time.Instant;
    import java.util.stream.Collectors;

    @Service
    public class TokenService {


        @Autowired
        private UserService service;

        @Autowired
        private JwtEncoder jwtEncoder;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;


        public ResponseEntity<LoginResponse> Login(LoginRequest loginRequest) {
            var user = service.findByUsername(loginRequest.username());

            if (user.isEmpty() || !user.get().isLoginCorrect(loginRequest, passwordEncoder)) {
                throw new BadCredentialsException("Usuário ou senha inválidos");
            }
            var now = Instant.now();
            var expiresIn = 300L;
            var scopes = user.get().getRoles()
                    .stream()
                    .map(Role::getName)
                    .collect(Collectors.joining(" "));


            var claims = JwtClaimsSet.builder()
                    .issuer("twitter-simplificado")
                    .subject(user.get().getUserId().toString())
                    .expiresAt(now.plusSeconds(expiresIn))
                    .issuedAt(now)
                    .claim("scope", scopes)
                    .build();

            var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
            return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
        }
    }
