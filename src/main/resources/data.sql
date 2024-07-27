DELETE FROM friend_status
WHERE status IN ('CONFIRMED', 'NOT_CONFIRMED');
INSERT INTO friend_status (id, status)
VALUES (1, 'CONFIRMED'),
    (2, 'NOT_CONFIRMED');

DELETE FROM age_ratings
WHERE age_rating IN ('G', 'PG', 'PG-13', 'R', 'NC-17');
INSERT INTO age_ratings (id, age_rating)
VALUES (1, 'G'),
    (2, 'PG'),
    (3, 'PG-13'),
    (4, 'R'),
    (5, 'NC-17');

DELETE FROM genres
WHERE genre IN ('Комедия', 'Драма', 'Мультфильм', 'Триллер', 'Документальный', 'Боевик');
INSERT INTO genres (id, genre)
VALUES (1, 'Комедия'),
    (2, 'Драма'),
    (3, 'Мультфильм'),
    (4, 'Триллер'),
    (5, 'Документальный'),
    (6, 'Боевик');