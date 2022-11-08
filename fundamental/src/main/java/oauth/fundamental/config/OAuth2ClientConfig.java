package oauth.fundamental.config;

import lombok.RequiredArgsConstructor;
import oauth.fundamental.common.authority.CustomAuthorityMapper;
import oauth.fundamental.service.CustomOAuth2UserService;
import oauth.fundamental.service.CustomOidcUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

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
                .antMatchers("/api/user")
                .access("hasAnyRole('SCOPE_profile', 'SCOPE_email')")
                .antMatchers("/api/oidc")
                .access("hasAnyRole('SCOPE_openid')")
                .antMatchers("/").permitAll()
                .anyRequest().authenticated());

        /**
         * Form 인증방식 사용 선언
         * 로그인 페이지는 항상 접근 가능
         */
        http.formLogin().loginPage("/login").loginProcessingUrl("/loginProc").defaultSuccessUrl("/").permitAll();

        /**
         * 우리가 만든 CustomOAuth2UserService를 사용하기 위해 Endpoint 지정
         * */
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpointConfig ->
                        userInfoEndpointConfig
                                .userService(customOAuth2UserService)
                                .oidcUserService(customOidcUserService)));

        /**
         * logout 처리 컨트롤러에서 진행
         */
//        http.logout().logoutSuccessUrl("/");

        /**
         * 인증 실패시 다시 로그인 페이지로 가게끔 설정
         */
        http.exceptionHandling().authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"));
        return http.build();
    }
}
