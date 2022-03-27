package com.shelter.ua.controller;

import com.shelter.ua.dto.SearchCriteria;
import com.shelter.ua.entity.Advert;
import com.shelter.ua.service.AdvertService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/adverts")
public class AdvertController {

    private final AdvertService advertService;

    @GetMapping
    public Page<Advert> getByPage(Pageable pageable,
                                  @RequestParam Map<String, String> params) {
        return advertService.getByPage(pageable, SearchCriteria.of(params));
    }

    @GetMapping("author")
    public List<Advert> getAuthorsAdverts(Principal principal) {
        return advertService.getByUsername(principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Advert create(@Valid @RequestBody Advert advert,
                         Principal principal) {
        return advertService.create(advert, principal.getName());
    }

    @PutMapping("/{id}")
    public Advert update(@PathVariable Long id,
                         @Valid @RequestBody Advert advert,
                         Principal principal) {
        return advertService.updateById(id, advert, principal.getName());
    }

}
