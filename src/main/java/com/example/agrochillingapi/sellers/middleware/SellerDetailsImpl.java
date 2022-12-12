package com.example.agrochillingapi.sellers.middleware;

import com.example.agrochillingapi.sellers.domain.model.entity.Seller;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SellerDetailsImpl implements UserDetails {

    private Long id;
    private String first_name;
    private String last_name;
    private int age;
    private String email;
    private String farm_address;
    @JsonIgnore
    private String password;
    private String phone;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() {
        return null;
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

    @Override
    public boolean equals(Object other) {
        if(this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        SellerDetailsImpl user = (SellerDetailsImpl) other;
        return Objects.equals(id, user.id);
    }

    public static SellerDetailsImpl build(Seller user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        return new SellerDetailsImpl(user.getId(), user.getFirst_name(), user.getLast_name(), user.getAge(), user.getEmail(), user.getFarm_address(), user.getPassword(), user.getPhone(), authorities);
    }
}
