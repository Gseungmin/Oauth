package oauth.fundamental;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuth2ClientConfig {

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
        http.authorizeRequests(request -> request.
                antMatchers("/").permitAll().
                anyRequest().authenticated());
        http.oauth2Login(Customizer.withDefaults());
        http.logout().logoutSuccessUrl("/");
        return http.build();
    }



}
