package tweetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tweetapp.model.TweetModel;

@Repository
public interface TweetRepo extends MongoRepository<TweetModel, String>{
	
	public Optional<TweetModel> deleteByTweetId(String tweetId);
	public List<TweetModel> findByUserNameLike(String userName);

}
