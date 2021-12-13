package com.innogram.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.innogram.vo.PostVO;

public interface PostDao {
	public int insertPost(PostVO post);
	public int updatePost(PostVO post);
	public PostVO selectPost(PostVO post);
	public List<PostVO> selectPostList(PostVO post);
	public int deletePost(PostVO post);
	public int updateLikePost(PostVO post, SqlSession session);
}
