package com.security.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.security.core.properties.OAuth2ClientProperties;
import com.security.core.properties.SecurityProperties;

@Configuration
@EnableAuthorizationServer
public class AuthoritzationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired(required = false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	
	@Autowired(required = false)
	public TokenEnhancer jwtTokenEnhancer;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
			.authenticationManager(authenticationManager)
			.userDetailsService(userDetailsService)
			.tokenStore(tokenStore);
		
		if(jwtAccessTokenConverter != null && jwtTokenEnhancer != null){
			TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
			
			List<TokenEnhancer> enhancers = new ArrayList<>();
			enhancers.add(jwtAccessTokenConverter);
			enhancers.add(jwtTokenEnhancer);
			
			enhancerChain.setTokenEnhancers(enhancers);
			
			endpoints
				.tokenEnhancer(enhancerChain)
				.accessTokenConverter(jwtAccessTokenConverter);
		}
	}
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		
		InMemoryClientDetailsServiceBuilder memoryService = clients.inMemory();
		
		OAuth2ClientProperties[] clientProperteis = securityProperties.getOauth2().getClients();
		if(ArrayUtils.isNotEmpty(clientProperteis)){
			Stream.of(clientProperteis).forEach( p -> {
				memoryService
					.withClient(p.getClientId())
					.secret(p.getClientSecret())
					.accessTokenValiditySeconds(p.getAccessTokenValiditySeconds())
					.authorizedGrantTypes("refresh_token", "password")
					.scopes("all", "read", "write");;
			});
		}
		
	}

}
