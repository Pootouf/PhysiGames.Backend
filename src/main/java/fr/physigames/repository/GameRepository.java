package fr.physigames.repository;

import fr.physigames.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g WHERE (LOWER(g.title) LIKE LOWER(CONCAT('%', :title, '%')) OR :title IS NULL)")
    Page<Game> searchByTitle(@Param("title") String title, Pageable pageable);
}

