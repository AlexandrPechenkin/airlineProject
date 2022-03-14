package app.services.impl;

import app.entities.registration.Registration;
import app.repositories.registration.RegistrationRepository;
import app.services.interfaces.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Override
    public Registration createOrUpdate(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public void delete(Registration registration) {
        registrationRepository.delete(registration);
    }

    @Override
    public Optional<Registration> findById(Long id) {
        return registrationRepository.findById(id);
    }

    @Override
    public List<Registration> findAll() {
        return registrationRepository.findAll();
    }
}
