package com.toshiro.dreamshops.Controller;


import com.toshiro.dreamshops.Model.Orders;
import com.toshiro.dreamshops.Response.ApiResponse;
import com.toshiro.dreamshops.Service.Order.OrderService;
import com.toshiro.dreamshops.Service.Order.iOrderService;
import com.toshiro.dreamshops.dto.OrderDto;
import com.toshiro.dreamshops.exceptions.ResourcNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order")
public class OrderController {


    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder( @RequestParam Long userId){
        try {
            Orders order = orderService.placeOrder(userId);
            OrderDto orderDto= orderService.convertTotoDto(order);
            return  ResponseEntity.ok(new ApiResponse(" Item order success!", orderDto));
        } catch (Exception e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error occured!", e.getMessage()));
        }
    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById(@PathVariable Long orderId){
        try {
            OrderDto orders = orderService.getOrder(orderId);
            return ResponseEntity.ok(new ApiResponse("Item order success!",orders));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }


    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<ApiResponse> getUserOrder(@PathVariable Long userId){
        try {
           List<OrderDto> orders = orderService.getUserOrder(userId);
            return ResponseEntity.ok(new ApiResponse("Item order success!",orders));
        } catch (ResourcNotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Oops!", e.getMessage()));
        }


    }

}
