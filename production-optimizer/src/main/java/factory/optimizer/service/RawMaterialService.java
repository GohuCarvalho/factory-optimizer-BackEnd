package factory.optimizer.service;

import factory.optimizer.dto.RawMaterialRequestDto;
import factory.optimizer.dto.RawMaterialResponseDto;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.RawMaterialRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RawMaterialService {

    private final RawMaterialRepository repository;

    public RawMaterialService(RawMaterialRepository repository) {
        this.repository = repository;
    }

    public List<RawMaterialResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public RawMaterialResponseDto findById(Long id) {

        RawMaterial material = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        return toResponseDto(material);
    }

    public RawMaterialResponseDto create(RawMaterialRequestDto dto) {

        RawMaterial material = new RawMaterial();

        material.setCode("MAT-" + UUID.randomUUID().toString().substring(0, 8));
        material.setName(dto.getName());
        material.setStockQuantity(dto.getStockQuantity());

        return toResponseDto(repository.save(material));
    }

    public RawMaterialResponseDto update(Long id, RawMaterialRequestDto dto) {

        RawMaterial material = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        material.setCode(dto.getCode());
        material.setName(dto.getName());
        material.setStockQuantity(dto.getStockQuantity());

        return toResponseDto(repository.save(material));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private RawMaterialResponseDto toResponseDto(RawMaterial material) {

        RawMaterialResponseDto dto = new RawMaterialResponseDto();

        dto.setId(material.getId());
        dto.setCode(material.getCode());
        dto.setName(material.getName());
        dto.setStockQuantity(material.getStockQuantity());

        return dto;
    }
}