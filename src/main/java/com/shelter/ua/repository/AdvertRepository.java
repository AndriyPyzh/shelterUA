package com.shelter.ua.repository;

import com.shelter.ua.entity.Advert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdvertRepository extends JpaRepository<Advert, Long>,
        JpaSpecificationExecutor<Advert> {

    Optional<Advert> findByIdAndOwnerUsername(Long id, String name);

}
