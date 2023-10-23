package com.davisy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davisy.dao.PostImagesDAO;
import com.davisy.entity.PostImages;
import com.davisy.service.PostImagesService;

@Service
public class PostImagesServiceImpl implements PostImagesService {

	@Autowired
	PostImagesDAO postImagesDAO;

	@Override
	public int countPostImages(int id) {
		return postImagesDAO.countPostImages(id);
	}

	// 22-9-2023 -lấy tổng ảnh của bài đăng
	@Override
	public List<PostImages> getListPostImagesByPostID(int id) {
		return postImagesDAO.getListPostImagesByPostID(id);
	}

	// 16-10-2023 - lấy tất cả hình ảnh của user đã đăng
	@Override
	public List<String> findAllImagesUser(int id) {
		List<String> list = postImagesDAO.findAllImagesUser(id);
		return list;
	}

	@Override
	public List<String> findAllImagesofPost(int id) {
		return postImagesDAO.findAllImagesofPost(id);
	}
}