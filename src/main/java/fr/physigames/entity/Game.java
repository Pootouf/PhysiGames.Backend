package fr.physigames.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "games")
@Getter
@Setter
@NoArgsConstructor
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToMany
    @JoinTable(
        name = "game_publishers",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<Publisher> publishers = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "game_development_studios",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "development_studio_id")
    )
    private Set<DevelopmentStudio> developmentStudios = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "game_genres",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "game_languages",
        joinColumns = @JoinColumn(name = "game_id"),
        inverseJoinColumns = @JoinColumn(name = "language_id")
    )
    private Set<Language> languages = new HashSet<>();

    public Game(String title) {
        this.title = title;
    }
}