package tweetapp.model;

import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Value;
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
@Document(collection = "Tweet")
public class TweetModel {
	
	@Id
	private String tweetId;
	@NotNull(message = "Tweet should not be null")
	@Size(max=144, message = "Tweet can have a max of 144 characters")
	private String tweet;
	private String userName;
	private String postedAt;
	private List<TweetModel> replies;
	@Size(max=144, message = "Tweettag can have a max of 50 characters")
	private String tweetTag;
	private long likes;
	private List<String> likedBy;
	
	public void doLike(String persons) {
		this.likedBy.add(persons);
	}
	
	public void addReply(TweetModel reply) {
		this.replies.add(reply);
	}
	
}
