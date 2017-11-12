package git.info.services;

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

    @Value("${git.id}")
    @Getter
    private String gitId;
    @Value("${git.secret}")
    private String gitSecret;


    public void getUser() {
        System.out.println(gitId + gitSecret);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Accept",
                "application/vnd.github.v3+json");    // using json v3 version of GitHub api

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        restTemplate.exchange("https://api.github.com/applications/grants/1", HttpMethod.GET, httpEntity, UserDto.class).getBody();
        System.out.println();
    }

}
