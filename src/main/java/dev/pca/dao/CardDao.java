package dev.pca.dao;

import dev.pca.models.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CardDao {
    @Value("${pca.api.maxRequestSize.cards:100}")
    private Long MAX_REQUEST_SIZE;

    private final CardRepository cardRepo;

    public CardDao(CardRepository cardRepo) {
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

    public Optional<Collection<Card>> getByPredicate(Collection<Predicate<Card>> filters) {
        return Optional.of(cardRepo.findAll().stream()
                .filter(filters.stream().reduce(Predicate::or).orElse(t->true))
                .limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList()));
    }

    public Optional<Collection<Card>> getByPredicate(Collection<Predicate<Card>> mainFilters, Collection<Predicate<Card>> filters) {
        Stream<Card> stream = cardRepo.findAll().stream();

        if (mainFilters != null && mainFilters.size() > 0) {
            stream = cardRepo.findAll().stream()
                    .filter(mainFilters.stream().reduce(Predicate::and).orElse(t -> true))
                    .collect(Collectors.toList())
                    .stream();
        }

        return Optional.of(stream
                .filter(filters.stream().reduce(Predicate::or).orElse(t->true))
                .limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList()));
    }
}
