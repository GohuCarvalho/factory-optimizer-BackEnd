package factory.optimizer.dto;

import lombok.Data;

@Data
public class ProductResponseDto {

    private Long id;
    private String code;
    private String name;
    private Double productValue;

}