package muri.ramos.vendas.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemsInfoDTO {
    private String desc;
    private BigDecimal unityPrice;
    private Integer qtd;
}
