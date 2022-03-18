package app.config;

import app.entities.Admin;
import app.entities.AirlineManager;
import app.entities.Passenger;
import app.entities.Passport;
import app.services.interfaces.AdminService;
import app.services.interfaces.AirlineManagerService;
import app.services.interfaces.PassengerService;
import app.services.interfaces.UserService;
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
    private final AirlineManagerService airlineManagerService;
    private final UserService userService;

    @PostConstruct
    public void init() {
        System.out.println("DataInitializer сработал!");

        createPassenger();
        System.out.println("Пассажир был создан.");

        createAdmin();
        System.out.println("Администратор был создан с AdminService, AdminRepository, AdminMapper, AdminDTO.");

        createAirlineManager();
        System.out.println("AirlineManager был создан c AirlineManagerService, AirlineManagerRepository, AirlineMapper, AirlineManagerDTO.");

        createAdminWithUserService();
        System.out.println("Администратор был создан при помощи UserService, UserRepository, AdminMapper, AdminDTO.");

        createPassengerAndPassportWithUserService();
        System.out.println("Пассажир и его паспорт был создан при помощи UserService, UserRepository, PassengerMapper, PassengerDTO.");

        createAirlineManagerWithUserService();
        System.out.println("AirlineManager был создан при помощи UserService, UserRepository, AirlineManagerMapper, AirlineManagerDTO");


    }

    private void createPassenger() {
        passengerService.createOrUpdatePassenger(
                Passenger.builder()
                        .firstName("Dereck")
                        .lastName("Storm")
                        .middleName("Totoro")
                        .dateOfBirth(LocalDate.of(1992, 2, 15))
                        .email("Airlines@test.com")
                        .password("passenger_password")
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
                        .roles("passenger")
                        .build()
        );
    }

    private void createAdmin() {
        adminService.createOrUpdateAdmin(
                Admin.builder()
                        .email("admin@mail.com")
                        .password("password_admin")
                        .nickname("admin_nickname")
                        .roles("admin")
                .build());
    }

    private void createAirlineManager() {
        airlineManagerService.createOrUpdateAirlineManager(
                AirlineManager.builder()
                        .email("airlinemanager@mail.com")
                        .parkName("park_name")
                        .password("airline_manager_password_user")
                        .roles("airline_manager")
                        .build());
    }

    private void createAdminWithUserService() {
        userService.createOrUpdateUser(
                Admin.builder().email("admin_user@mail.com")
                        .password("password_admin_user")
                        .nickname("nickname_admin_user")
                        .roles("admin_user")
                        .build());
    }

    private void createPassengerAndPassportWithUserService() {
        userService.createOrUpdateUser(
                Passenger.builder()
                        .firstName("Dereck_user")
                        .lastName("Storm_user")
                        .middleName("Totoro_user")
                        .dateOfBirth(LocalDate.of(1992, 2, 15))
                        .email("Airlines_user@test.com")
                        .password("passenger_password_user")
                        .passport(
                                Passport.builder()
                                        .firstName("Dereck_user")
                                        .lastName("Storm_user")
                                        .middleName("Totoro_user")
                                        .dateOfBirth(LocalDate.of(1990, 2, 15))
                                        .gender("Male_user")
                                        .birthplace("US_user")
                                        .residenceRegistration("New York_user")
                                        .seriesAndNumber("3333 123456_user")
                                        .build()
                        )
                        .roles("passenger_user")
                        .build()
        );
    }

    private void createAirlineManagerWithUserService() {
        userService.createOrUpdateUser(
                AirlineManager.builder()
                        .email("airline_manager_user@mail.com")
                        .password("airline_manager_password_user")
                        .roles("airline_manager_user")
                        .parkName("park_name_user")
                        .build());
    }
}
