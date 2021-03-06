package dev.pca.models;

import com.google.common.base.MoreObjects;

import java.util.Optional;

/**
 *  Some stats may appear as XX in the card.
 *  Thus, the parsing need to be made is integer values are needed.
 */
public class Stats {

    private String intellect;
    private String life;
    private String power;
    private String defense;
    private String cost;
    private String resources;

    public Stats(String intellect, String life, String power, String defense, String cost, String resources) {
        this.intellect = Optional.ofNullable(intellect).orElse("");
        this.life = Optional.ofNullable(life).orElse("");
        this.power = Optional.ofNullable(power).orElse("");
        this.defense = Optional.ofNullable(defense).orElse("");
        this.cost = Optional.ofNullable(cost).orElse("");
        this.resources = Optional.ofNullable(resources).orElse("");
    }

    public String getIntellect() {
        return intellect;
    }

    public void setIntellect(String intellect) {
        this.intellect = intellect;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getDefense() {
        return defense;
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getCost() {
        return cost;
    }

    public void setCosts(String cost) {
        this.cost = cost;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("intellect", intellect)
                .add("life", life)
                .add("power", power)
                .add("defense", defense)
                .add("cost", cost)
                .add("resources", resources)
                .toString();
    }
}
