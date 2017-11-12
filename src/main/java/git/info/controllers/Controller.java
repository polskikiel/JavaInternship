package git.info.controllers;

import git.info.services.GitServices;
import git.info.services.MySessionServices;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    GitServices gitServices;
    MySessionServices sessionServices;

    @GetMapping({"", "/"})
    public String getAuth() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + gitServices.getGitId() +
                "&scope=" + "repo" + "&state=" + sessionServices.getState();
    }

    @RequestMapping("/git")
    public String git(@RequestParam("code") String code,
                      @RequestParam("state") String state) {

        if (!sessionServices.checkState(state) || code == null) {
            return "redirect:/error";
            // "If the states don't match, the request was created by a third party and the process should be aborted."
        }

        sessionServices.setToken(gitServices.getAccessToken(code));

        return "redirect:/info";
    }

    @GetMapping("/info")
    public String info(Model model) {

        System.out.println(sessionServices.getToken());
        return "site";
    }

    @RequestMapping("/error")
    public String error(Model model) {
        model.addAttribute("error", "Auth failure - try login again");
        return "error";
    }
}
