package org.dfm.product.repository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.repository.dao.ProductDao;

@SpringBootApplication
public class ProductJpaAdapterApplication {

  public static void main(String[] args) {
    SpringApplication.run(ProductJpaAdapterApplication.class, args);
  }

  @TestConfiguration
  static class ProductJpaTestConfig {

    @Bean
    public ObtainProduct getObtainProductRepository(ProductDao productDao) {
      return new ProductRepository(productDao);
    }
  }
}
