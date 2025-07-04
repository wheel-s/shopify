package com.toshiro.dreamshops.Controller;


import com.toshiro.dreamshops.Model.Cart;
import com.toshiro.dreamshops.Model.User;
import com.toshiro.dreamshops.Response.ApiResponse;
import com.toshiro.dreamshops.Service.Cart.iCartItemService;
import com.toshiro.dreamshops.Service.Cart.iCartService;
import com.toshiro.dreamshops.Service.User.iUserService;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItem")
public class CartItemController {
    @Autowired
    private iCartItemService cartItemService;
    @Autowired
    private  iCartService cartService;
    @Autowired
    private iUserService userService;

    @PostMapping("/items/add")
    public ResponseEntity<ApiResponse> getCart(
                                               @RequestParam Long productId,
                                               @RequestParam Integer quantity){
        try {


                User user = userService.getAuthenticatedUser();
              Cart cart =   cartService.initializeNewCart(user);

             cartItemService.addItemToCart(cart.getId(), productId, quantity);
            return ResponseEntity.ok(new ApiResponse(" Add item success",null));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        } catch (JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(),null));
        }


    }

    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable Long cartId, @PathVariable Long itemId){
        try {
            cartItemService.removeItemFromCart(cartId, itemId);
            return ResponseEntity.ok(new ApiResponse(" deleted item successfully",null));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


    @PutMapping("/cart/{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long itemId,
                                                          @RequestParam Integer quantity){

        try {
            cartItemService.updateItemQuantity(cartId, itemId, quantity);
            return ResponseEntity.ok(new ApiResponse(" Update item success",null));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }


}
