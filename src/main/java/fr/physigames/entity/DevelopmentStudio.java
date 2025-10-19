package fr.physigames.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "development_studios")
public class DevelopmentStudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public DevelopmentStudio() {
    }

    public DevelopmentStudio(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

