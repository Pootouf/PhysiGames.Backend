package fr.physigames.mapper;

import fr.physigames.entity.Game;
import fr.physigames.row.GameRow;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.service.LocalizedGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GameMapper {

    private final PhysicalReleaseMapper physicalReleaseMapper;
    private final LocalizedGenreService localizedGenreService;

    public GameRow toRow(Game g) {
        return toRow(g, null);
    }

    public GameRow toRow(Game g, String languageCode) {
        GameRow row = new GameRow();
        row.setId(g.getId());
        row.setTitle(g.getTitle());

        if (g.getPublishers() != null) {
            row.setPublisherNames(g.getPublishers().stream().map(fr.physigames.entity.Publisher::getName).collect(Collectors.toSet()));
        }

        if (g.getDevelopmentStudios() != null) {
            row.setDevelopmentStudioNames(g.getDevelopmentStudios().stream().map(fr.physigames.entity.DevelopmentStudio::getName).collect(Collectors.toSet()));
        }

        if (g.getGenres() != null) {
            row.setGenreCodes(g.getGenres().stream().map(fr.physigames.entity.Genre::getCode).collect(Collectors.toSet()));
        }

        if (g.getLanguages() != null) {
            row.setLanguageCodes(g.getLanguages().stream().map(fr.physigames.entity.Language::getCode).collect(Collectors.toSet()));
        }

        // Map physical releases using PhysicalReleaseMapper; pass localized genre name when requested
        if (g.getPhysicalReleases() != null) {
            Set<PhysicalReleaseRow> prRows = g.getPhysicalReleases().stream().map(pr -> {
                String localizedGenreName = null;
                if (languageCode != null && g.getGenres() != null && !g.getGenres().isEmpty()) {
                    Long firstGenreId = g.getGenres().stream().findFirst().map(fr.physigames.entity.Genre::getId).orElse(null);
                    localizedGenreName = localizedGenreService.findNameByGenreIdAndLanguage(firstGenreId, languageCode).orElse(null);
                }
                return physicalReleaseMapper.toRow(pr, localizedGenreName);
            }).collect(Collectors.toSet());
            row.setPhysicalReleases(prRows);
        }

        return row;
    }
}
