package oauth.fundamental.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oauth.fundamental.model.*;
import oauth.fundamental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Getter
public abstract class AbstractOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * 먼저 네이버인지 구글인지 즉 서비스 구분이 필요
     */
    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();

        if (registrationId.equals("keycloak")) {
            return new KeycloakUser(oAuth2User, clientRegistration);
        } else if (registrationId.equals("google")) {
            return new GoogleUser(oAuth2User, clientRegistration);
        } else if (registrationId.equals("naver")) {
            return new NaverUser(oAuth2User, clientRegistration);
        }
        return null;
    }

    /**
     * 회원가입 로직
     * */
    protected void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {
        User user = userRepository.findByUsername(providerUser.getUsername());
        if (user == null) {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            userService.register(registrationId, providerUser);
        } else {
            System.out.println("user = " + user);
        }
    }
}
