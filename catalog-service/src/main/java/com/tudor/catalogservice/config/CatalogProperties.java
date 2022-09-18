package com.tudor.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "catalog")
public class CatalogProperties {
  /**
   * A message to welcome users.
   */
  private String greeting;
  public String getGreeting() {
    return greeting;
  }
  public void setGreeting(String greeting) {
    this.greeting = greeting;
  }
}
