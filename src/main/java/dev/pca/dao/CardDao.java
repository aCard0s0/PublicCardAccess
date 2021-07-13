package dev.pca.dao;

import dev.pca.models.Card;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public Optional<Card> getById(String id) {
        return cardRepo.findById(id);
    }

    public Optional<Card> getByCode(String code) {
        return cardRepo.findAll().stream().parallel()
                .filter(card -> card.getCardCode().equals(code)).findFirst();
    }

    public Optional<Collection<Card>> getByPredicate(Collection<Predicate<Card>> filters) {
        return Optional.of(cardRepo.findAll().stream()
                .filter(filters.stream().reduce(Predicate::and).orElse(t->true))
                .limit(MAX_REQUEST_SIZE)
                .collect(Collectors.toList()));
    }
}
