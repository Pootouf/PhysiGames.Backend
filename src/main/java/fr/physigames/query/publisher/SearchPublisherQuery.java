package fr.physigames.query.publisher;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchPublisherQuery {
    private String name;

}

