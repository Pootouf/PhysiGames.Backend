package fr.physigames.service;

import fr.physigames.entity.Publisher;
import fr.physigames.mapper.PublisherMapper;
import fr.physigames.repository.PublisherRepository;
import fr.physigames.row.PublisherRow;
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
    private final PublisherMapper publisherMapper;

    public Page<PublisherRow> search(String name, Pageable pageable) {
        return publisherRepository.searchByName(name, pageable).map(publisherMapper::toRow);
    }

    public Optional<PublisherRow> findById(Long id) {
        return publisherRepository.findById(id).map(publisherMapper::toRow);
    }

    @Transactional
    public Long create(Publisher publisher) {
        publisher.setId(null);
        Publisher saved = publisherRepository.save(publisher);
        return saved.getId();
    }

    @Transactional
    public Optional<Long> update(Long id, Publisher updated) {
        return publisherRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            Publisher saved = publisherRepository.save(existing);
            return saved.getId();
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
