package git.info.controllers;

import git.info.services.GitServices;
import git.info.services.MySessionServices;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    GitServices gitServices;
    MySessionServices sessionServices;

    @GetMapping({"", "/"})
    public String getMain() {

        if(sessionServices.getToken() != null)      // if we get token already we can go straight to the user data
            return "redirect:/git2";

        //  connect with github api
        //  ask for permissions
        return "redirect:/auth";
    }

    @RequestMapping("/auth")
    public String getAuth() {
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + gitServices.getGitId() +
                "&scope=" + "repo" + "&state=" + sessionServices.getState();
    }

    @RequestMapping("/git")     // GIT CALLBACK URL
    public String getToken(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletResponse response) {

        if (!sessionServices.checkState(state) || code == null) {
            return "redirect:/errors?nr=401";
            // "If the states don't match, the request was created by a third party and the process should be aborted."
        }

        String accessToken = gitServices.getAccessToken(code);
        sessionServices.setToken(accessToken);

        Cookie cookie = new Cookie("tkn", accessToken);
        cookie.setMaxAge(3600 * 24 * 3); // 3 day in seconds

        response.addCookie(cookie);

        return "redirect:/git2";
    }

    @RequestMapping("/git2")
    public String setUser() {       // data refresh

        try {
            sessionServices.setUser(            // repos, user info
                    gitServices.getUserInfo(sessionServices.getToken()));

        } catch (Exception npe) {
            return "redirect:/errors?nr=401";
        }

        return "redirect:/info";
    }


    @GetMapping("/info")
    public String info(Model model) {
        if (!sessionServices.hasUser()) {
            return "redirect:/errors?nr=404";
        }
        model.addAttribute("user", sessionServices.getUser());

        model.addAttribute("usedLanguagesMap", sessionServices.getMainRepoLanguages());

        model.addAttribute("mergedLanguageMaps", sessionServices.mergedLanguageMaps());

        return "site";
    }

    @RequestMapping("/errors")
    public String renderErrorPage(@RequestParam("nr") Integer nr) {

        System.out.println(nr);

        // authorize again after every error
        sessionServices.setToken(null);

        return "redirect:/";
    }

}
