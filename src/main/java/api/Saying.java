package api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Saying {
    private String content;

    public Saying() {
        // Jackson deserialization
    }

    public Saying(String content) {
        this.content = content;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("content", content)
                .toString();
    }
}
