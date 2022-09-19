package tweetapp.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tweetapp.model.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {
	private String status;
	private List<User> user;

}
