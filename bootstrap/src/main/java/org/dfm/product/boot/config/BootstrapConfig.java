package org.dfm.product.boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.dfm.product.domain.ProductDomain;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.domain.port.RequestProduct;
import org.dfm.product.repository.config.JpaAdapterConfig;

@Configuration
@Import(JpaAdapterConfig.class)
public class BootstrapConfig {

  @Bean
  public RequestProduct getRequestProduct(ObtainProduct obtainProduct) {
    return new ProductDomain(obtainProduct);
  }
}
