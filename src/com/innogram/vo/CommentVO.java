package com.innogram.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("comment")
public class CommentVO {
	private Integer commentId;
	private Integer postId;
	private String commentUserId;
	private String commentContents;
	private Integer commentDepth;
	private Integer commentBaseId;
	private Date commentCreatedDate;
	private Integer commentDeleteYn;
	
	public Integer getCommentId() {
		return commentId;
	}
	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getCommentUserId() {
		return commentUserId;
	}
	public void setCommentUserId(String commentUserId) {
		this.commentUserId = commentUserId;
	}
	public String getCommentContents() {
		return commentContents;
	}
	public void setCommentContents(String commentContents) {
		this.commentContents = commentContents;
	}
	public Integer getCommentDepth() {
		return commentDepth;
	}
	public void setCommentDepth(Integer commentDepth) {
		this.commentDepth = commentDepth;
	}
	public Integer getCommentBaseId() {
		return commentBaseId;
	}
	public void setCommentBaseId(Integer commentBaseId) {
		this.commentBaseId = commentBaseId;
	}
	public Date getCommentCreatedDate() {
		return commentCreatedDate;
	}
	public void setCommentCreatedDate(Date commentCreatedDate) {
		this.commentCreatedDate = commentCreatedDate;
	}
	public Integer getCommentDeleteYn() {
		return commentDeleteYn;
	}
	public void setCommentDeleteYn(Integer commentDeleteYn) {
		this.commentDeleteYn = commentDeleteYn;
	}
	
	@Override
	public String toString() {
		return "CommentVO [commentId=" + commentId + ", postId=" + postId + ", commentUserId=" + commentUserId
				+ ", commentContents=" + commentContents + ", commentDepth=" + commentDepth + ", commentBaseId="
				+ commentBaseId + ", commentCreatedDate=" + commentCreatedDate + ", commentDeleteYn=" + commentDeleteYn
				+ "]";
	}
	
	
}
