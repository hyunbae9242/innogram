package com.innogram.dao;

import java.util.List;

import com.innogram.vo.CommentVO;

public interface CommentDao {
	public int insertComment(CommentVO comment);
	public int updateComment(CommentVO comment);
	public CommentVO selectComment(CommentVO comment);
	public List<CommentVO> selectCommentList(CommentVO comment);
	public int deleteComment(CommentVO comment);
}
