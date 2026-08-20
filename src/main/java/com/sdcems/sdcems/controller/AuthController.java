package com.sdcems.sdcems.controller;

import com.sdcems.sdcems.model.User;
import com.sdcems.sdcems.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repo;

    public AuthController(UserRepository repo) {
        this.repo = repo;
    }

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if(repo.findByEmail(user.getEmail()).isPresent())
            return "Email already exists";

        repo.save(user);
        return "Registered Successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestParam String email,
                    @RequestParam String password,
                    jakarta.servlet.http.HttpSession session) {

    Optional<User> existing = repo.findByEmail(email);

    if(existing.isEmpty())
        return "User not found";

    if(!existing.get().getPassword().equals(password))
        return "Wrong password";

    session.setAttribute("userId", existing.get().getId());
    return "redirect:/uploadPage";

    }
}