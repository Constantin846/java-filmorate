CREATE TABLE IF NOT EXISTS public.friend_status (
	id INTEGER PRIMARY KEY,
	status VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR(64) NOT NULL,
    login VARCHAR(64) NOT NULL,
    name VARCHAR(64) NOT NULL,
    birthday DATE
);

CREATE TABLE IF NOT EXISTS public.friends (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    friend_id BIGINT NOT NULL REFERENCES users(id),
    friend_status_id INTEGER NOT NULL REFERENCES friend_status(id)
);

CREATE TABLE IF NOT EXISTS public.age_ratings (
	id INTEGER PRIMARY KEY,
	age_rating VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.films (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) NOT NULL,
    description VARCHAR,
    release_date DATE,
    duration BIGINT,
    age_rating_id INTEGER REFERENCES age_ratings(id)
);

CREATE TABLE IF NOT EXISTS public.liked_by_users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES films(id),
    user_id BIGINT NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS public.genres (
	id INTEGER PRIMARY KEY,
	genre VARCHAR(16) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.genres_of_film (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES films(id),
    genre_id INTEGER NOT NULL REFERENCES genres(id)
);