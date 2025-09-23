package com.example.productcatalog;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@RestController
@RequestMapping("/products")
public class ProductController {
    private final Map products = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    public ProductController() {
        products.put(counter.incrementAndGet(),
                new Product(1L, "Laptop", 1200.50,"2025-09-14"));
        products.put(counter.incrementAndGet(),
                new Product(2L, "Smartphone", 800.00, "2025-09-17"));
    }

    // GET /products - отримати продукти
    @GetMapping
    public List getAllProducts() {
        return new ArrayList<>(products.values());
    }

    // GET /products/{id} - отримати продукт за ID
    @GetMapping("/{id}")
    public ResponseEntity getProductById(@PathVariable long id) {
        Product product = (Product) products.get(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // POST /products - створити новий продукт
    @PostMapping
    public ResponseEntity createProduct(@RequestBody Product product) {
        long newId = counter.incrementAndGet();
        product.setId(newId);
        products.put(newId, product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    // PUT /products/{id} - оновити існуючий продукт
    @PutMapping("/{id}")
    public ResponseEntity updateProduct(
            @PathVariable long id,
            @RequestBody Product updatedProduct) {
        if (!products.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }
        updatedProduct.setId(id);
        products.put(id, updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    // DELETE /products/{id} - видалити продукт
    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable long id) {
        if (products.remove(id) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
