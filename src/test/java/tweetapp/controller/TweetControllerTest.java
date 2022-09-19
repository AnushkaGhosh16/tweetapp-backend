package tweetapp.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tweetapp.DTO.ReplyDTO;
import tweetapp.DTO.TweetDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.TweetModel;
import tweetapp.model.User;
import tweetapp.service.TweetService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class TweetControllerTest {
	
	private MockMvc mvc;
	
	@InjectMocks
	private TweetController tweetController;
	
	@Mock
	private TweetService tweetService;
	
	@BeforeEach
	private void init() {
		mvc = MockMvcBuilders.standaloneSetup(tweetController).build();
	}
	
	@Test
	void addTweetTest()throws Exception {
		TweetModel tweet=new TweetModel();
//		tweet.setUserName("Anu123");
//		tweet.setTweet("Going to a vacation");
//		tweet.setTweetTag("@Sara12");
//		tweet.setPostedAt(LocalDateTime.now().toString());
		String username=tweet.getUserName();
		Mockito.when(tweetService.addTweet(Mockito.any())).thenReturn(tweet);
		MvcResult result = mvc.perform(post("http://localhost:8081/api/v1.0/tweets/"+username+"/add")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userName\":\"Anu123\",\"tweet\":\"Going to a vacation\",\"tweetTag\":\"@Sara12\"}")
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertEquals("Tweet added",result.getResponse().getContentAsString());
	}
	
	@Test
	void addTweetExceptionTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String username=tweet.getUserName();
		Mockito.when(tweetService.addTweet(Mockito.any())).thenThrow(new CustomException("No user found"));
		MvcResult result = mvc.perform(post("http://localhost:8081/api/v1.0/tweets/"+username+"/add")
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userName\":\"\",\"tweet\":\"Going to a vacation\",\"tweetTag\":\"@Sara12\"}")
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertEquals("No user found",result.getResponse().getContentAsString());
	}
	
	@Test
	void getTweetsTest()throws Exception {
		TweetModel tweet=new TweetModel();
		List<TweetModel> list=new ArrayList<>();
		tweet.setUserName("Anu123");
		tweet.setTweet("Going to a vacation");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		list.add(tweet);
		Mockito.when(tweetService.getTweets()).thenReturn(list);
		MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/all")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void deleteTweetTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String id=tweet.getTweetId();
		String username=tweet.getUserName();
		Mockito.when(tweetService.deleteTweet(username)).thenReturn("Tweet Deleted");
		MvcResult result = mvc.perform(delete("http://localhost:8081/api/v1.0/tweets/"+username+"/delete/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals("Tweet Deleted", result.getResponse().getContentAsString());
	}
	
	@Test
	void deleteTweetExceptionTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String id=tweet.getTweetId();
		String username=tweet.getUserName();
		Mockito.when(tweetService.deleteTweet(Mockito.anyString())).thenThrow(new CustomException("Tweet ID not found"));
		MvcResult result = mvc.perform(delete("http://localhost:8081/api/v1.0/tweets/"+username+"/delete/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertEquals("Tweet ID not found", result.getResponse().getContentAsString());
	}
	
	@Test
	void updateTweetTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String username=tweet.getUserName();
		String id=tweet.getTweetId();
		String tweets=tweet.getTweet();
		Mockito.when(tweetService.updateTweet(username,id,tweets)).thenReturn(tweet);
		MvcResult result = mvc.perform(put("http://localhost:8081/api/v1.0/tweets/"+username+"/update/"+id)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"userName\":\"Anu123\",\"tweet\":\"Going for a vacation\",\"tweetTag\":\"@Sara12\"}")
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void updateTweetExceptionTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String id=tweet.getTweetId();
		String username=tweet.getUserName();
		Mockito.when(tweetService.updateTweet(Mockito.anyString(),Mockito.anyString(),Mockito.anyString())).thenThrow(new CustomException("Tweet ID not found"));
		MvcResult result = mvc.perform(put("http://localhost:8081/api/v1.0/tweets/"+username+"/update/"+id)
				.contentType(MediaType.APPLICATION_JSON)
				.content("{\"userName\":\"Anu123\",\"tweet\":\"Going for a vacation\",\"tweetTag\":\"@Sara12\"}")
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
	
//	@Test
//	void addLikeTest()throws Exception {
//		TweetModel tweet=new TweetModel();
//		String username=tweet.getUserName();
//		String id=tweet.getTweetId();
//		Mockito.when(tweetService.addLike(username,id)).thenReturn("Tweet liked");
//		MvcResult result = mvc.perform(put("http://localhost:8081/api/v1.0/tweets/"+username+"/like/"+id)
//					.contentType(MediaType.APPLICATION_JSON)
//					.accept(MediaType.APPLICATION_JSON))
//					.andReturn();
//		assertEquals("Tweet liked", result.getResponse().getContentAsString());
//	}
	
	@Test
	void addLikeExceptionTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String username=tweet.getUserName();
		String id=tweet.getTweetId();
		Mockito.when(tweetService.addLike(Mockito.anyString(),Mockito.anyString())).thenThrow(new CustomException("No tweet found"));
		MvcResult result = mvc.perform(put("http://localhost:8081/api/v1.0/tweets/"+username+"/like/"+id)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void tweetByUsernameTest()throws Exception {
		TweetModel tweet=new TweetModel();
		List<TweetModel> list=new ArrayList<>();
		tweet.setUserName("Anu123");
		tweet.setTweet("Going to a vacation");
		tweet.setTweetTag("@Sara12");
		tweet.setPostedAt(LocalDateTime.now().toString());
		list.add(tweet);
		String username=tweet.getUserName();
		Mockito.when(tweetService.viewTweetByUsername(username)).thenReturn(list);
		MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/"+username)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void tweetByUsernameExceptionTest()throws Exception {
		TweetModel tweet=new TweetModel();
		String username=tweet.getUserName();
		Mockito.when(tweetService.viewTweetByUsername(Mockito.anyString())).thenThrow(new CustomException("No user found"));
		MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/"+username)
					.contentType(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	void addReplyTest()throws Exception {
		ReplyDTO tweet=new ReplyDTO();
		tweet.setRepliedAt(LocalDateTime.now().toString());
		tweet.setUserName("Anu123");
		tweet.setTweet("Going to a vacation");
		tweet.setTweetTag("@Sara12");
		String id="1";
		String username=tweet.getUserName();
		Mockito.when(tweetService.addReply(id,tweet)).thenReturn("Replied successfully");
		MvcResult result = mvc.perform(post("http://localhost:8081/api/v1.0/tweets/"+username+"/reply/"+id)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"tweet\":\"where?\",\"tweetTag\":\"@vini\"}")
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertEquals("Replied successfully",result.getResponse().getContentAsString());
	}
	
	@Test
	void addReplyExceptionTest()throws Exception {
		ReplyDTO tweet=new ReplyDTO();
		String id="1";
		String username=tweet.getUserName();
		Mockito.when(tweetService.addReply(Mockito.anyString(),Mockito.any())).thenThrow(new CustomException("No tweet found"));
		MvcResult result = mvc.perform(post("http://localhost:8081/api/v1.0/tweets/"+username+"/reply/"+id)
					.contentType(MediaType.APPLICATION_JSON)
					.content("{\"tweet\":\"where?\",\"tweetTag\":\"@vini\"}")
					.accept(MediaType.APPLICATION_JSON))
					.andReturn();
		assertNotNull(result.getResponse().getContentAsString());
	}
}
