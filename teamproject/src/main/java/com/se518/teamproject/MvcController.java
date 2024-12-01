package com.se518.teamproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.security.Principal;

@Controller
public class MvcController {
    private AppService appService;

    @Autowired
    public MvcController(AppService appService){
        this.appService = appService;
    }
    @RequestMapping(value = "/landing", method = RequestMethod.GET)
    public String landing(Model model, Principal principal){
        model.addAttribute("name", (principal.getName()));
        return "LandingPage";
    }
}
