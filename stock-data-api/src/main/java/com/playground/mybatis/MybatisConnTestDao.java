package com.playground.mybatis;

import java.util.List;
import java.util.Map;

public interface MybatisConnTestDao {
    public List<Map<String,Object>> selectConnTest();
}
