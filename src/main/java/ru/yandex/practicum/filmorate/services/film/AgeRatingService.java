package ru.yandex.practicum.filmorate.services.film;

import ru.yandex.practicum.filmorate.dto.AgeRatingDto;

import java.util.List;

public interface AgeRatingService {
    AgeRatingDto getAgeRatingDtoById(int ageRatingId);

    List<AgeRatingDto> findAllAgeRatingDto();
}
