package fr.physigames.service;

import fr.physigames.repository.LocalizedGenreRepository;
import fr.physigames.entity.LocalizedGenre;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocalizedGenreService {

    private final LocalizedGenreRepository localizedGenreRepository;

    public Optional<String> findNameByGenreIdAndLanguage(Long genreId, String languageCode) {
        if (genreId == null || languageCode == null) {
            return Optional.empty();
        }
        return localizedGenreRepository.findByGenreIdAndLanguageCode(genreId, languageCode)
                .map(LocalizedGenre::getName);
    }
}
