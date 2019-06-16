// package org.yj.demo.security;

// import org.springframework.security.oauth2.common.OAuth2AccessToken;
// import org.springframework.security.oauth2.provider.ClientDetailsService;
// import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
// import org.springframework.security.oauth2.provider.TokenRequest;
// import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
// import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

// public class PasswordTokenGrant extends AbstractTokenGranter {

//     private static final String GRANT_TYPE = "password";

//     protected PasswordTokenGrant(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
//         super(tokenServices, clientDetailsService, requestFactory, grantType);
//     }

//     @Override
//     public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
//         OAuth2AccessToken token = super.grant(grantType,tokenRequest);
//         return null;
//     }
// }
