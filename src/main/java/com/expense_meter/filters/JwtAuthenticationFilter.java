package com.expense_meter.filters;

import com.expense_meter.services.CustomUserDetailsService;
import com.expense_meter.services.UserService;
import com.expense_meter.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String userName=null;String token=null;
        final String authHeader = request.getHeader("Authorization");

        if (authHeader!=null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                userName = jwtUtils.getUsernameFromToken(token);
                System.out.println("Username from Token " + userName);
            } catch (Exception e ) {
                System.out.println(e.getMessage());
//                e.printStackTrace();
            }

            if (userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);

                if (jwtUtils.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken upAt = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    upAt.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(upAt);
                }
            }
        }


        // Next
        filterChain.doFilter(request, response);
    }
}
