package org.dfm.product.repository.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.dfm.product.repository.entity.ProductEntity;

@Repository
public interface ProductDao extends JpaRepository<ProductEntity, Long> {

  Optional<ProductEntity> findByCode(Long code);
}
