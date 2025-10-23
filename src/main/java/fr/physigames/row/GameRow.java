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
    private Set<String> publisherNames;
    private Set<String> developmentStudioNames;
    private Set<String> genreCodes;
    private Set<String> languageCodes;
    private Set<PhysicalReleaseRow> physicalReleases;
}
