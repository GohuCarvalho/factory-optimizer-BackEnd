package factory.optimizer.service;

import factory.optimizer.dto.ProductionPlanResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.model.ProductComposition;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.ProductRepository;
import factory.optimizer.repository.RawMaterialRepository;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductionPlanServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private RawMaterialRepository rawMaterialRepository;

    @InjectMocks
    private ProductionPlanService productionPlanService;

    public ProductionPlanServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGenerateProductionPlanCorrectly() {

        // ===== GIVEN =====

        RawMaterial steel = new RawMaterial();
        steel.setId(1L);
        steel.setName("Steel");
        steel.setStockQuantity(1000.0);

        Product premium = new Product();
        premium.setId(1L);
        premium.setName("Premium Bottle");
        premium.setProductValue(100.0);

        ProductComposition comp = new ProductComposition();
        comp.setProduct(premium);
        comp.setRawMaterial(steel);
        comp.setQuantityRequired(400.0);

        premium.setCompositions(List.of(comp));

        // simulando retorno do banco
        when(productRepository.findAll()).thenReturn(List.of(premium));
        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        // ===== WHEN =====

        var result = productionPlanService.generatePlan();

        // ===== THEN =====

        assertEquals(1, result.size());

        assertEquals("Premium Bottle", result.get(0).getProductName());

        assertEquals(2, result.get(0).getQuantityToProduce());

        assertEquals(200.0, result.get(0).getTotalValue());
    }

    @Test
    void shouldPrioritizeMoreValuableProducts() {

        RawMaterial steel = new RawMaterial();
        steel.setId(1L);
        steel.setStockQuantity(1000.0);

        Product premium = new Product();
        premium.setId(1L);
        premium.setName("Premium Bottle");
        premium.setProductValue(100.0);

        Product standard = new Product();
        standard.setId(2L);
        standard.setName("Standard Bottle");
        standard.setProductValue(70.0);

        Product cup = new Product();
        cup.setId(3L);
        cup.setName("Small Cup");
        cup.setProductValue(20.0);

        ProductComposition compPremium = new ProductComposition();
        compPremium.setProduct(premium);
        compPremium.setRawMaterial(steel);
        compPremium.setQuantityRequired(400.0);

        ProductComposition compStandard = new ProductComposition();
        compStandard.setProduct(standard);
        compStandard.setRawMaterial(steel);
        compStandard.setQuantityRequired(250.0);

        ProductComposition compCup = new ProductComposition();
        compCup.setProduct(cup);
        compCup.setRawMaterial(steel);
        compCup.setQuantityRequired(100.0);

        premium.setCompositions(List.of(compPremium));
        standard.setCompositions(List.of(compStandard));
        cup.setCompositions(List.of(compCup));

        when(productRepository.findAll()).thenReturn(
                new ArrayList<>(List.of(premium, standard, cup)));

        when(rawMaterialRepository.findAll()).thenReturn(List.of(steel));

        List<ProductionPlanResponseDto> result = productionPlanService.generatePlan();

        assertFalse(result.isEmpty());
    }

}
