package ru.yandex.practicum.filmorate.storages.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class BaseDbExtractorRepository<T> extends BaseDbRepository<T>{
    private final ResultSetExtractor<List<T>> extractor;

    public BaseDbExtractorRepository(JdbcTemplate jdbc, RowMapper<T> mapper, ResultSetExtractor<List<T>> extractor) {
        super(jdbc, mapper);
        this.extractor = extractor;
    }

    protected Optional<T> findOneWithExtractor(String query, Object... params) {
        try {
            List<T> result = jdbc.query(query, extractor, params);
            if (result == null || result.isEmpty()) {
                return Optional.empty();
            }
            return Optional.ofNullable(result.getFirst());
        } catch (EmptyResultDataAccessException ignored) {
            log.warn("Empty result was gotten from database");
            return Optional.empty();
        }
    }

    protected List<T> findAllWithExtractor(String query, Object... params) {
        return jdbc.query(query, extractor, params);
    }
}
