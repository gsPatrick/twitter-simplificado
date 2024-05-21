package br.com.patrick.domain.controller.tweet;


import br.com.patrick.domain.dtos.tweet.CreateTweetDto;
import br.com.patrick.domain.dtos.tweet.FeedDto;
import br.com.patrick.domain.service.tweet.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
public class TweetController {

    @Autowired
    private TweetService tweetService;


    @PostMapping("/tweets")
    public ResponseEntity<Void> createTweet(@RequestBody CreateTweetDto dto, JwtAuthenticationToken token){

        return tweetService.createTweet(dto,token);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteTweet(@PathVariable("id") Long tweetId,JwtAuthenticationToken token) {
          return tweetService.deleteTweet(tweetId, token);
    }

    @GetMapping("/feed")
    public ResponseEntity<FeedDto> getfeed (@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return tweetService.feed(page, pageSize);
    }

}
