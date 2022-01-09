package dev.pca.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.pca.dao.CardDao;
import dev.pca.models.Card;
import dev.pca.services.predicates.CardPredicates;
import dev.pca.services.converters.SearchParamsToPredicatesConverter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static dev.pca.services.predicates.UtilsPredicates.getPossibleMultipleParamsFromRequestParams;

@Service
public class CardService {
    private final CardDao cardDao;
    private final SearchParamsToPredicatesConverter paramsToPredicates;

    public CardService(CardDao cardDao, SearchParamsToPredicatesConverter paramsToPredicates) {
        this.cardDao = cardDao;
        this.paramsToPredicates = paramsToPredicates;
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
            queryParams.remove("query");
        }

        queryParams.entrySet().forEach(entry -> {
            List<String> values = getPossibleMultipleParamsFromRequestParams(entry.getValue());
            if (values.size() == 1) {
                andFilters.add(paramsToPredicates.convert(entry));
            } else {
                for (String v: values) {
                    orFilters.add(paramsToPredicates.convert(Maps.immutableEntry(entry.getKey(), v)));
                }
            }
        });

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
