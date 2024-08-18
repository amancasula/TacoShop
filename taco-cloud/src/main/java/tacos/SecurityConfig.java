package tacos;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder();
	}
	
	/*@Bean
	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
		
		ArrayList<UserDetails> usersList = new ArrayList<>();
		usersList.add(new User("buzz",encoder.encode("password123"),Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
		return new InMemoryUserDetailsManager(usersList);
		
	} */
	
	@Bean
	public UserDetailsService userDetailsService(UserRepository userRepo) {
		
		return username ->  { tacos.User user = userRepo.findByUsername(username);
		if (user != null) {
			return user;
		}
		throw new UsernameNotFoundException("User "+ username + "not found");
		};
		
	} 
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
	//	return http.authorizeRequests().requestMatchers("/design","/orders").hasRole("USER").requestMatchers("/","/**").permitAll().and().formLogin().loginPage("/login").and().csrf().ignoringRequestMatchers("/h2-console/***").and().build();
		return http.authorizeRequests().requestMatchers("/design","/orders").hasRole("USER").requestMatchers("/","/**").permitAll().and().formLogin().loginPage("/login").and().build();

	}
	

}
