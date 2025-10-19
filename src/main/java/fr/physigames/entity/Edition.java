package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "editions")
@Getter
@Setter
@NoArgsConstructor
public class Edition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String libelle;

    public Edition(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }
}