package tweetapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import tweetapp.DTO.GeneralResponseDTO;
import tweetapp.DTO.ResponseDTO;
import tweetapp.DTO.UserDTO;
import tweetapp.DTO.UserLoginDTO;
import tweetapp.exception.CustomException;
import tweetapp.model.User;
import tweetapp.service.UserService;

@RestController
@RequestMapping("/api/v1.0/tweets")
@Log4j2
@CrossOrigin(origins="http://localhost:4200")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<GeneralResponseDTO> register(@RequestBody UserDTO user){
		try {
		userService.registerUser(user);
		return new ResponseEntity<>(new GeneralResponseDTO("Registered Successfully",false),HttpStatus.OK);
	}catch (Exception e) {
		return new ResponseEntity<>(new GeneralResponseDTO(e.getMessage(),true),HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("users/all")
	public ResponseEntity<List<User>> getUsers(){
		List<User> list=userService.getUsers();
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<GeneralResponseDTO> userLogin(@RequestBody UserLoginDTO login){
		try {
			userService.userLogin(login);
			return new ResponseEntity<>(new GeneralResponseDTO("Login Successful",false),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new GeneralResponseDTO(e.getMessage(),true),HttpStatus.BAD_REQUEST);
			}
	}
	
	@PutMapping("{username}/forgot")
	public ResponseEntity<GeneralResponseDTO> forgotPassword(@PathVariable("username") String username,@RequestBody String password){
		try {
			String reset=userService.forgotPassword(username,password);
			return new ResponseEntity<>(new GeneralResponseDTO(reset,false),HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(new GeneralResponseDTO(e.getMessage(),true),HttpStatus.BAD_REQUEST);
			}
	}
	@GetMapping("user/search/{username}")
	public ResponseEntity<ResponseDTO> userSearch(@PathVariable("username") String username){
		try {
			List<User> user=userService.searchUser(username);
			if(!user.isEmpty())
				return new ResponseEntity<>(new ResponseDTO("User Found",user),HttpStatus.OK);
			else
				return new ResponseEntity<>(new ResponseDTO("User Not Found",user),HttpStatus.NOT_FOUND);
		}catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

	}
}
