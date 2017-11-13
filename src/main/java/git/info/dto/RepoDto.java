package git.info.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RepoDto {
    String name;

    @JsonProperty("private")
    boolean isPrivate;

    String description;
    String html_url;

    String languages_url;
    String language;
    String created_at;

    Integer size;

    // from attributes
    Map<String, Integer> languagesMap;
}
