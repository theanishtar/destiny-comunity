package com.davisy.service;

import java.util.List;

import com.davisy.entity.Post;

public interface PostService {
	public int countPost(int id);

	List<Object[]> getTOP5Post();
	

	// 21-9-2023 -tìm post theo id
	public Post findPostByID(int id);

	// 21-9-2023 -lấy tổng số bài đăng theo tháng
	// lastest update 14-10
	public int getTotalPostByMonth(int month);

	// 21-9-2023 -lấy phần trăm bài đăng có trạng thái đã gửi
	public double getPercentPostSendSuccess();

	// 21-9-2023 -Top 4 bài đăng có lượt yêu thích nhiều nhất
	public List<Object[]> getTOP4Post();

	// 22-0-2023 -Tổng số bài đăng theo từng tháng
	public List<Object[]> getTotalPostEveryMonth();

	// 22-9-2023 -TOP 3 sản phẩm được đăng bài nhiều nhất
	public List<Object[]> getTOP3Product();

	// 23-9-2023 -Tổng bài đăng của người dùng đã dăng
	public int getTotalPostByUser(int id);

	// 23-9-2023 -Danh sách tất cả bài đăng của người dùng theo id
	public List<Post> getListPostByUserID(int id);

	public void create(Post post);

	public void update(Post post);

	public void disable(Post post);
}
