package factory.optimizer.service;

import factory.optimizer.dto.ProductCompositionRequestDto;
import factory.optimizer.dto.ProductCompositionResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.model.ProductComposition;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.ProductCompositionRepository;
import factory.optimizer.repository.ProductRepository;
import factory.optimizer.repository.RawMaterialRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCompositionService {

    private final ProductCompositionRepository compositionRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductCompositionService(ProductCompositionRepository compositionRepository,
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {

        this.compositionRepository = compositionRepository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<ProductCompositionResponseDto> findAll() {

        return compositionRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public ProductCompositionResponseDto create(ProductCompositionRequestDto dto) {

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        RawMaterial rawMaterial = rawMaterialRepository.findById(dto.getRawMaterialId())
                .orElseThrow(() -> new RuntimeException("Raw material not found"));

        ProductComposition composition = new ProductComposition();

        composition.setProduct(product);
        composition.setRawMaterial(rawMaterial);
        composition.setQuantityRequired(dto.getQuantityRequired());

        return toResponseDto(compositionRepository.save(composition));
    }

    public void delete(Long id) {
        compositionRepository.deleteById(id);
    }

    private ProductCompositionResponseDto toResponseDto(ProductComposition composition) {

        ProductCompositionResponseDto dto = new ProductCompositionResponseDto();

        dto.setId(composition.getId());
        dto.setQuantityRequired(composition.getQuantityRequired());

        dto.setProductId(composition.getProduct().getId());
        dto.setProductName(composition.getProduct().getName());

        dto.setRawMaterialId(composition.getRawMaterial().getId());
        dto.setRawMaterialName(composition.getRawMaterial().getName());

        return dto;
    }
}