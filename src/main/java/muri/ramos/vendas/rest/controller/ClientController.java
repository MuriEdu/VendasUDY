package muri.ramos.vendas.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import muri.ramos.vendas.domain.entity.Client;
import muri.ramos.vendas.domain.repository.Clients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@Tag(name = "Client API", description = "API para gerenciamento de clientes")
public class ClientController {

    @Autowired
    private Clients clients;

    @Operation(summary = "Busca de cliente por ID", method = "GET")
    @GetMapping("/{id}")
    public Client getClientById( @PathVariable Integer id){
        return clients.findById(id)
                .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "CLIENT NOT FOUNDED")
                );
    }

    @Operation(summary = "Cria novos usuários", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Client save ( @RequestBody @Valid Client client ){
        return clients.save(client);
    }

    @Operation(summary = "Deleta cliente por id", method = "DELETE")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Integer id ){
        clients.findById(id)
                .map( client -> {clients.delete(client);
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CLIENT NOT FOUNDED"));
    }

    @Operation(summary = "Altera cliente existente", method = "PUT")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Client client){
         clients
                .findById(id)
                .map( clienteExistente -> {
                    client.setId(clienteExistente.getId());
                    clients.save(client);
                    return clienteExistente;
                }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "CLIENT NOT FOUNDED"));
    }

    @Operation(summary = "Busca cliente por meio de parâmetros", method = "GETE")
    @GetMapping
    public List<Client> find(Client filter){
        ExampleMatcher matcher = ExampleMatcher
                                    .matching()
                                    .withIgnoreCase()
                                    .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return clients.findAll(example);
    }
}
