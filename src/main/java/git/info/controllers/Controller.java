package git.info.controllers;

import git.info.services.GitServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    GitServices gitServices;

    @GetMapping({"", "/"})
    public String getInfo() {

        return "redirect:https://github.com/login/oauth/authorize?client_id=" + gitServices.getGitId();
    }
}
