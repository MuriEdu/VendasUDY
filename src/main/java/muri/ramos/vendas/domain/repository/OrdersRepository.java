package muri.ramos.vendas.domain.repository;
import muri.ramos.vendas.domain.entity.Client;
import muri.ramos.vendas.domain.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByClient(Client client);

    @Query("select o from  Orders o left join fetch o.itens where o.id =:id")
    Optional<Orders> findByIdFetchItens (@Param("id") Integer id);

}
