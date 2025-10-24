package fr.physigames.service;

import fr.physigames.entity.DevelopmentStudio;
import fr.physigames.entity.Game;
import fr.physigames.entity.Genre;
import fr.physigames.entity.Language;
import fr.physigames.entity.Publisher;
import fr.physigames.mapper.GameMapper;
import fr.physigames.row.GameRow;
import fr.physigames.repository.DevelopmentStudioRepository;
import fr.physigames.repository.GameRepository;
import fr.physigames.repository.GenreRepository;
import fr.physigames.repository.LanguageRepository;
import fr.physigames.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameService {

    private final GameRepository gameRepository;
    private final PublisherRepository publisherRepository;
    private final DevelopmentStudioRepository developmentStudioRepository;
    private final GenreRepository genreRepository;
    private final LanguageRepository languageRepository;
    private final GameMapper gameMapper;
    private final LocalizedGenreService localizedGenreService;

    public Page<GameRow> search(String title, Pageable pageable, String languageCode) {
        return gameRepository.searchByTitle(title, pageable).map(g -> mapWithLanguage(g, languageCode));
    }

    public Optional<GameRow> findRowById(Long id, String languageCode) {
        return gameRepository.findById(id).map(g -> mapWithLanguage(g, languageCode));
    }

    @Transactional
    public Long create(Game game) {
        applyResolvedRelationsTo(game, game);
        Game saved = gameRepository.save(game);
        return saved.getId();
    }

    @Transactional
    public Optional<Long> update(Long id, Game updated) {
        return gameRepository.findById(id).map(existing -> {
            existing.setTitle(updated.getTitle());
            applyResolvedRelationsTo(existing, updated);
            Game saved = gameRepository.save(existing);
            return saved.getId();
        });
    }

    @Transactional
    public boolean delete(Long id) {
        return gameRepository.findById(id).map(existing -> {
            gameRepository.delete(existing);
            return true;
        }).orElse(false);
    }

    private void applyResolvedRelationsTo(Game target, Game source) {
        if (source.getPublishers() != null) {
            target.setPublishers(resolvePublishers(source.getPublishers()));
        }
        if (source.getDevelopmentStudios() != null) {
            target.setDevelopmentStudios(resolveDevelopmentStudios(source.getDevelopmentStudios()));
        }
        if (source.getGenres() != null) {
            target.setGenres(resolveGenres(source.getGenres()));
        }
        if (source.getLanguages() != null) {
            target.setLanguages(resolveLanguages(source.getLanguages()));
        }
    }

    /**
     * Mappe un Game en GameRow en appliquant la localisation des genres pour les physicalReleases si demandé.
     */
    private GameRow mapWithLanguage(Game g, String languageCode) {
        Map<Long, String> localizedGenreNames = null;
        if (languageCode != null && g.getGenres() != null && !g.getGenres().isEmpty()) {
            localizedGenreNames = new HashMap<>();
            for (Genre genre : g.getGenres()) {
                final Long gid = genre.getId();
                if (gid != null) {
                    java.util.Optional<String> nameOpt = localizedGenreService.findNameByGenreIdAndLanguage(gid, languageCode);
                    if (nameOpt.isPresent()) {
                        localizedGenreNames.put(gid, nameOpt.get());
                    }
                }
            }
        }

        return gameMapper.toRow(g, localizedGenreNames);
    }

    private Set<Publisher> resolvePublishers(Set<Publisher> publishers) {
        Set<Long> ids = publishers.stream()
                .map(Publisher::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new HashSet<>(publisherRepository.findAllById(ids));
    }

    private Set<DevelopmentStudio> resolveDevelopmentStudios(Set<DevelopmentStudio> studios) {
        Set<Long> ids = studios.stream()
                .map(DevelopmentStudio::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new HashSet<>(developmentStudioRepository.findAllById(ids));
    }

    private Set<Genre> resolveGenres(Set<Genre> genres) {
        Set<Long> ids = genres.stream()
                .map(Genre::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new HashSet<>(genreRepository.findAllById(ids));
    }

    private Set<Language> resolveLanguages(Set<Language> languages) {
        Set<Long> ids = languages.stream()
                .map(Language::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        return new HashSet<>(languageRepository.findAllById(ids));
    }
}
