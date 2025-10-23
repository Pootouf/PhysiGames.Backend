package fr.physigames.service;

import fr.physigames.entity.DevelopmentStudio;
import fr.physigames.repository.DevelopmentStudioRepository;
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

    public Page<DevelopmentStudio> search(String name, Pageable pageable) {
        return developmentStudioRepository.searchByName(name, pageable);
    }

    public Optional<DevelopmentStudio> findById(Long id) {
        return developmentStudioRepository.findById(id);
    }

    @Transactional
    public DevelopmentStudio create(DevelopmentStudio developmentStudio) {
        developmentStudio.setId(null);
        return developmentStudioRepository.save(developmentStudio);
    }

    @Transactional
    public Optional<DevelopmentStudio> update(Long id, DevelopmentStudio updated) {
        return developmentStudioRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            return developmentStudioRepository.save(existing);
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

