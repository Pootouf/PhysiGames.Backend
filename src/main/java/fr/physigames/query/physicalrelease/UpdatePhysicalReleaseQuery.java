package fr.physigames.query.physicalrelease;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class UpdatePhysicalReleaseQuery {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    private Long editionId;

    private Long publisherId;

    private String name;

    private Long gameId;

    private Long platformId;
}
