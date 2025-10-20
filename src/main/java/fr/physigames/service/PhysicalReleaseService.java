package fr.physigames.service;

import fr.physigames.entity.*;
import fr.physigames.query.SearchPhysicalReleaseQuery;
import fr.physigames.repository.LocalizedGenreRepository;
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
    private final LocalizedGenreRepository localizedGenreRepository;

    /**
     * Recherche paginée de PhysicalRelease avec filtres optionnels.
     * Effectue le mapping vers PhysicalReleaseRow.
     *
     * @param query Les critères de recherche
     * @param pageable Pagination et tri
     * @return Page de PhysicalReleaseRow
     */
    public Page<PhysicalReleaseRow> searchPhysicalReleases(
            SearchPhysicalReleaseQuery query,
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

        final String languageCode = query.getLanguageCode();
        return physicalReleases.map(pr -> mapToRow(pr, languageCode));
    }

    /**
     * Mappe une entité PhysicalRelease vers un PhysicalReleaseRow.
     * Pour les collections (publishers, developmentStudios, genres), prend uniquement le premier élément (mode aperçu).
     * Le nom du genre est résolu via le repository des genres localisés selon le code de langue fourni.
     *
     * @param physicalRelease L'entité à mapper
     * @param languageCode Code de la langue pour récupérer les libellés localisés (peut être null)
     * @return Le row mappé
     */
    private PhysicalReleaseRow mapToRow(PhysicalRelease physicalRelease, String languageCode) {
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
                game.getGenres().stream().findFirst().ifPresent(firstGenre -> {
                    row.setGenreCode(firstGenre.getCode());

                    // Résolution du nom localisé du genre si une langue est fournie
                    if (languageCode != null) {
                        localizedGenreRepository.findByGenreIdAndLanguageCode(firstGenre.getId(), languageCode)
                                .ifPresent(lg -> row.setGenreName(lg.getName()));
                    }
                });
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
