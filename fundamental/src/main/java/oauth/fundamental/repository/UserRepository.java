package oauth.fundamental.repository;

import oauth.fundamental.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {

    private Map<String, Object> users = new HashMap<>();

    //회원 검색
    public User findByUsername(String username) {
        if (users.containsKey(username)) {
            return (User) users.get(username);
        }
        return null;
    }

    //회원 저장
    public void register(User user) {
        if(users.containsKey(user.getUsername())) {
            return;
        }
        users.put(user.getUsername(), user);
    }
}
