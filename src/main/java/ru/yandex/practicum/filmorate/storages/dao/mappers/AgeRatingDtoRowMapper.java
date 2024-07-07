package ru.yandex.practicum.filmorate.storages.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.AgeRatingDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AgeRatingDtoRowMapper implements RowMapper<AgeRatingDto> {
    @Override
    public AgeRatingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
         AgeRatingDto ageRatingDto = new AgeRatingDto();
         ageRatingDto.setId(rs.getInt("id"));
         ageRatingDto.setName(rs.getString("age_rating"));

         return ageRatingDto;
    }
}
