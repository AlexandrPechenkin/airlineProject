package app.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.Set;

/**
 * Базовый абстрактный класс пользователя сайта.
 * От него наследуются остальные классы, специализирующиеся на своей области:
 * Admin, Passenger, AirlineManager.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User implements UserDetails {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * Электронная почта пользователя.
     */
    @NotEmpty(message = "Поле email не должно быть пустым")
    @Email(message = "Введите корректный email адрес")
    protected String email;

    /**
     * Пароль пользователя.
     */
    @NotEmpty(message = "Поле password не должно быть пустым")
    protected String password;

    /**
     * Роли пользователя
     */
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @Column(name = "users_roles")
    protected Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return roles;
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
