package fr.physigames.query.publisher;

import fr.physigames.entity.Publisher;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePublisherQuery {
    private String name;

    public Publisher toEntity() {
        Publisher p = new Publisher();
        p.setName(this.name);
        return p;
    }
}

