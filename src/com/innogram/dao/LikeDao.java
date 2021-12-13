package com.innogram.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.innogram.vo.LikeVO;

public interface LikeDao {
	public int insertLike(LikeVO like, SqlSession session);
	public int updateLike(LikeVO like, SqlSession session);
	public LikeVO selectLike(LikeVO like);
	public List<LikeVO> selectLikeList(LikeVO like);
	public int deleteLike(LikeVO like);
}
