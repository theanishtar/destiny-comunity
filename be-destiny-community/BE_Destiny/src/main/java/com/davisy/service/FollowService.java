package com.davisy.service;

import java.util.List;

import com.davisy.entity.Follower;

public interface FollowService {

	public List<Follower> findAllFollowers(int id);

	public List<Follower> findAll();

	public List<Follower> findALlFriend(int follow_id, int user_id);

	public List<Integer> findAllFollowingUser(int id);

	public int countFollowers(int id);

	public void delete(int id1, int id2);

	public void create(Follower follower);

	public boolean checkFriend(int id1, int id2);
}
