package fr.physigames.repository;

import fr.physigames.entity.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query("SELECT p FROM Publisher p WHERE (LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')) OR :name IS NULL)")
    Page<Publisher> searchByName(@Param("name") String name, Pageable pageable);
}

