package com.dac.ecommerce.livros.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dac.ecommerce.livros.config.jwt.JwtFilter;
import com.dac.ecommerce.livros.services.ImplementsUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecutiryConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired private ImplementsUserDetailsService usuarioDetailsService;
	@Autowired private JwtFilter jwtFilter;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/resources/**").permitAll()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.antMatchers(HttpMethod.GET, "/").permitAll()
		.antMatchers(HttpMethod.GET, "/detalhar-livro/*").permitAll()
		.antMatchers(HttpMethod.GET, "/categoria/*").permitAll()
		.antMatchers(HttpMethod.GET, "/pesquisar/*").permitAll()
		.antMatchers(HttpMethod.GET, "/user/cadastrar").permitAll()
		.antMatchers(HttpMethod.POST, "/user/cadastrar").permitAll()
		.antMatchers(HttpMethod.GET, "/user/login").permitAll()
		.antMatchers(HttpMethod.POST, "/user/autenticar").permitAll()
		.antMatchers(HttpMethod.GET, "/user/**").hasAnyAuthority("CLIENTE", "ADMIN")
		.antMatchers(HttpMethod.POST, "/user/**").hasAnyAuthority("CLIENTE", "ADMIN")
		.antMatchers(HttpMethod.GET, "/pedido/forma-pagamento").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/pedido/forma-pagamento").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/pedido/atualizar-forma-pagamento/*").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/pedido/atualizar-forma-pagamento/*").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/pedido/**").hasAuthority("CLIENTE")
		.antMatchers(HttpMethod.POST, "/pedido/**").hasAuthority("CLIENTE")
		.antMatchers(HttpMethod.GET, "/autor/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/autor/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/categoria/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/categoria/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/editora/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/editora/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/livro/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/livro/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/estoque/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/estoque/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.GET, "/item-estoque/**").hasAuthority("ADMIN")
		.antMatchers(HttpMethod.POST, "/item-estoque/**").hasAuthority("ADMIN")
		.anyRequest().authenticated()
		.and().exceptionHandling()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioDetailsService).passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	
}
