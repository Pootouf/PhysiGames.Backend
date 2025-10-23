package fr.physigames.controller;

import fr.physigames.entity.Publisher;
import fr.physigames.row.PublisherRow;
import fr.physigames.query.publisher.CreatePublisherQuery;
import fr.physigames.query.publisher.UpdatePublisherQuery;
import fr.physigames.query.publisher.SearchPublisherQuery;
import fr.physigames.service.PublisherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publishers")
@RequiredArgsConstructor
@Tag(name = "Publishers", description = "API de gestion des éditeurs")
public class PublisherController {

    private final PublisherService publisherService;

    @GetMapping
    @Operation(summary = "Rechercher des éditeurs", description = "Recherche paginée par nom (partiel, insensible à la casse)")
    public ResponseEntity<Page<PublisherRow>> search(
            @ModelAttribute SearchPublisherQuery query,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<PublisherRow> results = publisherService.search(query.getName(), pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un éditeur par son id")
    public ResponseEntity<PublisherRow> getById(@PathVariable Long id) {
        return publisherService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un éditeur")
    public ResponseEntity<Publisher> create(@RequestBody CreatePublisherQuery query) {
        Publisher toCreate = query.toEntity();
        Publisher created = publisherService.create(toCreate);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un éditeur existant")
    public ResponseEntity<Publisher> update(@PathVariable Long id, @RequestBody UpdatePublisherQuery query) {
        Publisher toUpdate = query.toEntity();
        return publisherService.update(id, toUpdate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un éditeur")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = publisherService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
