package com.playground.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MybatisConnTestDaoImpl implements MybatisConnTestDao{

    @Autowired
    private SqlSession sqlSession;

    public List<Map<String,Object>> selectConnTest(){
        return sqlSession.selectList("testMybatis.getConnTest");
    }
}
