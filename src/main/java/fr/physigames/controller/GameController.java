package fr.physigames.controller;

import fr.physigames.entity.Game;
import fr.physigames.query.game.CreateGameQuery;
import fr.physigames.query.game.SearchGameQuery;
import fr.physigames.query.game.UpdateGameQuery;
import fr.physigames.row.GameRow;
import fr.physigames.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
@RequiredArgsConstructor
@Tag(name = "Games", description = "API de gestion des jeux")
public class GameController {

    private final GameService gameService;

    @GetMapping
    @Operation(summary = "Rechercher des jeux", description = "Recherche paginée par titre (partiel, insensible à la casse)")
    public ResponseEntity<Page<GameRow>> search(@ModelAttribute SearchGameQuery query,
                                                @RequestParam(name = "language", required = false) String language,
                                                @PageableDefault(size = 20) Pageable pageable) {
        Page<GameRow> results = gameService.search(query.getTitle(), pageable, language);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un jeu par son id")
    public ResponseEntity<GameRow> getById(@PathVariable Long id,
                                           @RequestParam(name = "language", required = false) String language) {
        return gameService.findRowById(id, language)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer un jeu")
    public ResponseEntity<Game> create(@RequestBody CreateGameQuery query) {
        Game toCreate = query.toEntity();
        Game created = gameService.create(toCreate);
        return ResponseEntity.status(201).body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un jeu existant")
    public ResponseEntity<Game> update(@PathVariable Long id, @RequestBody UpdateGameQuery query) {
        Game toUpdate = query.toEntity();
        return gameService.update(id, toUpdate)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un jeu")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boolean deleted = gameService.delete(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
