package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ContextConfiguration;
import ru.yandex.practicum.filmorate.controllers.UserController;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.services.user.UserServiceImpl;
import ru.yandex.practicum.filmorate.storages.dao.UserDbRepository;
import ru.yandex.practicum.filmorate.storages.dao.mappers.UserRowMapper;
import ru.yandex.practicum.filmorate.validators.user.UserValidatorImpl;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest
@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ContextConfiguration(classes = {UserDbRepository.class, UserRowMapper.class, UserServiceImpl.class,
UserController.class, UserValidatorImpl.class})
class FilmorateApplicationTests {
	@Autowired
	private final UserDbRepository userStorage;

	@Test
	public void testFindUserById() {
		User user = new User();
		user.setEmail("email@gmail.com");
		user.setLogin("login");
		user.setName("name");
		user.setBirthday(LocalDate.now());
		userStorage.create(user);
		User gottenUser = userStorage.getUserById(1);

		assertThat(gottenUser).hasFieldOrPropertyWithValue("id", 1L);
	}

	@Test
	void contextLoads() {
	}
}
