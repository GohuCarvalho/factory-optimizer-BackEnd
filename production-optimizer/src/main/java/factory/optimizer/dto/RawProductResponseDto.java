package factory.optimizer.dto;

import lombok.Data;

@Data
public class RawProductResponseDto {

    private Long id;
    private String code;
    private String name;
    private Double productValue;

}