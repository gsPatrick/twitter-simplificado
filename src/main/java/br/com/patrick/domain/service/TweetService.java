package br.com.patrick.domain.service;

import br.com.patrick.domain.repository.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetService {

    @Autowired
    private TweetRepository repository;

    public TweetService (TweetRepository repository) {
        this.repository = repository;
    }

}
