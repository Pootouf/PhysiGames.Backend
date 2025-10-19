package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "physical_releases")
@Getter
@Setter
@NoArgsConstructor
public class PhysicalRelease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @ManyToOne
    @JoinColumn(name = "edition_id")
    private Edition edition;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    @OneToMany(mappedBy = "physicalRelease", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<LocalizedPhysicalRelease> localizedReleases = new HashSet<>();

    public PhysicalRelease(LocalDate releaseDate, Edition edition) {
        this.releaseDate = releaseDate;
        this.edition = edition;
    }
}
