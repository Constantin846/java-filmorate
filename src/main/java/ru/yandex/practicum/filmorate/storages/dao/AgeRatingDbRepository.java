package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storages.AgeRatingStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.AgeRatingDtoRowMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class AgeRatingDbRepository extends BaseDbRepository<AgeRatingDto> implements AgeRatingStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM age_ratings WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM age_ratings";

    public AgeRatingDbRepository(JdbcTemplate jdbc, AgeRatingDtoRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public AgeRatingDto getAgeRatingById(int ageRatingId) {
        Optional<AgeRatingDto> ageRatingDtoOp = super.findOne(FIND_BY_ID_QUERY, ageRatingId);
        if (ageRatingDtoOp.isPresent()) {
            return ageRatingDtoOp.get();
        } else {
            String message = String.format("Failed to search an age rating by id: %d", ageRatingId);
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public List<AgeRatingDto> findAllAgeRatings() {
        return super.findAll(FIND_ALL_QUERY);
    }
}
