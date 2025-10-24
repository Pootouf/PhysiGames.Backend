package fr.physigames.repository;

import fr.physigames.entity.PhysicalRelease;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PhysicalReleaseRepository extends JpaRepository<PhysicalRelease, Long> {

    /**
     * Recherche complexe avec tous les filtres de PhysicalReleaseRow.
     * Tous les paramètres sont optionnels (nullable).
     * Pour les collections (publishers, developmentStudios, genres), vérifie qu'au moins un élément contient la string recherchée.
     *
     * @param gameTitle Filtre sur le titre du jeu (contient, insensible à la casse)
     * @param publisherName Filtre sur les publishers du jeu (contient, insensible à la casse)
     * @param physicalPublisherName Filtre sur le publisher physique (contient, insensible à la casse)
     * @param physicalReleaseName Filter sur le nom physique du jeu
     * @param developmentStudioName Filtre sur les studios de développement du jeu (contient, insensible à la casse)
     * @param genreCode Filtre sur les genres du jeu (contient, insensible à la casse)
     * @param editionCode Filtre sur le code d'édition (contient, insensible à la casse)
     * @param platformCode Filtre sur le code de plateforme (contient, insensible à la casse)
     * @param releaseDateFrom Date de sortie minimale
     * @param releaseDateTo Date de sortie maximale
     * @param regionId Filtre sur l'ID de la région (optionnel)
     * @param pageable Pagination et tri
     * @return Page de PhysicalRelease filtrés
     */
    @Query("SELECT DISTINCT pr FROM PhysicalRelease pr " +
            "LEFT JOIN pr.game g " +
            "LEFT JOIN pr.platform p " +
            "LEFT JOIN pr.edition e " +
            "LEFT JOIN pr.physicalPublisher pp " +
            "LEFT JOIN g.publishers pub " +
            "LEFT JOIN g.developmentStudios ds " +
            "LEFT JOIN g.genres gen " +
            "WHERE (LOWER(g.title) LIKE LOWER(CONCAT('%', :gameTitle, '%')) OR :gameTitle IS NULL) " +
            "AND EXISTS (" +
            "    SELECT 1 FROM Publisher pub2 " +
            "    WHERE pub2 MEMBER OF g.publishers " +
            "    AND LOWER(pub2.name) LIKE LOWER(CONCAT('%', :publisherName, '%')) AND :publisherName IS NOT NULL" +
            ") " +
            "AND (LOWER(pp.name) LIKE LOWER(CONCAT('%', :physicalPublisherName, '%')) OR :physicalPublisherName IS NULL) " +
            "AND (LOWER(pr.name) LIKE LOWER(CONCAT('%', :physicalReleaseName, '%')) OR :physicalReleaseName IS NULL) " +
            "AND EXISTS (" +
            "    SELECT 1 FROM DevelopmentStudio ds2 " +
            "    WHERE ds2 MEMBER OF g.developmentStudios " +
            "    AND LOWER(ds2.name) LIKE LOWER(CONCAT('%', :developmentStudioName, '%')) AND :developmentStudioName IS NOT NULL" +
            ") " +
            "AND EXISTS (" +
            "    SELECT 1 FROM Genre gen2 " +
            "    WHERE gen2 MEMBER OF g.genres " +
            "    AND LOWER(gen2.code) LIKE LOWER(CONCAT('%', :genreCode, '%')) AND :genreCode IS NOT NULL" +
            ") " +
            "AND (LOWER(e.code) LIKE LOWER(CONCAT('%', :editionCode, '%')) OR :editionCode IS NULL) " +
            "AND (LOWER(p.code) LIKE LOWER(CONCAT('%', :platformCode, '%')) OR :platformCode IS NULL) " +
            "AND (:releaseDateFrom IS NULL OR pr.releaseDate >= :releaseDateFrom) " +
            "AND (:releaseDateTo IS NULL OR pr.releaseDate <= :releaseDateTo) " +
            "AND (:regionId IS NULL OR pr.region.id = :regionId)")
    Page<PhysicalRelease> searchPhysicalReleases(
            @Param("gameTitle") String gameTitle,
            @Param("publisherName") String publisherName,
            @Param("physicalPublisherName") String physicalPublisherName,
            @Param("physicalReleaseName") String physicalReleaseName,
            @Param("developmentStudioName") String developmentStudioName,
            @Param("genreCode") String genreCode,
            @Param("editionCode") String editionCode,
            @Param("platformCode") String platformCode,
            @Param("releaseDateFrom") LocalDate releaseDateFrom,
            @Param("releaseDateTo") LocalDate releaseDateTo,
            @Param("regionId") Long regionId,
            Pageable pageable);
}
