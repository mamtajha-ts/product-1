package org.dfm.product;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.dfm.product.domain.ProductDomain;
import org.dfm.product.domain.exception.ProductNotFoundException;
import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.model.ProductInfo;
import org.dfm.product.domain.port.ObtainProduct;
import org.dfm.product.domain.port.RequestProduct;

@ExtendWith(MockitoExtension.class)
public class AcceptanceTest {

  @Test
  @DisplayName("should be able to get products when asked for products from hard coded products")
  public void getProductsFromHardCoded() {
  /*
      RequestProduct    - left side port
      ProductDomain     - hexagon (domain)
      ObtainProduct     - right side port
   */
    RequestProduct requestProduct = new ProductDomain(); // the product is hard coded
    ProductInfo productInfo = requestProduct.getProducts();
    assertThat(productInfo).isNotNull();
    assertThat(productInfo.getProducts()).isNotEmpty().extracting("description")
        .contains(
            "If you could read a leaf or tree\r\nyoud have no need of books.\r\n-- Alistair Cockburn (1987)");
  }

  @Test
  @DisplayName("should be able to get products when asked for products from stub")
  public void getProductsFromMockedStub(@Mock ObtainProduct obtainProduct) {
    // Stub
    Product product = Product.builder().code(1L).description(
        "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)")
        .build();
    Mockito.lenient().when(obtainProduct.getAllProducts()).thenReturn(List.of(product));
    // hexagon
    RequestProduct requestProduct = new ProductDomain(obtainProduct);
    ProductInfo productInfo = requestProduct.getProducts();
    assertThat(productInfo).isNotNull();
    assertThat(productInfo.getProducts()).isNotEmpty().extracting("description")
        .contains(
            "I want to sleep\r\nSwat the flies\r\nSoftly, please.\r\n\r\n-- Masaoka Shiki (1867-1902)");
  }

  @Test
  @DisplayName("should be able to get product when asked for product by id from stub")
  public void getProductByIdFromMockedStub(@Mock ObtainProduct obtainProduct) {
    // Given
    // Stub
    Long code = 1L;
    String description = "I want to sleep\\r\\nSwat the flies\\r\\nSoftly, please.\\r\\n\\r\\n-- Masaoka Shiki (1867-1902)";
    Product expectedProduct = Product.builder().code(code).description(description).build();
    Mockito.lenient().when(obtainProduct.getProductByCode(code))
        .thenReturn(Optional.of(expectedProduct));
    // When
    RequestProduct requestProduct = new ProductDomain(obtainProduct);
    Product actualProduct = requestProduct.getProductByCode(code);
    assertThat(actualProduct).isNotNull().isEqualTo(expectedProduct);
  }

  @Test
  @DisplayName("should throw exception when asked for product by id that does not exists from stub")
  public void getExceptionWhenAskedProductByIdThatDoesNotExist(@Mock ObtainProduct obtainProduct) {
    // Given
    // Stub
    Long code = -1000L;
    Mockito.lenient().when(obtainProduct.getProductByCode(code)).thenReturn(Optional.empty());
    // When
    RequestProduct requestProduct = new ProductDomain(obtainProduct);
    // Then
    assertThatThrownBy(() -> requestProduct.getProductByCode(code)).isInstanceOf(
        ProductNotFoundException.class)
        .hasMessageContaining("Product with code " + code + " does not exist");
  }
}
