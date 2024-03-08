package muri.ramos.vendas.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import muri.ramos.vendas.domain.entity.Users;
import muri.ramos.vendas.domain.service.impl.UserService;
import muri.ramos.vendas.exeptions.InvalidPasswordException;
import muri.ramos.vendas.rest.dto.CredentialsDTO;
import muri.ramos.vendas.rest.dto.TokenDTO;
import muri.ramos.vendas.rest.dto.UserResponseDTO;
import muri.ramos.vendas.security.jwt.JwtService;

import static org.springframework.http.HttpStatus.*;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users API", description = "Gerencia usuários da aplicação")
public class UsersController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping
    @ResponseStatus(CREATED)
    @Operation(summary = "Cria novo usuário", method = "POST")
    public UserResponseDTO create(@RequestBody Users user){
        return userService.save(user);
    }

    @PostMapping("/auth")
    @Operation(summary = "Autentica usuário existente", method = "POST")
    public TokenDTO auth (@RequestBody CredentialsDTO credentials){
        try {
            Users user = Users.builder().
                    login(credentials.getLogin())
                    .password(credentials.getPassword())
                    .build();

            userService.auth(user);
            String token = jwtService.generateToken(user);
            return new TokenDTO(user.getLogin(), token);
        }
        catch (UsernameNotFoundException | InvalidPasswordException ex){
            throw new ResponseStatusException(UNAUTHORIZED, ex.getMessage());
        }
    }

}
