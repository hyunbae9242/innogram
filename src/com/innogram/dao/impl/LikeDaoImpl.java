package com.innogram.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.innogram.common.SqlSessionManager;
import com.innogram.dao.LikeDao;
import com.innogram.vo.LikeVO;

public class LikeDaoImpl implements LikeDao {

	SqlSessionFactory sqlSessionFactory = SqlSessionManager.getSqlSessionFactory();
	
	@Override
	public int insertLike(LikeVO like , SqlSession session) {
		try {
			if(session != null) {
				return session.insert("com.innogram.dao.LikeDao.insertLike",like);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateLike(LikeVO like, SqlSession session) {
		try {
			if(session != null) {
				return session.update("com.innogram.dao.LikeDao.updateLike",like);	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public LikeVO selectLike(LikeVO like) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectOne("com.innogram.dao.LikeDao.selectLike",like);
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
	public List<LikeVO> selectLikeList(LikeVO like) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.selectList("com.innogram.dao.LikeDao.selectLikeList",like);
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
	public int deleteLike(LikeVO like) {
		SqlSession session = null;
		try {
			session = sqlSessionFactory.openSession();
			return session.update("com.innogram.dao.LikeDao.updateLike",like);
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

}
