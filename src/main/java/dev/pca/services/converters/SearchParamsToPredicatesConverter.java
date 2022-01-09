package dev.pca.services.converters;

import com.google.common.base.Converter;
import dev.pca.models.Card;
import dev.pca.models.converters.SiglaFrameConverter;
import dev.pca.services.predicates.CardPredicates;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Predicate;

@Service
public class SearchParamsToPredicatesConverter extends Converter<Map.Entry<String, String>, Predicate<Card>> {
    private final SiglaFrameConverter siglaFrame;

    public SearchParamsToPredicatesConverter(SiglaFrameConverter siglaFrame) {
        this.siglaFrame = siglaFrame;
    }

    @Override
    protected Predicate<Card> doForward(Map.Entry<String, String> queryParams) {
        switch (queryParams.getKey()) {
            /*case "id":
                return CardPredicates.byCardId(queryParams.getValue());
            case "code":
                return CardPredicates.byCardCode(queryParams.getValue());*/
            case "name":
                return CardPredicates.byName(queryParams.getValue());
            case "text":
                return CardPredicates.byText(queryParams.getValue());
            case "flavour":
                return CardPredicates.byFlavour(queryParams.getValue());
            case "type":
                return CardPredicates.byType(queryParams.getValue());
            case "class":
                return CardPredicates.byCardClass(queryParams.getValue());
            case "talent":
                return CardPredicates.byTalent(queryParams.getValue());
            case "set":
                return CardPredicates.bySetCode(queryParams.getValue());
            case "rarity":
                return CardPredicates.byRarity(queryParams.getValue());
            case "intellect":
                return CardPredicates.byIntellect(queryParams.getValue());
            case "life":
                return CardPredicates.byLife(queryParams.getValue());
            case "power":
                return CardPredicates.byPower(queryParams.getValue());
            case "defense":
                return CardPredicates.byDefense(queryParams.getValue());
            case "cost":
                return CardPredicates.byCost(queryParams.getValue());
            case "resource":
                return CardPredicates.byResources(queryParams.getValue());
            case "illegalFormats":
                return CardPredicates.byIllegalFormats(queryParams.getValue());
            case "frame":
                return CardPredicates.byFrames(siglaFrame.convert(queryParams.getValue()));
            case "printings":
                return CardPredicates.byPrintings(queryParams.getValue());
            default:
                return CardPredicates.noFilter();
        }
    }

    @Override
    protected Map.Entry<String, String> doBackward(Predicate<Card> cardPredicate) {
        return null;
    }

}
