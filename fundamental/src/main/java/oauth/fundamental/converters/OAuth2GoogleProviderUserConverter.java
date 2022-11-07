package oauth.fundamental.converters;

import oauth.fundamental.enums.OAuth2Config;
import oauth.fundamental.model.ProviderUser;
import oauth.fundamental.model.social.GoogleUser;
import oauth.fundamental.util.OAuth2Utils;

public class OAuth2GoogleProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {

        if (!providerUserRequest.clientRegistration().getRegistrationId().
                equals(OAuth2Config.SocialType.GOOGLE.getSocialName())) {
            return null;
        }

        return new GoogleUser(OAuth2Utils.getMainAttributes(
                providerUserRequest.oAuth2User()),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
