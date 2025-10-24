package fr.physigames.controller;

import fr.physigames.query.physicalrelease.SearchPhysicalReleaseQuery;
import fr.physigames.query.physicalrelease.CreatePhysicalReleaseQuery;
import fr.physigames.query.physicalrelease.UpdatePhysicalReleaseQuery;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.service.PhysicalReleaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/physical-releases")
@RequiredArgsConstructor
@Tag(name = "Physical Releases", description = "API de gestion des sorties physiques de jeux")
public class PhysicalReleaseController {

    private final PhysicalReleaseService physicalReleaseService;

    /**
     * Recherche paginée de sorties physiques avec filtres optionnels.
     *
     * @param query Objet de recherche contenant tous les filtres
     * @param pageable Paramètres de pagination et tri
     * @return Page de résultats
     */
    @GetMapping
    @Operation(
            summary = "Rechercher des sorties physiques",
            description = "Recherche paginée de sorties physiques avec filtres optionnels sur le jeu, l'éditeur, la plateforme, etc. Le paramètre 'language' est obligatoire et permet de récupérer les libellés localisés (ex: nom du genre)."
    )
    public ResponseEntity<Page<PhysicalReleaseRow>> searchPhysicalReleases(
            @Parameter(description = "Objet de recherche contenant les filtres (voir SearchPhysicalReleaseQuery)")
            @ModelAttribute SearchPhysicalReleaseQuery query,
            @PageableDefault(size = 20, sort = "releaseDate") Pageable pageable
    ) {
        Page<PhysicalReleaseRow> results = physicalReleaseService.searchPhysicalReleases(query, pageable);
        return ResponseEntity.ok(results);
    }

    @PostMapping
    @Operation(summary = "Créer une sortie physique", description = "Crée une PhysicalRelease")
    public ResponseEntity<Long> createPhysicalRelease(
            @ModelAttribute CreatePhysicalReleaseQuery query
    ) {
        Long createdId = physicalReleaseService.createPhysicalRelease(
                query.getReleaseDate(),
                query.getGameId(),
                query.getEditionId(),
                query.getPlatformId(),
                query.getPublisherId(),
                query.getName()
        );
        return ResponseEntity.status(201).body(createdId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une sortie physique", description = "Met à jour une PhysicalRelease")
    public ResponseEntity<Long> updatePhysicalRelease(
            @Parameter(description = "ID de la PhysicalRelease à mettre à jour") @PathVariable Long id,
            @ModelAttribute UpdatePhysicalReleaseQuery query
    ) {
        Long updatedId = physicalReleaseService.updatePhysicalRelease(
                id,
                query.getReleaseDate(),
                query.getEditionId(),
                query.getPublisherId(),
                query.getName(),
                query.getGameId(),
                query.getPlatformId()
        );
        return ResponseEntity.ok(updatedId);
    }
}
