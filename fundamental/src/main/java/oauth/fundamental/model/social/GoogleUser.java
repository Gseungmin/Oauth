package oauth.fundamental.model.social;

import oauth.fundamental.model.Attributes;
import oauth.fundamental.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class GoogleUser extends OAuth2ProviderUser {

    /**
     * 사용자의 정보를 가지고와 인증을 받은 최종 객체: OAuth2User
     */
    public GoogleUser(Attributes mainAttributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(mainAttributes.getMainAttributes(), oAuth2User, clientRegistration);
    }

    /**
     * 구글은 둘다 sub로 속성을 가지고와 반환
     * */
    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("name");
    }

    //구글은 사진 가지고 오는 것이 없음 따라서 null 반환
    @Override
    public String getPicture() {
        return null;
    }

    @Override
    public OAuth2User getOAuth2User() {
        return null;
    }
}
