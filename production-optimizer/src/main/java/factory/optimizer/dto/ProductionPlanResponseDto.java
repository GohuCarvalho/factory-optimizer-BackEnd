package factory.optimizer.dto;

import lombok.Data;

@Data
public class ProductionPlanResponseDto {

    private String productName;
    private Integer quantityToProduce;
    private Double totalValue;

}