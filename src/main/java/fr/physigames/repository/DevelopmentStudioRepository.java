package fr.physigames.repository;

import fr.physigames.entity.DevelopmentStudio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DevelopmentStudioRepository extends JpaRepository<DevelopmentStudio, Long> {

    /**
     * Recherche paginée de studios par nom (recherche partielle, insensible à la casse).
     * Si le paramètre name est null, retourne tous les studios (paged).
     */
    @Query("SELECT ds FROM DevelopmentStudio ds WHERE (LOWER(ds.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL)")
    Page<DevelopmentStudio> searchByName(@Param("name") String name, Pageable pageable);
}

