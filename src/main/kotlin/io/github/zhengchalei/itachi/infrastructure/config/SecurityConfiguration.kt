package io.github.zhengchalei.itachi.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import java.util.*

/**
 * Security configuration
 *
 * @constructor Security configuration
 */
@Configuration
class SecurityConfiguration {

    @Bean
    fun auditorAware(): Optional<User> {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .map { it as User }
    }

}