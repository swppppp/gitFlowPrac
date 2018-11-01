package kr.or.kosta.shoppingmall.user.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import kr.or.kosta.shoppingmall.common.web.Params;
import kr.or.kosta.shoppingmall.user.domain.User;

public class MybatisUserDao implements UserDao {

	SqlSessionFactory sqlSessionFactory;
	
	private static final String NAMESPACE = "kr.or.kosta.shoppingmall.user.";

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public void create(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert(NAMESPACE + "create", user);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public User read(String id) throws Exception {
		User user = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		user = sqlSession.selectOne(NAMESPACE + "read", id);
		sqlSession.close();
		return user;
	}

	@Override
	public void update(User user) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(NAMESPACE + "update", user);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public void delete(String id) throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(NAMESPACE + "delete", id);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public List<User> listAll() throws Exception {
		List<User> list = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		list = sqlSession.selectList(NAMESPACE + "listAll");
		return list;
	}

	@Override
	public User certify(String id, String passwd) throws Exception {
		User user = null;
		Map<String, String> params = new HashMap<String, String>();
		params.put("id", id);
		params.put("passwd", passwd);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		user = sqlSession.selectOne(NAMESPACE + "certify", params);
		sqlSession.close();
		return user;
	}

	@Override
	public List<User> listByPage(int page) throws Exception {
		List<User> list = null;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		list = sqlSession.selectList(NAMESPACE + "listByPage1", page);
		sqlSession.close();
		return list;
	}

	@Override
	public List<User> listByPage(int page, int listSize) throws Exception {
		List<User> list = null;
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("listSize", listSize);
		params.put("page", page);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		list = sqlSession.selectList(NAMESPACE + "listByPage2", params);
		sqlSession.close();
		return list;
	}

	@Override
	public List<User> listByPage(int page, int listSize, String searchType, String searchValue) throws Exception {
		List<User> list = null;

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("listSize", listSize);
		params.put("page", page);
		params.put("searchType", searchType);
		params.put("searchValue", searchValue);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		list = sqlSession.selectList(NAMESPACE + "listByPageBySearch", params);
		sqlSession.close();

		return list;
	}

	@Override
	public List<User> listByPage(Params params) throws Exception {
		return listByPage(params.getPage(), params.getListSize(), params.getSearchType(), params.getSearchValue());
	}

	@Override
	public int countBySearch(String searchType, String searchValue) throws Exception {
		int count = 0;
		Map<String, String> params = new HashMap<String, String>();
		params.put("searchType", searchType);
		params.put("searchValue", searchValue);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		count = sqlSession.selectOne(NAMESPACE + "countBySearch", params);
		return count;
	}

	@Override
	public int countBySearch(Params params) throws Exception {
		return countBySearch(params.getSearchType(), params.getSearchValue());
	}

}
