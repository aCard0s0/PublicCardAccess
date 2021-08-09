package dev.pca.models;

import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "releases")
public class Release {

    @Id
    private String id;
    private String setCode;
    private String name;
    private Date date;
    private boolean isDraftable;
    private boolean isFirstEdition;
    private List<String> cardCodes;

    public Release(String id, String setCode, String name, Date date, boolean isDraftable, boolean isFirstEdition, List<String> cardCodes) {
        this.id = id;
        this.setCode = setCode;
        this.name = name;
        this.date = date;
        this.isDraftable = isDraftable;
        this.isFirstEdition = isFirstEdition;
        this.cardCodes = cardCodes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDraftable() {
        return isDraftable;
    }

    public void setDraftable(boolean draftable) {
        isDraftable = draftable;
    }

    public boolean isFirstEdition() {
        return isFirstEdition;
    }

    public void setFirstEdition(boolean firstEdition) {
        isFirstEdition = firstEdition;
    }

    public List<String> getCardCodes() {
        return cardCodes;
    }

    public void setCardCodes(List<String> cardCodes) {
        this.cardCodes = cardCodes;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("setCode", setCode)
                .add("name", name)
                .add("date", date)
                .add("isDraftable", isDraftable)
                .add("isFirstEdition", isFirstEdition)
                .add("cardCodes", cardCodes)
                .toString();
    }
}
