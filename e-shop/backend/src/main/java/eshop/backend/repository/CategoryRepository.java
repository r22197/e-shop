package eshop.backend.repository;

import eshop.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.name=:name and c.parentCategory.name=:parentCategoryName")
    public Category findCategoryByNameAndParent(
            @Param("name") String name,
            @Param("parentCategoryName") String parent
    );

}
