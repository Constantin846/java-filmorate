package ru.yandex.practicum.filmorate.validators.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;

import java.time.LocalDate;

@Slf4j
@Component
public class UserValidatorImpl implements UserValidator {
    private static final String EMAIL_REGEX = "^.+@.+$";
    private static final String SPACE = " ";

    public boolean checkUserValidation(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            String errorMessage = String.format("The user's email must not be empty: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        } else if (!user.getEmail().matches(EMAIL_REGEX) || user.getEmail().contains(SPACE)) {
            String errorMessage = String.format("Invalid email structure: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (user.getLogin() == null || user.getLogin().isBlank()) {
            String errorMessage = String.format("The user's login must not be empty: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        } else if (user.getLogin().contains(SPACE)) {
            String errorMessage = String.format("The user's login must not contain whitespace: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        if (user.getName() == null || user.getName().isBlank()) {
            log.info("The user's name is empty: {}", user);
            user.setName(user.getLogin());
        }

        if (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now())) {
            String errorMessage = String.format("The user's birthday must not be in the future: %s", user);
            log.warn(errorMessage);
            throw new ValidationException(errorMessage);
        }

        return true;
    }
}
