package factory.optimizer.dto;

import lombok.Data;

@Data
public class RawMaterialRequestDto {

    private String code;
    private String name;
    private Double stockQuantity;

}