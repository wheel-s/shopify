package com.toshiro.dreamshops.Repository;

import com.toshiro.dreamshops.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);


    boolean existsByName(String name);
}
