package com.toshiro.dreamshops.Service.Cart;

import com.toshiro.dreamshops.Model.Cart;
import com.toshiro.dreamshops.Model.User;

import java.math.BigDecimal;

public interface iCartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);


    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);
}
