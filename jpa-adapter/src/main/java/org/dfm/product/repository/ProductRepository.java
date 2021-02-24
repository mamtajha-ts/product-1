package org.dfm.product.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.repository.dao.ProductDao;
import org.dfm.product.repository.entity.ProductEntity;

public class ProductRepository implements ObtainProduct {

  private ProductDao productDao;

  public ProductRepository(ProductDao productDao) {
    this.productDao = productDao;
  }

  @Override
  public List<Product> getAllProducts() {
    return productDao.findAll().stream().map(ProductEntity::toModel).collect(Collectors.toList());
  }

  @Override
  public Optional<Product> getProductByCode(Long code) {
    Optional<ProductEntity> productEntity = productDao.findByCode(code);
    return productEntity.map(ProductEntity::toModel);
  }
}
