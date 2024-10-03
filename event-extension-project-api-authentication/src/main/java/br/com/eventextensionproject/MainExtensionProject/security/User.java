package br.com.eventextensionproject.MainExtensionProject.security;

import br.com.eventextensionproject.MainExtensionProject.entity.enumarator.RolePermission;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Data
public class User implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> rolePermission;

    public User(String email, String password, Set<RolePermission> rolePermission) {
        this.email = email;
        this.password = password;
        this.rolePermission = rolePermission.stream().map(x -> new SimpleGrantedAuthority(x.getDescricao())).collect(Collectors.toSet());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return rolePermission;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return true;
    }
}
