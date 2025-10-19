package fr.physigames.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalReleaseRow {

    private Long id;

    private String gameTitle;

    private String publisherName;

    private String physicalPublisherName;

    private String developmentStudioName;

    private String genreCode;

    private String editionCode;

    private String platformCode;

    private String platformLibelle;

    private LocalDate releaseDate;
}

