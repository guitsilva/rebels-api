package com.github.guitsilva.rebelsapi.security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.github.guitsilva.rebelsapi.security.details.data.RebelDataDetails;
import com.github.guitsilva.rebelsapi.security.details.services.RebelDetailsServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtValidationFilter extends BasicAuthenticationFilter {

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer ";

    private final RebelDetailsServiceImpl rebelDetailsService;

    public JwtValidationFilter(
            AuthenticationManager authenticationManager,
            RebelDetailsServiceImpl rebelDetailsService
    ) {
        super(authenticationManager);
        this.rebelDetailsService = rebelDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String header = request.getHeader(HEADER);

        if (header == null) {
            chain.doFilter(request, response);
            return;
        }

        if (!header.startsWith(PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX, "");
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String token) {
        String username = JWT.require(Algorithm.HMAC512(JwtAuthenticationFilter.TOKEN_SECRET))
                .build()
                .verify(token)
                .getSubject();

        RebelDataDetails rebelDataDetails = this.rebelDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(
                rebelDataDetails.getUsername(),
                rebelDataDetails.getPassword(),
                rebelDataDetails.getAuthorities()
        );
    }
}
