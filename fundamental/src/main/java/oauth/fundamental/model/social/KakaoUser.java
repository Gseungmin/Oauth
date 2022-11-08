package oauth.fundamental.model.social;

import oauth.fundamental.model.Attributes;
import oauth.fundamental.model.OAuth2ProviderUser;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class KakaoUser extends OAuth2ProviderUser {

    //카카오같은 경우 3계층이므로 main, sub 이후인 otherAttributes에 접근해야 함
    private Map<String, Object> otherAttributes;

    /**
     * 사용자의 정보를 가지고와 인증을 받은 최종 객체: OAuth2User
     * 네이버같은 경우는 response/id, response/name 같이 응답하므로 한번더 get 해야 함
     */
    public KakaoUser(Attributes attributes, OAuth2User oAuth2User, ClientRegistration clientRegistration) {
        super(attributes.getSubAttributes(), oAuth2User, clientRegistration);
        this.otherAttributes = attributes.getOtherAttributes();
    }

    /**
     * 네이버는 각각 id와 email로 속성을 가지고와 반환
     * */
    @Override
    public String getId() {
        return (String) getAttributes().get("id");
    }

    //email 아니어도 됨
    @Override
    public String getUsername() {
        return (String) otherAttributes.get("nickname");
    }

    @Override
    public String getPicture() {
        return (String) otherAttributes.get("profile_image_url");
    }

    @Override
    public OAuth2User getOAuth2User() {
        return null;
    }
}
