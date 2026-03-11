package factory.optimizer.controller;

import factory.optimizer.dto.ProductionPlanResponseDto;
import factory.optimizer.service.ProductionService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production-plan")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionPlanResponseDto> generatePlan() {
        return productionService.generatePlan();
    }
}