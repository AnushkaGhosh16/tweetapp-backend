package tweetapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import tweetapp.DTO.UserDTO;
import tweetapp.DTO.UserLoginDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.User;
import tweetapp.repository.UserRepo;

@Service
@Log4j2
public class UserService {
	
	@Autowired
	private UserRepo userRepo; 
	
	public User registerUser(UserDTO user)throws CustomException{
		User u=new User();
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			
			log.error("Passwords do not match");
			throw new CustomException("Passwords do not match");
			
		
		}else if(!userRepo.findByLoginIdOrEmail(user.getLoginId(),user.getEmail()).isEmpty()) {
			throw new CustomException("User already present");
		}
		else {
			u.setLoginId(user.getLoginId());
			u.setFirstName(user.getFirstName());
			u.setLastName(user.getLastName());
			u.setUserName(user.getUserName());
			u.setEmail(user.getEmail());
			u.setPassword(user.getPassword());
			u.setNumber(user.getNumber());
			userRepo.save(u);
			}
		return u;
	}
	
	public List<User> getUsers(){
		return userRepo.findAll();
	}
	
	public String userLogin(UserLoginDTO login)throws CustomException {
		User u;
		Optional<User> user = userRepo.findByUserName(login.getUserName());
		if(user.isPresent()) {
			u=user.get();
			if(u.getPassword().equals(login.getPassword())) {
				return "Login Successful";
			}else {
				return "Login unsuccessful";
			}
		}else {
			log.error("No user found");
			throw new CustomException("No user found");
		}
	}
	
	public List<User> searchUser(String userName) {
		log.info("User found");
		return userRepo.findByUserNameLike(userName);			
	}
	
	public String forgotPassword(String userName, String password)throws CustomException {
		User u;
		Optional<User> user=userRepo.findByUserName(userName);
		if(user.isEmpty()) {
			log.error("No user found");
			throw new CustomException("No user found");
		}
		else {
			u=user.get();
			if(u.getUserName().equals(userName)) {
				u.setPassword(password);
				userRepo.save(u);
				
			}
			return "Password reset";
		}
	}
}
