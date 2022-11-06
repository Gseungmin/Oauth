package oauth.fundamental;

import lombok.RequiredArgsConstructor;
import oauth.fundamental.service.CustomOAuth2UserService;
import oauth.fundamental.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class OAuth2ClientConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOidcUserService customOidcUserService;

    /**
     * webSecurity 설정을 변경할 수 있도록 Customizer 인터페이스가 제공
     * */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().
                antMatchers("/static/js/**", "/static/images/**", "/static/css/**","/static/scss/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request
                .antMatchers("/api/user").access("hasAnyRole('SCOPE_profile', 'SCOPE_email')")
                .antMatchers("/api/oidc").access("hasAnyRole('SCOPE_openid')")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated());

        /**
         * 우리가 만든 CustomOAuth2UserService를 사용하기 위해 Endpoint 지정
         * */
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService)));
        http.logout().logoutSuccessUrl("/");
        return http.build();
    }

    /**
     * 구글같은 경우 SCOPE 문자 필터링 필요
     * 즉 하나의 이메일과 프로필로 잘라내는 작업 필요
     * 즉 커스텀하게 권한을 매핑할 수 있도록 클래스 생성
     */
    @Bean
    public GrantedAuthoritiesMapper customAuthorityMapper() {
        return new CustomAuthorityMapper();
    }
}
