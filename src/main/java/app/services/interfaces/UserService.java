package app.services.interfaces;

import app.entities.users.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createOrUpdateUser(User user);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteUser(User user);
}
