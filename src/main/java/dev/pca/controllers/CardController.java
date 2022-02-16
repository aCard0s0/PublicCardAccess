package dev.pca.controllers;

import dev.pca.controllers.exceptions.CardNotFoundException;
import dev.pca.models.Card;
import dev.pca.services.CardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

@RestController
@RequestMapping(path="v0/fab/cards", produces = MediaType.APPLICATION_JSON_VALUE)
public class CardController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CardController.class);

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        checkArgument(cardService != null, "cardService cannot be null.");
        this.cardService = cardService;
    }

    @PostMapping
    public ResponseEntity<Collection<Card>> createCard(@RequestBody List<Card> cards) {
        LOGGER.debug("operation='createCard', message='{}'", cards);
        return new ResponseEntity<>(cardService.insert(cards), HttpStatus.CREATED);
    }

    /*@GetMapping
    public ResponseEntity<Collection<Card>> getAllCards() {
        LOGGER.debug("operation='getAllCards', message=''");
        return buildCollectionResponse(cardService.getAll(), "Empty DB");
    }*/

    @GetMapping(params = "ids")
    public ResponseEntity<Collection<Card>> getByCardId(@RequestParam List<String> ids) {
        LOGGER.debug("operation='getByCardId', message='{}'", ids);
        Optional<Collection<Card>> card = cardService.getByCardIds(ids);
        return buildCollectionResponse(card, String.format("Card ID: %s not found.", ids));
    }

    @GetMapping(params = "codes")
    public ResponseEntity<Collection<Card>> getByCardCodes(@RequestParam List<String> codes) {
        LOGGER.debug("operation='getByCardCodes', message='{}'", codes);
        Optional<Collection<Card>> cards = cardService.getByCardCodes(codes);
        return buildCollectionResponse(cards, String.format("Card code: %s not found.", codes));
    }

    @GetMapping(path = "search")
    public ResponseEntity<Collection<Card>> search(@RequestParam Map<String, String> queryParams) {
        LOGGER.debug("operation='search', message='{}'", queryParams);
        Optional<Collection<Card>> cards = cardService.filterByParams(queryParams);
        return buildCollectionResponse(cards, String.format("No cards matches the search parameters."));
    }

    private ResponseEntity<Collection<Card>> buildCollectionResponse(Optional<Collection<Card>> cards, String errorMessage) {
        if (cards.isEmpty()) {
            throw new CardNotFoundException(errorMessage);
        }
        return ResponseEntity.ok(cards.get());
    }
}
