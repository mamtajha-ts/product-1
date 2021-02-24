package org.dfm.product.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.dfm.product.domain.exception.ProductNotFoundException;

@RestControllerAdvice(basePackages = {"org.dfm.product"})
public class ProductExceptionHandler {

  @ExceptionHandler(value = ProductNotFoundException.class)
  public final ResponseEntity<ProductExceptionResponse> handleProductNotFoundException(
      final Exception exception, final WebRequest request) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
        ProductExceptionResponse.builder().message(exception.getMessage())
            .path(((ServletWebRequest) request).getRequest().getRequestURI()).build());
  }
}
