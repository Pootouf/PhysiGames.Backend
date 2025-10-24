package fr.physigames.mapper;

import fr.physigames.entity.*;
import fr.physigames.row.GenreRow;
import fr.physigames.row.PhysicalReleaseRow;
import fr.physigames.row.PhysicalReleaseMinimalRow;
import fr.physigames.row.PhysicalReleaseDetailRow;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
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

    public PhysicalReleaseDetailRow toDetailRow(PhysicalRelease physicalRelease, List<GenreRow> genreRows) {
        if (physicalRelease == null) return null;

        PhysicalReleaseDetailRow row = new PhysicalReleaseDetailRow();
        row.setId(physicalRelease.getId());
        row.setReleaseDate(physicalRelease.getReleaseDate());
        row.setPhysicalReleaseName(physicalRelease.getName());

        Game game = physicalRelease.getGame();
        if (game != null) {
            row.setGameTitle(game.getTitle());

            if (game.getPublishers() != null) {
                List<String> publisherNames = game.getPublishers().stream().map(Publisher::getName).collect(Collectors.toList());
                row.setPublisherNames(publisherNames);
            }

            if (game.getDevelopmentStudios() != null) {
                List<String> devNames = game.getDevelopmentStudios().stream().map(DevelopmentStudio::getName).collect(Collectors.toList());
                row.setDevelopmentStudioNames(devNames);
            }

            if (game.getGenres() != null) {
                if (genreRows != null) {
                    row.setGenres(genreRows);
                } else {
                    // fallback: build minimal GenreRow with id and code
                    List<GenreRow> minimal = game.getGenres().stream()
                            .map(g -> new GenreRow(g.getId(), g.getCode(), null))
                            .collect(Collectors.toList());
                    row.setGenres(minimal);
                }
            }
        }

        if (physicalRelease.getPhysicalPublisher() != null) {
            row.setPhysicalPublisherName(physicalRelease.getPhysicalPublisher().getName());
        }

        if (physicalRelease.getEdition() != null) {
            row.setEditionCode(physicalRelease.getEdition().getCode());
        }

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
