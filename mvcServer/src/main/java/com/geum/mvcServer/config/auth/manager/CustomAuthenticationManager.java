package com.geum.mvcServer.config.auth.manager;

import com.geum.mvcServer.config.auth.PrincipalDetails;
import com.geum.mvcServer.config.auth.PrincipalDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class CustomAuthenticationManager implements AuthenticationManager {

    /** Spring 2.7.0: WebSecurityConfigurerAdapter is deprecated.
     *  Added @Component AuthenticationManager(Custom) */

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final PrincipalDetailsService principalDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PrincipalDetails principalDetails = (PrincipalDetails) principalDetailsService.loadUserByUsername(authentication.getName());

        if(!bCryptPasswordEncoder.matches(authentication.getCredentials().toString(), principalDetails.getPassword())){
            throw new BadCredentialsException("Wrong password");
        }

        return new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
    }
}
