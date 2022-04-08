package app.controllers.rest;

import app.AirlineApplication;
import app.entities.Category;
import app.services.interfaces.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AirlineApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.yml")
@ActiveProfiles("integrationtest")
@FieldDefaults(level = AccessLevel.PRIVATE)
@WithMockUser(username = "admin@mai.ru", password = "123", authorities = "ADMIN")
public class CategoryRestControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    CategoryService categoryService;

    final String api = "/api/category";

    final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    Category createCategory() {
//        Category category = new Category("testCategory");
//        return category;
        return categoryService.createOrUpdate(Category.builder()
                .category("Economy")
                .build());
    }

    @Test
    void whenCreateCategory_thenStatus201() throws Exception {
        Category category = createCategory();

        mvc.perform(post(api)
                        .content(objectMapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.category", is(category.getCategory())));

    }

    @Test
    void whenCreateCategoryWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(post(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCategoryExist_whenUpdateCategory_thenStatus200() throws Exception {
        Category category = categoryService.createOrUpdate(createCategory());
        category.setCategory("testUpdate");
        mvc.perform(put(api)
                        .content(objectMapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is(category.getCategory())));
    }

    @Test
    void whenUpdateCategoryWithEmptyBody_thenStatus400() throws Exception {
        mvc.perform(put(api)
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void givenCategoryExist_whenGetByIdCategory_thenStatus200() throws Exception {
        Category category = categoryService.createOrUpdate(createCategory());

        mvc.perform(get(api + "/{id}", 1)
                        .content(objectMapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void givenCategoryExist_whenGetWithoutIdCategory_thenStatus404() throws Exception {
        Category category = categoryService.createOrUpdate(createCategory());

        mvc.perform(get(api + "/{id}", 100500)
                        .content(objectMapper.writeValueAsString(category))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCategoryDelete_thenStatus204() throws Exception {
        Category category = categoryService.createOrUpdate(createCategory());

        mvc.perform(delete(api + "/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testCategoryDelete_thenStatus404() throws Exception {
        mvc.perform(delete(api + "/{id}", 100500)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


}


