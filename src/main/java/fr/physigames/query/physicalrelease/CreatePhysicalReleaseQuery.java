package fr.physigames.query.physicalrelease;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
public class CreatePhysicalReleaseQuery {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate releaseDate;

    private Long gameId;

    private Long editionId;

    private Long platformId;

    private Long publisherId;

    private String name;
}

