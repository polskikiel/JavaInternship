package git.info.controllers;

import git.info.services.GitServices;
import git.info.services.MySessionServices;
import lombok.AllArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@org.springframework.stereotype.Controller
@AllArgsConstructor
public class Controller {

    GitServices gitServices;
    MySessionServices sessionServices;

    @GetMapping({"", "/"})
    public String getAuth() {

        if(sessionServices.getToken() != null)
            return "redirect:/git2";

        //  connect with github api
        //  ask for permissions
        return "redirect:https://github.com/login/oauth/authorize?client_id=" + gitServices.getGitId() +
                "&scope=" + "repo" + "&state=" + sessionServices.getState();
    }

    @RequestMapping("/git")     // GIT CALLBACK URL
    public String getToken(@RequestParam("code") String code,
                      @RequestParam("state") String state) {

        if (!sessionServices.checkState(state) || code == null) {
            return "redirect:/errors?nr=401";
            // "If the states don't match, the request was created by a third party and the process should be aborted."
        }

        sessionServices.setToken(gitServices.getAccessToken(code));

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
        if (!sessionServices.hasUser()) {   // authorize again
            return "redirect:/errors?nr=404";
        }
        model.addAttribute("user", sessionServices.getUser());

        model.addAttribute("usedLanguagesMap", sessionServices.getMainRepoLanguages());

        model.addAttribute("mergedLanguageMaps", sessionServices.mergedLanguageMaps());

        return "site";
    }

    @RequestMapping("/errors")
    public ModelAndView renderErrorPage(@RequestParam("nr") Integer nr) {

        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg;


        // authorize again after every error
        sessionServices.setToken(null);

        switch (nr) {
            case 400: {
                errorMsg = "Http Error Code: 400. Bad Request";
                break;
            }
            case 401: {
                errorMsg = "Http Error Code: 401. Unauthorized";
                break;
            }
            case 403:{
                errorMsg = "Http Error Code: 403. Too much requests";   // from git api docs - 403 Forbidden
                break;
            }
            case 404: {
                errorMsg = "Http Error Code: 404. Resource not found";
                break;
            }
            case 500: {
                errorMsg = "Http Error Code: 500. Internal Server Error";
                break;
            }
            default:{
                errorMsg = "Ups! Something is not ok";
            }
        }
        errorPage.addObject("errorMsg", errorMsg);
        return errorPage;
    }

}
