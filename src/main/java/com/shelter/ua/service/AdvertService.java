package com.shelter.ua.service;

import com.shelter.ua.dto.SearchCriteria;
import com.shelter.ua.entity.Advert;
import com.shelter.ua.exception.AdvertNotFoundException;
import com.shelter.ua.repository.AdvertRepository;
import com.shelter.ua.repository.specification.AdvertSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final AdvertRepository advertRepository;
    private final UserService userService;

    public Advert create(Advert advert, String name) {
        advert.setOwner(userService.findByUsername(name));
        return advertRepository.save(advert);
    }

    @Transactional
    public Advert updateById(Long id, Advert advert, String name) {
        Advert foundAdvert = advertRepository.findByIdAndOwnerUsername(id, name)
                .orElseThrow(() -> new AdvertNotFoundException("Advert with such id not found: " + advert.getId()));

        foundAdvert.setCountry(advert.getCountry());
        foundAdvert.setRegion(advert.getRegion());
        foundAdvert.setPhoneNumber(advert.getPhoneNumber());
        foundAdvert.setCity(advert.getCity());
        foundAdvert.setStreet(advert.getStreet());
        foundAdvert.setPersons(advert.getPersons());
        foundAdvert.setCriterias(advert.getCriterias());
        foundAdvert.setPeriod(advert.getPeriod());
        foundAdvert.setDescription(advert.getDescription());

        return foundAdvert;
    }

    public Page<Advert> getByPage(Pageable pageable, List<SearchCriteria> searchCriteria) {
        return advertRepository.findAll(AdvertSpecification.of(searchCriteria), pageable);
    }

}
