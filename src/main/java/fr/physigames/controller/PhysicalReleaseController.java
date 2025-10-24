package fr.physigames.controller;

import fr.physigames.query.physicalrelease.SearchPhysicalReleaseQuery;
import fr.physigames.query.physicalrelease.CreatePhysicalReleaseQuery;
import fr.physigames.query.physicalrelease.UpdatePhysicalReleaseQuery;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.row.PhysicalReleaseDetailRow;
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
            description = "Recherche paginée de sorties physiques avec filtres optionnels sur le jeu, l'éditeur, la plateforme, etc. Le header 'Accept-Language' permet de récupérer les libellés localisés (ex: nom du genre)."
    )
    public ResponseEntity<Page<PhysicalReleaseRow>> searchPhysicalReleases(
            @Parameter(description = "Objet de recherche contenant les filtres (voir SearchPhysicalReleaseQuery)")
            @ModelAttribute SearchPhysicalReleaseQuery query,
            @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage,
            @PageableDefault(size = 20, sort = "releaseDate") Pageable pageable
    ) {
        Page<PhysicalReleaseRow> results = physicalReleaseService.searchPhysicalReleases(query, pageable, acceptLanguage);
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
                query.getName(),
                query.getRegionId()
        );
        return ResponseEntity.status(201).body(createdId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une sortie physique par ID", description = "Récupère une PhysicalRelease détaillée par son ID")
    public ResponseEntity<PhysicalReleaseDetailRow> getPhysicalReleaseById(
            @Parameter(description = "ID de la PhysicalRelease à récupérer") @PathVariable Long id,
            @Parameter(description = "Code langue pour les libellés localisés (optionnel)") @RequestHeader(value = "Accept-Language", required = false) String acceptLanguage
    ) {
        PhysicalReleaseDetailRow row = physicalReleaseService.getPhysicalReleaseById(id, acceptLanguage);
        return ResponseEntity.ok(row);
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
                query.getPlatformId(),
                query.getRegionId()
        );
        return ResponseEntity.ok(updatedId);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une sortie physique", description = "Supprime une PhysicalRelease par son ID")
    public ResponseEntity<Void> deletePhysicalRelease(
            @Parameter(description = "ID de la PhysicalRelease à supprimer") @PathVariable Long id
    ) {
        physicalReleaseService.deletePhysicalRelease(id);
        return ResponseEntity.noContent().build();
    }
}
