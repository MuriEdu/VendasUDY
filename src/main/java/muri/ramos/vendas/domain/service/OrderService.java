package muri.ramos.vendas.domain.service;

import muri.ramos.vendas.domain.entity.Orders;
import muri.ramos.vendas.domain.enums.OrderStatus;
import muri.ramos.vendas.rest.dto.OrderDTO;

import java.util.Optional;

public interface OrderService {
    Orders save(OrderDTO dto);

    Optional<Orders> getCompleteOrder(Integer id);

    void updateStatus(Integer id, OrderStatus orderStatus);

}
