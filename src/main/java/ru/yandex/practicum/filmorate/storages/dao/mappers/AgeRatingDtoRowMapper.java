package ru.yandex.practicum.filmorate.storages.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;
import ru.yandex.practicum.filmorate.model.film.AgeRating;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AgeRatingDtoRowMapper implements RowMapper<AgeRatingDto> {
    @Override
    public AgeRatingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
         AgeRatingDto ageRatingDto = new AgeRatingDto();
         ageRatingDto.setId(rs.getInt("id"));
         ageRatingDto.setAgeRating(AgeRating.valueOf(rs.getString("age_rating")));

         return ageRatingDto;
    }
}
