package org.yj.demo.login;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class AuthToken extends AbstractAuthenticationToken {

    private String principal;

    public AuthToken(String principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    public AuthToken(String principal,Collection<? extends GrantedAuthority> authorities){
        super(authorities);
        this.principal = principal;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public String getPrincipal() {
        return principal;
    }
}
