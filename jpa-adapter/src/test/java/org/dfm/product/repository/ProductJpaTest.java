package org.dfm.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.port.ObtainProduct;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductJpaTest {

  @Autowired
  private ObtainProduct obtainProduct;

  @Test
  @DisplayName("should start the application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given products exist in database when asked should return all products from database")
  public void shouldGiveMeProductsWhenAskedGivenProductExistsInDatabase() {
    // Given from @Sql
    // When
    List<Product> products = obtainProduct.getAllProducts();
    // Then
    assertThat(products).isNotNull().extracting("description").contains("Twinkle twinkle little star");
  }

  @Test
  @DisplayName("given no products exists in database when asked should return empty")
  public void shouldGiveNoProductWhenAskedGivenProductsDoNotExistInDatabase() {
    // When
    List<Product> products = obtainProduct.getAllProducts();
    // Then
    assertThat(products).isNotNull().isEmpty();
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given products exists in database when asked for product by id should return the product")
  public void shouldGiveTheProductWhenAskedByIdGivenThatProductByThatIdExistsInDatabase() {
    // Given from @Sql
    // When
    Optional<Product> product = obtainProduct.getProductByCode(1L);
    // Then
    assertThat(product).isNotNull().isNotEmpty().get().isEqualTo(Product.builder().code(1L).description("Twinkle twinkle little star").build());
  }

  @Sql(scripts = {"/sql/data.sql"})
  @Test
  @DisplayName("given products exists in database when asked for product by id that does not exist should give empty")
  public void shouldGiveNoProductWhenAskedByIdGivenThatProductByThatIdDoesNotExistInDatabase() {
    // Given from @Sql
    // When
    Optional<Product> product = obtainProduct.getProductByCode(-1000L);
    // Then
    assertThat(product).isEmpty();
  }
}
