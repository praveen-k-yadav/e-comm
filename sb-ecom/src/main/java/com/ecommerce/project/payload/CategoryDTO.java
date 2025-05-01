package com.ecommerce.project.payload;


//Here we will have all the request object

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {

    private long categoryId;
    private String categoryName;


}
