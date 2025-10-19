package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "localized_physical_releases")
@Getter
@Setter
@NoArgsConstructor
public class LocalizedPhysicalRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "physical_release_id", nullable = false)
    private PhysicalRelease physicalRelease;

    @ManyToOne
    @JoinColumn(name = "language_id", nullable = false)
    private Language language;

    @Column(name = "box_image")
    private String boxImage;

    public LocalizedPhysicalRelease(PhysicalRelease physicalRelease, Language language, String boxImage) {
        this.physicalRelease = physicalRelease;
        this.language = language;
        this.boxImage = boxImage;
    }
}


