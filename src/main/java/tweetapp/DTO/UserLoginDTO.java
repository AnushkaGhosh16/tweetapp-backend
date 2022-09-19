package tweetapp.DTO;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {
	
	private String userName;
	@NotNull(message = "Password should not be null")
	private String password;
}
