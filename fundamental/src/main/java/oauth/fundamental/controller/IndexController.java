package oauth.fundamental.controller;

import oauth.fundamental.common.util.OAuth2Utils;
import oauth.fundamental.model.PrincipalUser;
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
                        @AuthenticationPrincipal PrincipalUser principalUser) {

        String view = "index";

        /**
         * 토큰의 타입에 따른 분류
         * OAuth2AuthenticationToken타입이면 적어도 userDetatils 타입은 아님
         * */
        if (authentication != null) {

            String userName = principalUser.providerUser().getUsername();

            model.addAttribute("user", userName);
            model.addAttribute("provider", principalUser.providerUser().getProvider());
        }
        return view;
    }
}
