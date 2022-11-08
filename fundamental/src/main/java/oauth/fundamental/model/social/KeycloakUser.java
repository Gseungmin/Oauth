package oauth.fundamental.model.social;

import oauth.fundamental.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class KeycloakUser extends OAuth2ProviderUser {

    /**
     * 사용자의 정보를 가지고와 인증을 받은 최종 객체: OAuth2User
     */
    public KeycloakUser(OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(oAuth2User.getAttributes(), oAuth2User, clientRegistration);
    }

    /**
     * keycloak 각각 id와 email로 속성을 가지고와 반환
     * */
    @Override
    public String getId() {
        return (String) getAttributes().get("sub");
    }

    @Override
    public String getUsername() {
        return (String) getAttributes().get("preferred_username");
    }

    @Override
    public String getPicture() {
        return null;
    }

    @Override
    public OAuth2User getOAuth2User() {
        return null;
    }
}
