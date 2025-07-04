package com.toshiro.dreamshops.Repository;

import com.toshiro.dreamshops.Model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Orders, Long> {
   List<Orders> findByUserId(Long userId);
}
