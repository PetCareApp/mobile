package cesar.cap7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static cesar.cap7.petfy.util.Constants.PAPEL_ADMIN;
import static cesar.cap7.petfy.util.Constants.PAPEL_PROPRIETARIO;

@Configuration
@EnableWebSecurity
@ComponentScan
public class PetfySecurity extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/").authenticated()
			.antMatchers("/public-resources/**").permitAll()
			.antMatchers("/rest/**").permitAll()
			.antMatchers("/proprietario/**").hasAuthority(PAPEL_PROPRIETARIO).anyRequest().authenticated()
			.antMatchers("/admin/**").hasAuthority(PAPEL_ADMIN).anyRequest().authenticated()
			.and()
				.formLogin().loginProcessingUrl("/login").loginPage("/login").permitAll()
			.and()
	            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}

}
