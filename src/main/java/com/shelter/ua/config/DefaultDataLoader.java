package com.shelter.ua.config;

import com.shelter.ua.dto.UserDto;
import com.shelter.ua.entity.Advert;
import com.shelter.ua.service.AdvertService;
import com.shelter.ua.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DefaultDataLoader implements ApplicationListener<ApplicationReadyEvent> {
    private final UserService userService;
    private final AdvertService advertService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        if (dataAlreadyLoaded()) {
            return;
        }
        loadData();
    }

    private boolean dataAlreadyLoaded() {
        return userService.existsByUsername("andriy");
    }

    void loadData() {
        UserDto userDto = UserDto.builder()
                .username("andriy")
                .password("password")
                .build();

        userService.create(userDto);

        Advert advert = Advert.builder()
                .country("Ukraine")
                .region("Ternopil")
                .city("Buchach")
                .street("Main")
                .phoneNumber("+380672568301")
                .persons(1)
                .period("all")
                .criterias(List.of("war", "any", "men"))
                .description("some description...")
                .build();

        advertService.create(advert, userDto.getUsername());

        Advert advert1 = Advert.builder()
                .country("USA")
                .region("Ternopil")
                .city("Buchach")
                .street("Main")
                .phoneNumber("+380672568301")
                .persons(1)
                .period("all")
                .criterias(List.of("any"))
                .description("some description...")
                .build();

        advertService.create(advert1, userDto.getUsername());
    }
}

