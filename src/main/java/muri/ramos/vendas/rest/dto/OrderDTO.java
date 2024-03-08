package muri.ramos.vendas.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import muri.ramos.vendas.domain.validation.NotEmptyList;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    @NotNull(message = "Client code is required")
    private Integer client;
    @NotNull(message = "Total is required")
    private BigDecimal total;
    @NotEmptyList(message = "Items is required")
    private List<OrderItemDTO> items;
}
