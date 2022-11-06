package oauth.fundamental.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model, Authentication authentication,
                        @AuthenticationPrincipal OAuth2User oAuth2User) {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        if (oAuth2AuthenticationToken != null) {
            //네이버를 위한 고려 필요, 속성에서 차이가 있음 ex)response/id
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String name = (String) attributes.get("name");

            //네이버일 경우
            if (oAuth2AuthenticationToken.getAuthorizedClientRegistrationId().equals("naver")) {
                Map<String, Object> response = (Map<String, Object>) attributes.get("response");
                name = (String) response.get("name");
            }
            model.addAttribute("user", name);
        }
        return "index";
    }

}
