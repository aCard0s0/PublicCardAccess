package dev.pca.services;

import com.google.common.collect.Lists;
import dev.pca.dao.CardDao;
import dev.pca.models.Card;
import dev.pca.services.predicates.CardPredicates;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        Collection<Predicate<Card>> orFilters = new ArrayList<>();
        Collection<Predicate<Card>> andFilters = new ArrayList<>();

        if (queryParams.containsKey("query")) {
            andFilters.addAll(buildQuery(queryParams.get("query")));
        }

        //region general information
        if (queryParams.containsKey("name")) {
            andFilters.add(CardPredicates.byName(queryParams.get("name")));
        }
        if (queryParams.containsKey("text")) {
            andFilters.add(CardPredicates.byText(queryParams.get("text")));
        }
        if (queryParams.containsKey("flavour")) {
            andFilters.add(CardPredicates.byFlavour(queryParams.get("flavour")));
        }
        if (queryParams.containsKey("type")) {
            andFilters.addAll(CardPredicates.byType(queryParams.get("type")));
        }
        if (queryParams.containsKey("class")) {
            andFilters.add(CardPredicates.byCardClass(queryParams.get("class")));
        }
        if (queryParams.containsKey("talent")) {
            andFilters.add(CardPredicates.byTalent(queryParams.get("talent")));
        }

        if (queryParams.containsKey("set")) {
            orFilters.addAll(CardPredicates.bySetCode(queryParams.get("set")));
        }
        if (queryParams.containsKey("rarity")) {
            andFilters.addAll(CardPredicates.byRarity(queryParams.get("rarity")));
        }
        //endregion

        //region stats
        if (queryParams.containsKey("intellect")) {
            andFilters.add(CardPredicates.byIntellect(queryParams.get("intellect")));
        }
        if (queryParams.containsKey("life")) {
            andFilters.add(CardPredicates.byLife(queryParams.get("life")));
        }
        if (queryParams.containsKey("power")) {
            andFilters.add(CardPredicates.byPower(queryParams.get("power")));
        }
        if (queryParams.containsKey("defense")) {
            andFilters.add(CardPredicates.byDefense(queryParams.get("defense")));
        }
        if (queryParams.containsKey("cost")) {
            andFilters.add(CardPredicates.byCost(queryParams.get("cost")));
        }
        if (queryParams.containsKey("resource")) {
            andFilters.add(CardPredicates.byResource(queryParams.get("resource")));
        }
        //endregion

        //region meta information
        if (queryParams.containsKey("illegalFormats")) {
            andFilters.add(CardPredicates.byIllegalFormats(queryParams.get("illegalFormats")));
        }
        if (queryParams.containsKey("frames")) {
            orFilters.add(CardPredicates.byFrames(queryParams.get("frames")));
        }
        if (queryParams.containsKey("printings")) {
            andFilters.add(CardPredicates.byPrintings(queryParams.get("printings")));
        }
        //endregion

        return cardDao.getByPredicate(orFilters, andFilters);
    }

    private Collection<Predicate<Card>> buildQuery(String query) {
        Collection<Predicate<Card>> andFilters = new ArrayList<>();
        Matcher matcher;

        // single CardCode
        matcher = Pattern.compile("^[a-zA-Z]{3}\\d{3}").matcher(query);
        if (matcher.find()) {
            // matcher.group();
            andFilters.addAll(CardPredicates.byCardCode(Collections.singletonList(matcher.group())));
        }
        /*final List<String> collect = Pattern.compile("^([a-zA-Z]{3}\\d{3})+")
                .matcher(query)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());*/

        // cardName
        matcher = Pattern.compile("\\w+\\.?").matcher(query);
        if (matcher.find()) {
            String text = matcher.group();
            andFilters.add(CardPredicates.byName(text));
            andFilters.add(CardPredicates.byText(text));
            //andFilters.add(CardPredicates.byFlavour(text));
        }

        return andFilters;
    }

}
