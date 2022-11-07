package oauth.fundamental.converters;


import oauth.fundamental.model.ProviderUser;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * DI 받기 위해 스프링 빈으로 관리
 * ProviderUserRequest 안에는 클라이언트 registration 정보와 OAuth2 user 정보가 존재
 * */
@Component
public class DelegatingProviderUserConverter implements ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    private List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> converters;

    public DelegatingProviderUserConverter() {
        List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> providerUserConverters =
                Arrays.asList(
                        new OAuth2GoogleProviderUserConverter(),
                        new OAuth2NaverProviderUserConverter());
        this.converters = Collections.unmodifiableList(new LinkedList<>(providerUserConverters));
    }

    @Override
    public ProviderUser converter(ProviderUserRequest providerUserRequest) {
        Assert.notNull(providerUserRequest, "providerUserRequest can not be null"); //providerUserRequest null 체크, null이면 안됨

        for (ProviderUserConverter<ProviderUserRequest, ProviderUser> converter : converters) {
            ProviderUser providerUser = converter.converter(providerUserRequest); //user가 구글 카카오 네이버 다 가능
            if (providerUser != null) return providerUser;
        }
        return null;
    }
}
