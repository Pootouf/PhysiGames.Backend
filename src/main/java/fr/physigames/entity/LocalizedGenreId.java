package fr.physigames.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LocalizedGenreId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "genre_id")
    private Long genreId;

    @Column(name = "language_id")
    private Long languageId;
}
