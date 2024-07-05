package ru.yandex.practicum.filmorate.storages;

/*

@SpringBootTest
public class FilmControllerTest {
    private FilmValidator filmValidator = new FilmValidatorImpl();
    private FilmController filmController;
    private Film film;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(1895,12,28));
        film.setDuration(Duration.ofMinutes(1));
    }

    @Test
    void create_shouldAddValidFilm() {
        Film actualFilm = filmController.create(film);

        Assertions.assertEquals(film, actualFilm);
        Assertions.assertEquals(film.getName(), actualFilm.getName());
        Assertions.assertEquals(film.getDescription(), actualFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), actualFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), actualFilm.getDuration());
    }

    @Test
    void create_filmNameShouldNotBeNull() {
        film.setName(null);

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_filmNameShouldNotBeBlank() {
        film.setName("");

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_filmDescriptionShouldNotBeLonger_200_symbols() {
        film.setDescription("a".repeat(201));

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_shouldAddFilmWithDescriptionLessThan_200_symbols() {
        film.setDescription("a".repeat(200));

        Film actualFilm = filmController.create(film);

        Assertions.assertEquals(film, actualFilm);
        Assertions.assertEquals(film.getName(), actualFilm.getName());
        Assertions.assertEquals(film.getDescription(), actualFilm.getDescription());
        Assertions.assertEquals(film.getReleaseDate(), actualFilm.getReleaseDate());
        Assertions.assertEquals(film.getDuration(), actualFilm.getDuration());
    }

    @Test
    void create_filmReleaseDateShouldNotBeBefore_28_12_1895() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }

    @Test
    void create_filmDurationShouldNotBeNegative() {
        film.setDuration(Duration.ofMinutes(-1));

        Assertions.assertThrows(ValidationException.class, () -> filmController.create(film));
    }
}
*/
