package factory.optimizer.controller;

import factory.optimizer.dto.RawMaterialRequestDto;
import factory.optimizer.dto.RawMaterialResponseDto;
import factory.optimizer.service.RawMaterialService;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
@CrossOrigin(origins = "*")
public class RawMaterialController {

    private final RawMaterialService service;

    public RawMaterialController(RawMaterialService service) {
        this.service = service;
    }

    @GetMapping
    public List<RawMaterialResponseDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public RawMaterialResponseDto findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public RawMaterialResponseDto create(@Valid @RequestBody RawMaterialRequestDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public RawMaterialResponseDto update(@PathVariable Long id,
            @RequestBody RawMaterialRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}