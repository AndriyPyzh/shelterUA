package com.shelter.ua.bootstrap;

import com.shelter.ua.dto.UserDto;
import com.shelter.ua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserLoader implements ApplicationListener<ApplicationReadyEvent> {
    private final UserService userService;

    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

        UserDto user = UserDto.builder()
                .username("andriy")
                .password("password")
                .build();

        userService.create(user);
    }
}

