package com.innogram.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.innogram.vo.CommentVO;

public interface CommentService {
	public int insertComment(HttpServletRequest request);
	public int updateComment(HttpServletRequest request);
	public CommentVO selectComment(HttpServletRequest request);
	public List<CommentVO> selectCommentList(HttpServletRequest request);
	public int deleteComment(HttpServletRequest request);
}
