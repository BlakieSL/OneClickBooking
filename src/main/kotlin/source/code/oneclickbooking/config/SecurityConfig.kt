package source.code.oneclickbooking.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.servlet.handler.HandlerMappingIntrospector
import source.code.oneclickbooking.service.implementation.util.JwtService
import source.code.oneclickbooking.auth.filter.BearerTokenFilter
import source.code.oneclickbooking.auth.filter.JwtAuthenticationFilter
import source.code.oneclickbooking.service.declaration.UserService

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    private val jwtService: JwtService,
    @Lazy private val userService: UserService
) {
    @Bean
    fun passwordEncoder() : PasswordEncoder  = BCryptPasswordEncoder()

    @Bean
    fun mvc(introspector: HandlerMappingIntrospector) : MvcRequestMatcher.Builder =
        MvcRequestMatcher.Builder(introspector)

    @Bean
    fun corsConfigurationSource() : CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:5173")
            allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
            allowedHeaders = listOf("*")
            allowCredentials = true
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }

    @Bean
    fun filterChain(
        http: HttpSecurity,
        mvc: MvcRequestMatcher.Builder,
        authenticationManagerBuilder: AuthenticationManagerBuilder
    ): SecurityFilterChain {
        val authenticationManager: AuthenticationManager = authenticationManagerBuilder.getOrBuild()
        val jwtAuthenticationFilter = JwtAuthenticationFilter(
            authenticationManager,
            jwtService,
            userService
        )
        val bearerTokenFilter = BearerTokenFilter(jwtService)

        http.authorizeHttpRequests { request ->
            request.requestMatchers(
                "/api/users/register",
                "/api/users/login",
                "/api/users/refresh-token"
            ).permitAll()
                .anyRequest().authenticated()
        }
        http.cors { cors -> cors.configurationSource(corsConfigurationSource()) }
        http.sessionManagement { sessionConfig -> sessionConfig
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        http.csrf{ it.disable() }

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterAfter(bearerTokenFilter, JwtAuthenticationFilter::class.java)

        return http.build()
    }
}