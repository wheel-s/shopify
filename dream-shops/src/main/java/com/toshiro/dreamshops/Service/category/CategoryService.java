package com.toshiro.dreamshops.Service.category;

import com.toshiro.dreamshops.Model.Category;
import com.toshiro.dreamshops.Repository.CategoryRepo;
import com.toshiro.dreamshops.exceptions.AlreadyExistsException;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements iCategoryService{
    @Autowired
    private CategoryRepo categoryRepo;



    @Override
    public Category getCategoryById(Long id) {
        return categoryRepo.findById(id).orElseThrow(()->new ResourcNotFoundException("Category not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c ->!categoryRepo.existsByName(c.getName()))
                .map(categoryRepo :: save).orElseThrow(()->new AlreadyExistsException(category.getName()+"Already exists"));
    }

    @Override
    public Category updateCategory(Category category, Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory ->{
            oldCategory.setName(category.getName());
            return categoryRepo.save(oldCategory);
        }).orElseThrow(()->new ResourcNotFoundException("Category not found"));
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepo.findById(id).ifPresentOrElse(categoryRepo::delete,
                ()->{throw new ResourcNotFoundException("Category not found!");} );

    }
}
