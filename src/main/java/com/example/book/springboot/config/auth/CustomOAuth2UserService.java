package com.example.book.springboot.config.auth;

import com.example.book.springboot.config.auth.dto.OAuthAttributes;
import com.example.book.springboot.config.auth.dto.SessionUser;
import com.example.book.springboot.domain.user.User;
import com.example.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest,OAuth2User>
                    delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.
                            loadUser(userRequest);
        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 지금은 구글만 사용하는 불필요한 값이지만, 이후 네이버 로그인 연동 시에 네이버로그인인지, 구글 로그인인지 구분하기 위해 사용
        String registrationId = userRequest.
                getClientRegistration().getRegistrationId();
        //OAuth2 로그인 진행 시 키가 되는 필드값을 이야기합니다. Primary Key와 같은 의미
        //구글의 경우 기본적으로 코드를 지원하지만,네이버카카오등은 기본 지원하지 않습니다. 구글의 기본코드"sub"입니다.
        //이후 네이버 로그인과 구글 로그인을 동시 지원할때 사용합니다.
        String userNameAttributeName = userRequest.
                getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().
                        getUserNameAttributeName();

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        // 이후 네이버등 다른 소셜 로그인도 이 글래스를 사용
        // 바로 아래에서 이 클래스의 코드가 나오니 차례로 생성하면됨
        OAuthAttributes attributes = OAuthAttributes.
                of(registrationId,userNameAttributeName,
                   oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        //SessionUser 세션에서 사용자 정보를 저장하기 위한 Dto클래스
        httpSession.setAttribute("user" , new SessionUser(user));

        return  new DefaultOAuth2User(
                Collections.singleton(new
        SimpleGrantedAuthority(user.getRoleKey())),
                        attributes.getAttributes(),
                        attributes.getNameAttributeKey());

    }

    private  User saveOrUpdate(OAuthAttributes attributes){
            User user = userRepository.findByEmail(attributes.getEmail())
                    .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                    .orElse(attributes.toEntity());


            return userRepository.save(user);


    }




}



