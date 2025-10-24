package fr.physigames.service;

import fr.physigames.entity.*;
import fr.physigames.mapper.PhysicalReleaseMapper;
import fr.physigames.query.physicalrelease.SearchPhysicalReleaseQuery;
import fr.physigames.repository.*;
import fr.physigames.row.PhysicalReleaseRow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PhysicalReleaseService {

    private final PhysicalReleaseRepository physicalReleaseRepository;
    private final PhysicalReleaseMapper physicalReleaseMapper;
    private final LocalizedGenreService localizedGenreService;
    private final GameRepository gameRepository;
    private final EditionRepository editionRepository;
    private final PlatformRepository platformRepository;
    private final PublisherRepository publisherRepository;

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
                query.getPhysicalReleaseName(),
                query.getDevelopmentStudioName(),
                query.getGenreCode(),
                query.getEditionCode(),
                query.getPlatformCode(),
                query.getReleaseDateFrom(),
                query.getReleaseDateTo(),
                pageable
        );

        final String languageCode = query.getLanguageCode();
        return physicalReleases.map(pr -> {
            String localizedGenreName = null;
            if (languageCode != null && pr.getGame() != null && pr.getGame().getGenres() != null && !pr.getGame().getGenres().isEmpty()) {
                Long firstGenreId = pr.getGame().getGenres().stream().findFirst().map(Genre::getId).orElse(null);
                localizedGenreName = localizedGenreService.findNameByGenreIdAndLanguage(firstGenreId, languageCode).orElse(null);
            }
            return physicalReleaseMapper.toRow(pr, localizedGenreName);
        });
    }

    /**
     * Crée une PhysicalRelease à partir des paramètres fournis.
     * @param releaseDate date de sortie (obligatoire)
     * @param gameId id du jeu (obligatoire)
     * @param editionId id de l'édition (obligatoire)
     * @param platformId id de la plateforme (obligatoire)
     * @param publisherId id du publisher physique (optionnel)
     * @param name nom de la release (optionnel)
     * @return l'id de l'entité PhysicalRelease sauvegardée
     */
    @Transactional
    public Long createPhysicalRelease(LocalDate releaseDate, Long gameId, Long editionId, Long platformId, Long publisherId, String name) {
        if (releaseDate == null) {
            throw new IllegalArgumentException("releaseDate is required");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("gameId is required");
        }
        if (editionId == null) {
            throw new IllegalArgumentException("editionId is required");
        }
        if (platformId == null) {
            throw new IllegalArgumentException("platformId is required");
        }

        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game not found for id: " + gameId));

        Edition edition = editionRepository.findById(editionId)
                .orElseThrow(() -> new IllegalArgumentException("Edition not found for id: " + editionId));

        Platform platform = platformRepository.findById(platformId)
                .orElseThrow(() -> new IllegalArgumentException("Platform not found for id: " + platformId));

        Publisher physicalPublisher = null;
        if (publisherId != null) {
            physicalPublisher = publisherRepository.findById(publisherId)
                    .orElseThrow(() -> new IllegalArgumentException("Publisher not found for id: " + publisherId));
        }

        PhysicalRelease pr = new PhysicalRelease();
        pr.setReleaseDate(releaseDate);
        pr.setGame(game);
        pr.setEdition(edition);
        pr.setPlatform(platform);
        pr.setPhysicalPublisher(physicalPublisher);
        pr.setName(name);

        return physicalReleaseRepository.save(pr).getId();
    }
}
