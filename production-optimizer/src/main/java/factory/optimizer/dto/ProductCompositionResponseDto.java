package factory.optimizer.dto;

import lombok.Data;

@Data
public class ProductCompositionResponseDto {

    private Long id;
    private Double quantityRequired;

    private Long productId;
    private Long rawMaterialId;

    private String productName;
    private String rawMaterialName;

}