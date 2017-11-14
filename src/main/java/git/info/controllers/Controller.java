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

        if (sessionServices.getRefresh()) { // use token from session to refresh user data
            return "redirect:/git2";
        }

        if(sessionServices.hasUser())   // if session contains user we don't need to get it from Github
            return "redirect:/info";

        return "redirect:https://github.com/login/oauth/authorize?client_id=" + gitServices.getGitId() +
                "&scope=" + "repo" + "&state=" + sessionServices.getState();
    }

    @RequestMapping("/git")     // GIT CALLBACK URL
    public String git(@RequestParam("code") String code,
                      @RequestParam("state") String state) {

        if (!sessionServices.checkState(state) || code == null) {
            return "redirect:/errors?nr=401";
            // "If the states don't match, the request was created by a third party and the process should be aborted."
        }

        sessionServices.setToken(gitServices.getAccessToken(code));

        return "redirect:/git2";
    }

    @RequestMapping("/git2")
    public String git2() {

        sessionServices.setUser(
                gitServices.getUserInfo(sessionServices.getToken()));

        return "redirect:/info";
    }


    @GetMapping("/info")    // this one in the end displays received model
    public String info(Model model) {
        if (!sessionServices.hasUser() || sessionServices.getRefresh()) {   // and go back if don't
            return "redirect:/";
        }
        model.addAttribute("user", sessionServices.getUser());

        model.addAttribute("usedLanguagesMap", sessionServices.getMainRepoLanguages());

        model.addAttribute("mergedLanguageMaps", sessionServices.mergedLanguageMaps());

        return "site";
    }

    @RequestMapping("/errors")  // custom error handling page
    public ModelAndView renderErrorPage(@RequestParam("nr") Integer nr) {

        ModelAndView errorPage = new ModelAndView("error");
        String errorMsg;

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
