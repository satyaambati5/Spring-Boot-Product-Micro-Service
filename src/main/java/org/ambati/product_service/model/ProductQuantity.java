package org.ambati.product_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductQuantity {
    private Long productId;
    private Integer quantity;



    // Getters and setters
}
