package com.catalog.catalogservice.service;

import com.catalog.catalogservice.entity.CatalogEntity;
import com.catalog.catalogservice.repository.CatalogRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogServiceImpl implements CatalogService {

    private final CatalogRespository catalogRespository;

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRespository.findAll();
    }
}
