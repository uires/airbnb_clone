package br.com.airbnb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.airbnb.security.filter.JWTFilter;
import br.com.airbnb.service.security.AutenticacacaoService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AutenticacacaoService autenticacaoService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/acomodacao/consulta-acomodacao").permitAll()
				.antMatchers(HttpMethod.POST, "/acomodacao/consulta-sumarizada").permitAll()
				.antMatchers(HttpMethod.GET, "/acomodacao/{id}").permitAll().antMatchers(HttpMethod.POST, "/auth/**")
				.permitAll().anyRequest().authenticated().and().csrf().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
	}

	@Bean
	public BCryptPasswordEncoder encoderProvider() {
		return new BCryptPasswordEncoder();
	}
}
