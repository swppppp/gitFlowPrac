package kr.or.kosta.shoppingmall;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import kr.or.kosta.shoppingmall.employee.domain.Employee;
import kr.or.kosta.shoppingmall.user.dao.MybatisUserDao;
import kr.or.kosta.shoppingmall.user.dao.UserDao;
import kr.or.kosta.shoppingmall.user.domain.User;

//별칭 : User
public class UserDaoTest {
	private static final String NAMESPACE = "kr.or.kosta.shoppingmall.user.";
	String resource = "mybatis-config.xml"; // resource 밑에 바로 있으니까 상대경로
	SqlSessionFactory sqlSessionFactory;

	Logger logger = Logger.getLogger(UserDaoTest.class);
	UserDao userDao;

	@Before
	public void setUp() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development");
		logger.debug("sqlSessionFactory 생성 완료!!!!!!");
		userDao = new MybatisUserDao();
		((MybatisUserDao) userDao).setSqlSessionFactory(sqlSessionFactory);

	}

	// @Test
	// 전체조회
	public void testCreate() throws Exception {
		User user = new User();
		user.setId("jiwon");
		user.setEmail("sk.dk");
		user.setPasswd("1111");
		user.setName("서지");
		userDao.create(user);
		logger.debug("완성");
	}

	// @Test
	public void testRead() throws Exception {
		User user = userDao.read("jiwon");
		logger.debug(user.toString());
	}

//	 @Test
	public void testUpdate() throws Exception {
		User user = new User();
		user.setPasswd("1112");
		user.setEmail("ss");
		user.setId("jiwon");
		userDao.update(user);
		logger.debug("업데이트 완료");
	}

	// @Test
	public void testDelete() throws Exception {
		String id = "bangry2";
		userDao.delete(id);
		logger.debug("삭제 완료");
	}

//	 @Test
	public void testCertify() throws Exception {
		User user = userDao.certify("jiwon", "1112");
		logger.debug(user.toString());
	}

//	@Test
	public void testListAll() throws Exception {
		List<User> list = userDao.listAll();
		for (User user : list) {
			logger.debug(user);
		}
	}

//	@Test
	public void testListByPage1() throws Exception {
		int page = 1;
		List<User> list = userDao.listByPage(page);
		for (User user : list) {
			logger.debug(user);
		}
	}

//	@Test
	public void testListByPage2() throws Exception {
		int listSize = 3;
		int page = 1;
		List<User> list = userDao.listByPage(page, listSize);
		for (User user : list) {
			logger.debug(user);
		}
	}

	@Test
	public void testListByPage3() throws Exception {
		int listSize = 3;
		int page = 1;
		String searchType = "name";
		String searchValue = "서";
		List<User> list = userDao.listByPage(page, listSize, searchType, searchValue);
		for (User user : list) {
			logger.debug(user);
		}
	}

	//@Test
	public void countBySearch() throws Exception {
		String searchType = "name";
		String searchValue = "서";
		int count = userDao.countBySearch(searchType, searchValue);
			logger.debug(count+"개");
	}
}