package com.toshiro.dreamshops.Service.Order;

import com.toshiro.dreamshops.Model.Cart;
import com.toshiro.dreamshops.Model.Orders;
import com.toshiro.dreamshops.Model.OrderItem;
import com.toshiro.dreamshops.Model.Product;
import com.toshiro.dreamshops.Repository.OrderRepo;
import com.toshiro.dreamshops.Repository.ProductRepo;
import com.toshiro.dreamshops.Service.Cart.CartService;
import com.toshiro.dreamshops.dto.OrderDto;
import com.toshiro.dreamshops.enums.OrderStatus;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService implements  iOrderService{
    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CartService cartService;

    @Autowired
    private ModelMapper modelmapper;

    @Override
    public Orders placeOrder(Long userId) {
        Cart cart = cartService.getCartByUserId(userId);
        Orders order = createOrder(cart);

        List<OrderItem> orderItemList = createOrderItems(order,cart);
        order.setOrderItems(new HashSet<>(orderItemList));
        order.setTotalAmount(calculateTotalAmount(orderItemList));

        Orders saveOrder = orderRepo.save(order);
        cartService.clearCart(cart.getId());

        return saveOrder;
    }


    private Orders createOrder(Cart cart){
        Orders order = new Orders();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());
        return  order;
    }


   private List<OrderItem> createOrderItems (Orders order, Cart cart){
        return  cart.getItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepo.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
   }



    private BigDecimal calculateTotalAmount(List<OrderItem> orderItemList){
        return  orderItemList
                .stream()
                .map(item-> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }





    @Override
    public OrderDto getOrder(Long orderId) {
        return orderRepo.findById(orderId)
                .map(this :: convertTotoDto)
                .orElseThrow(()-> new ResourcNotFoundException("Order not found"));

    }

    @Override
    public List<OrderDto> getUserOrder(Long userId){

        List<Orders> orders = orderRepo.findByUserId(userId);
        return  orders.stream().map(this :: convertTotoDto).toList();

    }

    @Override
    public OrderDto convertTotoDto(Orders order){
        return modelmapper.map(order, OrderDto.class);
    }

}
