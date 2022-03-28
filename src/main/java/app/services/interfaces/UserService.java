package app.services.interfaces;

import app.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createOrUpdateUser(User user);
    User findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
    void deleteUser(User user);
}
