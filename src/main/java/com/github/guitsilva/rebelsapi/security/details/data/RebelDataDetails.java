package com.github.guitsilva.rebelsapi.security.details.data;

import com.github.guitsilva.rebelsapi.entities.Rebel;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class RebelDataDetails implements UserDetails {

    private final Rebel rebel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        String authority = this.rebel.getRole().getAuthority();

        authorities.add(new SimpleGrantedAuthority(authority));

        return authorities;
    }

    @Override
    public String getPassword() {
        return (rebel == null) ? (new Rebel()).getPassword() : rebel.getPassword();
    }

    @Override
    public String getUsername() {
        return (rebel == null) ? (new Rebel()).getName() : rebel.getName();
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
