package ru.yandex.practicum.filmorate.controllers;


import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.services.user.UserServiceImpl;
import ru.yandex.practicum.filmorate.storages.dao.UserDbRepository;
import ru.yandex.practicum.filmorate.storages.dao.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.validators.user.UserValidatorImpl;

import java.time.LocalDate;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {UserDbRepository.class, UserRowMapper.class, UserServiceImpl.class,
        UserController.class, UserValidatorImpl.class, User.class})
public class UserControllerTest {
    private final UserController userController;
    private final User user;

    @BeforeEach
     void beforeEach() {
        user.setEmail("email@gmail.com");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.now());
    }

    @Test
    void create_shouldAddValidUser() {
        User actualUser = userController.create(user);

        Assertions.assertEquals(user, actualUser);
        Assertions.assertEquals(user.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(user.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(user.getName(), actualUser.getName());
        Assertions.assertEquals(user.getBirthday(), actualUser.getBirthday());
    }

    @Test
    void create_userEmailShouldNotBeNull() {
        user.setEmail(null);

        Assertions.assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void create_userEmailShouldNotBeWithoutSymbolAt() {
        user.setEmail("email.com");

        Assertions.assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void create_userLoginShouldNotBeBlank() {
        user.setLogin("");

        Assertions.assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void create_userLoginShouldNotBeContainWhitespace() {
        user.setLogin("login login");

        Assertions.assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void create_ifUserNameIsBlank_userNameShouldSetAsLogin() {
        user.setName("");
        User newUser = userController.create(user);

        Assertions.assertEquals(newUser.getLogin(), newUser.getName());
    }

    @Test
    void create_userBirthdayShouldNotBeInTheFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));

        Assertions.assertThrows(ValidationException.class, () -> userController.create(user));
    }
}
