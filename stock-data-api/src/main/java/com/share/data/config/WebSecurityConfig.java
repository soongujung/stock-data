package com.share.data.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 커스텀 스프링 부트 시큐리티 설정
 *   : WebSecurityConfigurerAdapter를 extends한 클래스를 선언해 @Configuration으로 등록한 후
 *     아무것도 하지 않을 경우는 기본 인증이 적용된다.
 *     기본 인증이 적용될때 디폴트로 WebSecurityConfigurerAdapter 내에서 수행하는
 *     기본 랜덤 패스워드/계정을 인메모리로 생성해낸다.
 *          ex)
 *          Using generated security password: 73e78e22-300f-4a18-93c3-a34f36adf624
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.authorizeRequests()
                .antMatchers("/", "/about", "/auth/slack/redirect").permitAll() // '/'와 /hello 에 해당하는 페이지는 모든 사용자가 볼 수 있도록 허용
                .anyRequest().authenticated()       // 그 외의 모든 요청은 authentication이 필요하다.
            .and()
                .formLogin()                        // 그리고 form 로그인을 사용할 것이고
            .and()
                .httpBasic();                       // httpBasic 인증도 사용할 것이다.
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
