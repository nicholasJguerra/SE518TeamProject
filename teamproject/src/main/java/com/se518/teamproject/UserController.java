package com.se518.teamproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    private AppService appService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(AppService appService, PasswordEncoder passwordEncoder){
        this.appService = appService;
    }

    @RequestMapping(value = "/user/list", method = RequestMethod.GET)
    public String viewAllUsers(Model model) {
        List<WebUser> users = appService.getAllUsers();
        model.addAttribute("users", users);
        return "BrowseUsers";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.GET)
    public String displayRegistrationForm(Model model, WebUser webUser){
        model.addAttribute("webUser", webUser);
        return "Register";
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    public String processRegistrationForm(Model model, WebUser webUser){
        if(!isRegistrationInfoValid(model, webUser)) {
            return "Register";
        }
        String encodedPswd = passwordEncoder.encode(webUser.getPassword());
        webUser.setPassword(encodedPswd);
        WebUser wu = appService.registerUser(webUser);
        return "redirect:/user/register/confirm?id=" + wu.getId();
    }

    public boolean isRegistrationInfoValid(Model model, WebUser webUser){
        if (appService.getUserByEmail(webUser.getEmail()) != null){
            model.addAttribute("message", "User with that email already exists.");
            return false;
        }
        if(!(webUser.getEmail().contains("@"))){
            model.addAttribute("message", "Invalid email address");
            return false;
        }
        if(webUser.getFirst().isEmpty()){
            model.addAttribute("message", "First name cannot be empty.");
            return false;
        }
        if(webUser.getLast().isEmpty()){
            model.addAttribute("message", "Last name cannot be empty.");
            return false;
        }
        if (webUser.getPassword().length() < 3){
            model.addAttribute("message", "Password is not strong enough. Must be at least 3 characters in length.");
            return false;
        }
        if(!(webUser.getPassword().equals(webUser.getConfPassword()))){
            model.addAttribute("message", "Passwords do not match.");
            return false;
        }
        return true;
    }

    @RequestMapping(value = "/user/register/confirm", method = RequestMethod.GET)
    public String confirmRegistration(@RequestParam("id") int id, Model model) {
        WebUser user = appService.getRegisteredUserInfoById(id);
        model.addAttribute("user", user);
        return "ConfirmRegister";
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder){
        this.passwordEncoder = passwordEncoder;
    }
}
