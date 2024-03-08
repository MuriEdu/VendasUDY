package muri.ramos.vendas.domain.repository;

import muri.ramos.vendas.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByLogin(String login);
}
