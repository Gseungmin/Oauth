package oauth.fundamental.common.converters;

import oauth.fundamental.common.enums.OAuth2Config;
import oauth.fundamental.common.util.OAuth2Utils;
import oauth.fundamental.model.ProviderUser;
import oauth.fundamental.model.social.KakaoUser;

public class OAuth2KakaoProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        if (!providerUserRequest.clientRegistration().getRegistrationId().
                equals(OAuth2Config.SocialType.KAKAO.getSocialName())) {
            return null;
        }

        return new KakaoUser(OAuth2Utils.getOtherAttributes(
                providerUserRequest.oAuth2User(), "kakao_account","profile"),
                providerUserRequest.oAuth2User(),
                providerUserRequest.clientRegistration());
    }
}
