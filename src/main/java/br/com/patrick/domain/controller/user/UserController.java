package br.com.patrick.domain.controller.user;

import br.com.patrick.domain.dtos.user.CreateUserDto;
import br.com.patrick.domain.service.user.basic.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> newUser (@RequestBody CreateUserDto dto) {
       userService.newUser(dto);
       return ResponseEntity.ok().build();
    }

}
