package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "box_image_id")
    private ImageEntity boxImage;

    @OneToMany(mappedBy = "localizedPhysicalRelease", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageEntity> images = new ArrayList<>();

    public void addImage(ImageEntity image) {
        images.add(image);
        image.setLocalizedPhysicalRelease(this);
    }

    public void removeImage(ImageEntity image) {
        images.remove(image);
        image.setLocalizedPhysicalRelease(null);
    }

    public void setBoxImage(ImageEntity image) {
        this.boxImage = image;
        if (image != null) {
            image.setLocalizedPhysicalRelease(this);
        }
    }

    public LocalizedPhysicalRelease(PhysicalRelease physicalRelease, Language language, ImageEntity boxImage) {
        this.physicalRelease = physicalRelease;
        this.language = language;
        setBoxImage(boxImage);
    }
}
