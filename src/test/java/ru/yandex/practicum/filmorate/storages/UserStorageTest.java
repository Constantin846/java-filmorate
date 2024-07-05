package ru.yandex.practicum.filmorate.storages;

/*
@SpringBootTest
public class UserStorageTest {
    private UserValidator userValidator = new UserValidatorImpl();
    private UserStorage userStorage;
    private User user;

    @BeforeEach
     void beforeEach() {
        userStorage = new InMemoryUserStorage(userValidator);
        user = new User();
        user.setEmail("email@gmail.com");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.now());
    }

    @Test
    void create_shouldAddValidUser() {
        User actualUser = userStorage.create(user);

        Assertions.assertEquals(user, actualUser);
        Assertions.assertEquals(user.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(user.getLogin(), actualUser.getLogin());
        Assertions.assertEquals(user.getName(), actualUser.getName());
        Assertions.assertEquals(user.getBirthday(), actualUser.getBirthday());
    }

    @Test
    void create_userEmailShouldNotBeNull() {
        user.setEmail(null);

        Assertions.assertThrows(ValidationException.class, () -> userStorage.create(user));
    }

    @Test
    void create_userEmailShouldNotBeWithoutSymbolAt() {
        user.setEmail("email.com");

        Assertions.assertThrows(ValidationException.class, () -> userStorage.create(user));
    }

    @Test
    void create_userLoginShouldNotBeBlank() {
        user.setLogin("");

        Assertions.assertThrows(ValidationException.class, () -> userStorage.create(user));
    }

    @Test
    void create_userLoginShouldNotBeContainWhitespace() {
        user.setLogin("login login");

        Assertions.assertThrows(ValidationException.class, () -> userStorage.create(user));
    }

    @Test
    void create_ifUserNameIsBlank_userNameShouldSetAsLogin() {
        user.setName("");
        User newUser = userStorage.create(user);

        Assertions.assertEquals(newUser.getLogin(), newUser.getName());
    }

    @Test
    void create_userBirthdayShouldNotBeInTheFuture() {
        user.setBirthday(LocalDate.now().plusDays(1));

        Assertions.assertThrows(ValidationException.class, () -> userStorage.create(user));
    }
}
*/
