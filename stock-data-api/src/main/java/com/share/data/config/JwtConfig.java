package com.share.data.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.security.KeyPair;

@Configuration
@EnableAuthorizationServer
public class JwtConfig extends AuthorizationServerConfigurerAdapter {
//    private AuthenticationManager authenticationManager;

    @Autowired(required = true)
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

//    @Bean
//    public TokenStore tokenStore(){
//        return new JwtTokenStore(accessTokenConverter());
//    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();

        String path = this.getClass().getClassLoader().getResource("keystore/test.jks").getPath();
        System.out.println("path :: " + path);

        KeyPair keyPair =
                new KeyStoreKeyFactory(
//                    new UrlResource(this.getClass().getClassLoader().getResource("test.jks")),
                    new FileSystemResource(this.getClass().getClassLoader().getResource("keystore/test.jks").getPath()),
                    "test1234".toCharArray()
                )
                .getKeyPair("asdf", "asdf1234".toCharArray());

        converter.setKeyPair(keyPair);
        System.out.println("keyPair :: " + keyPair);
        return converter;
    }

//    @Bean
//    @Primary
//    public DefaultTokenServices tokenServices(DataSource dataSource){
//        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
//        defaultTokenServices.setTokenStore(tokenStore());
//        defaultTokenServices.setSupportRefreshToken(true);
//        return defaultTokenServices;
//    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//        super.configure(endpoints);
//        endpoints.tokenStore(tokenStore())
//                .authenticationManager(authenticationManager)
//                .accessTokenConverter(accessTokenConverter());
        endpoints.accessTokenConverter(accessTokenConverter())
                .authenticationManager(this.authenticationManager);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        super.configure(clients);
        clients.withClientDetails(clientDetailsService);
    }
}
