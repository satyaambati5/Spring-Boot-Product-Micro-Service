package org.ambati.product_service.service;

import jakarta.transaction.Transactional;
import org.ambati.product_service.model.Product;
import org.ambati.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private  ProductRepository productRepository;

    @Transactional
    public Product addProduct(Product product){

        return productRepository.save(product);

    }
    @Transactional
    public List<Product> addProductBulk(List<Product> product){
        return productRepository.saveAll(product);
    }
    public void deleteProductById(Long id){
        productRepository.deleteById(id);

    }
    public List<Product> getAllProducts(){

        return productRepository.findAll();
    }
    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public List<Product> getProductsByIds(List<Long> ids) {
        return productRepository.findAllById(ids);
    }
    public Product updateProduct(Product product){
        return productRepository.save(product);
    }

    public double calculateTotalPrice(List<Product> products, List<Integer> quantities) {
        double total = 0;
        for (int i = 0; i < products.size(); i++) {
            total += products.get(i).getPrice() * quantities.get(i);
        }
        return total;
    }
}
