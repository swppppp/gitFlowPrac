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

public class MybatisTest {
	
	private static final String NAMESPACE = "kr.or.kosta.shoppingmall.employee.";
	String resource = "mybatis-config.xml";
	SqlSessionFactory sqlSessionFactory;
	
	// 로그찍을거야
	Logger logger = Logger.getLogger(MybatisTest.class);
	
	@Before
	public void setUp() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		}catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development");
		logger.debug("sqlSessionFactory생성 완료!");
	}
	
	@Test
	public void testMybatis() {
//		logger.debug("초기화 완료");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectList(NAMESPACE+"selectAll"); //selectAll2로도 해보기..(resultMap 사용한것)
		for (Employee employee : list) {
			logger.debug(employee);
		}
		sqlSession.close();
	}
	
//	@Test
	public void testSelectOne() {
		int num = 100;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Employee employee = sqlSession.selectOne(NAMESPACE+"selectEmployeeById", num);
		logger.debug(employee);
		sqlSession.close();
	}
	
//	@Test
	public void testSelectOne2() {
		int num = 100;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int salary = sqlSession.selectOne(NAMESPACE+"selectSalaryById", num);
		logger.debug(salary);
		sqlSession.close();
	}

//	@Test
	public void testSelectList2() {
		Map<String, Integer> params = new HashMap<>();
		params.put("min", 3000);
		params.put("max", 4000);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectOne(NAMESPACE+"selectEmployeesBySalary", params);
		for (Employee employee : list) {
			logger.debug(employee);
		}
		sqlSession.close();
	}

//	@Test
	public void testSelectList_like() {
		String name = "%E%";

		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectOne(NAMESPACE+"selectEmployeesByLastName", name);
		for (Employee employee : list) {
			logger.debug(employee);
		}
		sqlSession.close();
	}

//	@Test
	public void testSelectListJoin() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = sqlSession.selectList(NAMESPACE+"selectEmployeesByLastName");
		for (Map<String, Object> map : list) {
//			System.out.println(map);
			BigDecimal id = (BigDecimal)map.get("id");   // number타입은 int형 반환 안하고, bigDecimal타입으로 받음 형변환주의!!
			String lastName = (String)map.get("lastName");
			logger.debug(id+", "+lastName);
		}
		sqlSession.close();
	}
	
//	@Test
	public void testUpdate() {
		Employee emp = new Employee();
		emp.setId(100);
		emp.setSalary(7777);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update(NAMESPACE+"updateEmployee", emp);
		sqlSession.commit(); //Or sqSession얻을때 오토커밋 true로
		logger.debug("update완료");
		sqlSession.close();
	}
	
//	@Test
	public void testDynamic() {
		Map<String, String> map = new HashMap<>();
		map.put("searchType", "id");
		map.put("searchValue", "100");
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectList(NAMESPACE+"dynamicSQL", map);
		for (Employee employee : list) {
			logger.debug(employee);
		}
		sqlSession.close();
	}
	

}
