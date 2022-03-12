package app.services.registration;

import app.entities.registration.Registration;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {
    Registration createOrUpdateBooking(Registration registration);
    Optional<Registration> findById(Long id);
    void deleteBooking(Registration registration);
    List<Registration> findAllRegistrations();
}
