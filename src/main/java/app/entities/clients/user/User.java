package app.entities.clients.user;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class User implements UserDetails, Serializable {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    /**
     * Электронная почта пользователя.
     *
     * email - уникальное значение, исп-ся для того, чтобы UserServiceImpl, имплементирующий
     * UserDetailsService, находил пользователя по уникальному значению.
     */
    @Column(name = "email")
    @NonNull
    protected String email;// уникальное значение, исп-ся для того, чтобы UserServiceImpl, имплементирующий
    // UserDetailsService, находил пользователя по уникальному значению

    /**
     * Пароль пользователя.
     */
    @Column(name = "password")
    @Nullable
    protected String password;

    /**
     * Список ролей пользователя.
     */
//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "users_roles",
//            joinColumns = {@JoinColumn(name = "user_id", unique = false, updatable = true)},
//            inverseJoinColumns = @JoinColumn(name = "role_id", unique = false, updatable = true)
//    )
//    @JsonProperty("roles")
//    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
//    @Nullable
//    protected Collection<Role> roles = new ArrayList<>();

//    /**
//     * Никнейм пользователя.
//     */
//    @Column(name = "nickname")
//    protected String nickname;

    @Override
    public String getUsername() {
        return this.email;
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
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<Role> roles = this.getRoles();
//        if (roles == null) {
//            return null;
//        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
//        }

        return authorities;
    }
}
