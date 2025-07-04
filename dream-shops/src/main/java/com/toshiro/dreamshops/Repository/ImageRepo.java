package com.toshiro.dreamshops.Repository;

import com.toshiro.dreamshops.Model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepo extends JpaRepository<Image, Long> {
    List<Image> findByproductId(Long id);
}
