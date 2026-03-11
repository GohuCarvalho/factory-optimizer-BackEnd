package factory.optimizer.dto;

import lombok.Data;

@Data
public class RawMaterialResponseDto {

    private Long id;
    private String code;
    private String name;
    private Double stockQuantity;

}