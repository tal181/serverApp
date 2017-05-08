package com.myapp.auth;

import com.myapp.api.user.UserApi;
import com.myapp.domain.user.UserDetails;
import org.apache.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * Created by Tal on 08/05/2017.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider{
    @Autowired
    UserApi userApi;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username=authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();
        try {
           UserDetails userDetails= userApi.getUserDetails(username);
           if( userDetails.getPassword().equals(password)){
               return new UsernamePasswordAuthenticationToken(
                       username, password, new ArrayList<>());

           }
        }
        catch (Exception e){
          LogManager.getRootLogger().error("Failed to get user  " +username +e);
        }
        return null;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }
}
