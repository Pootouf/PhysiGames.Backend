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
public class LocalizedEditionId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "edition_id")
    private Long editionId;

    @Column(name = "language_id")
    private Long languageId;
}

