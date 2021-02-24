package org.dfm.product.repository.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.repository.ProductRepository;
import org.dfm.product.repository.dao.ProductDao;

@Configuration
@EntityScan("org.dfm.product.repository.entity")
@EnableJpaRepositories("org.dfm.product.repository.dao")
public class JpaAdapterConfig {

  @Bean
  public ObtainProduct getProductRepository(ProductDao productDao) {
    return new ProductRepository(productDao);
  }
}
