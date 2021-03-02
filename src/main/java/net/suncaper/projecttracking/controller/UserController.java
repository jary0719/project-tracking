package net.suncaper.projecttracking.controller;

import net.suncaper.projecttracking.client.UserClient;
import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.User;
import net.suncaper.projecttracking.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private UserClient userClient;

    public UserController(UserService userService, UserClient userClient) {
        this.userService = userService;
        this.userClient = userClient;
    }

    @GetMapping
    public String userListPage(Model model) {
        model.addAttribute("page", "user/user-list");

        return "index";
    }

    @GetMapping("user-edit")
    public String userEditPage(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "widget/user/user-edit";
    }

    @GetMapping("add")
    public String userAddPage(Model model) {
        model.addAttribute("user", new User());

        return "widget/user/user-edit";
    }

    @GetMapping("assign-role")
    public String assignRolePage(Model model, @RequestParam("userId") Integer userId) {
        model.addAttribute("assignedMap", userClient.getAssignedRoleByUserId(userId));
        model.addAttribute("userId", userId);
        return "widget/user/role-assign";
    }

    @GetMapping("/list")
    @ResponseBody
    public PagableResponse<List<User>> listUser(PageRequest request, @RequestParam("keyword") String Keyword) {
        return userService.list(request, Keyword);
    }

    @PostMapping("/save")
    @ResponseBody
    public Integer saveUser(User user) {
        userService.saveUser(user);
        return 1;
    }

    @GetMapping("/check-username")
    @ResponseBody
    public Boolean checkUserName(@RequestParam("username") String username, @RequestParam("id") Integer id) {
        return userService.checkUserName(username, id);
    }

    @GetMapping("/delete")
    @ResponseBody
    public Integer removeUser(@RequestParam("id") Integer id) {
        return userService.removeUser(id);
    }

    @GetMapping("/get")
    @ResponseBody
    public User getUserById(@RequestParam("id") Integer id) {
        return userService.getUserById(id);
    }

    @GetMapping("/contacts")
    @ResponseBody
    public List<User> getContactsDeptId(@RequestParam("deptId") Integer deptId) {
        return userClient.getContactsDeptId(deptId);
    }

    @GetMapping("/secretaries")
    @ResponseBody
    public List<User> getSecretaryDeptId(@RequestParam("deptId") Integer deptId) {
        return userClient.getSecretaryDeptId(deptId);
    }
}
