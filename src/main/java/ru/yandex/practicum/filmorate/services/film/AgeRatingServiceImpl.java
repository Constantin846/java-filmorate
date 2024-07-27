package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.dto.mappers.AgeRatingDtoMapper;
import ru.yandex.practicum.filmorate.storages.AgeRatingStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgeRatingServiceImpl implements AgeRatingService {
    private final AgeRatingStorage ageRatingStorage;
    private final AgeRatingDtoMapper ageRatingDtoMapper;

    @Override
    public AgeRatingDto getAgeRatingDtoById(int ageRatingId) {
        return ageRatingDtoMapper.ageRatingToDto(ageRatingStorage.getAgeRatingById(ageRatingId));
    }

    @Override
    public List<AgeRatingDto> findAllAgeRatingDto() {
        return ageRatingStorage.findAllAgeRating().stream()
                .map(ageRatingDtoMapper::ageRatingToDto)
                .toList();
    }
}
