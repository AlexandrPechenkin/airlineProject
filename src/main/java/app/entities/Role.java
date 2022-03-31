package app.entities;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

/**
 * Класс Role
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название роли
     */
    private String name;

    /**
     * пользователи
     */
    @Transient
    @ManyToMany(mappedBy = "roles")
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(columnDefinition = "role_id"),
            inverseJoinColumns = @JoinColumn(columnDefinition = "user_id"))
    private Set<User> users;

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
