ALTER TABLE physical_releases
    ADD publisher_id BIGINT;

ALTER TABLE physical_releases
    ADD CONSTRAINT FK_PHYSICAL_RELEASES_ON_PUBLISHER FOREIGN KEY (publisher_id) REFERENCES publishers (id);