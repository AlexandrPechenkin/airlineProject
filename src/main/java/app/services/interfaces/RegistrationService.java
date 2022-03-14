package app.services.interfaces;

import app.entities.registration.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration createOrUpdate(Registration registration);
    Optional<Registration> findById(Long id);
    void delete(Registration registration);
    List<Registration> findAll();
}
