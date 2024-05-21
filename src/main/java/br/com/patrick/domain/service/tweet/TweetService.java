package br.com.patrick.domain.service.tweet;

import br.com.patrick.domain.dtos.tweet.CreateTweetDto;
import br.com.patrick.domain.dtos.tweet.FeedDto;
import br.com.patrick.domain.dtos.tweet.FeedItemDto;
import br.com.patrick.domain.model.Role;
import br.com.patrick.domain.model.Tweet;
import br.com.patrick.domain.repository.TweetRepository;
import br.com.patrick.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private UserRepository userRepository;

    public TweetService (TweetRepository repository) {
        this.tweetRepository = tweetRepository;
    }


    public ResponseEntity<Void> createTweet (CreateTweetDto dto, JwtAuthenticationToken token) {

        var user = userRepository.findById(UUID.fromString(token.getName()));

        var tweet = new Tweet();
        tweet.setUser(user.get());
        tweet.setContent(dto.content());

        tweetRepository.save(tweet);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<Void> deleteTweet (Long tweetId,JwtAuthenticationToken token ) {

        var user = userRepository.findById(UUID.fromString(token.getName()));
        var tweet = tweetRepository.findById(tweetId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var isAdmin = user.get().getRoles()
                .stream().anyMatch(role -> role.getName().equalsIgnoreCase(Role.Values.ADMIN.name()));

        if(isAdmin || tweet.getUser().getUserId().equals(UUID.fromString(token.getName()))) {
            tweetRepository.deleteById(tweetId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<FeedDto> feed (@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
      var tweets = tweetRepository.findAll(PageRequest.of(page, pageSize, Sort.Direction.DESC,"creationTimestamp"))
              .map(tweet ->
                      new FeedItemDto(
                              tweet.getTweetId(),
                              tweet.getContent(),
                              tweet.getUser().getUsername())
              );

      return ResponseEntity.ok(new FeedDto( tweets.getContent(), page, pageSize, tweets.getTotalPages(), tweets.getTotalElements()));
    }

}
