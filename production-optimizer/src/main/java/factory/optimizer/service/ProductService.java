package factory.optimizer.service;

import factory.optimizer.dto.ProductRequestDto;
import factory.optimizer.dto.ProductResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<ProductResponseDto> findAll() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ProductResponseDto findById(Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return toResponseDto(product);
    }

    public ProductResponseDto create(ProductRequestDto dto) {

        Product product = new Product();

        product.setCode("PROD-" + UUID.randomUUID().toString().substring(0, 8));
        product.setName(dto.getName());
        product.setProductValue(dto.getProductValue());

        return toResponseDto(repository.save(product));
    }

    public ProductResponseDto update(Long id, ProductRequestDto dto) {

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

    private ProductResponseDto toResponseDto(Product product) {

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setCode(product.getCode());
        dto.setName(product.getName());
        dto.setProductValue(product.getProductValue());

        return dto;
    }
}