package factory.optimizer.controller;

import factory.optimizer.dto.RawProductCompositionRequestDto;
import factory.optimizer.dto.RawProductCompositionResponseDto;
import factory.optimizer.service.ProductCompositionService;

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
    public List<RawProductCompositionResponseDto> findAll() {
        return service.findAll();
    }

    @PostMapping
    public RawProductCompositionResponseDto create(
            @RequestBody RawProductCompositionRequestDto dto) {

        return service.create(dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}