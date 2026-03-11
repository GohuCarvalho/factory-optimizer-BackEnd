package factory.optimizer.dto;

import lombok.Data;

@Data
public class RawProductCompositionRequestDto {

    private Long productId;
    private Long rawMaterialId;
    private Double quantityRequired;

}