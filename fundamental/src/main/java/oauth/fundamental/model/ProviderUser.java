package oauth.fundamental.model;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;

/**
 * 구글 네이버 카카오 등 공통적으로 사용하는 메소드 추상화 인터페이스
 * 즉 공통 메소드 추상화, 각각 개별 속성을 구분
 * */
public interface ProviderUser {

    String getId();
    String getUsername();
    String getPassword();
    String getEmail();

    //어디 서비스 제공자로부터 생성된 사용자인지 알게해주는 메소드
    String getProvider();

    //권한 정보 메소드
    List<? extends GrantedAuthority> getAuthorities();

    //서비스 제공자로부터 받는 값
    //사용자의 Claim 정보로 된 속성을 가지고 오는것
    //즉 인가서버로부터 가져온 사용자의 정보를 담고 있음
    Map<String, Object> getAttributes();
}
