ALTER TABLE physical_releases
    ADD game_id BIGINT;

ALTER TABLE physical_releases
    ADD CONSTRAINT FK_PHYSICAL_RELEASES_ON_GAME FOREIGN KEY (game_id) REFERENCES games (id);