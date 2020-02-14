package com.stock.data.sample.mybatis;

import com.stock.data.domain.user.User;
import java.util.List;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class SampleMybatisDaoImpl implements SampleMybatisDao{

	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSessionTemplate sqlSession;

	@Override
	public List<User> getAllUsers() {
		return sqlSession.selectList("sample.selectAllUsers", null);
	}
}
