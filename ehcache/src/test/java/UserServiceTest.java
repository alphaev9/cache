import com.alpha.cache.starter.User;
import com.alpha.cache.starter.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

@SpringJUnitConfig(locations = "classpath:spring-config.xml")
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void getUserById() {
        User user1 = userService.getUserById(1);
        System.out.println(user1);
        User user2 = userService.getUserById(1);
        System.out.println(user2);
    }

    @Test
    void getAllUsers() {
        List<User> users1 = userService.getAllUsers();
        users1.forEach(user -> System.out.println(user));
        List<User> users2 = userService.getAllUsers();
        users2.forEach(user -> System.out.println(user));

    }

}
