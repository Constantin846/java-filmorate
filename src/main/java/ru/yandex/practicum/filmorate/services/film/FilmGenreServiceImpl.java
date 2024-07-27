package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.dto.mappers.FilmGenreDtoMapper;
import ru.yandex.practicum.filmorate.storages.FilmGenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmGenreServiceImpl implements FilmGenreService {
    private final FilmGenreStorage filmGenreStorage;
    private final FilmGenreDtoMapper filmGenreDtoMapper;

    @Override
    public FilmGenreDto getFilmGenreDtoById(int genreId) {
        return filmGenreDtoMapper.filmGenreToDto(filmGenreStorage.getFilmGenreById(genreId));
    }

    @Override
    public List<FilmGenreDto> findAllFilmGenreDto() {
        return filmGenreStorage.findAllFilmGenre().stream()
                .map(filmGenreDtoMapper::filmGenreToDto)
                .toList();
    }
}
