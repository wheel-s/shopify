package com.toshiro.dreamshops.Service.Order;

import com.toshiro.dreamshops.Model.Orders;
import com.toshiro.dreamshops.dto.OrderDto;

import java.util.List;

public interface iOrderService {
    Orders placeOrder(Long userId);
    OrderDto getOrder (Long orderId);

    List<OrderDto> getUserOrder(Long userId);

    OrderDto convertTotoDto(Orders order);
}
