package fr.physigames.mapper;

import fr.physigames.entity.Publisher;
import fr.physigames.row.PublisherRow;
import org.springframework.stereotype.Component;

@Component
public class PublisherMapper {

    public PublisherRow toRow(Publisher p) {
        if (p == null) return null;
        PublisherRow r = new PublisherRow();
        r.setId(p.getId());
        r.setName(p.getName());
        return r;
    }
}

