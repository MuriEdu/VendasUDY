package muri.ramos.vendas.rest.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import muri.ramos.vendas.domain.entity.OrderItem;
import muri.ramos.vendas.domain.entity.Orders;
import muri.ramos.vendas.domain.enums.OrderStatus;
import muri.ramos.vendas.domain.service.OrderService;
import muri.ramos.vendas.exeptions.BusinessRuleException;
import muri.ramos.vendas.rest.dto.NewOrderStatus;
import muri.ramos.vendas.rest.dto.OrderDTO;
import muri.ramos.vendas.rest.dto.OrderInfoDTO;
import muri.ramos.vendas.rest.dto.OrderItemsInfoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/orders")
@Tag(name = "Orders API", description = "API para gerenciamento de pedidos")
public class OrderController {

    @Autowired
    private OrderService service;

    @Operation(summary = "Cria um novo pedido", method = "POST")
    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody @Valid OrderDTO dto){
        return service.save((dto)).getId();
    }

    @Operation(summary = "Busca um pedido por ID", method = "GET")
    @GetMapping("/{id}")
    public OrderInfoDTO getById(@PathVariable Integer id){
        return service.
                getCompleteOrder(id)
                .map(P -> toOrderInfoDTO(P))
                .orElseThrow(() -> new BusinessRuleException("ORDER NOT FOUNDED"));
    }

    @Operation(summary = "Altera usuário existente", method = "PATCH")
    @PatchMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@RequestBody NewOrderStatus newOrderStatus, @PathVariable Integer id){
        String status = newOrderStatus.getNewStatus();
        service.updateStatus(id, OrderStatus.valueOf(status));
    }


    private OrderInfoDTO toOrderInfoDTO( Orders orders ){
        return OrderInfoDTO.builder()
                .code(orders.getId())
                .date(orders.getOrderDate().format(DateTimeFormatter.ofPattern("dd/MM/YYYY")))
                .clientName(orders.getClient().getNome())
                .total(orders.getTotal())
                .status(orders.getOrderStatus().name())
                .items(toOrderItemsInfoDTO(orders.getItens()))
                .build();
    }

    private List<OrderItemsInfoDTO> toOrderItemsInfoDTO(List<OrderItem> items){

        if(items.isEmpty()){
            return Collections.emptyList();
        }

        return items.stream()
                .map(i -> OrderItemsInfoDTO
                        .builder()
                        .desc(i.getProduct().getDesc())
                        .qtd(i.getQtd())
                        .unityPrice(i.getProduct().getPrice())
                        .build())
                .collect(Collectors.toList());
    }


}
