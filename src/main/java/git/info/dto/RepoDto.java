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
    boolean priv;

    String description;
    String html_url;

    String languages_url;
    String language;
    String created_at;

    Integer size;

    // getting it manually from languages_url
    Map<String, Integer> languagesMap;
}
