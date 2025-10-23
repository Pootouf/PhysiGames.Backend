package fr.physigames.mapper;

import fr.physigames.entity.Game;
import fr.physigames.row.GameRow;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.row.PublisherRow;
import fr.physigames.row.DevelopmentStudioRow;
import fr.physigames.row.GenreRow;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final PhysicalReleaseMapper physicalReleaseMapper;

    /**
     * Main mapping method. If localizedGenreNames is null, genre names won't be filled.
     */
    public GameRow toRow(Game g, Map<Long, String> localizedGenreNames) {
        GameRow row = new GameRow();
        row.setId(g.getId());
        row.setTitle(g.getTitle());

        if (g.getPublishers() != null) {
            Set<PublisherRow> publisherRows = g.getPublishers().stream()
                    .map(p -> new PublisherRow(p.getId(), p.getName()))
                    .collect(Collectors.toSet());
            row.setPublishers(publisherRows);
        }

        if (g.getDevelopmentStudios() != null) {
            Set<DevelopmentStudioRow> devRows = g.getDevelopmentStudios().stream()
                    .map(d -> new DevelopmentStudioRow(d.getId(), d.getName()))
                    .collect(Collectors.toSet());
            row.setDevelopmentStudios(devRows);
        }

        if (g.getGenres() != null) {
            Set<GenreRow> genreRows = g.getGenres().stream().map(ge -> {
                String localizedName = null;
                if (localizedGenreNames != null) {
                    localizedName = localizedGenreNames.get(ge.getId());
                }
                return new GenreRow(ge.getId(), ge.getCode(), localizedName);
            }).collect(Collectors.toSet());
            row.setGenres(genreRows);
        }

        if (g.getLanguages() != null) {
            row.setLanguageCodes(g.getLanguages().stream().map(fr.physigames.entity.Language::getCode).collect(Collectors.toSet()));
        }

        // Map physical releases using PhysicalReleaseMapper; pass localized genre name when requested
        if (g.getPhysicalReleases() != null) {
            Set<PhysicalReleaseRow> prRows = g.getPhysicalReleases().stream().map(pr -> {
                String localizedGenreName = null;
                if (localizedGenreNames != null && g.getGenres() != null && !g.getGenres().isEmpty()) {
                    Long firstGenreId = g.getGenres().stream().findFirst().map(fr.physigames.entity.Genre::getId).orElse(null);
                    if (firstGenreId != null) {
                        localizedGenreName = localizedGenreNames.get(firstGenreId);
                    }
                }
                return physicalReleaseMapper.toRow(pr, localizedGenreName);
            }).collect(Collectors.toSet());
            row.setPhysicalReleases(prRows);
        }

        return row;
    }
}
