package oauth.fundamental;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestCustomizers;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import javax.servlet.http.HttpServletRequest;

import java.util.function.Consumer;


public class CustomOAuth2AuthorizationRequestResolver implements OAuth2AuthorizationRequestResolver {

    private ClientRegistrationRepository clientRegistrationRepository;
    private DefaultOAuth2AuthorizationRequestResolver defaultOAuth2AuthorizationRequestResolver;
    private final AntPathRequestMatcher authorizationRequestMatcher;
    private static final String REGISTRATION_ID_URI_VARIABLE_NAME = "registrationId";
    private static final Consumer<OAuth2AuthorizationRequest.Builder> DEFAULT_PKCE_APPLIER = OAuth2AuthorizationRequestCustomizers
            .withPkce();

    public CustomOAuth2AuthorizationRequestResolver(ClientRegistrationRepository clientRegistrationRepository,
                                                    String baseUri) {
        this.clientRegistrationRepository = clientRegistrationRepository;
        this.defaultOAuth2AuthorizationRequestResolver =
                new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository, baseUri);
        this.authorizationRequestMatcher = new AntPathRequestMatcher(
                baseUri + "/{" + REGISTRATION_ID_URI_VARIABLE_NAME + "}");
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
        String registrationId = resolveRegistrationId(request);
        if (registrationId == null) {
            return null;
        }
        if (registrationId.equals("keycloakPKCE")){
            OAuth2AuthorizationRequest oAuth2AuthorizationRequest = defaultOAuth2AuthorizationRequestResolver.resolve(request);
            return customResolve(oAuth2AuthorizationRequest, registrationId);
        }
        return defaultOAuth2AuthorizationRequestResolver.resolve(request);
    }

    private OAuth2AuthorizationRequest customResolve(OAuth2AuthorizationRequest oAuth2AuthorizationRequest, String clientRegistration) {
        OAuth2AuthorizationRequest.Builder builder = OAuth2AuthorizationRequest.from(oAuth2AuthorizationRequest);
        DEFAULT_PKCE_APPLIER.accept(builder);
        return builder.build();
    }

    @Override
    public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
        String registrationId = resolveRegistrationId(request);
        if (registrationId == null) {
            return null;
        }
        if (registrationId.equals("keycloakPKCE")){
            OAuth2AuthorizationRequest oAuth2AuthorizationRequest = defaultOAuth2AuthorizationRequestResolver.resolve(request);
            customResolve(oAuth2AuthorizationRequest, clientRegistrationId);
        }
        return defaultOAuth2AuthorizationRequestResolver.resolve(request);
    }

    private String resolveRegistrationId(HttpServletRequest request) {
        if (this.authorizationRequestMatcher.matches(request)) {
            return this.authorizationRequestMatcher.matcher(request).getVariables()
                    .get(REGISTRATION_ID_URI_VARIABLE_NAME);
        }
        return null;
    }
}
