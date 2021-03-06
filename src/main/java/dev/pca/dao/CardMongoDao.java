package dev.pca.dao;

import dev.pca.delegations.CardServiceDelegate;
import dev.pca.models.Card;
import dev.pca.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CardMongoDao implements CardServiceDelegate {
    @Value("${pca.api.maxRequestSize.cards:100}")
    private Long MAX_REQUEST_SIZE;

    private final CardRepository cardRepo;

    public CardMongoDao(CardRepository cardRepo) {
        this.cardRepo = cardRepo;
    }

    public Card insert(Card card) {
        return cardRepo.insert(card);
    }

    public Collection<Card> getAll() {
        return cardRepo.findAll().stream()
                .limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList());
    }

    public Optional<Collection<Card>> filterByPredicates(Collection<Predicate<Card>> orFilters) {
        return Optional.of(cardRepo.findAll().stream()
                .filter(orFilters.stream().reduce(Predicate::or).orElse(t->true))
                //.limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList()));
    }

    public Optional<Collection<Card>> filterByPredicates(Collection<Predicate<Card>> orFilters, Collection<Predicate<Card>> andFilters) {
        Stream<Card> stream = cardRepo.findAll().stream();

        if (orFilters != null && orFilters.size() > 0) {
            stream = stream.filter(orFilters.stream().reduce(Predicate::or).orElse(t -> true))
                    .collect(Collectors.toList())
                    .stream();
        }

        if (andFilters != null && andFilters.size() > 0) {
            stream = stream.filter(andFilters.stream().reduce(Predicate::and).orElse(t -> true))
                    .collect(Collectors.toList())
                    .stream();
        }

        return Optional.of(stream.collect(Collectors.toList()));
    }
}
