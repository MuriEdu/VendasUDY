package muri.ramos.vendas.domain.service.impl;

import lombok.RequiredArgsConstructor;
import muri.ramos.vendas.domain.entity.Users;
import muri.ramos.vendas.domain.repository.UsersRepository;
import muri.ramos.vendas.exeptions.InvalidPasswordException;
import muri.ramos.vendas.rest.dto.UserResponseDTO;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UsersRepository repository;


    public UserDetails auth( Users user ){
        UserDetails crrUser = loadUserByUsername(user.getLogin());
        boolean isVerified = encoder.matches(user.getPassword(), crrUser.getPassword());
        if (isVerified) return crrUser;
        throw new InvalidPasswordException("Invalid Password");
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByLogin(username)
                .orElseThrow( () -> new UsernameNotFoundException("User not founded"));

        String[] roles = user.isAdmin() ? new String[]{"USER", "ADMIN"} : new String[]{"USER"};

        return User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    public UserResponseDTO save(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
        Users newUser = repository.save(user);
        UserResponseDTO res = new UserResponseDTO();
        res.setLogin(newUser.getLogin());
        res.setId(newUser.getId());
        res.setAdmin(newUser.isAdmin());
        return res;
    }

}
