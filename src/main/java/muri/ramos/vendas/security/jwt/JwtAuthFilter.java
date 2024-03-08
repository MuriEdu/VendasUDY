package muri.ramos.vendas.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import muri.ramos.vendas.domain.service.impl.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            String token = recoverToken(request);

            if(token != null) {

                boolean isValid = jwtService.validateToken(token);

                if (isValid) {
                    String userLogin = jwtService.getUserLogin(token);
                    UserDetails user = userService.loadUserByUsername(userLogin);
                    UsernamePasswordAuthenticationToken authUser = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authUser);
                }
            }
            filterChain.doFilter(request, response);
        }


    private String recoverToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null) return null;
        if (header.startsWith("Bearer")) return header.replace("Bearer ", "");
        return null;
    }

}

