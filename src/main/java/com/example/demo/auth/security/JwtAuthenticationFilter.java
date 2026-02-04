package com.example.demo.auth.security;

import com.example.demo.auth.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1️⃣ Read Authorization header
        final String authHeader = request.getHeader("Authorization");

        // 2️⃣ If no token or wrong format → continue without auth
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3️⃣ Extract JWT
        final String jwt = authHeader.substring(7);

        // 4️⃣ Extract username/email from token
        final String userEmail = jwtService.extractUsername(jwt);

        // 5️⃣ Authenticate only if not already authenticated
        if (userEmail != null &&
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // 6️⃣ Load user from database
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(userEmail);

            // 7️⃣ Validate token
            if (jwtService.isTokenValid(jwt, userDetails)) {

                // 8️⃣ Create authentication token
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // 9️⃣ Save authentication
                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);
            }
        }

        // 🔟 Continue filter chain
        filterChain.doFilter(request, response);
    }

    //filter
}
