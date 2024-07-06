package ru.yandex.practicum.filmorate.storages;

import ru.yandex.practicum.filmorate.dto.AgeRatingDto;

import java.util.List;

public interface AgeRatingStorage {
    AgeRatingDto getAgeRatingById(int ageRatingId);

    List<AgeRatingDto> findAllAgeRatings();
}
