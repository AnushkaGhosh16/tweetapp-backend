package tweetapp.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import tweetapp.DTO.UserDTO;
import tweetapp.DTO.UserLoginDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.User;
import tweetapp.repository.UserRepo;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService userService;
	
	@Mock
	private UserRepo userRepo;
	
	@Test
	void registerUserTest()throws CustomException {
		UserDTO user=new UserDTO();
		user.setLoginId(1);
		user.setFirstName("Anushka");
		user.setLastName("Ghosh");
		user.setUserName("Anu123");
		user.setEmail("anu@xyz.com");
		user.setPassword("xoxo");
		user.setConfirmPassword("xoxo");
		user.setNumber("9002341667");

		assertEquals("Anushka",userService.registerUser(user).getFirstName());
	}
	
	@Test
	void registerUserPasswordsTest() {
		UserDTO user=new UserDTO();
		user.setLoginId(1);
		user.setFirstName("Anushka");
		user.setLastName("Ghosh");
		user.setUserName("Anu123");
		user.setEmail("anu@xyz.com");
		user.setPassword("xoxo");
		user.setConfirmPassword("xyxy");
		user.setNumber("9002341667");
		assertThrows(CustomException.class,() -> userService.registerUser(user));
	}
	
	@Test
	void registerUserLoginIdTest(){
		
		UserDTO user=new UserDTO();
		user.setLoginId(1);
		user.setFirstName("Sana");
		user.setLastName("Bose");
		user.setUserName("Sana23");
		user.setEmail("sana@xyz.com");
		user.setPassword("xyxy");
		user.setConfirmPassword("xyxy");
		user.setNumber("9002341667");
		
		Mockito.when(userRepo.findByLoginIdOrEmail(Mockito.anyInt(), Mockito.anyString())).thenReturn(values());
		assertThrows(CustomException.class,() -> userService.registerUser(user));
	}
	private List<User> values(){
		List<User> list=new ArrayList<>();
		User user=new User();
		user.setLoginId(1);
		user.setFirstName("Sana");
		user.setLastName("Bose");
		user.setUserName("Sana23");
		user.setEmail("sana@xyz.com");
		user.setPassword("xyxy");
		user.setNumber("9002341667");
		list.add(user);
		return list;
	}
	
	@Test
	void getUsersTest() {
		User user=new User();
		List<User> user1=new ArrayList<>();
		user.setLoginId(1);
		user.setFirstName("Sana");
		user.setLastName("Bose");
		user.setUserName("Sana23");
		user.setEmail("sana@xyz.com");
		user.setPassword("xyxy");
		user.setNumber("9002341684");
		user1.add(user);
		Mockito.when(userRepo.findAll()).thenReturn(user1);
		assertEquals("Sana", userService.getUsers().get(0).getFirstName());
	}
	
	@Test
	void userLoginSuccessfulTest()throws CustomException{
		UserLoginDTO login=new UserLoginDTO();
		login.setUserName("Anu123");
		login.setPassword("xoxo");
		Mockito.when(userRepo.findByUserName(login.getUserName())).thenReturn(details());
		assertEquals("Login Successful", userService.userLogin(login));
	}
	
	private Optional<User> details(){
		User user=new User();
		user.setUserName("Anu123");
		user.setPassword("xoxo");
		Optional<User> value=Optional.of(user);
		return value;
	}
	
	@Test
	void userLoginUnsuccessfulTest(){
		UserLoginDTO login=new UserLoginDTO();
		login.setUserName("An");
		login.setPassword("xo");
		Mockito.when(userRepo.findByUserName(login.getUserName())).thenThrow(CustomException.class);
		assertThrows(CustomException.class, ()-> userService.userLogin(login));
	}
	
	@Test
	void searchUserTest() {
		User user=new User();
		List<User> user1=new ArrayList<>();
		user.setLoginId(1);
		user.setFirstName("Sana");
		user.setLastName("Bose");
		user.setUserName("Sana23");
		user.setEmail("sana@xyz.com");
		user.setPassword("xyxy");
		user.setNumber("9002341684");
		user1.add(user);
		Mockito.when(userRepo.findByUserNameLike("Sa")).thenReturn(user1);
		assertNotNull(userService.searchUser("Sana23"));
	}
	
	@Test
	void forgotPasswordTest()throws CustomException {
		User user=new User();
		user.setUserName("Anu123");
		String name=user.getUserName();
		String password="123";
		Mockito.when(userRepo.findByUserName(name)).thenReturn(details1());
		assertEquals("Password reset",userService.forgotPassword(name, password));
		
	}
	private Optional<User> details1(){
		User user=new User();
		user.setUserName("Anu123");
		Optional<User> value=Optional.of(user);
		return value;
	}
	
	@Test
	void forgotPasswordExceptionTest() {
		User user=new User();
		String name=user.getUserName();
		String password=user.getPassword();
		Mockito.when(userRepo.findByUserName(Mockito.anyString())).thenThrow(CustomException.class);
		assertThrows(CustomException.class,() -> userService.forgotPassword(name, password));
		
	}
	

	
//	@Test
//	void forgotPasswordTest1()throws CustomException {
//		User user=new User();
//		user.setUserName("Anu123");
//		String name=user.getUserName();
//		String password="aa";
//		Mockito.when(userRepo.findByUserName(name)).thenReturn(details3());
//		assertEquals("Password reset", userService.forgotPassword(name, password));
//		
//	}
//	
//	private Optional<User> details3(){
//		User user=new User();
//		user.setUserName("Anu123");
//		Optional<User> value=Optional.of(user);
//		return value;
//	}
}
