package br.com.patrick.domain.service;

import br.com.patrick.domain.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

}
