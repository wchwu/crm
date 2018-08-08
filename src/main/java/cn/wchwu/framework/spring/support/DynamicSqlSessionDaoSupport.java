package cn.wchwu.framework.spring.support;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;

public class DynamicSqlSessionDaoSupport extends SqlSessionDaoSupport {
	@Resource
	private SqlSessionFactoryBean sqlSessionFactoryBean;
	protected SqlSessionFactory sqlSessionFactory;
	private SqlSession sqlSession;

	@Override
	public SqlSession getSqlSession() {
		try {
			sqlSessionFactory = sqlSessionFactoryBean.getObject();
			this.sqlSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sqlSession;
	}

}
