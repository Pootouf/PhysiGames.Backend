package fr.physigames.query.developmentstudio;

import fr.physigames.entity.DevelopmentStudio;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SearchDevelopmentStudioQuery {
    private String name;
}

