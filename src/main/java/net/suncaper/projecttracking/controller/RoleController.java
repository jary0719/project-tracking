package net.suncaper.projecttracking.controller;

import net.suncaper.projecttracking.client.UserClient;
import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.Permission;
import net.suncaper.projecttracking.domain.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/role")
public class RoleController {
    private final UserClient userClient;

    public RoleController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("page", "role/role-list");

        return "index";
    }

    @GetMapping("/edit")
    public String editPage(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("role", userClient.getRoleById(id));

        return "widget/role/role-edit";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("role", new Role());

        return "widget/role/role-edit";
    }

    @GetMapping("/role-permission")
    public String rolePermissionPage(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("roleId", id);
        List<Permission> permissions = userClient.getPermissionsByRoleId(id);

//        Map<String, List<Permission>> permissionGroup = new HashMap<>();
//
//        for (Permission permission : permissions) {
//            String category = permission.getCategory();
//
//            if (permissionGroup.get(category) == null) {
//                permissionGroup.put(category, new ArrayList<>());
//            }
//
//            permissionGroup.get(category).add(permission);
//        }
//
//        permissions.stream().collect(Collectors.groupingBy(Permission::getCategory));

        model.addAttribute("permissionGroup", permissions.stream().collect(Collectors.groupingBy(Permission::getCategory)));

        return "widget/role/role-permission";
    }

    @PostMapping("/save")
    @ResponseBody
    public Boolean saveRole(Role role) {
        if (role.getId() != null) {
            userClient.updateRole(role);
        } else {
            userClient.createRole(role);
        }

        return Boolean.TRUE;
    }

    @GetMapping("/delete")
    @ResponseBody
    public Boolean deleteRole(@RequestParam("id") Integer id) {
        return userClient.removeRole(id);
    }

    @GetMapping("/list")
    @ResponseBody
    public PagableResponse<List<Role>> searchRole(PageRequest request, String keyword) {
        return userClient.searchRole(request, keyword);
    }

    @GetMapping("assign-role")
    @ResponseBody
    public boolean assignRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds[]") Integer[] roleIds) {
        return userClient.assignRole(userId, roleIds);
    }

    @GetMapping("unassign-role")
    @ResponseBody
    public boolean unassignRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds[]") Integer[] roleIds) {
        return userClient.unassignRole(userId, roleIds);
    }
}
