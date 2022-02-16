package dev.pca.services.perdicates;

import dev.pca.models.Release;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import static dev.pca.services.perdicates.UtilsPredicates.parseToCaseInsensitiveRegex;
import static dev.pca.services.perdicates.UtilsPredicates.parseToCaseSensitiveRegex;


public class ReleasePredicates {
    //region identifiers
    public static Collection<Predicate<Release>> byReleaseId(List<String> ids) {
        Collection<Predicate<Release>> filters = new ArrayList<>();
        for(String id: ids) {
            filters.add(set -> parseToCaseSensitiveRegex(id.trim()).matcher(set.getId()).matches());
        }
        return filters;
    }

    public static Collection<Predicate<Release>> byReleaseCode(List<String> codes) {
        Collection<Predicate<Release>> filters = new ArrayList<>();
        for (String code : codes) {
            filters.add(set -> parseToCaseInsensitiveRegex(code.trim()).matcher(set.getSetCode()).matches());
        }
        return filters;
    }
    //endregion
}
