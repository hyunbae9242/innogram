package com.innogram.vo;


import java.util.Date;
import java.util.List;

import org.apache.ibatis.type.Alias;

@Alias("post")
public class PostVO {
	private Integer postId;
	private String postTitle;
	private String postContents;
	private String postUserId;
	private String postPassword;
	private Date postCreatedDate;
	private Date postUpdatedDate;
	private Integer postLike;
	private Integer postDeleteYn;
	private String postIp;
	private List<CommentVO> commentList;
	
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getPostTitle() {
		return postTitle;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public String getPostContents() {
		return postContents;
	}
	public void setPostContents(String postContents) {
		this.postContents = postContents;
	}
	public String getPostUserId() {
		return postUserId;
	}
	public void setPostUserId(String postUserId) {
		this.postUserId = postUserId;
	}
	public String getPostPassword() {
		return postPassword;
	}
	public void setPostPassword(String postPassword) {
		this.postPassword = postPassword;
	}
	public Date getPostCreatedDate() {
		return postCreatedDate;
	}
	public void setPostCreatedDate(Date postCreatedDate) {
		this.postCreatedDate = postCreatedDate;
	}
	public Date getPostUpdatedDate() {
		return postUpdatedDate;
	}
	public void setPostUpdatedDate(Date postUpdatedDate) {
		this.postUpdatedDate = postUpdatedDate;
	}
	public Integer getPostLike() {
		return postLike;
	}
	public void setPostLike(Integer postLike) {
		this.postLike = postLike;
	}
	public Integer getPostDeleteYn() {
		return postDeleteYn;
	}
	public void setPostDeleteYn(Integer postDeleteYn) {
		this.postDeleteYn = postDeleteYn;
	}
	public String getPostIp() {
		return postIp;
	}
	public void setPostIp(String postIp) {
		this.postIp = postIp;
	}
	public List<CommentVO> getCommentList() {
		return commentList;
	}
	public void setCommentList(List<CommentVO> commentList) {
		this.commentList = commentList;
	}
	@Override
	public String toString() {
		return "PostVO [postId=" + postId + ", postTitle=" + postTitle + ", postContents=" + postContents
				+ ", postUserId=" + postUserId + ", postPassword=" + postPassword + ", postCreatedDate="
				+ postCreatedDate + ", postUpdatedDate=" + postUpdatedDate + ", postLike=" + postLike
				+ ", postDeleteYn=" + postDeleteYn + ", postIp=" + postIp + ", commentList=" + commentList + "]";
	}
}
