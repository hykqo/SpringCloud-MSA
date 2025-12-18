package com.catalog.catalogservice.repository;

import com.catalog.catalogservice.entity.CatalogEntity;
import org.springframework.data.repository.CrudRepository;

public interface CatalogRespository extends CrudRepository<CatalogEntity, Long> {
}
