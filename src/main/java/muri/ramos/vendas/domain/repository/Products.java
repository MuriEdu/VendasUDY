package muri.ramos.vendas.domain.repository;

import muri.ramos.vendas.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Products extends JpaRepository<Product, Integer> {

}
