package kz.iitu.librarymanager.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//Copied from Baeldung but modified; Required for encoding the password on authentication
	@Autowired
	private DataSource dataSource;

	//User Detail service init; Required for encoding the password on authentication
	@Bean
	public org.springframework.security.core.userdetails.UserDetailsService userDetailsService() {
		return new UsersDetailsService();
	}

	//https://www.baeldung.com/spring-security-registration-password-encoding-bcrypt

	//		Example of a "Random Salt"
	//		$2a$10$ZLhnHxdpHETcxmtEStgpI./Ri1mksgJ9iDP36FmfMdYyVg9g0b2dq
	//
	//	There are three fields separated by $:
	//	-The “2a” represents the BCrypt algorithm version
	//	-The “10” represents the strength of the algorithm
	//	-The “ZLhnHxdpHETcxmtEStgpI.” part is actually the randomly generated salt.
	//	 Basically, the first 22 characters are salt. The remaining part of the last field is the actual hashed version of the plain text

	//Initiate Password Encoder
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//This code is literally copied from Bauldung; It does password encoding on Authentication
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	//	The security configuration is simple:
	//	-we are injecting our implementation of the users details service
	//	-we are defining an authentication provider that references our details service
	//	-we are also enabling the password encoder
	//	And finally, we need to reference this auth provider in our security XML configuration;
	//	in this case we're using Java configuration:

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/books").authenticated()
				.antMatchers("/users").authenticated()
				.antMatchers("/members").authenticated()
				.antMatchers("/categories").authenticated()
				.antMatchers("/publishers").authenticated()
			.anyRequest().permitAll()
			.and()
			.formLogin()
				.usernameParameter("userName")
				.defaultSuccessUrl("/books")
				.defaultSuccessUrl("/users")
				.defaultSuccessUrl("/members")
				.defaultSuccessUrl("/categories")
				.defaultSuccessUrl("/publishers")
				.permitAll()
			.and()
			.logout().logoutSuccessUrl("/").permitAll();
	}
}
