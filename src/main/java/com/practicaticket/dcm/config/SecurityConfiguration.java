package com.practicaticket.dcm.config;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.oauth2.core.oidc.StandardClaimNames.PREFERRED_USERNAME;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcReactiveOAuth2UserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.ReactiveOAuth2UserService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.practicaticket.dcm.domain.Authority;
import com.practicaticket.dcm.domain.User;
import com.practicaticket.dcm.repository.AuthorityRepository;
import com.practicaticket.dcm.repository.UserRepository;
import com.practicaticket.dcm.security.AuthoritiesConstants;
import com.practicaticket.dcm.security.SecurityUtils;
import com.practicaticket.dcm.security.oauth2.AudienceValidator;

import tech.jhipster.config.JHipsterProperties;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

	private final JHipsterProperties jHipsterProperties;

	private final UserRepository userRepository;

	private final AuthorityRepository authorityRepository;

	@Value("${spring.security.oauth2.client.provider.oidc.issuer-uri}")
	private String issuerUri;

	public SecurityConfiguration(JHipsterProperties jHipsterProperties, UserRepository userRepository,
			AuthorityRepository authorityRepository) {
		this.jHipsterProperties = jHipsterProperties;
		this.userRepository = userRepository;
		this.authorityRepository = authorityRepository;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests(authz ->
		// prettier-ignore
		authz.requestMatchers(mvc.pattern("/api/authenticate")).permitAll()
				.requestMatchers(mvc.pattern("/api/auth-info")).permitAll()
				.requestMatchers(mvc.pattern("/api/admin/**")).hasAuthority(AuthoritiesConstants.ADMIN)
				.requestMatchers(mvc.pattern("/api/**")).authenticated().requestMatchers(mvc.pattern("/v3/api-docs/**"))
				.permitAll().requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
				.requestMatchers(mvc.pattern("/management/health")).permitAll()
				.requestMatchers(mvc.pattern("/management/health/**")).permitAll()
				.requestMatchers(mvc.pattern("/management/info")).permitAll()
				.requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
				.requestMatchers(mvc.pattern("/management/**")).hasAuthority(AuthoritiesConstants.ADMIN))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.oauth2ResourceServer(
						oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(authenticationConverter())))

				.oauth2Client(withDefaults());
		return http.build();
	}

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}

	Converter<Jwt, AbstractAuthenticationToken> authenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter
				.setJwtGrantedAuthoritiesConverter(new Converter<Jwt, Collection<GrantedAuthority>>() {
					@Override
					public Collection<GrantedAuthority> convert(Jwt jwt) {
						return SecurityUtils.extractAuthorityFromClaims(jwt.getClaims());
					}
				});
		jwtAuthenticationConverter.setPrincipalClaimName(PREFERRED_USERNAME);
		return jwtAuthenticationConverter;
	}

	@Bean
	JwtDecoder jwtDecoder() {
		NimbusJwtDecoder jwtDecoder = JwtDecoders.fromOidcIssuerLocation(issuerUri);

		OAuth2TokenValidator<Jwt> audienceValidator = new AudienceValidator(
				jHipsterProperties.getSecurity().getOauth2().getAudience());
		OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuerUri);
		OAuth2TokenValidator<Jwt> withAudience = new DelegatingOAuth2TokenValidator<>(withIssuer, audienceValidator);

		jwtDecoder.setJwtValidator(withAudience);

		return jwtDecoder;
	}

	
}
