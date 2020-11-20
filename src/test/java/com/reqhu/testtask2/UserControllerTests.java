package com.reqhu.testtask2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reqhu.testtask2.dto.UserWithRoles;
import com.reqhu.testtask2.service.UserService;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static com.reqhu.testtask2.UserTestData.NOT_EXISTING_LOGIN;
import static com.reqhu.testtask2.UserTestData.VASYA_LOGIN;
import static com.reqhu.testtask2.UserTestData.getNew;
import static com.reqhu.testtask2.UserTestData.getNewWithDuplicateLogin;
import static com.reqhu.testtask2.UserTestData.getNewWithInvalidLogin;
import static com.reqhu.testtask2.UserTestData.getNewWithInvalidName;
import static com.reqhu.testtask2.UserTestData.getNewWithInvalidPassword;
import static com.reqhu.testtask2.UserTestData.getNewWithoutRoles;
import static com.reqhu.testtask2.UserTestData.getUpdatedWithInvalidName;
import static com.reqhu.testtask2.UserTestData.getUpdatedWithInvalidPassword;
import static com.reqhu.testtask2.UserTestData.getUpdatedWithRoles;
import static com.reqhu.testtask2.UserTestData.getUpdatedWithoutRoles;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTests {

    private final String BASE_URL = "/rest/users/";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService service;

    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void create() throws Exception {
        UserWithRoles newUser = getNew();
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isCreated());
        assertEquals(newUser, service.getWithRoles(newUser.getLogin()));
    }

    @Test
    void createWithDuplicateLogin() throws Exception {
        UserWithRoles newUser = getNewWithDuplicateLogin();
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createWithInvalidLogin(String invalidLogin) throws Exception {
        UserWithRoles newUser = getNewWithInvalidLogin(invalidLogin);
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        if (invalidLogin != null) {
            assertThrows(NoSuchElementException.class, () -> service.getWithRoles(newUser.getLogin()));
        }
    }

    @ParameterizedTest
    @NullAndEmptySource
    void createWithInvalidName(String invalidName) throws Exception {
        UserWithRoles newUser = getNewWithInvalidName(invalidName);
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertThrows(NoSuchElementException.class, () -> service.getWithRoles(newUser.getLogin()));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "P", "п", "1", "password1", "пароль1", "pasоль"})
    void createWithInvalidPassword(String invalidPassword) throws Exception {
        UserWithRoles newUser = getNewWithInvalidPassword(invalidPassword);
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertThrows(NoSuchElementException.class, () -> service.getWithRoles(newUser.getLogin()));
    }

    @Test
    void createWithoutRoles() throws Exception {
        UserWithRoles newUser = getNewWithoutRoles();
        mvc.perform(MockMvcRequestBuilders
                .post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(newUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertThrows(NoSuchElementException.class, () -> service.getWithRoles(newUser.getLogin()));
    }

    @Test
    void updateWithoutRoles() throws Exception {
        UserWithRoles updatedUser = getUpdatedWithoutRoles();
        mvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + VASYA_LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(updatedUser)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThat(service.getWithRoles(VASYA_LOGIN))
                .usingRecursiveComparison()
                .ignoringFields("login", "roles")
                .isEqualTo(updatedUser);
    }

    @Test
    void updateWithRoles() throws Exception {
        UserWithRoles updatedUser = getUpdatedWithRoles();
        mvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + VASYA_LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(updatedUser)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThat(service.getWithRoles(VASYA_LOGIN))
                .usingRecursiveComparison(RecursiveComparisonConfiguration
                        .builder()
                        .withEqualsForFields(Object::equals)
                        .build()
                )
                .ignoringFields("login")
                .isEqualTo(updatedUser);
    }

    @ParameterizedTest
    @NullAndEmptySource
    void updateWithInvalidName(String invalidName) throws Exception {
        UserWithRoles originalUser = service.getWithRoles(VASYA_LOGIN);
        UserWithRoles updatedUser = getUpdatedWithInvalidName(invalidName);
        mvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + VASYA_LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(updatedUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertEquals(originalUser, service.getWithRoles(VASYA_LOGIN));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "P", "п", "1", "password1", "пароль1", "pasоль"})
    void updateWithInvalidPassword(String invalidPassword) throws Exception {
        UserWithRoles originalUser = service.getWithRoles(VASYA_LOGIN);
        UserWithRoles updatedUser = getUpdatedWithInvalidPassword(invalidPassword);
        mvc.perform(MockMvcRequestBuilders
                .put(BASE_URL + VASYA_LOGIN)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonMapper.writeValueAsString(updatedUser)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
        assertEquals(originalUser, service.getWithRoles(VASYA_LOGIN));
    }

    @Test
    void delete() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL + VASYA_LOGIN))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NoSuchElementException.class, () -> service.getWithRoles(VASYA_LOGIN));
    }

    @Test
    void deleteWhenUserDoNotExists() throws Exception {
        mvc.perform(MockMvcRequestBuilders
                .delete(BASE_URL + NOT_EXISTING_LOGIN))
                .andDo(print())
                .andExpect(status().isNotFound());
        assertThrows(NoSuchElementException.class, () -> service.getWithRoles(NOT_EXISTING_LOGIN));
    }

}
