package ru.yandex.practicum.filmorate.dto.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.model.film.AgeRating;

@Component
@RequiredArgsConstructor
public class AgeRatingDtoMapper {
    public AgeRatingDto ageRatingToDto(AgeRating ageRating) {
        AgeRatingDto ageRatingDto = new AgeRatingDto();
        ageRatingDto.setId(ageRating.getId());
        ageRatingDto.setName(ageRating.getName());
        return ageRatingDto;
    }

    public AgeRating ageRatingFromDto(AgeRatingDto ageRatingDto) {
        AgeRating ageRating = new AgeRating();
        ageRating.setId(ageRatingDto.getId());
        ageRating.setName(ageRatingDto.getName());
        return ageRating;
    }
}
