package tweetapp.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import tweetapp.DTO.ReplyDTO;
import tweetapp.DTO.TweetDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.TweetModel;
import tweetapp.model.User;
import tweetapp.repository.TweetRepo;
import tweetapp.repository.UserRepo;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TweetServiceTest {
	
	@InjectMocks
	private TweetService tweetService;
	
	@Mock
	private TweetRepo tweetRepo;
	
	@Mock
	private UserRepo userRepo;
	
	@Test
	void addTweetTest()throws CustomException {
		TweetDTO tweet=new TweetDTO();
		tweet.setUserName("Anu123");
		tweet.setTweet("Going to a movie");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		Mockito.when(userRepo.findByUserName(tweet.getUserName())).thenReturn(details());
		assertEquals("Anu123", tweetService.addTweet(tweet).getUserName());
	}
	
	@Test
	void addTweetTest1()throws CustomException {
		TweetDTO tweet=new TweetDTO();
		tweet.setUserName("Anu123");
		tweet.setTweet("Going to a movie");
		tweet.setTweetTag("");
		tweet.setPostedAt(LocalDateTime.now().toString());
		Mockito.when(userRepo.findByUserName(tweet.getUserName())).thenReturn(details());
		assertEquals("Anu123", tweetService.addTweet(tweet).getUserName());
	}
	
	private Optional<User> details(){
		User user=new User();
		user.setUserName("Anu123");
		Optional<User> value=Optional.of(user);
		return value;
	}
	
//	@Test
//	void addTweetExceptionTest() {
//		TweetDTO tweet=new TweetDTO();
//		tweet.setUserName("Anu12");
//		Mockito.when(tweetRepo.findByUserNameLike(tweet.getUserName())).thenThrow(CustomException.class);
//		assertThrows(CustomException.class,() -> tweetService.addTweet(tweet));
//	}
	
	@Test
	void getTweetsTest() {
		TweetModel tweet=new TweetModel();
		List<TweetModel> list=new ArrayList<>();
		tweet.setUserName("Anu123");
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(0);
		tweet.setReplies(Collections.emptyList());
		tweet.setLikedBy(Collections.emptyList());
		list.add(tweet);
		Mockito.when(tweetRepo.findAll()).thenReturn(list);
		assertEquals("Enjoying my trip", tweetService.getTweets().get(0).getTweet());
	}
	
	@Test
	void deleteTweetTest()throws CustomException {
		TweetModel tweet=new TweetModel();
		Mockito.when(tweetRepo.findById(tweet.getTweetId())).thenReturn(data());
		Mockito.when(tweetRepo.deleteByTweetId(tweet.getTweetId())).thenReturn(data());
		assertNotNull(tweetService.deleteTweet(tweet.getTweetId()));
	}
	
	private Optional<TweetModel> data(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("1");
		tweet.setUserName("Anu123");
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(0);
		tweet.setReplies(Collections.emptyList());
		tweet.setLikedBy(Collections.emptyList());
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void deleteTweetExceptionTest() {
		TweetModel tweet=new TweetModel();
		String s=tweet.getTweetId();
		Mockito.when(tweetRepo.findById(Mockito.anyString())).thenReturn(data1());
		assertThrows(CustomException.class,() -> tweetService.deleteTweet(s));
	}
	
	private Optional<TweetModel> data1(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("");
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void updateTweetExceptionTest() {
		TweetModel tweet=new TweetModel();
		String name=tweet.getUserName();
		String tweets=tweet.getTweet();
		String s=tweet.getTweetId();
		Mockito.when(tweetRepo.findById(Mockito.anyString())).thenReturn(datas());
		assertThrows(CustomException.class,() -> tweetService.updateTweet(name,s,tweets));
	}
	
	private Optional<TweetModel> datas(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("");
		tweet.setUserName("");
		tweet.setTweet("");
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void updateTweetTest()throws CustomException {
		TweetModel tweet=new TweetModel();
		
		String name="Anu123";
		String tweets="Enjoying my trip in bali";
		String s=tweet.getTweetId();
		Mockito.when(tweetRepo.findById(tweet.getTweetId())).thenReturn(data2());
		assertEquals("Anu123", tweetService.updateTweet(name, s, tweets).getUserName());
	}
	
	private Optional<TweetModel> data2(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("1");
		tweet.setUserName("Anu123");
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(0);
		tweet.setReplies(Collections.emptyList());
		tweet.setLikedBy(Collections.emptyList());
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void updateTweetNameTest(){
		TweetModel tweet=new TweetModel();
		
		String name="A";
		String tweets="Enjoying my trip in bali";
		String s=tweet.getTweetId();
		Mockito.when(tweetRepo.findById(tweet.getTweetId())).thenReturn(data3());
		assertThrows(CustomException.class, () -> tweetService.updateTweet(name, s, tweets));
	}
	
	private Optional<TweetModel> data3(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("1");
		tweet.setUserName("Anu123");
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(0);
		tweet.setReplies(Collections.emptyList());
		tweet.setLikedBy(Collections.emptyList());
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void addLikeTest1() {
		TweetModel tweet=new TweetModel();
		String name=tweet.getUserName();
		String s=tweet.getTweetId();
		Mockito.when(tweetRepo.findById(s)).thenReturn(data5());
		assertNotNull(tweetService.addLike(name, s));
	}
	
	private Optional<TweetModel> data4(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("");
		tweet.setUserName("");
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void addLikeTest()throws CustomException {
		TweetModel model=new TweetModel();
		String name=model.getUserName();
		String id=model.getTweetId();
		Mockito.when(tweetRepo.findById(id)).thenReturn(data5());
		assertEquals("Tweet liked", tweetService.addLike(name, id));
	}
	
	private Optional<TweetModel> data5(){
		TweetModel tweet=new TweetModel();
		List<String> list=new ArrayList<>();
		tweet.setTweetId("1");
		tweet.setUserName("Ana23");
		list.add(tweet.getUserName());
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(1);
		tweet.setReplies(Collections.emptyList());
		tweet.setLikedBy(list);
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
	@Test
	void viewTweetByUsernameExceptionTest() {
		TweetModel tweet=new TweetModel();
		String name=tweet.getUserName();
		Mockito.when(tweetRepo.findByUserNameLike(Mockito.anyString())).thenThrow(CustomException.class);
		assertThrows(CustomException.class,() -> tweetService.viewTweetByUsername(name));
	}
	
//	@Test
//	void viewTweetByUsernameTest()throws CustomException {
//		TweetModel model=new TweetModel();
//		model.setUserName("Ana23");
//		String name=model.getUserName();
//		Mockito.when(tweetRepo.findByUserNameLike(name)).thenReturn(data7());
//		assertEquals("Ana23",tweetService.viewTweetByUsername(name).get(0).getUserName());
//	}
	
	private List<TweetModel> data7(){
		
		TweetModel tweet=new TweetModel();
		List<TweetModel> list=new ArrayList<>();
		tweet.setTweetId("1");
		tweet.setUserName("Ana23");
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(1);
		tweet.setReplies(Collections.emptyList());
		tweet.setLikedBy(Collections.emptyList());
		list.add(tweet);
		return list;
	}
	
	@Test
	void addReplyExceptionTest() {
		ReplyDTO replyDTO=new ReplyDTO();
		TweetModel tweet=new TweetModel();
		String name=tweet.getUserName();
		Mockito.when(tweetRepo.findById(Mockito.anyString())).thenReturn(data8());
		assertThrows(CustomException.class,() -> tweetService.addReply(name,replyDTO));
	}
	
	private Optional<TweetModel> data8(){
		TweetModel tweet=new TweetModel();
		tweet.setTweetId("");
		tweet.setUserName("");
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
		
	@Test
	void addReplyTest() {
		ReplyDTO replyDTO=new ReplyDTO();
		TweetModel tweet=new TweetModel();
		String id=tweet.getTweetId();
		
		String name=tweet.getUserName();
		Mockito.when(tweetRepo.findById(id)).thenReturn(data9());
		assertEquals("Replied successfully",tweetService.addReply(name,replyDTO));
	}
	
	private Optional<TweetModel> data9(){
		TweetModel tweet=new TweetModel();
		List<String> list=new ArrayList<>();
		List<TweetModel> list1=new ArrayList<>();
		tweet.setTweetId("1");
		tweet.setUserName("Ana23");
		list.add(tweet.getUserName());
		tweet.setTweet("Enjoying my trip");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		tweet.setLikes(1);
		tweet.setLikedBy(list);
		list1.add(tweet);
		tweet.setReplies(list1);
		Optional<TweetModel> value=Optional.of(tweet);
		return value;
	}
	
}
