package muri.ramos.vendas.domain.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import muri.ramos.vendas.domain.entity.Client;
import muri.ramos.vendas.domain.entity.OrderItem;
import muri.ramos.vendas.domain.entity.Orders;
import muri.ramos.vendas.domain.entity.Product;
import muri.ramos.vendas.domain.enums.OrderStatus;
import muri.ramos.vendas.domain.repository.Clients;
import muri.ramos.vendas.domain.repository.OrdersItem;
import muri.ramos.vendas.domain.repository.OrdersRepository;
import muri.ramos.vendas.domain.repository.Products;
import muri.ramos.vendas.domain.service.OrderService;
import muri.ramos.vendas.exeptions.BusinessRuleException;
import muri.ramos.vendas.exeptions.RecordNotFoundedException;
import muri.ramos.vendas.rest.dto.OrderDTO;
import muri.ramos.vendas.rest.dto.OrderItemDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrdersRepository ordersRepository;
    private final Clients clientsRepository;
    private final Products productsRepository;
    private final OrdersItem ordersItemRepository;

    @Override
    @Transactional
    public Orders save(OrderDTO dto) {

        Integer clientId = dto.getClient();
        Client client = clientsRepository
                .findById(clientId)
                .orElseThrow(() ->
            new BusinessRuleException("CLIENT NOT FOUNDED")
        );


        Orders newOrder = new Orders();
        newOrder.setTotal(dto.getTotal());
        newOrder.setOrderDate(LocalDate.now());
        newOrder.setClient(client);
        newOrder.setOrderStatus(OrderStatus.ACCEPTED);

        List<OrderItem> orderItems = toOrderItemList(newOrder, dto.getItems());

        ordersRepository.save(newOrder);
        ordersItemRepository.saveAll(orderItems);
        newOrder.setItens(orderItems);


        return newOrder;
    }

    @Override
    public Optional<Orders> getCompleteOrder(Integer id) {
        return ordersRepository.findByIdFetchItens(id);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, OrderStatus orderStatus) {
        ordersRepository.findById(id)
                .map(order -> {
                    order.setOrderStatus(orderStatus);
                    return ordersRepository.save(order);
                })
                .orElseThrow(() -> new RecordNotFoundedException(("ORDER NOT FOUDED")));
    }

    private List<OrderItem> toOrderItemList(Orders orders, List<OrderItemDTO> items){
        if(items.isEmpty()){
            throw new BusinessRuleException("ORDER WITHOUT ITEMS");
        }

        return items
                .stream()
                .map( dto -> {
                    Integer productId = dto.getProduct();
                    Product product = productsRepository
                            .findById(productId)
                            .orElseThrow(() -> new BusinessRuleException(("PRODUCT NOT FOUNDED")));

                    OrderItem orderItem = new OrderItem();
                    orderItem.setQtd(dto.getQtd());
                    orderItem.setOrders(orders);
                    orderItem.setProduct(product);


                    return orderItem;

                }).collect(Collectors.toList());
    }
}
