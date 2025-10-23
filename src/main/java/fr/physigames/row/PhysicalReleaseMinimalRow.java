package fr.physigames.row;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhysicalReleaseMinimalRow {
    private String physicalReleaseName;
    private String platformCode;
    private String platformLibelle;
    private LocalDate releaseDate;
}

