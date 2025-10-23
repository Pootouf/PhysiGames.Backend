package fr.physigames.query.developmentstudio;

import fr.physigames.entity.DevelopmentStudio;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateDevelopmentStudioQuery {
    private String name;

    public DevelopmentStudio toEntity() {
        DevelopmentStudio ds = new DevelopmentStudio();
        ds.setName(this.name);
        return ds;
    }
}

