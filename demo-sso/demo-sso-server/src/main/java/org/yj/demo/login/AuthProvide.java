package org.yj.demo.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.NonceExpiredException;


public class AuthProvide implements AuthenticationProvider {

    private Logger logger = LoggerFactory.getLogger(AuthProvide.class);

    @Setter
    @Getter
    private UserDetailsService userDetailsService;

    @Setter
    @Getter
    private PasswordEncoder passwordEncoder;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        AuthToken token = (AuthToken)authentication;
        String principal = token.getPrincipal();
        JSONObject jsonObject = JSON.parseObject(principal);
        String username = jsonObject.getString("name");
        String password = jsonObject.getString("password");
        UserDetails details = userDetailsService.loadUserByUsername(username);
        if (details == null){
            throw new UsernameNotFoundException("cannot find user "+username);
        }
        logger.info("select by userservice "+JSONObject.toJSONString(details));
        if (!passwordEncoder.matches((password),details.getPassword())){
            throw new BadCredentialsException("password is error");
        }
        if (!details.isAccountNonExpired()){
            throw new NonceExpiredException("you account is expired");
        }

        if (!details.isAccountNonLocked()){
            throw new LockedException("you account is locked");
        }
        JSONObject json = new JSONObject();
        json.put("username",details.getUsername());
        json.put("authorities",details.getAuthorities());
        return new AuthToken(json.toJSONString(),details.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthToken.class.isAssignableFrom(authentication);
    }
}
