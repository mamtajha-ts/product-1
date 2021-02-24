package org.dfm.product.domain.port;

import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.model.ProductInfo;

public interface RequestProduct {

  ProductInfo getProducts();
  Product getProductByCode(Long code);
}
