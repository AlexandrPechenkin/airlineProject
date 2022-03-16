package app.config;

import app.entities.clients.admin.Admin;
import app.entities.clients.passenger.Passenger;
import app.entities.clients.passenger.Passport;
import app.services.interfaces.AdminService;
import app.services.interfaces.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

/**
 * В этом классе инициализируются тестовые данные для базы.
 * Эти данные будут каждый раз создаваться заново при поднятии SessionFactory и удаляться из БД при её остановке.
 * Инжектьте и используйте здесь соответствующие сервисы ваших сущностей."
 */
@RequiredArgsConstructor
@Component
public class DataInitializer {
    private final PassengerService passengerService;
    private final AdminService adminService;

    @PostConstruct
    public void init() {
        System.out.println("DataInitializer сработал!");

        createPassenger();
        System.out.println("Пассажир был создан.");

        createAdmin();
        System.out.println("Админ был создан");
    }

    private void createPassenger() {
        passengerService.createOrUpdatePassenger(
                Passenger.builder()
                        .firstName("Dereck")
                        .lastName("Storm")
                        .middleName("Totoro")
                        .dateOfBirth(LocalDate.of(1992, 2, 15))
                        .email("Airlines@test.com")
                        .password("password")
                        .passport(
                                Passport.builder()
                                        .firstName("Dereck")
                                        .lastName("Storm")
                                        .middleName("Totoro")
                                        .dateOfBirth(LocalDate.of(1990, 2, 15))
                                        .gender("Male")
                                        .birthplace("US")
                                        .residenceRegistration("New York")
                                        .seriesAndNumber("3333 123456")
                                        .build()
                        )
                        .build()
        );
    }

    private void createAdmin() {
        adminService.createOrUpdateAdmin(Admin.builder()
                        .email("email_admin")
                        .password("password_admin")
                .build());
    }
}
