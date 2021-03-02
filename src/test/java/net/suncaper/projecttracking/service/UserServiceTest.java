package net.suncaper.projecttracking.service;

import net.suncaper.projecttracking.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    public void testAddUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("admin");
        user.setNickName("超级管理员");
        user.setDisabled(false);

        userService.saveUser(user);

        user = new User();
        user.setUsername("contact");
        user.setPassword("123");
        user.setNickName("联系人");
        user.setDisabled(false);

        userService.saveUser(user);


        user = new User();
        user.setUsername("secretary");
        user.setPassword("123");
        user.setNickName("秘书");
        user.setDisabled(false);

        userService.saveUser(user);
    }
}
