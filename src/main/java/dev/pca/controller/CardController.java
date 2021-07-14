package dev.pca.controller;

import dev.pca.controller.exceptions.CardNotFoundException;
import dev.pca.models.Card;
import dev.pca.service.CardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("fab/v1/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Collection<Card>> createCard(@RequestBody List<Card> cards) {
        return new ResponseEntity<>(cardService.insert(cards), HttpStatus.CREATED);
    }

    @GetMapping
    public Collection<Card> getAllCards() {
        return cardService.getAll();
    }

    @GetMapping(params = "id")
    public ResponseEntity<Card> getByCardId(@RequestParam("id") String id) {
        Optional<Card> card = cardService.getByCardId(id);
        if (card.isEmpty()) {
            throw new CardNotFoundException("Card ID: " +id+ " not found.");
        }
        return ResponseEntity.ok(card.get());
    }

    @GetMapping(params = "code")
    public ResponseEntity<Card> getByCardCode(@RequestParam("code") String code) {
        Optional<Card> card = cardService.getByCardCode(code);
        if (card.isEmpty()) {
            throw new CardNotFoundException("Card code: " +code+ " not found.");
        }
        return ResponseEntity.ok(card.get());
    }

    @GetMapping(path = "search")
    public ResponseEntity<Collection<Card>> getByCardFilter(@RequestParam Map<String, String> queryParams) {
        Optional<Collection<Card>> cards = cardService.filterBy(queryParams);
        if (cards.isEmpty()) {
            throw new CardNotFoundException("No cards matches the search parameters.");
        }
        return ResponseEntity.ok(cards.get());
    }
}
