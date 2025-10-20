package fr.physigames.controller;

import fr.physigames.query.SearchPhysicalReleaseQuery;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.service.PhysicalReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/physical-releases")
@RequiredArgsConstructor
@Tag(name = "Physical Releases", description = "API de gestion des sorties physiques de jeux")
public class PhysicalReleaseController {

    private final PhysicalReleaseService physicalReleaseService;

    /**
     * Recherche paginée de sorties physiques avec filtres optionnels.
     *
     * @param gameTitle Filtre sur le titre du jeu
     * @param publisherName Filtre sur le nom de l'éditeur
     * @param physicalPublisherName Filtre sur le nom de l'éditeur physique
     * @param developmentStudioName Filtre sur le nom du studio de développement
     * @param genreCode Filtre sur le code du genre
     * @param editionCode Filtre sur le code de l'édition
     * @param platformCode Filtre sur le code de la plateforme
     * @param releaseDateFrom Date de sortie minimale
     * @param releaseDateTo Date de sortie maximale
     * @param pageable Paramètres de pagination et tri
     * @return Page de résultats
     */
    @GetMapping
    @Operation(
            summary = "Rechercher des sorties physiques",
            description = "Recherche paginée de sorties physiques avec filtres optionnels sur le jeu, l'éditeur, la plateforme, etc. Le paramètre 'language' est obligatoire et permet de récupérer les libellés localisés (ex: nom du genre)."
    )
    public ResponseEntity<Page<PhysicalReleaseRow>> searchPhysicalReleases(
            @Parameter(description = "Filtre sur le titre du jeu (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String gameTitle,

            @Parameter(description = "Filtre sur le nom de l'éditeur du jeu (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String publisherName,

            @Parameter(description = "Filtre sur le nom de l'éditeur physique (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String physicalPublisherName,

            @Parameter(description = "Filtre sur le nom du studio de développement (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String developmentStudioName,

            @Parameter(description = "Filtre sur le code du genre (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String genreCode,

            @Parameter(description = "Filtre sur le code de l'édition (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String editionCode,

            @Parameter(description = "Filtre sur le code de la plateforme (recherche partielle, insensible à la casse)")
            @RequestParam(required = false) String platformCode,

            @Parameter(description = "Date de sortie minimale (format: yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDateFrom,

            @Parameter(description = "Date de sortie maximale (format: yyyy-MM-dd)")
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate releaseDateTo,

            @Parameter(description = "Code de la langue pour les libellés localisés (ex: 'fr-fr')", required = true)
            @RequestParam(name = "language") String language,

            @PageableDefault(size = 20, sort = "releaseDate") Pageable pageable
    ) {
        SearchPhysicalReleaseQuery query = SearchPhysicalReleaseQuery.builder()
                .gameTitle(gameTitle)
                .publisherName(publisherName)
                .physicalPublisherName(physicalPublisherName)
                .developmentStudioName(developmentStudioName)
                .genreCode(genreCode)
                .editionCode(editionCode)
                .platformCode(platformCode)
                .releaseDateFrom(releaseDateFrom)
                .releaseDateTo(releaseDateTo)
                .languageCode(language)
                .build();

        Page<PhysicalReleaseRow> results = physicalReleaseService.searchPhysicalReleases(query, pageable);
        return ResponseEntity.ok(results);
    }
}
