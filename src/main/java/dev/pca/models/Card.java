package dev.pca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cards")
public class Card {

    @Id
    private String id;
    @JsonProperty("cardCode")
    private String cardCode;
    @JsonProperty("name")
    private String name;
    @JsonProperty("text")
    private String text;
    @JsonProperty("flavour")
    private String flavour;
    @JsonProperty("rarity")
    private String rarity;
    @JsonProperty("type")
    private String type;
    @JsonProperty("cardClass")
    private String cardClass;
    @JsonProperty("talent")
    private String talent;
    @JsonProperty("setCode")
    private String setCode;
    @JsonProperty("imageApiPath")
    private String imageApiPath;
    @JsonProperty("stats")
    private Stats stats;
    @JsonProperty("illegalFormats")
    private List<String> illegalFormats;
    @JsonProperty("printings")
    private List<String> printings;
    @JsonProperty("frames")
    private List<String> frames;
    @JsonProperty("keywords")
    private List<String> keywords;

    public Card(String id, String cardCode, String name, String text, String flavour, String rarity, String type,
                String cardClass, String talent, String setCode, String imageApiPath, Stats stats,
                List<String> illegalFormats, List<String> printings, List<String> frames, List<String> keywords)
    {
        this.id = id;
        this.cardCode = cardCode;
        this.name = name;
        this.text = text;
        this.flavour = flavour;
        this.rarity = rarity;
        this.type = type;
        this.cardClass = cardClass;
        this.talent = talent;
        this.setCode = setCode;
        this.imageApiPath = imageApiPath;
        this.stats = stats;
        this.illegalFormats = illegalFormats;
        this.printings = printings;
        this.frames = frames;
        this.keywords = keywords;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavour() {
        return flavour;
    }

    public void setFlavour(String flavour) {
        this.flavour = flavour;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String cardClass) {
        this.cardClass = cardClass;
    }

    public String getTalent() {
        return talent;
    }

    public void setTalent(String talent) {
        this.talent = talent;
    }

    public String getSetCode() {
        return setCode;
    }

    public void setSetCode(String setCode) {
        this.setCode = setCode;
    }

    public String getImageApiPath() {
        return imageApiPath;
    }

    public void setImageApiPath(String imageApiPath) {
        this.imageApiPath = imageApiPath;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<String> getIllegalFormats() {
        return illegalFormats;
    }

    public void setIllegalFormats(List<String> illegalFormats) {
        this.illegalFormats = illegalFormats;
    }

    public List<String> getPrintings() {
        return printings;
    }

    public void setPrintings(List<String> printings) {
        this.printings = printings;
    }

    public List<String> getFrames() {
        return frames;
    }

    public void setFrames(List<String> frames) {
        this.frames = frames;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("cardCode", cardCode)
                .add("name", name)
                .add("text", text)
                .add("flavour", flavour)
                .add("rarity", rarity)
                .add("type", type)
                .add("cardClass", cardClass)
                .add("talent", talent)
                .add("setCode", setCode)
                .add("imageApiPath", imageApiPath)
                .add("stats", stats)
                .add("illegalFormats", illegalFormats)
                .add("printings", printings)
                .add("frames", frames)
                .add("keywords", keywords)
                .toString();
    }
}
