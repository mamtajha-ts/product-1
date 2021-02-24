package org.dfm.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.dfm.product.domain.ProductDomain;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.domain.port.RequestProduct;
import org.dfm.product.repository.config.JpaAdapterConfig;

@SpringBootApplication
public class ProductE2EApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductE2EApplication.class);
  }

  @TestConfiguration
  @Import(JpaAdapterConfig.class)
  static class ProductConfig {

    @Bean
    public RequestProduct getRequestProduct(final ObtainProduct obtainProduct) {
      return new ProductDomain(obtainProduct);
    }
  }
}
