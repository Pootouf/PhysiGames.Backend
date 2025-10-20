package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "localized_editions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocalizedEdition {

    @EmbeddedId
    private LocalizedEditionId id;

    @MapsId("editionId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edition_id", nullable = false)
    private Edition edition;

    @MapsId("languageId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "name", nullable = false)
    private String name;

    public LocalizedEdition(Edition edition, Language language, String name) {
        this.id = new LocalizedEditionId(edition.getId(), language.getId());
        this.edition = edition;
        this.language = language;
        this.name = name;
    }
}

