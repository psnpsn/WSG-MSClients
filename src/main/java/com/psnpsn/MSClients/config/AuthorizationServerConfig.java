/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.psnpsn.MSClients.config;

/**
 *
 * @author Guqnn
 */
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	@Value("${security.jwt.client-id}")
	private String clientId;

	@Value("${security.jwt.client-secret}")
	private String clientSecret;

	@Value("${security.jwt.grant-type}")
	private String grantType;

	@Value("${security.jwt.scope-read}")
	private String scopeRead;

	@Value("${security.jwt.scope-write}")
	private String scopeWrite = "write";

	@Value("${security.jwt.resource-ids}")
	private String resourceIds;

        private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
"MIIEpAIBAAKCAQEAvMEx3OF9ViIwRQ/SycEu5S8GzsN6yqyvkLdsRFjt4LVm2cWo\n" +
"abE0miARqDOAOYI46pYMn77AZIme+ToiPb8qjMXGzGXzzIkBNEF9DOP3oq2hsgjT\n" +
"g/WSM50iOBnfDr+rOu09K1sH91wABw05Ad2spoV50y7fWEw8nZoFqEOYn9VDR4TN\n" +
"rvY/kdDKWz1M4QRZ5pTiYM/z8YRR77emVEtp7KXUN+OkA2LygfmHWyD/MM/nN+Fa\n" +
"btS46N+sc+wDrbftFxe3llE4ztyVr3XZV+ulx12Y1C7hAsKzvZv6izA7OSBYpwpQ\n" +
"nB2e+shIJb2Rov2n/seXIfhFKtwVGFrrUeWKwwIDAQABAoIBAFCnrQrZP5limoWo\n" +
"MUYMymEcKyiG1x1rlMmd1yGNbngqi3xavZAMLJH/Sgw5lXz9bByxaUuJUuSi+c89\n" +
"TAxed2wXxs7ocbjggic2JJ0xfEuDLijJJLiqYoKzjj+yrmG6/Qv8+7pe95R3N3vL\n" +
"5qQpePDe8CORT9aHHQ+rFOfFmp4Q4YVQRWPaBcFwJkDSiTkh53Lg2G7GlIdCUaRh\n" +
"L211Gt0Q5FZ40kgJrRFl/XQPd/DzCqj/KpJbJ3vVGf/GqE9YISDtm7S+oZrBgjs7\n" +
"Ek6bAh846HcdXteUqpznRoI8QihVKI/QWdFPL2lI/ioysW94tgwoDAZxdsPdnz2I\n" +
"/oHxKaECgYEA+XH/qIC6TeH7yV3iCjKzfNQtDZILfbUmnGHUYIC5WwzLLg5kX7p0\n" +
"frnDspBz0GC0tTwNA9QPMTP1rYyWWM6WW6tRKVMO9m/vA/AbquHaGrdMvRceLpqE\n" +
"otnemmB2q4doqoL3pEOpIjaZXS9aAWWOSVrEjQFLENjXSDLVJxeKMg8CgYEAwbbv\n" +
"MvcGSWoCfrtiQzIlIAR4nxhruNe1tz+JgSY0ji2YmQELWFC4bxLQXtjRtmWGaTLF\n" +
"kcqVnI8GrvY/ZdpWQXDoC2hHbiC8q9lWxEtPhCr8yU8YB2DcTfDZiXkPmRYZFZ1I\n" +
"FN+Fe+vH4RjTka7OpbeqVAqvyh62e9ZFs78PAA0CgYEA533YGPR98VFLbbDm2Tf8\n" +
"QlhWB0YFy/+VeYOAqJ5NZiYVWQSaOgy7l++loc1U7YkRqy+zlF30S2FBGnJiBMCT\n" +
"4Ta393XRaPqwAiOOSaOX2TtKyCDwLafQd6FhaDslZkIf7D3mvFbeW5X39fa/vJQW\n" +
"3BSUW5dbpqLWB3bFQaYi5hkCgYEAk7IWzpTphi/9lwLn+LPS+oZuMV89QsG1GvKm\n" +
"tIGcsIqzPnU9YSiqdoWxuZ82xgrCYJMFcBM13xht0BiQXegKu/qJ8aigTadIMv6S\n" +
"2TdSeUMNWa+kcnu3fPWV5usWqXHfzksNGl0SOVmcHFQBe6H8jP1sMIe1YDXqjKDi\n" +
"pBmYJkECgYBdBLThC8NUZ908IlWUdsXfcpfXYvuOf4oZSe9uqwTzfhJUribYYLAZ\n" +
"ohGzVnIe3MejXj5B6ljoWmJDNgiE6EYbol8TaqqCh3HYiX79LtXZZKzbeWtA8S9C\n" +
"QJJllP6JDC0d0L4FpzobL9VSuxvjREOgDbymxSP9gMsDlJiklwC04g==\n" +
"-----END RSA PRIVATE KEY-----";
        private String publicKey = "-----BEGIN PUBLIC KEY-----\n" +
"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvMEx3OF9ViIwRQ/SycEu\n" +
"5S8GzsN6yqyvkLdsRFjt4LVm2cWoabE0miARqDOAOYI46pYMn77AZIme+ToiPb8q\n" +
"jMXGzGXzzIkBNEF9DOP3oq2hsgjTg/WSM50iOBnfDr+rOu09K1sH91wABw05Ad2s\n" +
"poV50y7fWEw8nZoFqEOYn9VDR4TNrvY/kdDKWz1M4QRZ5pTiYM/z8YRR77emVEtp\n" +
"7KXUN+OkA2LygfmHWyD/MM/nN+FabtS46N+sc+wDrbftFxe3llE4ztyVr3XZV+ul\n" +
"x12Y1C7hAsKzvZv6izA7OSBYpwpQnB2e+shIJb2Rov2n/seXIfhFKtwVGFrrUeWK\n" +
"wwIDAQAB\n" +
"-----END PUBLIC KEY-----";
        
	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setSigningKey(privateKey);
		converter.setVerifierKey(publicKey);
		return converter;
	}
        
        @Bean
        public TokenEnhancer tokenEnhancer() {
                return new CustomTokenEnhancer();
        }


	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(accessTokenConverter());
	}
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
                tokenEnhancerChain.setTokenEnhancers(
                        Arrays.asList(tokenEnhancer(), accessTokenConverter()));
 
                endpoints.tokenStore(tokenStore())
                         .tokenEnhancer(tokenEnhancerChain)
                        .authenticationManager(authenticationManager);
	}
	
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").passwordEncoder(NoOpPasswordEncoder.getInstance())
                        .addTokenEndpointAuthenticationFilter(new SimpleCorsFilter());
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients
                        .inMemory()
                        .withClient(clientId)
                        .secret(clientSecret)
                        .scopes(scopeRead, scopeWrite)
			.authorizedGrantTypes(grantType, "refresh_token").accessTokenValiditySeconds(20000).refreshTokenValiditySeconds(20000);

        }
        

}