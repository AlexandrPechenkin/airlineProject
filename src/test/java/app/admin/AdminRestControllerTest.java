package app.admin;

import app.AirlineApplication;
import app.entities.Admin;
import app.services.interfaces.AdminService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AirlineApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
@ActiveProfiles("integrationtest")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AdminRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired @Qualifier("adminServiceImpl")
    AdminService adminService;

    final String api = "/admin";
    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Admin createAdmin() {
        return Admin.builder()
                .email("admin@mail.com")
                .roles("admin")
                .password("nimda")
                .nickname("admin_nickname")
                .build();
    }

    @Test
    void whenCreateAdmin_thenStatus201() throws Exception {
        Admin admin = createAdmin();
        mvc.perform(post(api)
                    .content(objectMapper.writeValueAsString(admin))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email", is(admin.getEmail())))
                .andExpect(jsonPath("$.password", is(admin.getPassword())))
                .andExpect(jsonPath("$.nickname", is(admin.getNickname())));
    }

    @Test
    void whenCreateAdminWithEmptyBody_thenStatus404() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAdminExist_whenGetByIdExistedAdmin_thenStatus200() throws Exception {
        Admin admin = adminService.createOrUpdateAdmin(createAdmin());
        mvc.perform(get(api + "/{id}", admin.getId())
                        .content(objectMapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(admin.getId().intValue())));
    }

    @Test
    void givenAdminExist_whenGetByIdNotExistedAdmin_thenStatus404() throws Exception {
        Admin admin = adminService.createOrUpdateAdmin(createAdmin());
        mvc.perform(get(api + "/{id}", 123123)
                        .content(objectMapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void givenAdminExist_whenUpdateAdmin_thenStatus200() throws Exception {
        Admin admin = adminService.createOrUpdateAdmin(createAdmin());
        admin.setNickname("new_admin_nickname");
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(admin))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is(admin.getNickname())));
    }

    @Test
    void givenAdminExist_whenUpdateAdminWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenAdminExist_whenDeleteByIdExistedAdmin_thenStatus200() throws Exception {
        Admin admin = adminService.createOrUpdateAdmin(createAdmin());
        mvc.perform(delete(api + "/{id}", admin.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenAdminExist_whenDeleteByIdNotExistedAdmin_thenStatus404() throws Exception {
        Admin admin = adminService.createOrUpdateAdmin(createAdmin());
        mvc.perform(delete(api + "/{id}", 12310123)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void whenGetAllAdminListExist_thenStatus200() throws Exception {
        for (int i = 0; i < 5; i++) {
            adminService.createOrUpdateAdmin(createAdmin());
        }
        mvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void whenGetAllAdminListNotExist_thenStatus404() throws Exception {
        List<Admin> adminList = adminService.findAll();
        adminList.forEach(admin -> adminService.deleteAdminById(admin.getId()));
        mvc.perform(get(api)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
