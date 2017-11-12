package git.info.services;

import git.info.components.MySession;
import git.info.dto.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class MySessionServices {
    MySession mySession;

    public String getState() {
        mySession.setState(UUID.randomUUID().toString());
        System.out.println(mySession.getState());
        return mySession.getState();
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
