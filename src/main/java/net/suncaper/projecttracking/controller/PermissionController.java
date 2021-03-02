package net.suncaper.projecttracking.controller;

import net.suncaper.projecttracking.client.UserClient;
import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.Permission;
import net.suncaper.projecttracking.domain.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/permission")
public class PermissionController {
    private final UserClient userClient;

    public PermissionController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping
    public String listPage(Model model) {
        model.addAttribute("page", "permission/permission-list");

        return "index";
    }

    @GetMapping("/edit")
    public String editPage(Model model, @RequestParam("id") Integer id) {
        model.addAttribute("permission", userClient.getPermissionById(id));

        return "widget/permission/permission-edit";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("permission", new Permission());

        return "widget/permission/permission-edit";
    }

    @PostMapping("/save")
    @ResponseBody
    public Boolean savePermission(Permission permission) {
        if (permission.getId() != null) {
            userClient.updatePermission(permission);
        } else {
            userClient.createPermission(permission);
        }

        return Boolean.TRUE;
    }

    @GetMapping("/delete")
    @ResponseBody
    public Boolean deletePermission(@RequestParam("id") Integer id) {
        return userClient.removePermission(id);
    }

    @GetMapping("/list")
    @ResponseBody
    public PagableResponse<List<Permission>> searchPermission(PageRequest request, String keyword) {
        return userClient.searchPermission(request, keyword);
    }

    @GetMapping("/select")
    @ResponseBody
    public Boolean selectPermission(@RequestParam("roleId") Integer roleId, @RequestParam("permissionId") Integer permissionId) {
        return userClient.selectPermission(roleId, permissionId);
    }

    @GetMapping("/deselect")
    @ResponseBody
    public Boolean deselectPermission(@RequestParam("roleId") Integer roleId, @RequestParam("permissionId") Integer permissionId) {
        return userClient.deselectPermission(roleId, permissionId);
    }


}
