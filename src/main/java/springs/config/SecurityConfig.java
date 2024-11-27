package springs.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Autowired
    private AuthMiddleware middleware;

    @Autowired
    private AuthProvider authProvider;

    @Autowired
    private SuccessHandler successHandler;

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }

    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.cors(
            corsCustomizer ->
                    corsCustomizer.configurationSource(new CorsConfigurationSource(){

                        @Override
                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                            CorsConfiguration cors = new CorsConfiguration();
                            cors.setAllowedOrigins(Collections.singletonList("http://localhost:8080"));
                            cors.setAllowedMethods(Collections.singletonList("*"));
                            cors.setAllowCredentials(true);
                            return cors;
                        }

                    })
        )
        .csrf(csrf -> csrf.disable())
                .addFilterBefore(middleware, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests(auth ->
                        auth.requestMatchers("/loginPage","/login-success","/login-failed","/dashboard",
                        "/admin-dashboard","/user-dashboard","/routes","/WEB-INF/jsp/**","/resource/images/**","/css/**","/js/**",
                        "/test","/add-route","/bus-types","/add-bus-type","/bus").permitAll()  
                                .anyRequest().authenticated()).  
                                formLogin(login -> {
                                        try {
                                            login.loginPage("/loginPage").
                                            loginProcessingUrl("/login")
                                            .successHandler(successHandler)
                                            .failureUrl("/login-failed")
                                            .and()
                                            .logout(log -> 
                                            log.logoutUrl("/logout")
                                            .clearAuthentication(true)
                                            .deleteCookies("accessToken","refreshToken")
                                            .logoutSuccessUrl("/loginPage")
                                            .permitAll()
                                             )
                                            .sessionManagement(
                                                session -> session.sessionFixation(
                                                    sess -> sess.newSession()
                                                ).invalidSessionUrl("/loginPage")
                                            );
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    
                                }).httpBasic(Customizer.withDefaults());
             
         
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        return  http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(authProvider)
                .build();
    }

    @Bean
	public BCryptPasswordEncoder pass() {
		return new BCryptPasswordEncoder();
	}
}
