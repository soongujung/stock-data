package com.stock.data.sample.mybatis;

import com.stock.data.domain.user.User;
import java.util.List;

public interface SampleMybatisDao {
	public List<User> getAllUsers();
}
