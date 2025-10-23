package fr.physigames.service;

import fr.physigames.entity.Publisher;
import fr.physigames.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PublisherService {

    private final PublisherRepository publisherRepository;

    public Page<Publisher> search(String name, Pageable pageable) {
        return publisherRepository.searchByName(name, pageable);
    }

    public Optional<Publisher> findById(Long id) {
        return publisherRepository.findById(id);
    }

    @Transactional
    public Publisher create(Publisher publisher) {
        publisher.setId(null);
        return publisherRepository.save(publisher);
    }

    @Transactional
    public Optional<Publisher> update(Long id, Publisher updated) {
        return publisherRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            return publisherRepository.save(existing);
        });
    }

    @Transactional
    public boolean delete(Long id) {
        return publisherRepository.findById(id).map(existing -> {
            publisherRepository.delete(existing);
            return true;
        }).orElse(false);
    }
}

