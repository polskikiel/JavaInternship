package git.info.services;

import git.info.dto.AccessTokenDto;
import git.info.dto.UserDto;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GitServices {
    private RestTemplate restTemplate;

    @Autowired
    public GitServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Getter
    @Value("${git.id}")
    private String gitId;

    @Getter
    @Value("${git.secret}")
    private String gitSecret;



    public String getAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept",
                "application/vnd.github.v3+json");    // using json v3 version of GitHub api

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange("https://github.com/login/oauth/access_token?client_id=" + getGitId() +
                "&client_secret=" + getGitSecret() + "&code=" + code,
                HttpMethod.POST, httpEntity, AccessTokenDto.class).getBody().getAccess_token();

    }

}
