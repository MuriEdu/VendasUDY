package muri.ramos.vendas.domain.repository;
import muri.ramos.vendas.domain.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Clients extends JpaRepository<Client, Integer> {

    List<Client> findByNomeLike(String muri);

    void deleteByNome(String nome);

    void existsByNome(String nome);
}



