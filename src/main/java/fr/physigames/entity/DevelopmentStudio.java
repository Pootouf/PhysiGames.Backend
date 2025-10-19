package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "development_studios")
@Getter
@Setter
@NoArgsConstructor
public class DevelopmentStudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public DevelopmentStudio(String name) {
        this.name = name;
    }
}
