package tweetapp.controller;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import tweetapp.DTO.GeneralResponseDTO;
import tweetapp.DTO.UserDTO;
import tweetapp.DTO.UserLoginDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.User;
import tweetapp.service.UserService;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserControllerTest {
	
	private MockMvc mvc;
	
	@InjectMocks
	private UserController userController;
	
	@Mock
	private UserService userService;
	
	@BeforeEach
	private void init() {
		mvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	 void registerationTest() throws Exception {
		 User user = new User();
		 Mockito.when(userService.registerUser(Mockito.any())).thenReturn(user);
		 MvcResult result = mvc.perform(post("/api/v1.0/tweets/register")
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.content("{\"id\":\"id\",\"userName\":\"admin\",\"password\":\"admin\",\"confirmPassword\":\"admin\",\"email\":\"fse@gmail.com\",\"firstName\":\"admin\",\"lastName\":\"admin\",\"contactNumber\":\"9007774890\"}")
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();

		 assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	 void registerationExceptionTest()throws Exception {
		 UserDTO user = new UserDTO();
		 user.setFirstName("admin");
		 user.setLastName("admin");
		 user.setUserName("admin");
		 user.setEmail("fse@gmail.com");
		 user.setPassword("admin");
		 user.setConfirmPassword("ad");
		 user.setNumber("89939394040");
	
		 Mockito.when(userService.registerUser(Mockito.any())).thenThrow(new CustomException("Passwords do not match"));
		 MvcResult result = mvc.perform(post("/api/v1.0/tweets/register")
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.content("{\"id\":\"id\",\"username\":\"admin\",\"password\":\"admin\",\"confirmPassword\":\"ad\",\"email\":\"fse@gmail.com\",\"firstName\":\"admin\",\"lastName\":\"admin\",\"number\":\"89939394040\"}")
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
	}
	
	@Test
	 void getUsersTest()throws Exception {
		 User user = new User();
		 List<User> list=new ArrayList<>();
		 user.setFirstName("admin");
		 user.setLastName("admin");
		 user.setUserName("admin");
		 user.setEmail("fse@gmail.com");
		 user.setPassword("admin");
		 user.setNumber("89939394040");
		 list.add(user);
		 Mockito.when(userService.getUsers()).thenReturn(list);
		 MvcResult result = mvc.perform(get("/api/v1.0/tweets/users/all")
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();
		
		 assertNotNull(result.getResponse().getContentAsString());
	}
	
	 @Test
	 void LoginPositiveTest() throws Exception {
		 UserLoginDTO user = new UserLoginDTO();
		 GeneralResponseDTO res=new GeneralResponseDTO();
		 user.setUserName("Anu123");
		 user.setPassword("xoxo");
		 res.setMessage("Login Successful");
		 res.setError(false);
		 Mockito.when(userService.userLogin(user)).thenReturn("Login Successful");
		 MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/login")
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.content("{\"userName\":\"Anu123\",\"password\":\"xoxo\"}")
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
	 }
	 
	 @Test
	 void LoginNegativeTest() throws Exception {
		 UserLoginDTO user = new UserLoginDTO();
		 GeneralResponseDTO res=new GeneralResponseDTO();
		 user.setUserName("A");
		 user.setPassword("xoxo");
		 res.setMessage("Login Unsuccessful");
		 res.setError(true);
		 Mockito.when(userService.userLogin(user)).thenThrow(new CustomException("Login Unsuccessful"));
		 MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/login")
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.content("{\"userName\":\"A\",\"password\":\"xoxo\"}")
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
	 }
	 
	 @Test
	 void searchByUserNameTest() throws Exception {
		 User user = new User();
		 List<User> list=new ArrayList<>();
		 user.setFirstName("admin");
		 user.setLastName("admin");
		 user.setUserName("Anu123");
		 user.setEmail("fse@gmail.com");
		 user.setPassword("admin");
		 user.setNumber("89939394040");
		 list.add(user);
		 String username=user.getUserName();
		
		 Mockito.when(userService.searchUser(username)).thenReturn(list);
		 MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/user/search/"+username)
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
	 }
	 
	 @Test
	 void searchByUserNameExceptionTest() throws Exception {
		 User user = new User();
		 List<User> list=new ArrayList<>();
		 user.setFirstName("admin");
		 user.setLastName("admin");
		 user.setEmail("fse@gmail.com");
		 user.setPassword("admin");
		 user.setNumber("89939394040");
		 list.add(user);
		 String username=user.getUserName();
	
		 Mockito.when(userService.searchUser(username)).thenReturn(list);
		 MvcResult result = mvc.perform(get("http://localhost:8081/api/v1.0/tweets/user/search/"+username)
				 					.contentType(MediaType.APPLICATION_JSON)
				 					.accept(MediaType.APPLICATION_JSON))
				 					.andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
	 }
	 

	 
}
