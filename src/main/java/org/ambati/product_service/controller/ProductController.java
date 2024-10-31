package org.ambati.product_service.controller;

import org.ambati.product_service.model.Product;
import org.ambati.product_service.model.ProductQuantity;
import org.ambati.product_service.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController {
@Autowired
private ProductService productService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        HttpHeaders headers = new HttpHeaders();

        headers.add("X-Total-Count", String.valueOf(products.size()));
        if (products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> products = productService.getProductById(id);

        if (products.isEmpty()) {
            // Return 404 Not Found
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(products.get());
    }

    @PostMapping("/calculate-order")
    public String calculateOrder(@RequestBody List<ProductQuantity> productQuantities) {
        List<Long> ids = productQuantities.stream().map(ProductQuantity::getProductId).collect(Collectors.toList());
        List<Product> products = productService.getProductsByIds(ids);
        List<Integer> quantities = productQuantities.stream().map(ProductQuantity::getQuantity).collect(Collectors.toList());
        double totalPrice = productService.calculateTotalPrice(products, quantities);

        return "Total price: $" + totalPrice;
    }
    @PostMapping("/products")
    public ResponseEntity<Product> addProduct(@RequestBody Product product){
        Product newItem= productService.addProduct(product);
        return new ResponseEntity<>(newItem,HttpStatus.CREATED);
    }
    @PostMapping("/products/bulk")
    public ResponseEntity<List<Product>> addMenuItem(@RequestBody List<Product> product){
        List<Product> newBulkItem = productService.addProductBulk(product);
        return  new ResponseEntity<List<Product>>(newBulkItem,HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id){
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/products")
    public ResponseEntity<Product> updateMenuItem(@RequestBody Product product){
        Product updateItem = productService.updateProduct(product);
        return ResponseEntity.ok(updateItem);
    }
}
