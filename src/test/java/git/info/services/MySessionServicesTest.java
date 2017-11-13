package git.info.services;

import git.info.components.MySession;
import git.info.dto.RepoDto;
import git.info.dto.UserDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MySessionServicesTest {        // some example tests
    @Autowired
    MySessionServices services;

    @Test
    public void getState() throws Exception {
        Assert.assertFalse(services.getState().equals(services.getState()));    // they are different every time
    }

    @Test
    public void getMainRepoLanguages() throws Exception {
        UserDto userDto = new UserDto();

        List<RepoDto> list = new ArrayList<>();
        RepoDto repoDto = new RepoDto();
        RepoDto repoDto1 = new RepoDto();
        RepoDto repoDto2 = new RepoDto();

        repoDto.setLanguage("Java");
        repoDto1.setLanguage("Java");
        repoDto2.setLanguage("Scala");

        list.add(repoDto);
        list.add(repoDto1);
        list.add(repoDto2);

        userDto.setRepos(list);
        services.setUser(userDto);

        Map<String, Integer> map = services.getMainRepoLanguages();

        Assert.assertTrue(map.size() == 2);
        Assert.assertTrue(map.get("Java") == 2);
        Assert.assertTrue(map.get("Scala") == 1);

    }

    @Test
    public void mergedLanguageMaps() throws Exception {
        UserDto userDto = new UserDto();

        List<RepoDto> list = new ArrayList<>();
        RepoDto repoDto = new RepoDto();
        RepoDto repoDto1 = new RepoDto();
        RepoDto repoDto2 = new RepoDto();
        Map<String, Integer> map = new HashMap<>();

        map.put("Java", 1000);

        repoDto.setLanguagesMap(map);
        repoDto1.setLanguagesMap(map);
        repoDto2.setLanguagesMap(map);


        list.add(repoDto);
        list.add(repoDto1);
        list.add(repoDto2);

        userDto.setRepos(list);
        services.setUser(userDto);

        map = services.mergedLanguageMaps();

        Assert.assertTrue(map.size() == 1);
        Assert.assertTrue(map.get("Java") == 3000);

    }

    @Test
    public void checkState() throws Exception {
        MySession mySession = new MySession();
        mySession.setState("somestate");
        services.setMySession(mySession);

        Assert.assertTrue(services.checkState("somestate"));

    }

}