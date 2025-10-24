package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "localized_physical_release_id", nullable = false)
    private LocalizedPhysicalRelease localizedPhysicalRelease;

    @Column(name = "url")
    private String url;

    public ImageEntity(LocalizedPhysicalRelease localizedPhysicalRelease, String url) {
        this.localizedPhysicalRelease = localizedPhysicalRelease;
        this.url = url;
    }
}

