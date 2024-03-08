package muri.ramos.vendas.domain.repository;

import muri.ramos.vendas.domain.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersItem extends JpaRepository<OrderItem, Integer> {
}
