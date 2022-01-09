package dev.pca.services.predicates;

import dev.pca.models.Card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static dev.pca.services.predicates.UtilsPredicates.*;
import static java.util.Objects.nonNull;

public class CardPredicates {
    public static Predicate<Card> noFilter() {
        return Objects::nonNull;
    }

    public static Collection<Predicate<Card>> byCardId(List<String> ids) {
        Collection<Predicate<Card>> filters = new ArrayList<>();
        for(String id: ids) {
            filters.add(card -> parseToCaseSensitiveRegex(id.trim()).matcher(card.getId()).matches());
        }
        return filters;
    }

    public static Collection<Predicate<Card>> byCardCode(List<String> codes) {
        Collection<Predicate<Card>> filters = new ArrayList<>();
        for(String code: codes) {
            filters.add(card -> parseToCaseInsensitiveRegex(code.trim()).matcher(card.getCardCode()).matches());
        }
        return filters;
    }

    public static Predicate<Card> byName(String name) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(name).matcher(card.getName()).find();
    }

    public static Predicate<Card> byText(String text) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(text).matcher(card.getText()).find();
    }

    public static Predicate<Card> byFlavour(String flavour) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(flavour).matcher(card.getFlavour()).find();
    }

    public static Predicate<Card> byRarity(String rarities) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(rarities).matcher(card.getRarity()).find();
    }

    public static Predicate<Card> byType(String types) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(types).matcher(card.getType()).find();
    }

    public static Predicate<Card> byCardClass(String cardClass) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(cardClass).matcher(card.getCardClass()).find();
    }

    public static Predicate<Card> byTalent(String talent) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(talent).matcher(card.getTalent()).find();
    }

    public static Predicate<Card> bySetCode(String setCodes) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(setCodes).matcher(card.getSetCode()).find();
    }

    public static Predicate<Card> byIntellect(String intellect) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(intellect).matcher(card.getStats().getIntellect()).find();
    }

    public static Predicate<Card> byLife(String life) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(life).matcher(card.getStats().getLife()).find();
    }

    public static Predicate<Card> byPower(String power) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(power).matcher(card.getStats().getPower()).find();
    }

    public static Predicate<Card> byDefense(String defense) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(defense).matcher(card.getStats().getDefense()).find();
    }

    public static Predicate<Card> byCost(String cost) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(cost).matcher(card.getStats().getCost()).find();
    }

    public static Predicate<Card> byResources(String resources) {
        return card -> nonNull(card) && parseToCaseInsensitiveRegex(resources).matcher(card.getStats().getResource()).find();
    }

    public static Predicate<Card> byIllegalFormats(String illegalFormats) {
        return card -> {
            for (String format : card.getIllegalFormats()) {
                if (parseToCaseInsensitiveRegex(illegalFormats).matcher(format).matches()) {
                    return true;
                }
            }
            return false;
        };
    }

    public static Predicate<Card> byFrames(String frames) {
        return card -> //nonNull(card) && parseToCaseInsensitiveRegex(frames).matcher(card.getStats().getResources()).find();
        {
            for (String format : card.getFrames()) {
                if (parseToCaseInsensitiveRegex(frames).matcher(format).matches()) {
                    return true;
                }
            }
            return false;
        };
    }

    public static Predicate<Card> byPrintings(String printings) {
        return card -> {
            for (String format : card.getPrintings()) {
                if (parseToCaseInsensitiveRegex(printings).matcher(format).matches()) {
                    return true;
                }
            }
            return false;
        };
    }
}
