package factory.optimizer.service;

import factory.optimizer.dto.ProductionPlanResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.model.ProductComposition;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.ProductRepository;
import factory.optimizer.repository.RawMaterialRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProductionPlanServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductionPlanService productionPlanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should generate production plan for a single product correctly based on stock")
    void shouldGenerateProductionPlanCorrectly() {
        RawMaterial steel = createRawMaterial(1L, "Steel", 1000.0);

        Product premium = createProduct(1L, "Premium Bottle", 100.0, steel, 400.0);

        when(productRepository.findAll()).thenReturn(List.of(premium));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        List<ProductionPlanResponseDto> result = productionPlanService.generatePlan();

        assertFalse(result.isEmpty());
        assertEquals("Premium Bottle", result.get(0).getProductName());
        assertEquals(2, result.get(0).getQuantityToProduce());
        assertEquals(200.0, result.get(0).getTotalValue());
    }

    @Test
    @DisplayName("Should prioritize high-value products and fill remaining capacity with cheaper ones")
    void shouldPrioritizeMoreValuableProductsAndUseResidualStock() {

        RawMaterial steel = createRawMaterial(1L, "Steel", 1000.0);

        Product premium = createProduct(1L, "Premium Bottle", 100.0, steel, 400.0);

        Product standard = createProduct(2L, "Standard Bottle", 70.0, steel, 250.0);

        Product cup = createProduct(3L, "Small Cup", 20.0, steel, 100.0);

        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(standard, premium, cup)));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        List<ProductionPlanResponseDto> result = productionPlanService.generatePlan();

        ProductionPlanResponseDto premiumRes = findInResult(result, "Premium Bottle");
        ProductionPlanResponseDto standardRes = findInResult(result, "Standard Bottle");
        ProductionPlanResponseDto cupRes = findInResult(result, "Small Cup");

        assertEquals(2, premiumRes.getQuantityToProduce(), "Should maximize the most valuable product first");

        assertTrue(standardRes == null || standardRes.getQuantityToProduce() == 0,
                "Should not produce Standard if remaining material is insufficient");

        assertNotNull(cupRes);
        assertEquals(2, cupRes.getQuantityToProduce(), "Should use residual stock for next possible product");

        double totalValue = result.stream().mapToDouble(ProductionPlanResponseDto::getTotalValue).sum();
        assertEquals(240.0, totalValue);
    }

    private RawMaterial createRawMaterial(Long id, String name, Double stock) {
        RawMaterial rm = new RawMaterial();
        rm.setId(id);
        rm.setName(name);
        rm.setStockQuantity(stock);
        return rm;
    }

    private Product createProduct(Long id, String name, Double value, RawMaterial mat, Double qtyRequired) {
        Product p = new Product();
        p.setId(id);
        p.setName(name);
        p.setProductValue(value);

        ProductComposition comp = new ProductComposition();
        comp.setProduct(p);
        comp.setRawMaterial(mat);
        comp.setQuantityRequired(qtyRequired);

        p.setCompositions(List.of(comp));
        return p;
    }

    private ProductionPlanResponseDto findInResult(List<ProductionPlanResponseDto> result, String name) {
        return result.stream()
                .filter(r -> r.getProductName().equals(name))
                .findFirst()
                .orElse(null);
    }
}