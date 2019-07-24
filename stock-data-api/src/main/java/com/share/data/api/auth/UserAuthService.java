package com.share.data.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserAuthService implements UserDetailsService {

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private PasswordEncoder encoder;

    public UserAuthVo createUserAuthVo(String username, String password){
        UserAuthVo userAuthVo = new UserAuthVo();
        userAuthVo.setUsername(username);
        userAuthVo.setPassword(encoder.encode(password));
        return userAuthRepository.save(userAuthVo);
    }

    /**
     * 1. DAO로 user 검색
     * 2. User 객체 생성 및 권한 생성
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAuthVo> foundUser = userAuthRepository.findByUsername(username);
        UserAuthVo userAuthVo = foundUser.orElseThrow(()-> new UsernameNotFoundException(username));

        return new User(userAuthVo.getUsername(), userAuthVo.getPassword(), authorities());
    }

    /**
     * 간단한 인증 권한 부여 함수 :
     *      "ROLE_USER" 라는 권한을 가진 USER라는 정보를 세팅해서
     *      GrantedAuthority 타입의 컬렉션으로 리턴해주는 함수
     * @return
     */
    private Collection<? extends GrantedAuthority> authorities() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
