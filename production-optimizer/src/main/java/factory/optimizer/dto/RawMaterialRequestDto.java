package factory.optimizer.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

@Data
public class RawMaterialRequestDto {

    private String code;

    @NotBlank(message = "Material name cannot be empty")
    private String name;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    private Double stockQuantity;

}