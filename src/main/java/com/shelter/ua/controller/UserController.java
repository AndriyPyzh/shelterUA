package com.shelter.ua.controller;

import com.shelter.ua.dto.PasswordDto;
import com.shelter.ua.dto.UserDto;
import com.shelter.ua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void create(@Valid @RequestBody UserDto dto) {
        userService.create(dto);
    }

    @GetMapping("/current")
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody PasswordDto passwordDto, @PathVariable String username) {
        userService.updatePassword(passwordDto, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
