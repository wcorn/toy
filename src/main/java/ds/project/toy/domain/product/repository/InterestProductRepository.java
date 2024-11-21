package ds.project.toy.domain.product.repository;

import ds.project.toy.domain.product.entity.InterestProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

}
