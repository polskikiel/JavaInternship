package git.info.services;

import git.info.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
public class GitServices {
    private RestTemplate restTemplate;

    public void getUser() {
        restTemplate.exchange("https://api.github.com/applications/grants/1", HttpMethod.GET, HttpEntity.EMPTY, UserDto.class).getBody();
        System.out.println();
    }

}
