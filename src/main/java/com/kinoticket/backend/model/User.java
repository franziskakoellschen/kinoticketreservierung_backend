package com.kinoticket.backend.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = "USERS")
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String username;

    @NonNull
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    @OneToMany()
    private List<Booking> bookings;

    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    @NonNull
    private boolean active;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.active = false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName().name()))
            .collect(Collectors.toList());
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
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass())
            return false;
        User user = (User) o;
        return this.id == user.id;
    }
}
