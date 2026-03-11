package factory.optimizer.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String name;

    private Double productValue;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductComposition> compositions;
}