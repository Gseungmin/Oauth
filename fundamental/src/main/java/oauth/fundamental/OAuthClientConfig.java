package oauth.fundamental;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuthClientConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(authRequest ->
                authRequest.antMatchers("/loginPage").permitAll().anyRequest().authenticated());
        http.oauth2Login(oauth2 -> oauth2.loginPage("/loginPage")); //로그인 페이지 설정
        return http.build();
    }
}
