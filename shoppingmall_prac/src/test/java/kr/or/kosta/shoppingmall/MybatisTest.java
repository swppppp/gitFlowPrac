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
	String resource = "mybatis-config.xml"; // resource 밑에 바로 있으니까 상대경로
	SqlSessionFactory sqlSessionFactory;

	Logger logger = Logger.getLogger(MybatisTest.class);

	@Before
	public void setUp() {
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "development");
		logger.debug("sqlSessionFactory 생성 완료!!!");

	}

//	 @Test
	// 전체조회
	public void testMybatis() {
//		logger.debug("초기화 완료.."); : 연습
//		select문 불러오기
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectList(NAMESPACE + "selectAll");
//		List<Employee> list = sqlSession.selectList(NAMESPACE + "selectAll2");
		for (Employee employee : list) {
			logger.debug(employee.toString());
		}
		sqlSession.close();
	}

//	@Test
	// 매개변수 하나 전달 + 파라미터 타입 안써줘도 실행가능
	public void testSelectOne() {
		int num = 100;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Employee employee = sqlSession.selectOne(NAMESPACE + "selectEmployeeById", num);
		logger.debug(employee.toString());
		sqlSession.close();
	}

//	@Test
	// 매개변수 하나 전달 : 사원번호 100번인 사람의 salary
	public void testSelectOne2() {
		int num = 100;
		SqlSession sqlSession = sqlSessionFactory.openSession();
		int salary = sqlSession.selectOne(NAMESPACE + "selectSalaryById", num);
		logger.debug(salary);
		sqlSession.close();
	}

//	@Test
	// 매개변수 두개 전달 : salary 가 3000과 4000사이인 사원
	public void testSelectList2() {
		Map<String, Integer> params = new HashMap<String, Integer>();
		params.put("min", 3000);
		params.put("max", 4000);
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectList(NAMESPACE + "selectEmployeesBySalary", params);
		for (Employee employee : list) {
			logger.debug(employee);
		}
		sqlSession.close();
	}

//	@Test
	// 매개변수 한개 전달 : 와일드카드 검색 : 전달할 때 %를 포함해서 보내야함
	public void testSelectListLike() {
		String name = "%E%";
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Employee> list = sqlSession.selectList(NAMESPACE + "selectEmployeesByLastName", name);
		for (Employee employee : list) {
			logger.debug(employee);
		}
		sqlSession.close();
	}

//	@Test
	// 조인 + select
	public void testSelectListJoin() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		List<Map<String, Object>> list = sqlSession.selectList(NAMESPACE + "selectEmployeesWithDepartment");
		for (Map<String, Object> map : list) {
			// logger.debug(map); - map으로 가져온것
//			int id = (Integer)map.get("id"); : integer가 아님 - 실행 불가
			BigDecimal id = (BigDecimal) map.get("id");
			String lastName = (String) map.get("lastName");
			logger.debug(id + "," + lastName);
			// logger.debug(map.get("id")); //한 애들의 id만 받기
		}
		sqlSession.close();
	}

//	@Test
	// 수정
	public void testUpdate() {
		Employee employee = new Employee();
		employee.setId(100);
		employee.setSalary(7777);
		SqlSession sqlSession = sqlSessionFactory.openSession(true); // 오토커밋
		sqlSession.update(NAMESPACE + "updateEmployee", employee);
		sqlSession.commit(); // 커밋 지정
		logger.debug("업데이트 완료");
		sqlSession.close();
	}

	@Test
	// Dynamic Sql
	public void testDynamic() {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		Map<String, String> map = new HashMap<String, String>();
		map.put("searchType", "id");
		map.put("searchValue", "100");
		List<Employee> list = sqlSession.selectList(NAMESPACE + "dynamicSQL", map);
		for (Employee employee : list) {
			logger.debug(employee);
		}
	}
}
