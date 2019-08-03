package com.share.data.api.playground.jpa.sampleuser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
//@SpringBootTest
@DataJpaTest
public class SampleUserRepositoryTest {

    @Autowired
//    @Qualifier("hikariDataSource")
//    DataSource dataSource;
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SampleUserRepository sampleUserRepository;

    @Test
    public void di() throws SQLException {
        try(Connection conn = dataSource.getConnection()){
            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println(metaData.getUserName());
            System.out.println(metaData.getURL());
            System.out.println(metaData.getDriverName());
        }
    }

    @Test
    public void repositoryTest() throws SQLException{
        SampleUserEntity user = new SampleUserEntity();
//        user.setId(1);
        user.setName("sgjung");
        user.setVender("slack");

        final SampleUserEntity savedUser = sampleUserRepository.save(user);

        // junit.AssertThat이 import 되어 있다면 제거하고 AssertJ의 assertThat을 사용한다.
        assertThat(savedUser).isNotNull();

        // 꿀팁... alt + enter (quick fix) 로 test 코드에서 Repository에 자동으로 메서드를 만든다.
        SampleUserEntity data = sampleUserRepository.findByName(savedUser.getName());
        assertThat(data).isNotNull();

        SampleUserEntity slack = sampleUserRepository.findByName("slack");
        assertThat(data).isNull();

    }
}