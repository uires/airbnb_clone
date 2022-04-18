package br.com.airbnb.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/css/**", "/images/**", "/js/**").permitAll().antMatchers("/home/**")
				.permitAll().antMatchers("/consulta-municipio/**").permitAll().anyRequest().authenticated().and()
				.formLogin(formulario -> formulario.loginPage("/").defaultSuccessUrl("/dashboard", true).permitAll())
				.logout(logout -> logout.logoutUrl("/logout")).csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		BCryptPasswordEncoder cryptPasswordEncoder = new BCryptPasswordEncoder();

		// UserDetails user =
		// User.builder().username("uires").password(cryptPasswordEncoder.encode("root")).roles("ADM")
		// .build();

		auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(cryptPasswordEncoder);
	}
}
