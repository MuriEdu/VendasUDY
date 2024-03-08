package muri.ramos.vendas.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import muri.ramos.vendas.domain.entity.Product;
import muri.ramos.vendas.domain.repository.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Products API", description = "API para gerenciamento de produtos")
public class ProductController {

    @Autowired
    private Products products;

    @Operation(summary = "Busca produto por ID", method = "GET")
    @GetMapping("/{id}")
    public Product getProductById( @PathVariable Integer id){
        return products.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PRODUCT NOT FOUNDED"));
    }

    @Operation(summary = "Cria um novo produto", method = "POST")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody @Valid Product product){
        return products.save(product);
    }


    @Operation(summary = "Deleta um produto existente por id", method = "DELETE")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        products.findById(id).map(existingProduct -> {
            products.delete(existingProduct);
            return products;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PRODUCT NOT FOUNDED"));
    }

    @Operation(summary = "Atualiza produto existente", method = "PUT")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody @Valid Product product){
        products.findById(id).map(existingProduct -> {
            product.setId(existingProduct.getId());
            products.save(product);
            return product;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "PRODUCT NOT FOUNDED"));
    }

    @Operation(summary = "Busca produto por parâmetros", method = "GET")
    @GetMapping
    public List<Product> find (Product filter){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Product> example = Example.of(filter, matcher);
        return products.findAll(example);
    }
}
