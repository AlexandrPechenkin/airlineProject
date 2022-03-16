package app.entities.users.admin;

import app.entities.users.user.User;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * Класс, описывающий администратора сайта. Отвечает за управление созданием, удалением, изменением
 * всех пользователей (классов, наследующихся от app.entities.users.user.User) и добавление ролей
 * для этих классов.
 */
@Entity(name = "Admin")
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Admin extends User {

    /**
     * Никнейм администратора.
     */
    @Column(name = "nickanme")
    private String nickname;
}
