package com.davisy.dao;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.davisy.entity.Follower;
//@Cacheable("follower")//Tạo bộ nhớ đệm
@Repository
public interface FollowDAO extends JpaRepository<Follower, String> {

//	@Cacheable("follower") 
	@Query(value = "SELECT /*+ RESULT_CACHE */ *FROM follower f WHERE f.follower_id=:id", nativeQuery = true)
	public List<Follower> findAllFollower(int id);

//	@Cacheable("follower")
	@Query(value = "select /*+ RESULT_CACHE */ count(f.follower_id) as FollowerCount from follower f inner join users u on f.user_id =u.user_id where u.user_id =:id", nativeQuery = true)
	public int countFollowers(int id);

//	@Cacheable("follower")
	@Query(value = "select /*+ RESULT_CACHE */ *from follower f where f.follower_id =:id1 and f.user_id =:id2", nativeQuery = true)
	public Follower unFollow(int id1, int id2);

//	@Cacheable("follower")
	@Query(value = "SELECT /*+ RESULT_CACHE */ *FROM follower WHERE (follower_id =:id1 AND user_id =:id2) OR (follower_id =:id2 AND user_id =:id1)", nativeQuery = true)
	public List<Follower> findAllSuggest(int id1, int id2);

//	@Cacheable("follower")
	@Query(value = "SELECT /*+ RESULT_CACHE */ *FROM follower WHERE follower_id =:follower_id or user_id =:user_id", nativeQuery = true)
	public List<Follower> findALlFriend(int follower_id, int user_id);

//	@Cacheable("follower")
	@Query(value = "select /*+ RESULT_CACHE */ f.user_id from follower f where f.follower_id =:id", nativeQuery = true)
	public List<Integer> findAllFollowingUser(int id);

//	@Cacheable("follower")
	@Query(value = "select /*+ RESULT_CACHE */ *from follower f where f.follower_id =:follower_id and f.user_id =:user_id", nativeQuery = true)
	public Follower checkFollow(int follower_id, int user_id);
}
