package com.davisy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davisy.dao.PostDAO;
import com.davisy.service.PostService;

@Service
public class PostServiceImpl implements PostService{

	@Autowired
	PostDAO postDAO;
	@Override
	public int countPost(int id) {
		return postDAO.countPost(id);
	}

}
