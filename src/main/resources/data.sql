DELETE FROM friend_status
WHERE status IN ('CONFIRMED', 'NOT_CONFIRMED');
INSERT INTO friend_status (id, status)
VALUES (1, 'CONFIRMED'),
    (2, 'NOT_CONFIRMED');

DELETE FROM age_ratings
WHERE age_rating IN ('G', 'PG', 'PG_13', 'R', 'NC_17');
INSERT INTO age_ratings (id, age_rating)
VALUES (1, 'G'),
    (2, 'PG'),
    (3, 'PG_13'),
    (4, 'R'),
    (5, 'NC_17');

DELETE FROM genres
WHERE genre IN ('COMEDY', 'DRAMA', 'CARTOON', 'THRILLER', 'DOCUMENTARY', 'ACTION');
INSERT INTO genres (id, genre)
VALUES (1, 'COMEDY'),
    (2, 'DRAMA'),
    (3, 'CARTOON'),
    (4, 'THRILLER'),
    (5, 'DOCUMENTARY'),
    (6, 'ACTION');
