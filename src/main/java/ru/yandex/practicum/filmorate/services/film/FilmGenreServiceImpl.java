package ru.yandex.practicum.filmorate.services.film;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.FilmGenreDto;
import ru.yandex.practicum.filmorate.storages.FilmGenreStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmGenreServiceImpl implements FilmGenreService {
    private final FilmGenreStorage filmGenreStorage;

    @Override
    public FilmGenreDto getFilmGenreDtoById(int genreId) {
        return filmGenreStorage.getFilmGenreDtoById(genreId);
    }

    @Override
    public List<FilmGenreDto> findAllFilmGenreDto() {
        return filmGenreStorage.findAllFilmGenreDto();
    }
}
