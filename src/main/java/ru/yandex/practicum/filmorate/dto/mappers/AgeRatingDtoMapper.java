package ru.yandex.practicum.filmorate.dto.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.model.film.AgeRating;
import ru.yandex.practicum.filmorate.storages.AgeRatingStorage;

@Component
@RequiredArgsConstructor
public class AgeRatingDtoMapper {
    private final AgeRatingStorage ageRatingStorage;

    public AgeRatingDto ageRatingToDto(AgeRating ageRating) {
        AgeRatingDto ageRatingDto = new AgeRatingDto();
        ageRatingDto.setId(ageRatingStorage.getAgeRatingIdByName(ageRating.name()));
        ageRatingDto.setName(ageRating.name());

        return ageRatingDto;
    }

    public AgeRating ageRatingFromDto(AgeRatingDto ageRatingDto) {
        return ageRatingStorage.getAgeRatingById(ageRatingDto.getId());
    }
}
