package git.info.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import git.info.dto.AccessTokenDto;
import git.info.dto.RepoDto;
import git.info.dto.UserDto;
import git.info.util.MyJson;
import lombok.Getter;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<AccessTokenDto> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange("https://github.com/login/oauth/access_token?client_id=" + getGitId() +
                        "&client_secret=" + getGitSecret() + "&code=" + code,
                HttpMethod.POST, httpEntity, AccessTokenDto.class).getBody().getAccess_token();

        // we can also get requested permission scopes and type of the token (from AccessTokenDto)
    }

    public UserDto getUserInfo(String token) {
        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", "token " + token);
        // sending auth token by header - cleaner solution

        HttpEntity<UserDto> httpEntity = new HttpEntity<>(headers);
        UserDto userDto = restTemplate.exchange
                ("https://api.github.com/user", HttpMethod.GET, httpEntity, UserDto.class).getBody();



        HttpEntity<RepoDto> repoDtoHttpEntity = new HttpEntity<>(headers);
        List<RepoDto> repoDtos = restTemplate.exchange(userDto.getRepos_url(), HttpMethod.GET, repoDtoHttpEntity,
                new ParameterizedTypeReference<List<RepoDto>>() {
                }).getBody();



        for (RepoDto repoDto : repoDtos) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(repoDto.getLanguages_url());
                URLConnection urlConnection = url.openConnection();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream(), "UTF-8"
                ));

                String input;

                while ((input = bufferedReader.readLine()) != null) {
                    sb.append(input);
                }

                JSONObject object = new JSONObject(sb.toString());

                Map<String, Integer> map = MyJson.toMap(object);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        userDto.setRepos(repoDtos);




        return userDto;
    }


}
