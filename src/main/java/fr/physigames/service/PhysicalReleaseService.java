package fr.physigames.service;

import fr.physigames.entity.*;
import fr.physigames.query.PhysicalReleaseQuery;
import fr.physigames.repository.PhysicalReleaseRepository;
import fr.physigames.row.PhysicalReleaseRow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhysicalReleaseService {

    private final PhysicalReleaseRepository physicalReleaseRepository;

    /**
     * Recherche paginée de PhysicalRelease avec filtres optionnels.
     * Effectue le mapping vers PhysicalReleaseRow.
     *
     * @param query Les critères de recherche
     * @param pageable Pagination et tri
     * @return Page de PhysicalReleaseRow
     */
    public Page<PhysicalReleaseRow> searchPhysicalReleases(
            PhysicalReleaseQuery query,
            Pageable pageable) {

        Page<PhysicalRelease> physicalReleases = physicalReleaseRepository.searchPhysicalReleases(
                query.getGameTitle(),
                query.getPublisherName(),
                query.getPhysicalPublisherName(),
                query.getDevelopmentStudioName(),
                query.getGenreCode(),
                query.getEditionCode(),
                query.getPlatformCode(),
                query.getReleaseDateFrom(),
                query.getReleaseDateTo(),
                pageable
        );

        return physicalReleases.map(this::mapToRow);
    }

    /**
     * Mappe une entité PhysicalRelease vers un PhysicalReleaseRow.
     * Pour les collections (publishers, developmentStudios, genres), prend uniquement le premier élément (mode aperçu).
     *
     * @param physicalRelease L'entité à mapper
     * @return Le row mappé
     */
    private PhysicalReleaseRow mapToRow(PhysicalRelease physicalRelease) {
        PhysicalReleaseRow row = new PhysicalReleaseRow();

        row.setId(physicalRelease.getId());
        row.setReleaseDate(physicalRelease.getReleaseDate());

        // Mapping du jeu
        if (physicalRelease.getGame() != null) {
            Game game = physicalRelease.getGame();
            row.setGameTitle(game.getTitle());

            // Mapping des publishers (prend uniquement le premier)
            if (game.getPublishers() != null && !game.getPublishers().isEmpty()) {
                String firstPublisher = game.getPublishers().stream()
                        .findFirst()
                        .map(Publisher::getName)
                        .orElse(null);
                row.setPublisherName(firstPublisher);
            }

            // Mapping des studios de développement (prend uniquement le premier)
            if (game.getDevelopmentStudios() != null && !game.getDevelopmentStudios().isEmpty()) {
                String firstStudio = game.getDevelopmentStudios().stream()
                        .findFirst()
                        .map(DevelopmentStudio::getName)
                        .orElse(null);
                row.setDevelopmentStudioName(firstStudio);
            }

            // Mapping des genres (prend uniquement le premier)
            if (game.getGenres() != null && !game.getGenres().isEmpty()) {
                String firstGenre = game.getGenres().stream()
                        .findFirst()
                        .map(Genre::getCode)
                        .orElse(null);
                row.setGenreCode(firstGenre);
            }
        }

        // Mapping du publisher physique
        if (physicalRelease.getPhysicalPublisher() != null) {
            row.setPhysicalPublisherName(physicalRelease.getPhysicalPublisher().getName());
        }

        // Mapping de l'édition
        if (physicalRelease.getEdition() != null) {
            row.setEditionCode(physicalRelease.getEdition().getCode());
        }

        // Mapping de la plateforme
        if (physicalRelease.getPlatform() != null) {
            Platform platform = physicalRelease.getPlatform();
            row.setPlatformCode(platform.getCode());
            row.setPlatformLibelle(platform.getLibelle());
        }

        return row;
    }
}
