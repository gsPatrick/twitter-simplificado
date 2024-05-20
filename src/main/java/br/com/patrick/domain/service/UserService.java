package br.com.patrick.domain.service;

import br.com.patrick.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserService (UserRepository repository ) {
        this.repository = repository;
    }


}
