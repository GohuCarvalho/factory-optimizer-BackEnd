package factory.optimizer.controller;

import factory.optimizer.dto.ProductCompositionRequestDto;
import factory.optimizer.dto.ProductCompositionResponseDto;
import factory.optimizer.service.ProductCompositionService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-compositions")
public class ProductCompositionController {

    private final ProductCompositionService service;

    public ProductCompositionController(ProductCompositionService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductCompositionResponseDto> findAll() {
        return service.findAll();
    }

    @PostMapping
    public ProductCompositionResponseDto create(
            @Valid @RequestBody ProductCompositionRequestDto dto) {

        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}