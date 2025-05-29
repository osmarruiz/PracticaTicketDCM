package com.practicaticket.dcm.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.practicaticket.dcm.domain.Authority;
import com.practicaticket.dcm.domain.User;
import com.practicaticket.dcm.repository.AuthorityRepository;
import com.practicaticket.dcm.repository.UserRepository;

@Component
public class LoginSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepository authorityRepository;

	@Override
	@Transactional
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Object principal = event.getAuthentication().getPrincipal();

		if (principal instanceof Jwt jwt) {
			String login = jwt.getClaimAsString("preferred_username");
			String email = jwt.getClaimAsString("email");
			String firstName = jwt.getClaimAsString("given_name");
			String lastName = jwt.getClaimAsString("family_name");

			System.out.println("*********** JWT LOGIN DETECTED ************");
			System.out.println("login: " + login);
			System.out.println("email: " + email);
			System.out.println("firstName: " + firstName);
			System.out.println("lastName: " + lastName);
			System.out.println("*******************************************");
			
			
			

			userRepository.findOneByLogin(login).orElseGet(() -> {
				User newUser = new User();
				newUser.setId(login);
				newUser.setLogin(login);
				newUser.setEmail(email);
				newUser.setActivated(true);
				newUser.setLangKey("en");
				newUser.setFirstName(firstName);
				newUser.setLastName(lastName);

				Set<String> roles = SecurityUtils.extractAuthorityFromClaims(jwt.getClaims()).stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

				Set<Authority> authorities = roles.stream()
						.map(role -> authorityRepository.findById(role).orElseGet(() -> {
							Authority newAuthority = new Authority();
							newAuthority.setName(role);
							return authorityRepository.save(newAuthority);
						})).collect(Collectors.toSet());

				newUser.setAuthorities(authorities);

				return userRepository.save(newUser);
			});
		}
	}
}
