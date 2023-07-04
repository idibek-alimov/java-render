package maryam.security;

import lombok.RequiredArgsConstructor;
//import maryam.filter.CustomAuthenticationFilter;
//import maryam.filter.CustomAuthorizationFilter;
import maryam.filter.JwtAuthenticationFilter;
import maryam.models.role.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig{// extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //hello testing branch
        http
                .cors(c->{
                    CorsConfigurationSource source = request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.setAllowedOrigins(
                        List.of("/**","http://localhost:3000","*"));
                    config.setAllowedMethods(
                        List.of("GET","POST","PUT","DELETE"));
                    config.setAllowedHeaders(
                        List.of("Authorization","Content-Type","Cache-Control","Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers"));
                    config.setExposedHeaders(
                        List.of("Authorization","Content-Type","Cache-Control","Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers"));

                        return config;
                    };
                    c.configurationSource(source);
                })
                //.cors().disable()
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/api/user/authenticate/**","/api/article/allow/**")
                .permitAll()
                .requestMatchers("/api/user/user/**","/api/article/user/**","/api/product/user/**","/api/item/user/**","/api/order/user/**")
                .hasRole("USER")
                .requestMatchers("/api/product/seller/**","/api/article/seller/**","/api/user/seller/**")
                .hasRole("SELLER")
                .requestMatchers("/api/item/manager/*")
                .hasRole("MANAGER")
                .requestMatchers("/api/user/register")
                .permitAll()
//                .requestMatchers("/api/**")
//                .permitAll()
//                .requestMatchers("/api/*")
//                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.cors(c->{
//            CorsConfigurationSource source = request -> {
//                CorsConfiguration config = new CorsConfiguration();
//                config.setAllowedOrigins(
//                        List.of("/**","http://localhost:3000","*"));
//                config.setAllowedMethods(
//                        List.of("GET","POST","PUT","DELETE"));
//                config.setAllowedHeaders(
//                        List.of("Authorization","Content-Type","Cache-Control","Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers"));
//
//                config.setExposedHeaders(
//                        List.of("Authorization","Content-Type","Cache-Control","Access-Control-Allow-Origin","Access-Control-Allow-Methods","Access-Control-Allow-Headers"));
//
//                return config;
//            };
//            c.configurationSource(source);
//        });
//        http.csrf().disable();
//        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/product/create/new").hasRole("SELLER_ROLE");
//        http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/product/article/add/**").hasRole("SELLER_ROLE");
//        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeRequests().antMatchers("/login/**").permitAll();
//        http.authorizeRequests().antMatchers("/api/product/**").permitAll();
//        http.authorizeRequests().anyRequest().authenticated();
//        http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));
//        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http)
//        throws Exception{
//        http.csrf()
//                .disable()
//                .authorizeRequests()
//                .antMatchers("/api/product/create/**")
//                .hasRole("SELLER_USER")
//                .antMatchers("/**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic()
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        return http.build();
//    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception{
//        return super.authenticationManagerBean();
//    }
}


