package com.ecommerce.project.repositories;

import com.ecommerce.project.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;


//Spring data JPA automatically generate the implementation at runtime and we can use it to perform all the basic crud operations on category table
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByCategoryName(@NotBlank @Size(min = 5,message = "CategoryName must contain at least 5 characters") String categoryName);
}
