package fr.physigames.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalReleaseRow {

    private Long id;

    private String gameTitle;

    private String publisherName;

    private String physicalPublisherName;

    private String physicalReleaseName;

    private String developmentStudioName;

    private String genreCode;

    private String genreName;

    private String editionCode;

    private String platformCode;

    private String platformLibelle;

    private LocalDate releaseDate;

    private String regionCode;

    private String regionName;
}
