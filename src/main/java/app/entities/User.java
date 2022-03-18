package app.entities;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.Nullable;
import javax.persistence.*;

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
public abstract class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    protected Long id;

    /**
     * Электронная почта пользователя.
     */
    @Column(name = "email")
    @Nullable
    protected String email;

    /**
     * Пароль пользователя.
     */
    @Column(name = "password")
    @Nullable
    protected String password;

    // Временное поле для обозначения роли пользователя.
    @Column(name = "roles")
    private String roles;
}
