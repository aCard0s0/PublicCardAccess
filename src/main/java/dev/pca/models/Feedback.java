package dev.pca.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "feedback")
public class Feedback {
    @Id
    private String id;
    @JsonProperty("email")
    private String email;
    @JsonProperty("text")
    private String text;

    public Feedback(String id, String email, String text) {
        this.id = id;
        this.email = email;
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("email", email)
                .add("text", text)
                .toString();
    }
}
