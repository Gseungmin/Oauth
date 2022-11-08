package oauth.fundamental.common.converters;

import oauth.fundamental.model.users.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

/**
 * equals hashcode tostring 자동적으로 생성
 * 불변 객체이므로 상속도 안됨
 * 멀티 쓰레드에서 세이프
 */
public record ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User, User user) {

    /**
     * OAuthUser같은 경우 clientRegistration, oAuth2User 파라미터 필요
     */
    public ProviderUserRequest(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        this(clientRegistration,oAuth2User,null);
    }

    /**
     * Form 인증 시 유저 객체만 받음
     * */
    public ProviderUserRequest(User user) {
        this(null,null,user);
    }
}
