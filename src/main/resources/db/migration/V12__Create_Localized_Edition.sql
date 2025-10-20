CREATE TABLE localized_editions
(
    name        VARCHAR(255) NOT NULL,
    edition_id  BIGINT       NOT NULL,
    language_id BIGINT       NOT NULL,
    CONSTRAINT pk_localized_editions PRIMARY KEY (edition_id, language_id)
);

ALTER TABLE localized_editions
    ADD CONSTRAINT FK_LOCALIZED_EDITIONS_ON_EDITION FOREIGN KEY (edition_id) REFERENCES editions (id);

ALTER TABLE localized_editions
    ADD CONSTRAINT FK_LOCALIZED_EDITIONS_ON_LANGUAGE FOREIGN KEY (language_id) REFERENCES languages (id);