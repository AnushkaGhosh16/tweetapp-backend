package tweetapp.DTO;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
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
		@NotNull(message = "Confirm Password should not be null")
		private String confirmPassword;
		@NotNull(message = "Phone number should not be null")
		private String number;

}
