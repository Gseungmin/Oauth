package oauth.fundamental.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

//AbstractHttpConfigurer는 SecurityConfigurer를 상속받는 클래스
public class CustomSecurityConfigurer extends AbstractHttpConfigurer<CustomSecurityConfigurer, HttpSecurity> {

    private boolean isSecure;

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
        System.out.println("init start");
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        super.configure(builder);
        System.out.println("configurer start");

        if(isSecure) {
            System.out.println("https is required");
        } else {
            System.out.println("https is optional");
        }
    }

    public CustomSecurityConfigurer setFlag(boolean isSecure) {
        this.isSecure = isSecure;
        return this;
    }
}
