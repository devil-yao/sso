package org.yj.demo.login;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    public AuthFilter(){
        super(new AntPathRequestMatcher("/auth/login"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals(RequestMethod.POST.name())){
            throw new AuthenticationServiceException("unsupport method "+request.getMethod());
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
           throw new AuthorizationServiceException("username or password cannot be null");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",username);
        jsonObject.put("password",password);
        AuthToken authToken = new AuthToken(jsonObject.toJSONString());

        return getAuthenticationManager().authenticate(authToken);
    }
}
