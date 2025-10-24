package fr.physigames.controller;

import fr.physigames.query.developmentstudio.CreateDevelopmentStudioQuery;
import fr.physigames.query.developmentstudio.UpdateDevelopmentStudioQuery;
import fr.physigames.query.developmentstudio.SearchDevelopmentStudioQuery;
import fr.physigames.row.DevelopmentStudioRow;
import fr.physigames.service.DevelopmentStudioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/development-studios")
@RequiredArgsConstructor
@Tag(name = "Development Studios", description = "API de gestion des studios de développement")
public class DevelopmentStudioController {

    private final DevelopmentStudioService developmentStudioService;

    @GetMapping
    @Operation(summary = "Rechercher des studios de développement", description = "Recherche paginée par nom (partiel, insensible à la casse)")
    public ResponseEntity<Page<DevelopmentStudioRow>> search(
            @ModelAttribute SearchDevelopmentStudioQuery query,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<DevelopmentStudioRow> results = developmentStudioService.search(query.getName(), pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un studio par son id")
    public ResponseEntity<DevelopmentStudioRow> getById(@PathVariable Long id) {
        return developmentStudioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un studio de développement")
    public ResponseEntity<Long> create(@RequestBody CreateDevelopmentStudioQuery query) {
        var toCreate = query.toEntity();
        var createdId = developmentStudioService.create(toCreate);
        return ResponseEntity.status(201).body(createdId);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un studio existant")
    public ResponseEntity<Long> update(@PathVariable Long id, @RequestBody UpdateDevelopmentStudioQuery query) {
        var toUpdate = query.toEntity();
        return developmentStudioService.update(id, toUpdate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un studio")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = developmentStudioService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
