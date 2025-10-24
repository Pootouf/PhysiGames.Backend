package fr.physigames.mapper;

import fr.physigames.entity.Game;
import fr.physigames.entity.PhysicalRelease;
import fr.physigames.entity.Platform;
import fr.physigames.entity.Region;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.row.PhysicalReleaseMinimalRow;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhysicalReleaseMapper {

    public PhysicalReleaseRow toRow(PhysicalRelease physicalRelease, String localizedGenreName) {
        PhysicalReleaseRow row = new PhysicalReleaseRow();

        row.setId(physicalRelease.getId());
        row.setReleaseDate(physicalRelease.getReleaseDate());
        row.setPhysicalReleaseName(physicalRelease.getName());

        // Mapping du jeu et de ses relations (aperçu)
        Game game = physicalRelease.getGame();
        if (game != null) {
            row.setGameTitle(game.getTitle());

            if (game.getPublishers() != null && !game.getPublishers().isEmpty()) {
                game.getPublishers().stream().findFirst().ifPresent(pub -> row.setPublisherName(pub.getName()));
            }

            if (game.getDevelopmentStudios() != null && !game.getDevelopmentStudios().isEmpty()) {
                game.getDevelopmentStudios().stream().findFirst().ifPresent(ds -> row.setDevelopmentStudioName(ds.getName()));
            }

            if (game.getGenres() != null && !game.getGenres().isEmpty()) {
                game.getGenres().stream().findFirst().ifPresent(firstGenre -> {
                    row.setGenreCode(firstGenre.getCode());
                    if (localizedGenreName != null) {
                        row.setGenreName(localizedGenreName);
                    }
                });
            }
        }

        // Physical publisher
        if (physicalRelease.getPhysicalPublisher() != null) {
            row.setPhysicalPublisherName(physicalRelease.getPhysicalPublisher().getName());
        }

        // Edition
        if (physicalRelease.getEdition() != null) {
            row.setEditionCode(physicalRelease.getEdition().getCode());
        }

        // Platform
        Platform platform = physicalRelease.getPlatform();
        if (platform != null) {
            row.setPlatformCode(platform.getCode());
            row.setPlatformLibelle(platform.getLibelle());
        }

        // Region
        Region region = physicalRelease.getRegion();
        if (region != null) {
            row.setRegionCode(region.getCode());
            row.setRegionName(region.getName());
        }

        return row;
    }

    public PhysicalReleaseMinimalRow toMinimalRow(PhysicalRelease physicalRelease) {
        if (physicalRelease == null) return null;

        PhysicalReleaseMinimalRow row = new PhysicalReleaseMinimalRow();
        row.setPhysicalReleaseName(physicalRelease.getName());
        row.setReleaseDate(physicalRelease.getReleaseDate());

        Platform platform = physicalRelease.getPlatform();
        if (platform != null) {
            row.setPlatformCode(platform.getCode());
            row.setPlatformLibelle(platform.getLibelle());
        }

        Region region = physicalRelease.getRegion();
        if (region != null) {
            row.setRegionCode(region.getCode());
            row.setRegionName(region.getName());
        }

        return row;
    }
}
