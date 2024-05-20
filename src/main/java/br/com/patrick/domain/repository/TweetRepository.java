package br.com.patrick.domain.repository;

import br.com.patrick.domain.model.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetRepository extends JpaRepository<Tweet, Long> {
}
