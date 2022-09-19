package tweetapp.DTO;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tweetapp.model.TweetModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
	
	@NotNull(message = "Tweet should not be null")
	@Size(max=144, message = "Tweet can have a max of 144 characters")
	private String tweet;
	@NotNull(message = "Username cannot be null")
	private String userName;
	@Size(max=144, message = "Tweettag can have a max of 50 characters")
	private String tweetTag;
	private String repliedAt;
}
