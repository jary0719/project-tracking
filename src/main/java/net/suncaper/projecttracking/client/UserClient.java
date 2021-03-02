package net.suncaper.projecttracking.client;

import net.suncaper.projecttracking.common.PagableResponse;
import net.suncaper.projecttracking.common.PageRequest;
import net.suncaper.projecttracking.domain.Permission;
import net.suncaper.projecttracking.domain.Role;
import net.suncaper.projecttracking.domain.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "USER-PROVIDER")
public interface UserClient {
    @PostMapping("/user/create")
    Boolean createUser(User user);

    @PostMapping("/user/update")
    Boolean updateUser(User user);

    @GetMapping("/user/delete")
    Integer removeUser(@RequestParam("id") Integer id);

    @GetMapping("/user/check-username")
    Boolean checkUserName(@RequestParam("username") String username, @RequestParam("id") Integer id);

    @GetMapping("/user")
    User getUserById(@RequestParam("id") Integer id);

    @GetMapping("/user/user-with-roles")
    User getUserWithRoleAndPermissionByName(@RequestParam("username") String username);

    @GetMapping("/user/search")
    PagableResponse<List<User>> search(@SpringQueryMap PageRequest request, @RequestParam("keyword") String keyword);


    @GetMapping("/user/contacts")
    List<User> getContactsDeptId(@RequestParam("deptId") Integer deptId);

    @GetMapping("/user/secretaries")
    List<User> getSecretaryDeptId(@RequestParam("deptId") Integer deptId);

    @GetMapping("/user/leaders")
    List<User> getLeaders();


    @PostMapping("/role/create")
    Boolean createRole(Role role);

    @PostMapping("/role/update")
    Boolean updateRole(Role user);

    @GetMapping("/role/delete")
    Boolean removeRole(@RequestParam("id") Integer id);

    @GetMapping("/role/check-username")
    Boolean checkRoleName(@RequestParam("rolename") String rolename, @RequestParam("id") Integer id);

    @GetMapping("/role")
    Role getRoleById(@RequestParam("id") Integer id);

    @GetMapping("/role/search")
    PagableResponse<List<Role>> searchRole(@SpringQueryMap PageRequest request, @RequestParam("keyword") String keyword);

    @GetMapping("/role/assigned")
    Map<String, List<Role>> getAssignedRoleByUserId(@RequestParam("userId") Integer userId);

    @GetMapping("/role/assign-role")
    boolean assignRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") Integer[] roleIds);

    @GetMapping("/role/unassign-role")
    boolean unassignRole(@RequestParam("userId") Integer userId, @RequestParam("roleIds") Integer[] roleIds);


    @PostMapping("/permission/create")
    Boolean createPermission(Permission permission);

    @PostMapping("/permission/update")
    Boolean updatePermission(Permission permission);

    @GetMapping("/permission/delete")
    Boolean removePermission(@RequestParam("id") Integer id);

    @GetMapping("/permission")
    Permission getPermissionById(@RequestParam("id") Integer id);

    @GetMapping("/permission/search")
    PagableResponse<List<Permission>> searchPermission(@SpringQueryMap PageRequest request, @RequestParam("keyword") String keyword);

    @GetMapping("/permission/role-permissions")
    List<Permission> getPermissionsByRoleId(@RequestParam("roleId") Integer roleId);

    @GetMapping("/permission/select")
    Boolean selectPermission(@RequestParam("roleId") Integer roleId, @RequestParam("permissionId") Integer permissionId);

    @GetMapping("/permission/deselect")
    Boolean deselectPermission(@RequestParam("roleId") Integer roleId, @RequestParam("permissionId") Integer permissionId);
}
