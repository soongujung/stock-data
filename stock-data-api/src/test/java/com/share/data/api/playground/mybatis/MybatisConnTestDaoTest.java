package com.share.data.api.playground.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@MybatisTest
public class MybatisConnTestDaoTest {
    @Autowired
    private SqlSession sqlSession;

    @Test
    public void sqlSessionTest(){
        List<Map<String,Object>> data = sqlSession.selectList("testMybatis.getConnTest");
        Map<String,Object> firstRow = data.get(0);

        String name = Optional
                        .ofNullable(firstRow.get("name"))
                        .map(String::valueOf)
                        .orElse("null");

        assertTrue(!name.equalsIgnoreCase("null"));
    }
}