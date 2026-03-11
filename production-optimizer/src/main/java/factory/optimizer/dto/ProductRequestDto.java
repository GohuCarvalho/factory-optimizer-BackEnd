package factory.optimizer.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class ProductRequestDto {

    private String code;

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotNull(message = "Product value is required")
    @Positive(message = "Product value must be greater than zero")
    private Double productValue;
}