package oauth.fundamental.service;

import lombok.Getter;
import oauth.fundamental.converters.ProviderUserConverter;
import oauth.fundamental.converters.ProviderUserRequest;
import oauth.fundamental.model.*;
import oauth.fundamental.model.social.GoogleUser;
import oauth.fundamental.model.social.KeycloakUser;
import oauth.fundamental.model.social.NaverUser;
import oauth.fundamental.model.users.User;
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

    @Autowired
    private ProviderUserConverter<ProviderUserRequest, ProviderUser> providerUserConverter;

    /**
     * 먼저 네이버인지 구글인지 즉 서비스 구분이 필요
     */
    public ProviderUser providerUser(ProviderUserRequest providerUserRequest) {
        return providerUserConverter.converter(providerUserRequest);
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
