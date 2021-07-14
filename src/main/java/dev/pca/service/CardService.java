package dev.pca.service;

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

    public Collection<Card> getAll() {
        return cardDao.getAll();
    }

    public Optional<Card> getByCardId(String id) {
        return cardDao.getById(id);
    }

    public Optional<Card> getByCardCode(String code) {
        return cardDao.getByCode(code);
    }

    public Optional<Collection<Card>> filterBy(Map<String,String> queryParams) {
        Collection<Predicate<Card>> filters = new ArrayList<>();

        if (queryParams.containsKey("code")) {
            Predicate<Card> filter = card -> queryParams.get("code").contains(card.getCardCode());
            filters.add(filter);
        }
        if (queryParams.containsKey("name")) {
            Predicate<Card> filter = card -> queryParams.get("name").contains(card.getName());
            filters.add(filter);
        }
        if (queryParams.containsKey("text")) {
            Predicate<Card> filter = card -> queryParams.get("text").contains(card.getText());
            filters.add(filter);
        }
        if (queryParams.containsKey("flavour")) {
            Predicate<Card> filter = card -> queryParams.get("flavour").contains(card.getText());
            filters.add(filter);
        }
        if (queryParams.containsKey("rarity")) {
            Predicate<Card> filter = card -> queryParams.get("rarity").contains(card.getText());
            filters.add(filter);
        }
        if (queryParams.containsKey("type")) {
            Predicate<Card> filter = card -> queryParams.get("type").contains(card.getText());
            filters.add(filter);
        }
        //region stats
        if (queryParams.containsKey("intellect")) {
            Predicate<Card> filter = card -> queryParams.get("intellect").contains(card.getStats().getIntellect());
            filters.add(filter);
        }
        if (queryParams.containsKey("life")) {
            Predicate<Card> filter = card -> queryParams.get("life").contains(card.getStats().getLife());
            filters.add(filter);
        }
        if (queryParams.containsKey("power")) {
            Predicate<Card> filter = card -> queryParams.get("power").contains(card.getStats().getPower());
            filters.add(filter);
        }
        if (queryParams.containsKey("defense")) {
            Predicate<Card> filter = card -> queryParams.get("defense").contains(card.getStats().getDefense());
            filters.add(filter);
        }
        if (queryParams.containsKey("cost")) {
            Predicate<Card> filter = card -> queryParams.get("cost").contains(card.getStats().getCost());
            filters.add(filter);
        }
        if (queryParams.containsKey("resource")) {
            Predicate<Card> filter = card -> queryParams.get("resource").contains(card.getStats().getResource());
            filters.add(filter);
        }
        //endregion
        if (queryParams.containsKey("class")) {
            Predicate<Card> filter = card -> queryParams.get("class").contains(card.getCardClass());
            filters.add(filter);
        }
        if (queryParams.containsKey("talent")) {
            Predicate<Card> filter = card -> queryParams.get("class").contains(card.getTalent());
            filters.add(filter);
        }
        if (queryParams.containsKey("set")) {
            Predicate<Card> filter = card -> queryParams.get("set").contains(card.getSetCode());
            filters.add(filter);
        }
        if (queryParams.containsKey("illegalFormats")) {
            Predicate<Card> filter = card -> {
                for (String format : card.getIllegalFormats()) {
                    return queryParams.get("illegalFormats").contains(format);
                }
                return false;
            };
            filters.add(filter);
        }
        if (queryParams.containsKey("frames")) {
            Predicate<Card> filter = card -> {
                for (String frame : card.getFrames()) {
                    return queryParams.get("frames").contains(frame);
                }
                return false;
            };
            filters.add(filter);
        }
        if (queryParams.containsKey("printings")) {
            Predicate<Card> filter = card -> {
                for (String print : card.getPrintings()) {
                    return queryParams.get("printings").contains(print);
                }
                return false;
            };
            filters.add(filter);
        }

        return cardDao.getByPredicate(filters);
    }

}
