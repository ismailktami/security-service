package com.example.demo.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String jwt=httpServletRequest.getHeader(SecurityParams.JWT_HEADER);
        /*
        if(httpServletRequest.getRequestURI().equals("/login"))
            filterChain.doFilter(httpServletRequest,httpServletResponse);

        */
        if(jwt==null || !jwt.startsWith(SecurityParams.HEADER_PREFIX)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
        else{
            JWTVerifier jwtVerifier=JWT.require(Algorithm.HMAC256(SecurityParams.PRIVATE_KEY)).build();
            String token=jwt.substring(SecurityParams.HEADER_PREFIX.length());
            DecodedJWT decodeJwt= jwtVerifier.verify(token);
            List<String> roles=decodeJwt.getClaim("roles").asList(String.class);
            System.out.println("roles++++++++++++++"+roles);
            Collection<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>();
            String username=decodeJwt.getSubject();
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
            UsernamePasswordAuthenticationToken user=new UsernamePasswordAuthenticationToken(username,null,authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }
}
