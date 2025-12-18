package com.catalog.catalogservice.vo;

import com.catalog.catalogservice.entity.CatalogEntity;
import jakarta.persistence.Column;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public record ResponseCatalog(
        String productId,
        String productName,
        Integer stock,
        Integer unitPrice
) {

    public static ResponseCatalog of(CatalogEntity catalogEntity) {
        return ResponseCatalog.builder()
                .productId(catalogEntity.getProductId())
                .productName(catalogEntity.getProductName())
                .stock(catalogEntity.getStock())
                .unitPrice(catalogEntity.getUnitPrice())
                .build();
    }

    public static List<ResponseCatalog> from(Iterable<CatalogEntity> catalogEntities) {
        List<ResponseCatalog> from = new ArrayList<>();
        catalogEntities.forEach(c -> from.add(ResponseCatalog.of(c)));
        return from;
    }
}
