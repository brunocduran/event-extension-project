package br.com.eventextensionproject.MainExtensionProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        CorsConfiguration corsConfig = new CorsConfiguration().applyPermitDefaultValues();
        corsConfig.addAllowedMethod(HttpMethod.DELETE);
        corsConfig.addAllowedMethod(HttpMethod.PUT);

        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors().configurationSource(request -> corsConfig)
                .and().sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/state/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/city/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/city/listByState/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/city/findByNameCity/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/city/findByCodIbge/**").permitAll()

                        .requestMatchers(HttpMethod.GET, "/api/v1/person/listByCpfCnpj/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/person/listByEmail/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/person/insertParticipant").permitAll()
                        .requestMatchers("/api/v1/person/listById/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER", "PARTICIPANT")
                        .requestMatchers("/api/v1/person/updateParticipant").hasAnyRole("ADMINISTRATOR", "ORGANIZER", "PARTICIPANT")
                        .requestMatchers("/api/v1/person/personToken").hasAnyRole("ADMINISTRATOR", "ORGANIZER", "PARTICIPANT")
                        .requestMatchers("/api/v1/person/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/eventCategory/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/function/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/jobType/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/institution/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/institution/image/**").permitAll()
                        .requestMatchers("/api/v1/institution/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/course/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/activityType/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/event/listHome").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/listByIdHome/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/event/image/**").permitAll()
                        .requestMatchers("/api/v1/event/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers(HttpMethod.GET, "/api/v1/banner/list").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/banner/listActive").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/banner/image/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/eventActivity/listDates/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/v1/eventActivity/listByEventDate/**").permitAll()
                        .requestMatchers("/api/v1/banner/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/lot/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/eventOrganizer/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/eventActivity/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/donation/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/expense/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/enrollment/insert").hasAnyRole("PARTICIPANT")
                        .requestMatchers("/api/v1/enrollment/listById/**").hasAnyRole("PARTICIPANT", "ADMINISTRATOR", "ORGANIZER")
                        .requestMatchers("/api/v1/enrollment/listByIdCertificate/**").hasAnyRole("PARTICIPANT", "ADMINISTRATOR", "ORGANIZER")
                        .requestMatchers("/api/v1/enrollment/listByPerson/**").hasAnyRole("PARTICIPANT", "ADMINISTRATOR", "ORGANIZER")
                        .requestMatchers("/api/v1/enrollment/delete/**").hasAnyRole("PARTICIPANT", "ADMINISTRATOR", "ORGANIZER")
                        .requestMatchers("/api/v1/enrollment/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .requestMatchers("/api/v1/attendance/**").hasAnyRole("ADMINISTRATOR", "ORGANIZER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
