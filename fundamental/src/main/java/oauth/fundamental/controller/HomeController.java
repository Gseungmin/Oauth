package oauth.fundamental.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 컨트롤러 호출 전에 이미 인증을 다 받은 상태
 * */
@RestController
public class HomeController {

    /**
     * OAuth2 User Type
     * */
    @GetMapping("/api/user")
    public Authentication user(Authentication authentication, @AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println("authentication = " + authentication);
        return authentication;
    }

    /**
     * OIDC User Type
     * */
    @GetMapping("/api/oidc")
    public Authentication oidc(Authentication authentication, @AuthenticationPrincipal OidcUser oidcUser) {
        System.out.println("authentication = " + authentication);
        return authentication;
    }
}
