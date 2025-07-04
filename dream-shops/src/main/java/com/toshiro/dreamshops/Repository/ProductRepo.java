package com.toshiro.dreamshops.Repository;

import com.toshiro.dreamshops.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository <Product, Long>{
    List<Product> findByName(String name);
    
    List<Product> findByBrand(String brand);

    List<Product> findByCategoryName(String category);


    List<Product> findByCategoryNameAndBrand(String category, String brand);


    List<Product> findByBrandAndCategoryName(String brand, String category);

    List<Product> findByBrandAndName(String brand, String name);

    boolean existsByNameAndBrand(String name, String brand);
}
