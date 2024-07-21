package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.model.film.AgeRating;

import java.util.List;

public interface AgeRatingStorage {
    AgeRating getAgeRatingById(int ageRatingId);

    List<AgeRating> findAllAgeRating();

    int getAgeRatingIdByName(String name);
}
