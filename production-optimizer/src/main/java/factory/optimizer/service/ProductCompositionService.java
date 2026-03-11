package factory.optimizer.service;

import factory.optimizer.dto.RawProductCompositionRequestDto;
import factory.optimizer.dto.RawProductCompositionResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.model.ProductComposition;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.RawProductCompositionRepository;
import factory.optimizer.repository.RawProductRepository;
import factory.optimizer.repository.RawMaterialRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCompositionService {

    private final RawProductCompositionRepository compositionRepository;
    private final RawProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductCompositionService(RawProductCompositionRepository compositionRepository,
            RawProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {

        this.compositionRepository = compositionRepository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<RawProductCompositionResponseDto> findAll() {

        return compositionRepository.findAll()
                .stream()
                .map(this::toResponseDto)
                .collect(Collectors.toList());
    }

    public RawProductCompositionResponseDto create(RawProductCompositionRequestDto dto) {

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

    private RawProductCompositionResponseDto toResponseDto(ProductComposition composition) {

        RawProductCompositionResponseDto dto = new RawProductCompositionResponseDto();

        dto.setId(composition.getId());
        dto.setQuantityRequired(composition.getQuantityRequired());

        dto.setProductId(composition.getProduct().getId());
        dto.setProductName(composition.getProduct().getName());

        dto.setRawMaterialId(composition.getRawMaterial().getId());
        dto.setRawMaterialName(composition.getRawMaterial().getName());

        return dto;
    }
}