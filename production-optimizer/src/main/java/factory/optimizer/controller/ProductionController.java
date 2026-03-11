package factory.optimizer.controller;

import factory.optimizer.dto.ProductionPlanResponseDto;
import factory.optimizer.service.ProductionPlanService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production-plan")
public class ProductionController {

    private final ProductionPlanService productionService;

    public ProductionController(ProductionPlanService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionPlanResponseDto> generatePlan() {
        return productionService.generatePlan();
    }
}