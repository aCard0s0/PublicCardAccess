package dev.pca.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.pca.dao.CardMongoDao;
import dev.pca.models.Card;
import dev.pca.services.api2pca.SearchParamsToPredicatesConverter;
import dev.pca.services.perdicates.CardPredicates;
import dev.pca.services.perdicates.UtilsPredicates;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CardService {
    // private final CardServiceDelegate cardDelegation;
    private final CardMongoDao cardDelegation;
    private final SearchParamsToPredicatesConverter paramsToPredicates;

    public CardService(CardMongoDao cardDao, SearchParamsToPredicatesConverter paramsToPredicates) {
        this.cardDelegation = cardDao;
        this.paramsToPredicates = paramsToPredicates;
    }

    public Collection<Card> insert(List<Card> cards) {
        List<Card> response = Lists.newArrayList();
        cards.forEach(c -> response.add(cardDelegation.insert(c)));
        return response;
    }

    public Optional<Collection<Card>> getAll() {
        return Optional.of(cardDelegation.getAll());
    }

    public Optional<Collection<Card>> getByCardIds(List<String> ids) {
        return cardDelegation.filterByPredicates(CardPredicates.byCardId(ids));
    }

    public Optional<Collection<Card>> getByCardCodes(List<String> codes) {
        return cardDelegation.filterByPredicates(CardPredicates.byCardCode(codes));
    }

    public Optional<Collection<Card>> filterByParams(Map<String, String> queryParams) {
        Collection<Predicate<Card>> orFilters = new ArrayList<>();
        Collection<Predicate<Card>> andFilters = new ArrayList<>();

        if (queryParams.containsKey("query")) {
            andFilters.addAll(buildQuery(queryParams.get("query")));
            queryParams.remove("query");
        }

        queryParams.entrySet().forEach(entry -> {
            List<String> values = UtilsPredicates.getPossibleMultipleParamsFromRequestParams(entry.getValue());
            if (values.size() == 1) {
                andFilters.add(paramsToPredicates.convert(entry));
            } else {
                for (String v: values) {
                    orFilters.add(paramsToPredicates.convert(Maps.immutableEntry(entry.getKey(), v)));
                }
            }
        });

        return cardDelegation.filterByPredicates(orFilters, andFilters);
    }

    private Collection<Predicate<Card>> buildQuery(String query) {
        Collection<Predicate<Card>> andFilters = new ArrayList<>();
        Matcher matcher;

        // single CardCode
        matcher = Pattern.compile("^[a-zA-Z]{3}\\d{3}").matcher(query);
        if (matcher.find()) {
            // matcher.group();
            andFilters.addAll(CardPredicates.byCardCode(Collections.singletonList(matcher.group())));
            query = query.replace(matcher.group(), "");
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
