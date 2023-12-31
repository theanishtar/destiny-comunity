package com.davisy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davisy.dao.CommentDAO;
import com.davisy.entity.Comment;
import com.davisy.entity.CommentEntity;
import com.davisy.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDAO commentDao;

	// 22-9-2023 -lấy tất cả bình luận của bài đăng
	@Override
	public List<Comment> getListCommentByPostID(int id) {
		return commentDao.getListCommentByPostID(id);
	}

	// 22-9-2023 -lấy tổng bình luận của bài đăng)
	@Override
	public int totalCommentByPost(int id) {
		return commentDao.totalCommentByPost(id);
	}

	// 23-9-2023 -lấy tổng bình luận của người dùng đã bình luận
	@Override
	public int totalCommentByUser(int id) {
		return commentDao.totalCommentByUser(id);
	}

	@Override
	public Comment findById(int id) {
		return commentDao.findById(id).get();
	}

	@Override
	public List<Comment> findAllByIdComment(int id) {
		return commentDao.findAllByIdComment(id);
	}

	@Override
	public List<Object[]> findAllComment(int id, int check) {
		return commentDao.findAllComment(id, check);
	}

	@Override
	public void create(Comment comment) {
		commentDao.save(comment);
	}

	@Override
	public void update(Comment comment) {
		commentDao.saveAndFlush(comment);
	}

	@Override
	public void delete(Comment comment) {
		commentDao.delete(comment);
	}

	@Override
	public Integer findByIdtoUser(int id) {
		return commentDao.findByIdtoUser(id);
	}
}
