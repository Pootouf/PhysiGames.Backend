package fr.physigames.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalReleaseDetailRow {
    private Long id;

    private String gameTitle;

    private List<String> publisherNames;

    private List<String> developmentStudioNames;

    private List<GenreRow> genres;

    private String physicalPublisherName;

    private String physicalReleaseName;

    private String editionCode;

    private String platformCode;

    private String platformLibelle;

    private LocalDate releaseDate;

    private String regionCode;

    private String regionName;
}
