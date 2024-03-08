package muri.ramos.vendas;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@OpenAPIDefinition(info = @Info(title = "Vendas", description = "Aplicação web para estudo", version = "1.0"))
@SpringBootApplication
public class VendasApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(VendasApplication.class, args);
    }
}
