package oauth.fundamental.service;

import lombok.RequiredArgsConstructor;
import oauth.fundamental.model.ProviderUser;
import oauth.fundamental.model.users.User;
import oauth.fundamental.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void register(String registrationId, ProviderUser providerUser) {

        User user = User.builder()
                .registrationId(registrationId)
                .id(providerUser.getId())
                .username(providerUser.getUsername())
                .password(providerUser.getPassword())
                .email(providerUser.getEmail())
                .authorities(providerUser.getAuthorities())
                .build();
        userRepository.register(user);
    }
}
