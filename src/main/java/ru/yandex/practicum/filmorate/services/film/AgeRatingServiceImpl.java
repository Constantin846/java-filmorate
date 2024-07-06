package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.storages.AgeRatingStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgeRatingServiceImpl implements AgeRatingService{
    private final AgeRatingStorage ageRatingStorage;

    @Override
    public AgeRatingDto getAgeRatingById(int ageRatingId) {
        return ageRatingStorage.getAgeRatingById(ageRatingId);
    }

    @Override
    public List<AgeRatingDto> findAllAgeRatings() {
        return ageRatingStorage.findAllAgeRatings();
    }
}
