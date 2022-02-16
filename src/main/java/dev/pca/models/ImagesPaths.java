package dev.pca.models;

import com.google.common.base.MoreObjects;

import java.util.List;

public class ImagesPaths {
    private String firstEdition;
    private String unlimitedEdition;
    private List<String> otherEditions;

    public ImagesPaths(String firstEdition, String unlimitedEdition, List<String> otherEditions) {
        this.firstEdition = firstEdition;
        this.unlimitedEdition = unlimitedEdition;
        this.otherEditions = otherEditions;
    }

    public String getFirstEdition() {
        return firstEdition;
    }

    public void setFirstEdition(String firstEdition) {
        this.firstEdition = firstEdition;
    }

    public String getUnlimitedEdition() {
        return unlimitedEdition;
    }

    public void setUnlimitedEdition(String unlimitedEdition) {
        this.unlimitedEdition = unlimitedEdition;
    }

    public List<String> getOtherEditions() {
        return otherEditions;
    }

    public void setOtherEditions(List<String> otherEditions) {
        this.otherEditions = otherEditions;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("firstEdition", firstEdition)
                .add("unlimitedEdition", unlimitedEdition)
                .add("otherEdition", otherEditions)
                .toString();
    }
}
