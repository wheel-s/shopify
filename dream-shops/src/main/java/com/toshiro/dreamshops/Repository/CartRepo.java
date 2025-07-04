package com.toshiro.dreamshops.Repository;

import com.toshiro.dreamshops.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long userId);
}
