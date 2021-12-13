package com.innogram.vo;

import java.util.Date;

import org.apache.ibatis.type.Alias;

@Alias("like")
public class LikeVO {
	private Integer likeId;
	private Integer postId;
	private Integer likeLikeYn;
	private Date likeDate;
	private String likeIp;
	
	public Integer getLikeId() {
		return likeId;
	}
	public void setLikeId(Integer likeId) {
		this.likeId = likeId;
	}
	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public Integer getLikeLikeYn() {
		return likeLikeYn;
	}
	public void setLikeLikeYn(Integer likeLikeYn) {
		this.likeLikeYn = likeLikeYn;
	}
	public Date getLikeDate() {
		return likeDate;
	}
	public void setLikeDate(Date likeDate) {
		this.likeDate = likeDate;
	}
	public String getLikeIp() {
		return likeIp;
	}
	public void setLikeIp(String likeIp) {
		this.likeIp = likeIp;
	}
	
	@Override
	public String toString() {
		return "LikeVO [likeId=" + likeId + ", postId=" + postId + ", likeLikeYn=" + likeLikeYn + ", likeDate="
				+ likeDate + ", likeIp=" + likeIp + "]";
	}
}
