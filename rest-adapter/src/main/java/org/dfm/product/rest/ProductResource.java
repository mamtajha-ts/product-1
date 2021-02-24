package org.dfm.product.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.model.ProductInfo;
import org.dfm.product.domain.port.RequestProduct;

@RestController
@RequestMapping("/api/v1/products")
public class ProductResource {

  private RequestProduct requestProduct;

  public ProductResource(RequestProduct requestProduct) {
    this.requestProduct = requestProduct;
  }

  @GetMapping
  public ResponseEntity<ProductInfo> getProducts() {
    return ResponseEntity.ok(requestProduct.getProducts());
  }

  @GetMapping("/{code}")
  public ResponseEntity<Product> getProductByCode(@PathVariable Long code) {
    return ResponseEntity.ok(requestProduct.getProductByCode(code));
  }
}
