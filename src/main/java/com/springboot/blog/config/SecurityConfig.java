package com.springboot.blog.config;


import com.springboot.blog.security.CustomUserDetailService;
import com.springboot.blog.security.JwsAuthenticationFilter;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //this is for method level security
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //this is database security which is used down
    @Autowired
    private CustomUserDetailService userDetailService;

    //JWT security
    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public JwsAuthenticationFilter jwsAuthenticationFilter() {
        return new JwsAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //CMD+O to check all available method and override this method
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .anyRequest()
                .authenticated();
//                .and()
//                .httpBasic();

        http.addFilterBefore(jwsAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    //this is for database security method which is created in security
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder());
    }

    //this is for sign in component
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //    @Override  this is in memory authentication
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails meghraj = User.builder().username("meghraj")
//                .password(passwordEncoder().encode("meghraj"))
//                .roles("USER").build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(meghraj, admin);
//
//    }


}
