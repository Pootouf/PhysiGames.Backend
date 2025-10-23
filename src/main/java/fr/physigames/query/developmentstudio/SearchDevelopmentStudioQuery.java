package fr.physigames.query.developmentstudio;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDevelopmentStudioQuery {
    private String name;
}

