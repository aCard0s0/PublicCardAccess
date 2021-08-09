package dev.pca.services;

import com.google.common.collect.Lists;
import dev.pca.dao.CardDao;
import dev.pca.models.Card;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
public class CardService {
    private final CardDao cardDao;

    public CardService(CardDao cardDao) {
        this.cardDao = cardDao;
    }

    public Collection<Card> insert(List<Card> cards) {
        List<Card> response = Lists.newArrayList();
        cards.forEach(c -> response.add(cardDao.insert(c)));
        return response;
    }

    public Optional<Collection<Card>> getAll() {
        return Optional.of(cardDao.getAll());
    }

    public Optional<Collection<Card>> getByCardId(List<String> ids) {
        return cardDao.getByPredicate(CardPredicates.byCardId(ids));
    }

    public Optional<Collection<Card>> getByCardCode(List<String> codes) {
        return cardDao.getByPredicate(CardPredicates.byCardCode(codes));
    }

    public Optional<Collection<Card>> filterBy(Map<String, String> queryParams) {
        Collection<Predicate<Card>> filters = new ArrayList<>();

        //region general information
        if (queryParams.containsKey("name")) {
            filters.add(CardPredicates.byName(queryParams.get("name")));
        }
        if (queryParams.containsKey("text")) {
            filters.add(CardPredicates.byText(queryParams.get("text")));
        }
        if (queryParams.containsKey("flavour")) {
            filters.add(CardPredicates.byFlavour(queryParams.get("flavour")));
        }
        if (queryParams.containsKey("rarity")) {
            filters.addAll(CardPredicates.byRarity(queryParams.get("rarity")));
        }
        if (queryParams.containsKey("type")) {
            filters.addAll(CardPredicates.byType(queryParams.get("type")));
        }
        if (queryParams.containsKey("class")) {
            filters.add(CardPredicates.byCardClass(queryParams.get("class")));
        }
        if (queryParams.containsKey("talent")) {
            filters.add(CardPredicates.byTalent(queryParams.get("talent")));
        }
        if (queryParams.containsKey("set")) {
            filters.add(CardPredicates.bySetCode(queryParams.get("set")));
        }
        //endregion

        //region stats
        if (queryParams.containsKey("intellect")) {
            filters.add(CardPredicates.byIntellect(queryParams.get("intellect")));
        }
        if (queryParams.containsKey("life")) {
            filters.add(CardPredicates.byLife(queryParams.get("life")));
        }
        if (queryParams.containsKey("power")) {
            filters.add(CardPredicates.byPower(queryParams.get("power")));
        }
        if (queryParams.containsKey("defense")) {
            filters.add(CardPredicates.byDefense(queryParams.get("defense")));
        }
        if (queryParams.containsKey("cost")) {
            filters.add(CardPredicates.byCost(queryParams.get("cost")));
        }
        if (queryParams.containsKey("resource")) {
            filters.add(CardPredicates.byResource(queryParams.get("resource")));
        }
        //endregion

        //region meta information
        if (queryParams.containsKey("illegalFormats")) {
            filters.add(CardPredicates.byIllegalFormats(queryParams.get("illegalFormats")));
        }
        if (queryParams.containsKey("frames")) {
            filters.add(CardPredicates.byFrames(queryParams.get("frames")));
        }
        if (queryParams.containsKey("printings")) {
            filters.add(CardPredicates.byPrintings(queryParams.get("printings")));
        }
        //endregion

        return cardDao.getByPredicate(filters);
    }

}
