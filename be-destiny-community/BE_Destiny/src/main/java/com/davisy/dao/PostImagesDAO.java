package com.davisy.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.davisy.entity.PostImages;

public interface PostImagesDAO extends JpaRepository<PostImages, Long> {
	@Query(value = "select count(pi2.post_images_id) as imgcount from post_images pi2  inner join post p on pi2.post_id =p.post_id where p.user_id =:id", nativeQuery = true)
	public int countPostImages(int id);

	// 22-9-2023 -lấy tổng ảnh của bài đăng
	@Query(value = "SELECT * FROM post_images WHERE post_id =:id", nativeQuery = true)
	public List<PostImages> getListPostImagesByPostID(int id);
}
