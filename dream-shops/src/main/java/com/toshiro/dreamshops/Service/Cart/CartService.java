package com.toshiro.dreamshops.Service.Cart;

import com.toshiro.dreamshops.Model.Cart;
import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Repository.CartItemRepo;
import com.toshiro.dreamshops.Repository.CartRepo;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;


@Service
@RequiredArgsConstructor
public class CartService  implements iCartService{
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private CartItemRepo cartItemRepo;



    @Override
    public Cart getCart(Long id) {
        Cart cart = cartRepo.findById(id).orElseThrow(()->new ResourcNotFoundException("Cart not found"));
            BigDecimal totalAmount = cart.getTotalAmount();
            cart.setTotalAmount(totalAmount);
        return cartRepo.save(cart);
    }
    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepo.deleteAllByCartId(id);
        cart.getItems().clear();
        cartRepo.deleteById(id);

    }

    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);
        return cart.getTotalAmount();
    }


    @Override
    public Cart initializeNewCart(User user){
      return Optional.ofNullable(getCartByUserId(user.getId()))
              .orElseGet(()->{
                  Cart cart =  new Cart();
                  cart.setUser(user);
                  return  cartRepo.save(cart);
              });
    }

    @Override
    public Cart getCartByUserId(Long userId) {
        return  cartRepo.findByUserId(userId);
    }

}
