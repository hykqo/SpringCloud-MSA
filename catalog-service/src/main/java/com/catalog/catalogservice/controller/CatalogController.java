package com.catalog.catalogservice.controller;

import com.catalog.catalogservice.entity.CatalogEntity;
import com.catalog.catalogservice.service.CatalogService;
import com.catalog.catalogservice.vo.ResponseCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
@RequiredArgsConstructor
public class CatalogController {
    private final Environment env;
    private final CatalogService catalogService;
    private final DiscoveryClient discoveryClient;


    @GetMapping("/health_check")
    public String healthCheck() {
        List<ServiceInstance> serviceList = getApplications();
        for (ServiceInstance instance : serviceList) {
            System.out.println(String.format("instanceId:%s, serviceId:%s, host:%s, scheme:%s, uri:%s",
                    instance.getInstanceId(), instance.getServiceId(), instance.getHost(), instance.getScheme(), instance.getUri()));
        }

        return String.format("It's Working in Catalog Service on LOCAL PORT %s (SERVER PORT %s)",
                env.getProperty("local.server.port"),
                env.getProperty("server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs(){
        Iterable<CatalogEntity> allCatalogs = catalogService.getAllCatalogs();
        ResponseCatalog.from(allCatalogs);
        return ResponseEntity.ok(ResponseCatalog.from(allCatalogs));
    }

    private List<ServiceInstance> getApplications() {

        List<String> services = this.discoveryClient.getServices();
        List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
        services.forEach(serviceName -> {
            this.discoveryClient.getInstances(serviceName).forEach(instance ->{
                instances.add(instance);
            });
        });
        return instances;
    }

}
