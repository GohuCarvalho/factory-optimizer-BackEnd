package factory.optimizer.service;

import factory.optimizer.dto.ProductionPlanResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.model.ProductComposition;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.RawProductRepository;
import factory.optimizer.repository.RawMaterialRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductionService {

    private final RawProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductionService(RawProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {

        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<ProductionPlanResponseDto> generatePlan() {

        List<Product> products = productRepository.findAll();
        List<RawMaterial> materials = rawMaterialRepository.findAll();

        Map<Long, Double> stock = new HashMap<>();

        for (RawMaterial m : materials) {
            stock.put(m.getId(), m.getStockQuantity());
        }

        products.sort((a, b) -> Double.compare(b.getProductValue(), a.getProductValue()));

        List<ProductionPlanResponseDto> result = new ArrayList<>();

        for (Product product : products) {

            int maxUnits = Integer.MAX_VALUE;

            for (ProductComposition comp : product.getCompositions()) {

                Double available = stock.get(comp.getRawMaterial().getId());

                int possibleUnits = (int) (available / comp.getQuantityRequired());

                maxUnits = Math.min(maxUnits, possibleUnits);
            }

            if (maxUnits > 0) {

                for (ProductComposition comp : product.getCompositions()) {

                    Long materialId = comp.getRawMaterial().getId();

                    double used = comp.getQuantityRequired() * maxUnits;

                    stock.put(materialId,
                            stock.get(materialId) - used);
                }

                ProductionPlanResponseDto dto = new ProductionPlanResponseDto();

                dto.setProductName(product.getName());
                dto.setQuantityToProduce(maxUnits);
                dto.setTotalValue(maxUnits * product.getProductValue());

                result.add(dto);
            }
        }

        return result;
    }
}