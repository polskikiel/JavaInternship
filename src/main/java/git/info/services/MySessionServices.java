package git.info.services;

import git.info.components.MySession;
import git.info.dto.RepoDto;
import git.info.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@Service
@AllArgsConstructor
public class MySessionServices {

    private MySession mySession;

    public String getState() {
        mySession.setState(UUID.randomUUID().toString());
        return mySession.getState();
    }

    public Map<String, Integer> getMainRepoLanguages() {
        List<String > list = new ArrayList<>();

        for (RepoDto repoDto : getUser().getRepos()) {
            list.add(repoDto.getLanguage());
        }

        Map<String, Integer> map =
                list.stream().collect(Collectors.toMap(o -> o, o -> 1, Integer::sum));

        return map;
    }

    public Map<String, Integer> mergedLanguageMaps() {
        Map<String, Integer> map = new HashMap<>();

        try {
            getUser().getRepos().
                    forEach(repo -> repo.getLanguagesMap().
                            forEach((key, value) -> map.merge(key, value, Integer::sum)));

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        return map;
    }


    public boolean checkState(String state) {
        try {
            return mySession.getState().equals(state);
        } catch (NullPointerException npe) {
            return false;
        }
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
        return mySession != null && mySession.getUser() != null;
    }

}
