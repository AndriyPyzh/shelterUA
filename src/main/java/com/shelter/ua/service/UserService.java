package com.shelter.ua.service;

import com.shelter.ua.dto.PasswordDto;
import com.shelter.ua.dto.UserDto;
import com.shelter.ua.entity.User;
import com.shelter.ua.exception.UserAlreadyExistsException;
import com.shelter.ua.exception.UserNotFoundException;
import com.shelter.ua.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Transactional
    public void create(UserDto userDto) {
        Optional<User> existing = repository.findByUsername(userDto.getUsername());

        existing.ifPresent(usr -> {
            throw new UserAlreadyExistsException("User already exists");
        });

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(User.Role.ROLE_USER)
                .build();

        repository.save(user);

        log.trace("User {} was created successfully", user.getUsername());
    }

    public void updatePassword(PasswordDto passwordDto, String username) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User with such username not exists: " + username));

        user.setPassword(passwordEncoder.encode(passwordDto.getPassword()));

        repository.save(user);
    }

}
