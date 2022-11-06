package oauth.fundamental;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class OAuthClientConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests(request -> request.antMatchers("/login").permitAll()
                .anyRequest().authenticated());
        http.oauth2Login(oauth2 -> oauth2.loginPage("/login")
                .authorizationEndpoint(authorizationEndpointConfig ->
                        authorizationEndpointConfig.baseUri("/oauth2/v1/authorization")));
        return http.build();
    }
}
