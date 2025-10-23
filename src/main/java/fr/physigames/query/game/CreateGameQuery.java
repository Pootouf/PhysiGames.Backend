package fr.physigames.query.game;

import fr.physigames.entity.DevelopmentStudio;
import fr.physigames.entity.Game;
import fr.physigames.entity.Genre;
import fr.physigames.entity.Language;
import fr.physigames.entity.Publisher;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class CreateGameQuery {
    private String title;
    private List<Long> publisherIds;
    private List<Long> developmentStudioIds;
    private List<Long> genreIds;
    private List<Long> languageIds;

    public Game toEntity() {
        Game g = new Game();
        g.setTitle(this.title);
        if (publisherIds != null) {
            g.setPublishers(publisherIds.stream().map(id -> {
                Publisher p = new Publisher();
                p.setId(id);
                return p;
            }).collect(Collectors.toSet()));
        }
        if (developmentStudioIds != null) {
            g.setDevelopmentStudios(developmentStudioIds.stream().map(id -> {
                DevelopmentStudio ds = new DevelopmentStudio();
                ds.setId(id);
                return ds;
            }).collect(Collectors.toSet()));
        }
        if (genreIds != null) {
            g.setGenres(genreIds.stream().map(id -> {
                Genre ge = new Genre();
                ge.setId(id);
                return ge;
            }).collect(Collectors.toSet()));
        }
        if (languageIds != null) {
            g.setLanguages(languageIds.stream().map(id -> {
                Language l = new Language();
                l.setId(id);
                return l;
            }).collect(Collectors.toSet()));
        }
        return g;
    }
}

