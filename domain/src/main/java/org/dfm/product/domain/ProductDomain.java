package org.dfm.product.domain;

import java.util.Optional;
import org.dfm.product.domain.exception.ProductNotFoundException;
import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.model.ProductInfo;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.domain.port.RequestProduct;

public class ProductDomain implements RequestProduct {

  private ObtainProduct obtainProduct;

  public ProductDomain() {
    this(new ObtainProduct() {
    });
  }

  public ProductDomain(ObtainProduct obtainProduct) {
    this.obtainProduct = obtainProduct;
  }

  @Override
  public ProductInfo getProducts() {
    return ProductInfo.builder().products(obtainProduct.getAllProducts()).build();
  }

  @Override
  public Product getProductByCode(Long code) {
    Optional<Product> product = obtainProduct.getProductByCode(code);
    return product.orElseThrow(() -> new ProductNotFoundException(code));
  }
}
