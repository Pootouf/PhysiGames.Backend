CREATE TABLE localized_genres
(
    name        VARCHAR(255) NOT NULL,
    genre_id    BIGINT       NOT NULL,
    language_id BIGINT       NOT NULL,
    CONSTRAINT pk_localized_genres PRIMARY KEY (genre_id, language_id)
);

ALTER TABLE localized_genres
    ADD CONSTRAINT FK_LOCALIZED_GENRES_ON_GENRE FOREIGN KEY (genre_id) REFERENCES genres (id);

ALTER TABLE localized_genres
    ADD CONSTRAINT FK_LOCALIZED_GENRES_ON_LANGUAGE FOREIGN KEY (language_id) REFERENCES languages (id);


