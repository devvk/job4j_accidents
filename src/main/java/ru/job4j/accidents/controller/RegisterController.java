package ru.job4j.accidents.controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.authority.AuthorityRepository;
import ru.job4j.accidents.repository.user.UserRepository;

@Controller
public class RegisterController {

    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public RegisterController(PasswordEncoder encoder, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registrationUser", new User());
        return "users/register";
    }

    @PostMapping("/register")
    public String createUser(@ModelAttribute User registrationUser, Model model) {
        try {
            registrationUser.setEnabled(true);
            registrationUser.setPassword(encoder.encode(registrationUser.getPassword()));
            registrationUser.setAuthority(authorityRepository.findByAuthority("ROLE_USER"));
            userRepository.save(registrationUser);
            return "redirect:/login";
        } catch (DataIntegrityViolationException e) {
            registrationUser.setPassword("");
            model.addAttribute("registrationUser", registrationUser);
            model.addAttribute("error", "Пользователь с таким логином уже существует.");
            return "users/register";
        }
    }
}
