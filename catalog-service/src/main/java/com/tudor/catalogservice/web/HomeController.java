package com.tudor.catalogservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tudor.catalogservice.config.CatalogProperties;

@RestController
public class HomeController {

    private final CatalogProperties catalogProperties;
    
    public HomeController(CatalogProperties catalogProperties) {
        this.catalogProperties = catalogProperties;
    }

    @GetMapping("/")
    public String getGreetings() {
        return catalogProperties.getGreeting();
    }
}
