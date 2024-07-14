package ru.yandex.practicum.filmorate.validators.user;

import ru.yandex.practicum.filmorate.model.user.User;

public interface UserValidator {
    boolean checkUserValidation(User user);
}
