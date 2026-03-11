package factory.optimizer.repository;

import factory.optimizer.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawProductRepository extends JpaRepository<Product, Long> {
}