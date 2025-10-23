package fr.physigames.service;

import fr.physigames.entity.*;
import fr.physigames.mapper.PhysicalReleaseMapper;
import fr.physigames.query.physicalrelease.SearchPhysicalReleaseQuery;
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
    private final PhysicalReleaseMapper physicalReleaseMapper;
    private final LocalizedGenreService localizedGenreService;

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
}
