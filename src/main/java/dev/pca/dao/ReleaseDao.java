package dev.pca.dao;

import dev.pca.models.Release;
import dev.pca.repositories.ReleaseRepository;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class ReleaseDao {
    private final ReleaseRepository releaseRepo;

    public ReleaseDao(ReleaseRepository releaseRepo) {
        this.releaseRepo = releaseRepo;
    }

    public Release insert(Release release) {
        return releaseRepo.insert(release);
    }

    public Collection<Release> getAll() {
        return releaseRepo.findAll();
    }

    public Optional<Release> getById(String id) {
        return releaseRepo.findById(id);
    }

    public Optional<Release> getByCode(String code) {
        return releaseRepo.findAll().stream()
                .filter(set -> set.getSetCode().equals(code))
                .findFirst();
    }

    public Optional<Collection<Release>> getByPredicate(Collection<Predicate<Release>> filters) {
        return Optional.of(releaseRepo.findAll().stream()
                .filter(filters.stream().reduce(Predicate::or).orElse(t->true))
                .collect(Collectors.toList()));
    }
}

