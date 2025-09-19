package com.devsuperior.dscatalog.config.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import com.devsuperior.dscatalog.domain.entities.User;

@Component
public class MyPrincipalJwtConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    @Nullable
    public UsernamePasswordAuthenticationToken convert(@NonNull Jwt jwt) {
        String email = jwt.getClaimAsString("username");
        User user = (User) userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(user, jwt, user.getAuthorities());
    }
    
}
