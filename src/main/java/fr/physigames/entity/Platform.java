package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "platforms")
@Getter
@Setter
@NoArgsConstructor
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String libelle;

    public Platform(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
}

