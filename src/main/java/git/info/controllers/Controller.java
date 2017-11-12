package git.info.controllers;

import git.info.services.GitServices;
import git.info.services.MySessionServices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    GitServices gitServices;
    MySessionServices sessionServices;

    @GetMapping({"", "/"})
    public String getInfo() {

        return "redirect:https://github.com/login/oauth/authorize?client_id=" + gitServices.getGitId() +
                "&scope=" + "repo" + "&state=" + sessionServices.getState();
    }

    @RequestMapping("/git")
    public String git() {
        return "site";
    }
}
