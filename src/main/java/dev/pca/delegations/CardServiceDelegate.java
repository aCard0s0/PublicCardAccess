package dev.pca.delegations;


import dev.pca.models.Card;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;

public interface CardServiceDelegate {

    Card insert(Card card);

    Collection<Card> getAll();

    Optional<Collection<Card>> filterByPredicates(Collection<Predicate<Card>> orFilters);

    Optional<Collection<Card>> filterByPredicates(Collection<Predicate<Card>> orFilters, Collection<Predicate<Card>> andFilters);
}
