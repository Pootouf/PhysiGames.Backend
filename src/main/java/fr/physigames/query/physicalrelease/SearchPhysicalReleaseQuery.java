package fr.physigames.query.physicalrelease;

import lombok.*;

import java.time.LocalDate;

/**
 * Query object pour la recherche de PhysicalRelease.
 * Tous les champs sont optionnels (nullable) sauf la langue qui sera exigée par le controller.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchPhysicalReleaseQuery {

    /**
     * Filtre sur le titre du jeu (contient, insensible à la casse)
     */
    private String gameTitle;

    /**
     * Filtre sur les publishers du jeu (contient, insensible à la casse)
     */
    private String publisherName;

    /**
     * Filtre sur le publisher physique (contient, insensible à la casse)
     */
    private String physicalPublisherName;

    /**
     * Filtre sur le nom de la release physique (contient, insensible à la casse)
     */
    private String physicalReleaseName;

    /**
     * Filtre sur les studios de développement du jeu (contient, insensible à la casse)
     */
    private String developmentStudioName;

    /**
     * Filtre sur les genres du jeu (contient, insensible à la casse)
     */
    private String genreCode;

    /**
     * Filtre sur le code d'édition (contient, insensible à la casse)
     */
    private String editionCode;

    /**
     * Filtre sur le code de plateforme (contient, insensible à la casse)
     */
    private String platformCode;

    /**
     * Date de sortie minimale (inclusive)
     */
    private LocalDate releaseDateFrom;

    /**
     * Date de sortie maximale (inclusive)
     */
    private LocalDate releaseDateTo;

    /**
     * Code de la langue utilisée pour récupérer les libellés localisés (ex: "fr-fr").
     * Ce champ est requis au niveau de la route HTTP mais peut rester nullable dans l'objet query.
     */
    private String languageCode;
}
