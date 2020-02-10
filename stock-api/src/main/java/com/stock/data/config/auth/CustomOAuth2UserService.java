package com.stock.data.config.auth;

import com.stock.data.config.auth.dto.OAuthAttributes;
import com.stock.data.config.auth.dto.SessionUser;
import com.stock.data.domain.user.User;
import com.stock.data.domain.user.UserRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService delegator = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegator.loadUser(userRequest);

		// 1) google 로그인시 로그인한 클라이언트의 registrationId를 얻어온다.
		String registrationId =
			userRequest.getClientRegistration().getRegistrationId();

		// 2) user name 속성을 받아온다.
		String userNameAttributeName =
			userRequest.getClientRegistration().getProviderDetails()
						.getUserInfoEndpoint()
						.getUserNameAttributeName();

		// 3) registrationId, userName 과 OAuth Security 에서 건네받은 OAuth2User 를 기반으로 OAuthAttributes 객체를 생성
		OAuthAttributes attributes =
			OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

		// 	  findByEmail로 user를 찾아서 DB에 등록한다.
		User user = saveOrUpdate(attributes);

		// 4) 찾은 사용자를 session에 저장해둔다.
		httpSession.setAttribute("user", new SessionUser(user));

		return new DefaultOAuth2User(
			Collections.singleton(
				new SimpleGrantedAuthority(user.getRoleKey())
			),
			attributes.getAttributes(),
			attributes.getNameAttributeKey()
		);
	}

	// findByEmail 로 user를 찾아서 저장
	private User saveOrUpdate(OAuthAttributes attributes){
		User user =
			userRepository.findByEmail(attributes.getEmail())
							.map(entity->entity.update(attributes.getName(), attributes.getPicture()))
							.orElse(attributes.toEntity());

		return userRepository.save(user);
	}
}
