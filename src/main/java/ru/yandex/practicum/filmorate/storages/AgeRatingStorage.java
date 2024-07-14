package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.model.film.AgeRating;

import java.util.List;

public interface AgeRatingStorage {
    AgeRatingDto getAgeRatingDtoById(int ageRatingId);

    List<AgeRatingDto> findAllAgeRatingDto();

    int getAgeRatingIdByName(String name);

    AgeRating getAgeRatingById(int ageRatingId);
}
