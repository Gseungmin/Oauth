package oauth.fundamental.service;

import oauth.fundamental.common.converters.ProviderUserRequest;
import oauth.fundamental.model.PrincipalUser;
import oauth.fundamental.model.ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * OAuth2UserRequest를 받아 OAuth2User를 반환
 * */
@Service
public class CustomOAuth2UserService
        extends AbstractOAuth2UserService
        implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();

        /**
         * 구글이나 네이버 등 인가서버와 통신해서 사용자 정보를 가지고 옴
         * 가지고 온 정보를 OAuth2User객체에 저장하고 반환
         * 따라서 해당 객체에는 사용자 정보와 관련된 정보가 다 갖춰져있음
         */
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

        ProviderUserRequest providerUserRequest = new ProviderUserRequest(clientRegistration, oAuth2User);

        /**
         * 해당 정보가 구글인지 네이버인지 현재 상태를 모름
         * 최종적으로 반환하는 타입을 정해야 하므로 위로 보냄
         * 따라서 추상화된 User 객체로 반환받음
         * */
        ProviderUser providerUser = providerUser(providerUserRequest);

        //회원가입
        super.register(providerUser, userRequest);

        return new PrincipalUser(providerUser);
    }
}
