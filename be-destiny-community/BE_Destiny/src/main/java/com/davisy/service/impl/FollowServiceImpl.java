package com.davisy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davisy.dao.FollowDAO;
import com.davisy.entity.Follower;
import com.davisy.service.FollowService;

@Service
public class FollowServiceImpl implements FollowService {
	@Autowired
	FollowDAO followDAO;

	@Override
	public List<Follower> findAllFollowers(int id) {
		List<Follower> list = followDAO.findAllFollower(id);
		if (list == null)
			return null;
		return list;
	}

	@Override
	public int countFollowers(int id) {
		return followDAO.countFollowers(id);
	}

	@Override
	public void delete(int id1, int id2) {
		followDAO.delete(followDAO.unFollow(id1, id2));
	}

	@Override
	public void create(Follower follower) {
		followDAO.save(follower);
	}

	@Override
	public List<Follower> findAll() {
		return followDAO.findAll();
	}

	@Override
	public boolean checkFriend(int id1, int id2) {
		List<Follower> list = followDAO.findAllSuggest(id1, id2);
		if (list.size() < 2)
			return false;
		else
			return true;
	}

	@Override
	public List<Follower> findALlFriend(int follow_id, int user_id) {
		List<Follower> list = followDAO.findALlFriend(follow_id, user_id);
		if (list == null)
			return null;
		return list;
	}

	@Override
	public List<Integer> findAllFollowingUser(int id) {
		List<Integer> list = followDAO.findAllFollowingUser(id);
		if (list == null)
			return null;
		return list;
	}

	@Override
	public boolean checkFollow(int id1, int id2) {
		Follower follower = followDAO.checkFollow(id1, id2);
		if (follower == null)
			return false;
		return true;
	}

}
