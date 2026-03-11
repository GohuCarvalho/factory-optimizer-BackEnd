package factory.optimizer.service;

import factory.optimizer.dto.RawProductRequestDto;
import factory.optimizer.dto.RawProductResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.repository.RawProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final RawProductRepository repository;

    public ProductService(RawProductRepository repository) {
        this.repository = repository;
    }

    public List<RawProductResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public RawProductResponseDto findById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return toResponseDto(product);
    }

    public RawProductResponseDto create(RawProductRequestDto dto) {

        Product product = new Product();

        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setProductValue(dto.getProductValue());

        return toResponseDto(repository.save(product));
    }

    public RawProductResponseDto update(Long id, RawProductRequestDto dto) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setCode(dto.getCode());
        product.setName(dto.getName());
        product.setProductValue(dto.getProductValue());

        return toResponseDto(repository.save(product));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private RawProductResponseDto toResponseDto(Product product) {

        RawProductResponseDto dto = new RawProductResponseDto();

        dto.setId(product.getId());
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setProductValue(product.getProductValue());

        return dto;
    }
}