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
    public FilmGenreDto getFilmGenreById(int genreId) {
        return filmGenreStorage.getFilmGenreById(genreId);
    }

    @Override
    public List<FilmGenreDto> findAllFilmGenres() {
        return filmGenreStorage.findAllFilmGenres();
    }
}
