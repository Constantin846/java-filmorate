package ru.yandex.practicum.filmorate.dto.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.FilmDto;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.FilmGenre;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FilmDtoMapper {
    private final AgeRatingDtoMapper ageRatingDtoMapper;
    private final FilmGenreDtoMapper filmGenreDtoMapper;

    public FilmDto filmToDto(Film film) {
        FilmDto filmDto = new FilmDto();
        filmDto.setId(film.getId());
        filmDto.setName(film.getName());
        filmDto.setDescription(film.getDescription());
        filmDto.setReleaseDate(film.getReleaseDate());
        filmDto.setDuration(film.getDuration());

        if (film.getAgeRating() != null) {
            filmDto.setMpa(ageRatingDtoMapper.ageRatingToDto(film.getAgeRating()));
        }

        if (film.getFilmGenres() != null) {
            Set<FilmGenre> filmGenres = film.getFilmGenres();
            Set<FilmGenreDto> filmGenreDto = filmGenres.stream()
                    .map(filmGenreDtoMapper::filmGenreToDto)
                    .collect(Collectors.toSet());

            TreeSet<FilmGenreDto> treeSetFilmGenreDto = new TreeSet<>(Comparator.comparingInt(FilmGenreDto::getId));
            treeSetFilmGenreDto.addAll(filmGenreDto);

            filmDto.setGenres(treeSetFilmGenreDto);
        }
        return filmDto;
    }

    public Film filmFromDto(FilmDto filmDto) {
        Film film = new Film();
        film.setId(filmDto.getId());
        film.setName(filmDto.getName());
        film.setDescription(filmDto.getDescription());
        film.setReleaseDate(filmDto.getReleaseDate());
        film.setDuration(filmDto.getDuration());

        if (filmDto.getMpa() != null) {
            film.setAgeRating(ageRatingDtoMapper.ageRatingFromDto(filmDto.getMpa()));
        }

        if (filmDto.getGenres() != null) {
            Set<FilmGenreDto> filmGenreDto = filmDto.getGenres();
            Set<FilmGenre> filmGenres = filmGenreDto.stream()
                    .map(filmGenreDtoMapper::filmGenreFromDto)
                    .collect(Collectors.toSet());

            film.setFilmGenres(filmGenres);
        }
        return film;
    }
}
