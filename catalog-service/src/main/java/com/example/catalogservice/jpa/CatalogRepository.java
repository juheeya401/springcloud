package com.example.catalogservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CatalogRepository extends CrudRepository<CatalogEntity, Long> {
    Optional<CatalogEntity> findAllByProductId(String productId);
}
