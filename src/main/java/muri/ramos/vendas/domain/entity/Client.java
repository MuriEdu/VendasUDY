package muri.ramos.vendas.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    @NotEmpty(message = "Name is Required")
    private String nome;

    @Column(name = "cpf", length = 11)
    @NotEmpty(message = "CPF is Required")
    @CPF
    private String cpf;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Orders> orders;


    public Client(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
    }

}
