package ru.yandex.practicum.filmorate.storages.dao.mappers;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmGenreDtoRowMapper implements RowMapper<FilmGenreDto> {
    @Override
    public FilmGenreDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        FilmGenreDto filmGenreDto = new FilmGenreDto();
        filmGenreDto.setId(rs.getInt("id"));
        filmGenreDto.setFilmGenre(FilmGenre.valueOf(rs.getString("genre")));

        return filmGenreDto;
    }
}
