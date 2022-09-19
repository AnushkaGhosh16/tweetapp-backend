package tweetapp.model;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Generated
@Document(collection = "Users")
public class User {
	@Id
	private String id;
	@NotNull
	private int loginId;
	@NotNull(message = "First name should not be null")
	private String firstName;
	@NotNull(message = "Last name should not be null")
	private String lastName;
	private String userName;
	@NotNull(message = "Email should not be null")
	private String email;
	@NotNull(message = "Password should not be null")
	private String password;
	@NotNull(message = "Phone number should not be null")
	private String number;
	
	
}
