package muri.ramos.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInfoDTO {
    private Integer code;
    private String clientName;
    private BigDecimal total;
    private String date;
    private String status;
    private List<OrderItemsInfoDTO> items;
}
