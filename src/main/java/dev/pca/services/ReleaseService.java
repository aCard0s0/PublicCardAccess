package dev.pca.services;

import com.google.common.collect.Lists;
import dev.pca.dao.ReleaseDao;
import dev.pca.models.Release;
import dev.pca.services.perdicates.ReleasePredicates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

@Service
public class ReleaseService {

    private final ReleaseDao releaseDao;

    public ReleaseService(ReleaseDao releaseDao) {
        this.releaseDao = releaseDao;
    }

    public Collection<Release> insert(List<Release> sets) {
        List<Release> response = Lists.newArrayList();
        sets.forEach(c -> response.add(releaseDao.insert(c)));
        return response;
    }

    public Optional<Collection<Release>> getAll() {
        return Optional.of(releaseDao.getAll());
    }

    public Optional<Collection<Release>> getByReleaseId(List<String> ids) {
        return releaseDao.getByPredicate(ReleasePredicates.byReleaseId(ids));
    }

    public Optional<Collection<Release>> getByReleaseCode(List<String> codes) {
        return releaseDao.getByPredicate(ReleasePredicates.byReleaseCode(codes));
    }

    public Optional<Collection<Release>> filterBy(Map<String, String> queryParams) {
        Collection<Predicate<Release>> filters = new ArrayList<>();
        return null;
    }
}
