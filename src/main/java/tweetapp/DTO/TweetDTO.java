package tweetapp.DTO;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TweetDTO {
	
	

	@NotNull(message = "Tweet should not be null")
	@Size(max=144, message = "Tweet can have a max of 144 characters and 50 characters with a tag")
	private String tweet;
	@NotNull(message = "Username cannot be nulls")
	private String userName;
	private String postedAt;
	@Size(max=144, message = "Tweettag can have a max of 50 characters")
	private String tweetTag;
	//@Size(max=144, message = "Replies can have a max of 144 characters")
//	private List<Reply> replies;
//	private List<String> likes;

}
