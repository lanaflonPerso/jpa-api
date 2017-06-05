package com.manowar.backend.integration.security;

import com.manowar.backend.integration.repository.AccountRepository;
import com.manowar.backend.integration.repository.RoleRepository;
import com.manowar.backend.model.Account;
import com.manowar.backend.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jacky on 05/06/2017.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (shouldAuthenticate(name,password,authorities)) {
            return new UsernamePasswordAuthenticationToken(
                    name, password, authorities);
        } else {
            return null;
        }
    }

    private boolean shouldAuthenticate(String name, String password, List<GrantedAuthority> authorities) {
        Account account = accountRepository.findAccountByUsernameAndPassword(name, password).get(0);
        if (account == null || !account.isEnabled()){
            return false;
        }
        List<Role> roles = roleRepository.findByUsername(name);
        if(!roles.isEmpty()){
            for (Role role : roles) {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                authorities.add(grantedAuthority);
            }
        }
        return true;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);    }
}
