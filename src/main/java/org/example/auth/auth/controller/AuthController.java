package org.example.auth.auth.controller;

import jakarta.validation.Valid;
import org.example.auth.auth.config.MyUserDetails;
import org.example.auth.auth.model.User;
import org.example.auth.auth.service.ServiceResult;
import org.example.auth.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        if (error != null) {
            modelAndView.addObject("error", "Invalid username or password!");
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String register(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        ServiceResult<User> result = userService.register(user);
        if (result.isSuccess()) {
            return "redirect:/login";
        } else {
            model.addAttribute("error", result.getMessage());
            return "registration";
        }
    }

    @GetMapping("/")
    public String showWelcomePage(Model model, Authentication authentication) {
        if (authentication != null && authentication.getPrincipal() instanceof MyUserDetails myUserDetails) {
            User user = myUserDetails.getUser();
            model.addAttribute("user", user);
            System.out.println("User session found: " + user.getUsername());
        } else {
            System.out.println("No user session found");
        }
        return "index";
    }
}
