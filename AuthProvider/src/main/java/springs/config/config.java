package springs.config;


import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class config {

//	@Bean
//	public InMemoryUserDetailsManager inMemory() {
//		UserDetails user = User.withDefaultPasswordEncoder().username("harsh").password("pass@123").build();
//        return new InMemoryUserDetailsManager(user);
//	}
	
	@Bean
	public BCryptPasswordEncoder pass() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain security(HttpSecurity http) throws Exception {
        http.cors(
                corsCustomizer ->
                        corsCustomizer.configurationSource(new CorsConfigurationSource(){

                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration cors = new CorsConfiguration();
                                cors.setAllowedOrigins(Collections.singletonList("http://localhost:8000"));
                                cors.setAllowedMethods(Collections.singletonList("*"));
                                cors.setAllowCredentials(true);
                                return cors;
                            }

                        })
        ).csrf(csrf -> csrf.disable()).
                authorizeHttpRequests(auth ->
                auth.requestMatchers("/login-success").hasRole("USER")
                        .requestMatchers("/login-success-admin", "/login-failed", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                        .permitAll()
        ).formLogin(login ->
                login.loginProcessingUrl("/login")
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login-failed"))
                        .defaultSuccessUrl("/login-success")
        ).logout(log ->
                log.logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
        ).httpBasic(Customizer.withDefaults());
		
		
		http.sessionManagement(session -> {
		 session.maximumSessions(1)
		 .maxSessionsPreventsLogin(true)
		 .expiredUrl("/expired-session")
		 ;
		});
		return http.build();
	}
	
	
}
