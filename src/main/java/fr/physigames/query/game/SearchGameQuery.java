package fr.physigames.query.game;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchGameQuery {
    private String title;
}

