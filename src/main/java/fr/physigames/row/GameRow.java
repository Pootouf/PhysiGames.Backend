package fr.physigames.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameRow {
    private Long id;
    private String title;
    private Set<PublisherRow> publishers;
    private Set<DevelopmentStudioRow> developmentStudios;
    private Set<GenreRow> genres;
    private Set<String> languageCodes;
    private Set<PhysicalReleaseRow> physicalReleases;
}
