package net.suncaper.projecttracking.domain;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

public class SystemUser implements UserDetails {
    private User user;

    public SystemUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(CollectionUtils.isNotEmpty(user.getRoles())) {
//            return user.getRoles()
//                    .stream()
//                    .map(role -> new SimpleGrantedAuthority(role.getRole()))
//                    .collect(Collectors.toList());
            Set<GrantedAuthority> authorities =  new HashSet<>();
            Set<GrantedAuthority> permissions = new HashSet<>();
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole()));
                for (Permission permission : role.getPermissions()) {
                    permissions.add(new SimpleGrantedAuthority(permission.getPermission()));
                }
            }
            authorities.addAll(permissions);
            return authorities;
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.FALSE.equals(user.getDisabled());
    }
}
