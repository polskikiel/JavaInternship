package git.info.services;

import git.info.components.MySession;
import git.info.dto.LanguageDto;
import git.info.dto.RepoDto;
import git.info.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MySessionServices {
    MySession mySession;

    public String getState() {
        mySession.setState(UUID.randomUUID().toString());
        return mySession.getState();
    }

    public Map<String, Integer> getMostUsedLanguages() {
        List<String > list = new ArrayList<>();

        for (RepoDto repoDto : getUser().getRepos()) {
            list.add(repoDto.getLanguage());
        }

        Map<String, Integer> map =
                list.stream().collect(Collectors.toMap(o -> o, o -> 1, Integer::sum));

        return map;
    }

    public boolean checkState(String state) {
        return state != null && mySession.getState().equals(state);
    }

    public void setToken(String token) {
        mySession.setAccessToken(token);
    }

    public String getToken() {
        return mySession.getAccessToken();
    }

    public void setUser(UserDto userDto) {
        mySession.setUser(userDto);
    }

    public UserDto getUser() {
        return mySession.getUser();
    }

    public boolean hasUser() {
        return mySession.getUser() != null;
    }

}
