package fr.physigames.service;

import fr.physigames.entity.DevelopmentStudio;
import fr.physigames.mapper.DevelopmentStudioMapper;
import fr.physigames.repository.DevelopmentStudioRepository;
import fr.physigames.row.DevelopmentStudioRow;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DevelopmentStudioService {

    private final DevelopmentStudioRepository developmentStudioRepository;
    private final DevelopmentStudioMapper developmentStudioMapper;

    public Page<DevelopmentStudioRow> search(String name, Pageable pageable) {
        return developmentStudioRepository.searchByName(name, pageable).map(developmentStudioMapper::toRow);
    }

    public Optional<DevelopmentStudioRow> findById(Long id) {
        return developmentStudioRepository.findById(id).map(developmentStudioMapper::toRow);
    }

    @Transactional
    public Long create(DevelopmentStudio developmentStudio) {
        developmentStudio.setId(null);
        DevelopmentStudio saved = developmentStudioRepository.save(developmentStudio);
        return saved.getId();
    }

    @Transactional
    public Optional<Long> update(Long id, DevelopmentStudio updated) {
        return developmentStudioRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            DevelopmentStudio saved = developmentStudioRepository.save(existing);
            return saved.getId();
        });
    }

    @Transactional
    public boolean delete(Long id) {
        return developmentStudioRepository.findById(id).map(existing -> {
            developmentStudioRepository.delete(existing);
            return true;
        }).orElse(false);
    }
}
