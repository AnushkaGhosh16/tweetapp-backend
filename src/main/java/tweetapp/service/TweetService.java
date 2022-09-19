package tweetapp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import tweetapp.DTO.ReplyDTO;
import tweetapp.DTO.TweetDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.TweetModel;
import tweetapp.model.User;
import tweetapp.repository.TweetRepo;
import tweetapp.repository.UserRepo;

@Service
@Log4j2
public class TweetService {
	
	@Autowired
	private TweetRepo tweetRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	public TweetModel addTweet(TweetDTO tweet) throws CustomException{
		TweetModel model=new TweetModel();
		
//		if(userRepo.findByUserName(tweet.getUserName()).isEmpty()) {
//			log.error("No user found");
//			throw new CustomException("No user found");
//		}else {
			LocalDateTime myDateObj = LocalDateTime.now();
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    String formattedDate = myDateObj.format(myFormatObj);
		    tweet.setPostedAt(formattedDate);
		    if(tweet.getTweetTag().length()==0) {
				model.setTweet(tweet.getTweet());
				model.setUserName(tweet.getUserName());
				model.setTweetTag("");
				model.setReplies(Collections.emptyList());
				model.setLikedBy(Collections.emptyList());
				model.setPostedAt(tweet.getPostedAt());
				tweetRepo.save(model);
		    }else {
		    	model.setTweet(tweet.getTweet());
				model.setUserName(tweet.getUserName());
				model.setTweetTag(tweet.getTweetTag());
				model.setReplies(Collections.emptyList());
				model.setLikedBy(Collections.emptyList());
				model.setPostedAt(tweet.getPostedAt());
				tweetRepo.save(model);
		    	}
		
		return model;
	}
	
	public List<TweetModel> getTweets(){
		return tweetRepo.findAll();
	}
	
	public String deleteTweet(String tweetId) throws CustomException{
//		if(userRepo.findByUserName(userName).isEmpty()) {
//			log.error("No user found");
//			throw new CustomException("No user found");
//		}else {
			Optional<TweetModel> find=tweetRepo.findById(tweetId);
			if(find.isEmpty())
				throw new CustomException("Tweet Id not found");
			else {
				tweetRepo.deleteById(tweetId);
				return "Tweet Deleted";
		}
//		}
	}
	
	public TweetModel updateTweet(String userName, String tweetId, String tweet)throws CustomException {
		TweetModel model1;
		Optional<TweetModel> find=tweetRepo.findById(tweetId);
		if(find.isEmpty()) {
			log.error("No tweet found");
			throw new CustomException("No tweet found");
		}
		else {
			model1=find.get();
			log.info("1");
			if(model1.getUserName().equals(userName)) {
				model1.setTweet(tweet);
				tweetRepo.save(model1);
				return model1;	
			}else {
				log.info("2");
				throw new CustomException("the tweet doesn't belong to the username");
			}
		}
	}
	
	public String addLike(String userName, String tweetId) {
		TweetModel model;
		
		Optional<TweetModel> find=tweetRepo.findById(tweetId);
		
			model=find.get();
			List<String> list=model.getLikedBy();
			
			if(list.isEmpty()) {
				list.add(userName);
				model.setLikedBy(list);
				model.setLikes(list.size());
				tweetRepo.save(model);
				return "Tweet liked";
					
			}
			else {
				if(list.contains(userName)) {
					list.remove(userName);
					model.setLikedBy(list);
					model.setLikes(list.size());
					tweetRepo.save(model);
					return "Tweet unliked";
				}
				else {
					list.add(userName);
					model.setLikedBy(list);
					model.setLikes(list.size());
					tweetRepo.save(model);
					return "Tweet liked";
				}
			}
			
			
		}
	
	public List<TweetModel> viewTweetByUsername(String userName) {
		if(userRepo.findByUserNameLike(userName).isEmpty()) {
			log.error("No user found");
			throw new CustomException("No user found");
		}
		else {
		List<TweetModel> find=tweetRepo.findByUserNameLike(userName);
//			if(find.isEmpty()) {
//				log.error("No tweet found");
//				throw new CustomException("No tweet found");
//				
//			}
//			else 
				return find;
		}
	}
	
	public String addReply(String tweetId, ReplyDTO replyDTO) {
		TweetModel model;
		Optional<TweetModel> find=tweetRepo.findById(tweetId);
		if(!find.isPresent()) {
			log.error("No tweet found");
			throw new CustomException("No tweet found");
		}
		else {
			model=find.get();
			TweetModel reply=new TweetModel();
			
			LocalDateTime myDateObj = LocalDateTime.now();
		    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		    String formattedDate = myDateObj.format(myFormatObj);
		    replyDTO.setRepliedAt(formattedDate);
		    reply.setTweetId(UUID.randomUUID().toString());
			reply.setTweet(replyDTO.getTweet());
			reply.setUserName(replyDTO.getUserName());
			reply.setTweetTag(replyDTO.getTweetTag());
			reply.setPostedAt(replyDTO.getRepliedAt());
			reply.setReplies(Collections.emptyList());
			reply.setLikes(0);
			reply.setLikedBy(Collections.emptyList());
			
			List<TweetModel> list=model.getReplies();
			list.add(reply);
			model.setReplies(list);
			tweetRepo.save(model);
			return "Replied successfully";
		}
	}
}

