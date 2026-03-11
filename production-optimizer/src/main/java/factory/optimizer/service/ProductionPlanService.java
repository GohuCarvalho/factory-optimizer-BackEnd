package factory.optimizer.service;

import factory.optimizer.dto.ProductionPlanResponseDto;
import factory.optimizer.model.Product;
import factory.optimizer.model.ProductComposition;
import factory.optimizer.model.RawMaterial;
import factory.optimizer.repository.ProductRepository;
import factory.optimizer.repository.RawMaterialRepository;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductionPlanService {

    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductionPlanService(ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository) {
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    public List<ProductionPlanResponseDto> generatePlan() {

        List<Product> products = new ArrayList<>(productRepository.findAll());
        List<RawMaterial> materials = rawMaterialRepository.findAll();

        Map<Long, Double> stock = buildStockMap(materials);

        products.sort((a, b) -> Double.compare(b.getProductValue(), a.getProductValue()));

        List<ProductionPlanResponseDto> result = new ArrayList<>();

        for (Product product : products) {

            if (product.getCompositions() == null || product.getCompositions().isEmpty()) {
                continue;
            }

            int maxUnits = calculateMaxUnits(product, stock);

            if (maxUnits > 0) {

                updateStock(product, stock, maxUnits);

                ProductionPlanResponseDto dto = new ProductionPlanResponseDto();
                dto.setProductName(product.getName());
                dto.setQuantityToProduce(maxUnits);
                dto.setTotalValue(maxUnits * product.getProductValue());

                result.add(dto);
            }
        }

        return result;
    }

    private Map<Long, Double> buildStockMap(List<RawMaterial> materials) {

        Map<Long, Double> stock = new HashMap<>();

        for (RawMaterial m : materials) {
            stock.put(m.getId(), m.getStockQuantity());
        }

        return stock;
    }

    private int calculateMaxUnits(Product product, Map<Long, Double> stock) {

        int maxUnits = Integer.MAX_VALUE;

        for (ProductComposition comp : product.getCompositions()) {

            Long materialId = comp.getRawMaterial().getId();
            Double available = stock.get(materialId);

            if (available == null) {
                return 0;
            }

            int possibleUnits = (int) (available / comp.getQuantityRequired());

            maxUnits = Math.min(maxUnits, possibleUnits);
        }

        return maxUnits;
    }

    private void updateStock(Product product, Map<Long, Double> stock, int unitsProduced) {

        for (ProductComposition comp : product.getCompositions()) {

            Long materialId = comp.getRawMaterial().getId();

            double used = comp.getQuantityRequired() * unitsProduced;

            stock.put(materialId, stock.get(materialId) - used);
        }
    }
}