package com.stock.data.sample.mybatis;

import com.stock.data.domain.user.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SampleMybatisServiceImpl implements SampleMybatisService{

	@Autowired
	private SampleMybatisDao sampleMybatisDao;

	@Override
	public List<User> getAllUsers() {
		return sampleMybatisDao.getAllUsers();
	}
}
