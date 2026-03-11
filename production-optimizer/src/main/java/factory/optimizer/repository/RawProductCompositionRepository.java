package factory.optimizer.repository;

import factory.optimizer.model.ProductComposition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawProductCompositionRepository extends JpaRepository<ProductComposition, Long> {

}
