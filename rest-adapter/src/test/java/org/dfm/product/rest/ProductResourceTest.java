package org.dfm.product.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.dfm.product.domain.exception.ProductNotFoundException;
import org.dfm.product.domain.model.Product;
import org.dfm.product.domain.model.ProductInfo;
import org.dfm.product.domain.port.RequestProduct;
import org.dfm.product.rest.exception.ProductExceptionResponse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = ProductRestAdapterApplication.class, webEnvironment = RANDOM_PORT)
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class ProductResourceTest {

  private static final String LOCALHOST = "http://localhost:";
  private static final String API_URI = "/api/v1/products";
  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;
  @Autowired
  private RequestProduct requestProduct;

  @Test
  @DisplayName("should start the rest adapter application")
  public void startup() {
    assertThat(Boolean.TRUE).isTrue();
  }

  @Test
  @DisplayName("should give products when asked for products with the support of domain stub")
  public void obtainProductsFromDomainStub() {
    // Given
    Product product = Product.builder().code(1L).description("Johnny Johnny Yes Papa !!").build();
    Mockito.lenient().when(requestProduct.getProducts())
        .thenReturn(ProductInfo.builder().products(List.of(product)).build());
    // When
    String url = LOCALHOST + port + API_URI;
    ResponseEntity<ProductInfo> responseEntity = restTemplate.getForEntity(url, ProductInfo.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody().getProducts()).isNotEmpty().extracting("description")
        .contains("Johnny Johnny Yes Papa !!");
  }

  @Test
  @DisplayName("should give the product when asked for an product by code with the support of domain stub")
  public void obtainProductByCodeFromDomainStub() {
    // Given
    Long code = 1L;
    String description = "Johnny Johnny Yes Papa !!";
    Product product = Product.builder().code(code).description(description).build();
    Mockito.lenient().when(requestProduct.getProductByCode(code)).thenReturn(product);
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<Product> responseEntity = restTemplate.getForEntity(url, Product.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(product);
  }

  @Test
  @DisplayName("should give exception when asked for an product by code that does not exists with the support of domain stub")
  public void shouldGiveExceptionWhenAskedForAnProductByCodeFromDomainStub() {
    // Given
    Long code = -1000L;
    Mockito.lenient().when(requestProduct.getProductByCode(code)).thenThrow(new
        ProductNotFoundException(code));
    // When
    String url = LOCALHOST + port + API_URI + "/" + code;
    ResponseEntity<ProductExceptionResponse> responseEntity = restTemplate
        .getForEntity(url, ProductExceptionResponse.class);
    // Then
    assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(responseEntity.getBody()).isNotNull();
    assertThat(responseEntity.getBody()).isEqualTo(ProductExceptionResponse.builder()
        .message("Product with code " + code + " does not exist").path(API_URI + "/" + code)
        .build());
  }
}
