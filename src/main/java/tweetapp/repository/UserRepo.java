package tweetapp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import tweetapp.model.User;

@Repository
public interface UserRepo extends MongoRepository<User, String>{
	public List<User> findByLoginIdOrEmail(int loginId, String email);
	public Optional<User> findByUserNameAndPassword(String userName, String password);
	public List<User> findByUserNameLike(String userName);
	public Optional<User> findByUserName(String userName);
}
