package fr.physigames.mapper;

import fr.physigames.entity.DevelopmentStudio;
import fr.physigames.row.DevelopmentStudioRow;
import org.springframework.stereotype.Component;

@Component
public class DevelopmentStudioMapper {

    public DevelopmentStudioRow toRow(DevelopmentStudio ds) {
        if (ds == null) return null;
        DevelopmentStudioRow r = new DevelopmentStudioRow();
        r.setId(ds.getId());
        r.setName(ds.getName());
        return r;
    }
}

