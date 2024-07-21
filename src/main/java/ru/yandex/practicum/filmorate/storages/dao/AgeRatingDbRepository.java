package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.AgeRating;
import ru.yandex.practicum.filmorate.storages.AgeRatingStorage;
import ru.yandex.practicum.filmorate.storages.dao.mappers.AgeRatingRowMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class AgeRatingDbRepository extends BaseDbRepository<AgeRating> implements AgeRatingStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM age_ratings WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM age_ratings";
    private static final String SELECT_AGE_RATING_ID_QUERY = "SELECT id FROM age_ratings WHERE age_rating = ?";

    public AgeRatingDbRepository(JdbcTemplate jdbc, AgeRatingRowMapper mapper) {
        super(jdbc, mapper);
    }

    @Override
    public AgeRating getAgeRatingById(int ageRatingId) {
        Optional<AgeRating> ageRatingOp = super.findOne(FIND_BY_ID_QUERY, ageRatingId);
        if (ageRatingOp.isPresent()) {
            return ageRatingOp.get();
        } else {
            String message = String.format("Failed to search an age rating by id: %d", ageRatingId);
            log.warn(message);
            throw new NotFoundException(message);
        }
    }

    @Override
    public List<AgeRating> findAllAgeRating() {
        return super.findAll(FIND_ALL_QUERY);
    }

    @Override
    public int getAgeRatingIdByName(String name) {
        Integer result = jdbc.queryForObject(SELECT_AGE_RATING_ID_QUERY, Integer.class, name);

        if (result == null) {
            String message = String.format("The age rating was not found: %s", name);
            log.warn(message);
            throw new NotFoundException(message);
        }
        return result;
    }
}
