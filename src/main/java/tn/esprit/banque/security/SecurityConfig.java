/*
 * Copyright 2002-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tn.esprit.banque.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.PersonContextMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic();
        http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
	
	@Bean
	UnboundIdContainer ldapContainer() {
		UnboundIdContainer container = new UnboundIdContainer("dc=esprit,dc=tn", "classpath:users.ldif");
		container.setPort(0);
		return container;
	}

	@Bean
	ContextSource contextSource(UnboundIdContainer container) {
		int port = container.getPort();
		return new DefaultSpringSecurityContextSource("ldap://localhost:" + port + "/dc=esprit,dc=tn");
	}

	@Bean
	BindAuthenticator authenticator(BaseLdapPathContextSource contextSource) {
		BindAuthenticator authenticator = new BindAuthenticator(contextSource);
		authenticator.setUserDnPatterns(new String[] { "uid={0},ou=morale","uid={0},ou=physique","uid={0},ou=employee" });
		return authenticator;
	}

	@Bean
	LdapAuthenticationProvider authenticationProvider(LdapAuthenticator authenticator,BaseLdapPathContextSource contextSource) {
		LdapAuthoritiesPopulator authorities = 	new DefaultLdapAuthoritiesPopulator(contextSource, "ou=groups");	
		LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator,authorities);
		provider.setUserDetailsContextMapper(new PersonContextMapper());
		return provider;
	}

}
