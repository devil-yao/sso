package org.yj.demo.login;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.BadClientCredentialsException;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.Collections;

public class LoginSuccessHandle implements AuthenticationSuccessHandler {

    @Getter
    @Setter
    private AuthorizationServerTokenServices tokenServices;

    @Getter
    @Setter
    private ClientDetailsService clientDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Basic_")){
            throw new InvalidRequestException("cannot find auth header");
        }
        String[] clientInfo = expose(auth);
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientInfo[0]);
        if (clientDetails == null){
            throw new InvalidClientException("cannot find any client detail by"+clientInfo[0]);
        }
        if (!clientDetails.getClientSecret().equals(clientInfo[1])){
            throw new BadClientCredentialsException();
        }
        TokenRequest tokenRequest = new TokenRequest(Collections.emptyMap(),clientDetails.getClientId(),clientDetails.getScope(),"password");
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request,authentication);
        OAuth2AccessToken token = tokenServices.createAccessToken(oAuth2Authentication);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        response.setCharacterEncoding("utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(token));
        writer.flush();
        writer.close();

    }

    private String[] expose(String header){
        String clientBase = header.replace("Basic_","");
        String clientInfo = new String(Base64.getDecoder().decode(clientBase));
        if (!clientInfo.contains(":")){
            throw new InvalidRequestException("auth client fail");
        }
        return clientInfo.split(":",2);
    }

    public static void main(String[] args) {
        System.out.println(Base64.getEncoder().encodeToString("sso:".getBytes()));;
    }
}
