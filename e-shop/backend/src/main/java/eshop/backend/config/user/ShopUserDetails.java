package eshop.backend.config.user;

import eshop.backend.model.User;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopUserDetails implements UserDetails {
    private Long id;
    private  String email;
    private String password;
    private Collection<GrantedAuthority> authorities;



    public static ShopUserDetails buildUserDetails(User user){
        List<GrantedAuthority> listOfAuthorities = new ArrayList<>();
        listOfAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        listOfAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        return new ShopUserDetails(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                listOfAuthorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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