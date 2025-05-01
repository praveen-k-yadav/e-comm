package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFounException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Added Service annotation to tell the spring to manage this component as a bean
@Service
public class CategoryServiceImpl implements CategoryService {

    //private List<Category> categories = new ArrayList<>();
    //private long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize,String sortBy, String sortOrder) {
        //Sort.by create object using the variable sortBy
        Sort sortByAndOrder=sortOrder.equalsIgnoreCase("asc")
                ?Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        //PageRequest is method that create Pageable object
        Pageable pageDetails = PageRequest.of(pageNumber,pageSize,sortByAndOrder);
        //Page is a JPA class that contain the data of single page and useful metadata about total number of records and page
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        //categoryPage is paginated object that JPA is helping us get based on page number and page size
       // List<Category> categories = categoryRepository.findAll();

        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty())
            throw new APIException("No category created till now!!!");
        //findAll return all the categories that exist in the database

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse=new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());
        //categoryresponse is wrapper class that contain list of category DTO object
        return categoryResponse;
    }

    @Override
    public CategoryDTO creatCategory(CategoryDTO categoryDTO) {

        //Logic to check if category name already exists
     if(categoryRepository.findByCategoryName(categoryDTO.getCategoryName())!=null)
     {
       throw new APIException("Category with the Name" + categoryDTO.getCategoryName() +"already exists");
     }

     //Map DTO->Entity
     Category category = new Category();
     category.setCategoryName(categoryDTO.getCategoryName());

     //Saved the new category and return the DTO
     Category savedCategory = categoryRepository.save(category);
     CategoryDTO savedCatergoryDTO = new
             CategoryDTO(savedCategory.getCategoryId(), savedCategory.getCategoryName());
     return savedCatergoryDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {

        Category category= categoryRepository.findById(categoryId).orElseThrow(() ->new ResourceNotFounException("category","categoryId",categoryId));

        categoryRepository.delete(category);
        return modelMapper.map(category,CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        //Just check if that category exists or not, if not then just throw the exception
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() ->new ResourceNotFounException("category","categoryId",categoryId));

        Category category = modelMapper.map(categoryDTO,Category.class);
        category.setCategoryId(categoryId);
        savedCategory=categoryRepository.save(category);
        return modelMapper.map(savedCategory,CategoryDTO.class);

    }
}
