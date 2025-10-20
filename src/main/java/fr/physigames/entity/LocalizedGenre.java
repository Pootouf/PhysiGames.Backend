package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "localized_genres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedGenre {

    @EmbeddedId
    private LocalizedGenreId id;

    @MapsId("genreId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id", nullable = false)
    private Genre genre;

    @MapsId("languageId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "name", nullable = false)
    private String name;

    public LocalizedGenre(Genre genre, Language language, String name) {
        this.id = new LocalizedGenreId(genre.getId(), language.getId());
        this.genre = genre;
        this.language = language;
        this.name = name;
    }
}

