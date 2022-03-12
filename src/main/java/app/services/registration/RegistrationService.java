package app.services.registration;

import app.entities.registration.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration createOrUpdateRegistration(Registration registration);
    Optional<Registration> findById(Long id);
    void deleteRegistration(Registration registration);
    List<Registration> findAllRegistrations();
}
