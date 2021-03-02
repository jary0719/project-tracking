package net.suncaper.projecttracking.service;

import net.suncaper.projecttracking.client.UserClient;
import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.SystemUser;
import net.suncaper.projecttracking.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserClient userClient;

    public UserService( BCryptPasswordEncoder bCryptPasswordEncoder, UserClient userClient) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userClient = userClient;
    }

    public PagableResponse<List<User>> list(PageRequest request, String keyword) {
       return userClient.search(request, keyword);
    }

    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if(user.getId() != null) {
            userClient.updateUser(user);
        } else {
            userClient.createUser(user);
        }
    }

    public Boolean checkUserName(String username, Integer id) {
        return userClient.checkUserName(username, id);
    }

    public Integer removeUser(Integer id) {
        return userClient.removeUser(id);
    }

    public User getUserById(Integer id) {
        return userClient.getUserById(id);
    }

    public UserDetails loadUserByUsername(String username) {
       User user = userClient.getUserWithRoleAndPermissionByName(username);

        if(user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new SystemUser(user);
    }
}
