package git.info.services;

import git.info.components.MySession;
import git.info.dto.RepoDto;
import git.info.dto.UserDto;
import git.info.util.MyMaps;
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

    public Map<String, Integer> getMainRepoLanguages() {  // count main languages in every repo
        List<String > list = new ArrayList<>();

        for (RepoDto repoDto : getUser().getRepos()) {
            list.add(repoDto.getLanguage());
        }

        // group list by language and set number of their occurrences as value

        Map<String, Integer> map =
                list.stream().collect(Collectors.toMap(o -> o, o->1, Integer::sum));


        return MyMaps.sortMapByValue(map);
    }

    public Map<String, Integer> mergedLanguageMaps() {  // each language bytes from every repo
        Map<String, Integer> map = new HashMap<>();

        try {
            getUser().getRepos().
                    forEach(repo -> repo.getLanguagesMap().
                            forEach((key, value) -> map.merge(key, value, Integer::sum)));

            //merging both maps in one, putting their sum as value

        } catch (NullPointerException npe) {
            npe.printStackTrace();
        }

        // sorting by value
        return MyMaps.sortMapByValue(map);
    }




    public boolean checkState(String state) {       // check for third party
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

    public boolean hasUser() {      // if don't - load again
        return mySession != null && mySession.getUser() != null;
    }

}
