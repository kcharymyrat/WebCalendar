//package webCalendarSpring
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.http.HttpMethod
//import org.springframework.security.config.annotation.web.builders.HttpSecurity
//import org.springframework.security.web.SecurityFilterChain
//
//
//@Configuration
//class SecurityConfig {
//
//    @Bean
//    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
//        http
//            .authorizeRequests()
//            .requestMatchers("/actuator/shutdown").permitAll() // Allow shutdown without authentication
//            .anyRequest().permitAll()  // Require authentication for all other requests
//            .and()
//            .csrf().disable() // Disable CSRF protection for simplicity, but consider enabling it in production
//            .headers().frameOptions().disable() // Disable frame options for simplicity, but consider enabling it in production
//
//        return http.build()
//    }
//
//}