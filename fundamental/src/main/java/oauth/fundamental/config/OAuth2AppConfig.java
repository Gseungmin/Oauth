package oauth.fundamental.config;

import oauth.fundamental.common.authority.CustomAuthorityMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;

@Configuration
public class OAuth2AppConfig {

    /**
     * 구글같은 경우 SCOPE 문자 필터링 필요
     * 즉 하나의 이메일과 프로필로 잘라내는 작업 필요
     * 즉 커스텀하게 권한을 매핑할 수 있도록 클래스 생성
     */
    @Bean
    public GrantedAuthoritiesMapper grantedAuthoritiesMapper() {
        return new CustomAuthorityMapper();
    }
}
