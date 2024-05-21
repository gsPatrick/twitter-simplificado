package br.com.patrick.domain.service.user.basic;

import br.com.patrick.domain.dtos.user.CreateUserDto;
import br.com.patrick.domain.model.Role;
import br.com.patrick.domain.model.User;
import br.com.patrick.domain.repository.RoleRepository;
import br.com.patrick.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserService (UserRepository userRepository ) {
        this.userRepository = userRepository;
    }


    public void newUser (CreateUserDto dto) {

    var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

    var userFromDb = userRepository.findByUsername(dto.username());

    if(userFromDb.isPresent()){
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    var user = new User();
    user.setUsername(dto.username());
    user.setPassword(passwordEncoder.encode(dto.Password()));
    user.setRoles(Set.of(basicRole));

    userRepository.save(user);

    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
