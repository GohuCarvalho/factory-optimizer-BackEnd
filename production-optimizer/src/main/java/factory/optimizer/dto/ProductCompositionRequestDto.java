package factory.optimizer.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class ProductCompositionRequestDto {

    @NotNull(message = "Product id is required")
    private Long productId;

    @NotNull(message = "Raw material id is required")
    private Long rawMaterialId;

    @NotNull(message = "Quantity required is mandatory")
    @Positive(message = "Quantity must be greater than zero")
    private Double quantityRequired;

}