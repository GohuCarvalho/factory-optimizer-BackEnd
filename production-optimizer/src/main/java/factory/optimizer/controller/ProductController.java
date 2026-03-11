package factory.optimizer.controller;

import factory.optimizer.dto.RawProductRequestDto;
import factory.optimizer.dto.RawProductResponseDto;
import factory.optimizer.service.ProductService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<RawProductResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RawProductResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public RawProductResponseDto create(@RequestBody RawProductRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RawProductResponseDto update(@PathVariable Long id,
            @RequestBody RawProductRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}