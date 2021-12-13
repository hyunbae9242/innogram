package com.innogram.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.innogram.common.SqlSessionManager;
import com.innogram.dao.PostDao;
import com.innogram.vo.PostVO;

public class PostDaoImpl implements PostDao {
	
	SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSessionFactory();
	
	@Override
	public int insertPost(PostVO post) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.insert("com.innogram.dao.PostDao.insertPost",post);
		}catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.commit();
				session.close();
			}
		}
		return 0;
	}

	@Override
	public int updatePost(PostVO post) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.update("com.innogram.dao.PostDao.updatePost",post);
		}catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.commit();
				session.close();
			}
		}
		return 0;
	}

	@Override
	public PostVO selectPost(PostVO post) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.innogram.dao.PostDao.selectPost",post);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public List<PostVO> selectPostList(PostVO post) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectList("com.innogram.dao.PostDao.selectPostList",post);
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.close();
			}
		}
		return null;
	}

	@Override
	public int deletePost(PostVO post) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.update("com.innogram.dao.PostDao.updatePost",post);
		}catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}finally {
			if(session != null) {
				session.commit();
				session.close();
			}
		}
		return 0;
	}
	
	@Override
	public int updateLikePost(PostVO post, SqlSession session) {
		try {
			return session.update("com.innogram.dao.PostDao.updateLikePost",post);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void main(String[] args) {
		PostDao postDao = new PostDaoImpl();
		PostVO post = new PostVO();
		post.setPostId(15);
		post.setPostTitle("제목");
		post.setPostContents("contents");
		post.setPostUserId("userId");
		post.setPostPassword("password");
		post.setPostIp("ip");
		int result = postDao.insertPost(post);
		System.out.println(result);
	}
}
