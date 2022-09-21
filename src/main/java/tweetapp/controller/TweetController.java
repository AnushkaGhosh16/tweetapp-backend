package tweetapp.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import tweetapp.DTO.ReplyDTO;
import tweetapp.DTO.TweetDTO;
import tweetapp.model.TweetModel;
import tweetapp.model.User;
import tweetapp.repository.TweetRepo;
import tweetapp.service.TweetService;

@RestController
@RequestMapping("/api/v1.0/tweets")
@Log4j2
@CrossOrigin(origins="*") 
public class TweetController {
	
	@Autowired
	private TweetService tweetService;
	
	@PostMapping("/{username}/add")
	public ResponseEntity<String> addTweet(@PathVariable("username") String username, @RequestBody TweetDTO tweet){
		try {
			tweet.setUserName(username);
			tweetService.addTweet(tweet);
			return new ResponseEntity<>("Tweet added",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<TweetModel>> getTweets(){
		List<TweetModel> list=tweetService.getTweets();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<String> deleteTweet(@PathVariable("username") String username,@PathVariable("id") String tweetId){
		try {
			tweetService.deleteTweet(tweetId);
			return new ResponseEntity<>("Tweet Deleted",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>("Tweet ID not found",HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<TweetModel> updateTweet(@PathVariable("username") String username,@PathVariable("id") String tweetId, @RequestBody TweetDTO tweet){
		try {
			TweetModel obj=tweetService.updateTweet(username, tweetId, tweet.getTweet());
		
			return new ResponseEntity<>(obj, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			}
	}
	
	@PutMapping("/{username}/like/{id}")
	public ResponseEntity<String> addLike(@PathVariable("username") String username,@PathVariable("id") String tweetId){
		try {
			String s=tweetService.addLike(username, tweetId);
			return new ResponseEntity<>(s, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> viewTweetByUsername(@PathVariable("username") String username){
		try {
			List<TweetModel> list=tweetService.viewTweetByUsername(username);
			return new ResponseEntity<>(list, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
			}
	}
	
	@PostMapping("/{username}/reply/{id}")
	public ResponseEntity<String> addReply(@PathVariable("username") String username, @PathVariable("id") String tweetId, @RequestBody ReplyDTO replyDTO){
		try {
			replyDTO.setUserName(username);
			tweetService.addReply(tweetId, replyDTO);
			return new ResponseEntity<>("Replied successfully",HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
