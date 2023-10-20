package com.davisy.controller.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.davisy.config.JwtTokenUtil;
import com.davisy.dto.Admin;
import com.davisy.dto.AdminPostDetail;
import com.davisy.dto.AdminUserProfile;
import com.davisy.dto.CommentDetail;
import com.davisy.dto.PostImagesDetail;
import com.davisy.entity.Comment;
import com.davisy.entity.Post;
import com.davisy.entity.PostImages;
import com.davisy.entity.User;
import com.davisy.service.CommentService;
import com.davisy.service.FollowService;
import com.davisy.service.InterestedService;
import com.davisy.service.PostImagesService;
import com.davisy.service.PostService;
import com.davisy.service.ShareService;
import com.davisy.service.UserService;

@RestController
@CrossOrigin("*")
@RolesAllowed("ROLE_ADMIN")
public class AdminControl {
	@Autowired
	private PostService postService;
	@Autowired
	private InterestedService interestedService;
	@Autowired
	private ShareService shareService;
	@Autowired
	private PostImagesService postImagesService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private FollowService followService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	// 23-9-2023 -xem chi tiết bài đăng
	// update lastest 7-10
	@GetMapping("/v1/admin/detailPost/{postid}")
	public ResponseEntity<AdminPostDetail> detailPost(@PathVariable int postid) {
		try {
			Post post = postService.findPostByID(postid);

			return ResponseEntity.status(200).body(setPostDetail(post));

		} catch (Exception e) {
			System.out.println("Error at admin/detailPost: " + e);
			return ResponseEntity.status(403).body(null);
		}
	}

	// 23-9-2023 -xem chi tiết người dùng
	// update lastest 7-10
	@GetMapping("/v1/admin/detailUser/{email}")
	public ResponseEntity<AdminUserProfile> detailUser(@PathVariable String email) {
		try {
			User user = userService.findByEmail(email);

			return ResponseEntity.status(200).body(setUserDetail(user));
		} catch (Exception e) {
			System.out.println("Error at admin/actionOnUser: " + e);
			return ResponseEntity.status(403).body(null);
		}
	}

	// 22-9-2023 -Vô hiệu hóa bài viết
	@GetMapping("/v1/admin/actionOnPost/{postid}")
	public ResponseEntity<Post> adminGetActionPost(@PathVariable int postid) {
		try {
			Post post = postService.findPostByID(postid);
			postService.disable(post);
			return ResponseEntity.status(200).body(post);
		} catch (Exception e) {
			System.out.println("Error at admin/actionOnPost: " + e);
			return ResponseEntity.status(403).body(null);
		}
	}

	// 22-9-2023 -Vô hiệu hóa người dùng
	@GetMapping("/v1/admin/actionOnUser/{email}")
	public ResponseEntity<User> adminGetActionUser(@PathVariable String email) {
		try {
			User user = userService.findByEmail(email);
			userService.disable(user);
			return ResponseEntity.status(200).body(user);
		} catch (Exception e) {
			System.out.println("Error at admin/actionOnUser: " + e);
			return ResponseEntity.status(403).body(null);
		}
	}

	public AdminUserProfile setUserDetail(User user) {

		AdminUserProfile userProfile = new AdminUserProfile();

		userProfile.setFullname(user.getFullname());
		userProfile.setEmail(user.getEmail());
		userProfile.setIntro(user.getIntro());

		userProfile.setBirthday(formatDate(user.getBirthday()));

		userProfile.setDay_join(String.valueOf(user.getDay_create().get(Calendar.DAY_OF_MONTH)));
		userProfile.setMonth_join(String.valueOf(user.getDay_create().get(Calendar.MONTH) + 1));
		userProfile.setYear_join(String.valueOf(user.getDay_create().get(Calendar.YEAR)));

		userProfile.setCity_name(user.getProvinces().getFull_name());
		userProfile.setGender_name(user.getGender().getGender_name());
		userProfile.setAvartar(user.getAvatar());
		userProfile.setThumb(user.getThumb());
		userProfile.setMark(user.getMark());

		userProfile.setTotalPost(userTotalPost(user.getUser_id()));

		userProfile.setTotalInterested(userTotalInterested(user.getUser_id()));

		userProfile.setTotalFollower(userTotalFollower(user.getUser_id()));

		userProfile.setTotalComment(userTotalComment(user.getUser_id()));

		userProfile.setTotalShare(userTotalShare(user.getUser_id()));

		List<Post> listPost = postService.getListPostByUserID(user.getUser_id());

		List<AdminPostDetail> listAdminPostDetails = new ArrayList<>();

		for (Post post : listPost) {

			listAdminPostDetails.add(setPostDetail(post));
		}

		userProfile.setListAllPostOfUser(listAdminPostDetails);

		return userProfile;
	}

	// update lastest 7-10
	public AdminPostDetail setPostDetail(Post post) {

		AdminPostDetail postDTO = new AdminPostDetail();

		postDTO.setPost_id(post.getPost_id());

		postDTO.setUser_email(post.getUser().getEmail());

		postDTO.setUser_fullname(post.getUser().getFullname());

		postDTO.setUser_avartar(post.getUser().getAvatar());

		postDTO.setContent(post.getContent());

		postDTO.setDate_Post(timeCaculate(post.getDate_Post()));

		postDTO.setProduct(post.getProduct());

		postDTO.setTotalInterested(postTotalInterested(post.getPost_id()));

		postDTO.setTotalShare(postTotalShare(post.getPost_id()));

		postDTO.setTotalComment(postTotalComment(post.getPost_id()));

		postDTO.setListPostImages(listImagesDetail(post.getPost_id()));

		postDTO.setListComments(listCommentDetail(post.getPost_id()));

		return postDTO;
	}

	// update lastest 7-10
	public String formatDate(Calendar birthday) {
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		String formatted = format.format(birthday.getTime());

		return formatted;
	}

	// update lastest 7-10
	public int userTotalPost(int userId) {
		return postService.getTotalPostByUser(userId);
	}

	// update lastest 7-10
	public int userTotalInterested(int userId) {
		return interestedService.totalInterestedByUser(userId);
	}

	// update lastest 7-10
	public int userTotalFollower(int userId) {
		return followService.countFollowers(userId);
	}

	// update lastest 7-10
	public int userTotalComment(int userId) {
		return commentService.totalCommentByUser(userId);
	}

	// update lastest 7-10
	public int userTotalShare(int userId) {
		return shareService.totalShareByUser(userId);
	}

	// update lastest 7-10
	public int postTotalInterested(int postId) {
		return interestedService.totalInterestedByPost(postId);
	}

	// update lastest 7-10
	public int postTotalShare(int postId) {
		return shareService.totalShareByPost(postId);
	}

	// update lastest 7-10
	public int postTotalComment(int postId) {
		return commentService.totalCommentByPost(postId);
	}

	// update lastest 7-10
	public String timeCaculate(Calendar datePost) {
		String timeCaculate = "";

		Calendar calendar = GregorianCalendar.getInstance();

		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);

		int dayPost = datePost.get(Calendar.DAY_OF_MONTH);
		int monthPost = datePost.get(Calendar.MONTH) + 1;
		int yearPost = datePost.get(Calendar.YEAR);

		int yearCaculate = year - yearPost;
		int monthCaculate = month - monthPost;

		int totalDayInMonth = checkMonth(monthPost, yearPost);
		int dayCaculate = (totalDayInMonth - dayPost) + day;

		if (yearCaculate > 0) {
			timeCaculate = String.valueOf(yearCaculate + " năm trước");
		}
		if (monthCaculate == 1) {

			if (dayCaculate <= 7) {
				timeCaculate = String.valueOf(dayCaculate + " ngày trước");
			} else if (dayCaculate > 7 && dayCaculate <= 14) {
				timeCaculate = String.valueOf("2 tuần trước");
			} else if (dayCaculate > 14 && dayCaculate <= 21) {
				timeCaculate = String.valueOf("3 tuần trước");
			} else {
				timeCaculate = String.valueOf(monthCaculate + " tháng trước");
			}

		} else if (monthCaculate > 1) {
			timeCaculate = String.valueOf(monthCaculate + " tháng trước");
		} else {
			timeCaculate = String.valueOf(dayCaculate - totalDayInMonth + " ngày trước");
		}

		return timeCaculate;

	}

	// update lastest 7-10
	public boolean checkYear(int year) {
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	// update lastest 7-10
	public int checkMonth(int month, int year) {
		int totalDayInMonth = 0;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			totalDayInMonth = 31;
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			totalDayInMonth = 30;
		} else if (month == 2) {
			if (checkYear(year)) {
				totalDayInMonth = 29;
			} else {
				totalDayInMonth = 28;
			}
		}
		return totalDayInMonth;
	}

	// update lastest 7-10
	public List<PostImagesDetail> listImagesDetail(int postId) {

		List<PostImages> listImages = postImagesService.getListPostImagesByPostID(postId);
		List<PostImagesDetail> listImagesDetail = new ArrayList<>();
		for (PostImages postImages : listImages) {
			PostImagesDetail postImagesDetail = new PostImagesDetail();
			postImagesDetail.setLink_image(postImages.getLink_image());
			listImagesDetail.add(postImagesDetail);
		}
		String img = "https://firebasestorage.googleapis.com/v0/b/destiny-davisy.appspot.com/o/08.jpg?alt=media&token=1027fbbb-43ee-4046-8e13-5640153356ea&_gl=1*17e3a7c*_ga*MTcxMDU3NTczOS4xNjc2OTc2NjE1*_ga_CW55HF8NVT*MTY5NjUwMzgxNi44LjEuMTY5NjUwNTg5Ny42MC4wLjA.";

		PostImagesDetail postImagesDetail = new PostImagesDetail();
		postImagesDetail.setLink_image(img);
		listImagesDetail.add(postImagesDetail);
		PostImagesDetail postImagesDetail2 = new PostImagesDetail();
		postImagesDetail2.setLink_image(img);
		listImagesDetail.add(postImagesDetail2);

		return listImagesDetail;
	}

	// update lastest 7-10
	public List<CommentDetail> listCommentDetail(int postId) {

		List<Comment> listComment = commentService.getListCommentByPostID(postId);
		List<CommentDetail> listCommentDetail = new ArrayList<>();
		for (Comment comment : listComment) {
			CommentDetail commentDetail = new CommentDetail();
			commentDetail.setContent(comment.getContent());
			commentDetail.setUser_fullname(comment.getUser().getFullname());
			commentDetail.setUser_avartar(comment.getUser().getAvatar());
			commentDetail.setUser_email(comment.getUser().getEmail());
			listCommentDetail.add(commentDetail);
		}

		return listCommentDetail;
	}

}
