package fr.physigames.repository;

import fr.physigames.entity.LocalizedGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalizedGenreRepository extends JpaRepository<LocalizedGenre, Long> {

    @Query("SELECT lg FROM LocalizedGenre lg WHERE lg.genre.id = :genreId AND lg.language.code = :languageCode")
    Optional<LocalizedGenre> findByGenreIdAndLanguageCode(@Param("genreId") Long genreId, @Param("languageCode") String languageCode);
}

