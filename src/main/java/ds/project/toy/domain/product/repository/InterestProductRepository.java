package ds.project.toy.domain.product.repository;

import ds.project.toy.domain.product.entity.InterestProduct;
import ds.project.toy.domain.product.entity.Product;
import ds.project.toy.domain.user.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestProductRepository extends JpaRepository<InterestProduct, Long> {

    boolean existsByUserInfoAndProduct(UserInfo userInfo, Product product);
}
