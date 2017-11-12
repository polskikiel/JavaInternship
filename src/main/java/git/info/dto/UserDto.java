package git.info.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    String login;
    String name;
    String email;
    String avatar_url;
    String repos_url;
    String url;

    List<RepoDto> repos;
    // ^we are setting this manually, using repos_url
}
