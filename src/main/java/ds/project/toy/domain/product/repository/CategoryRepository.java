package ds.project.toy.domain.product.repository;

import ds.project.toy.domain.product.entity.Category;
import ds.project.toy.domain.product.vo.CategoryState;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByCategoryState(CategoryState categoryState);
}
