package io.github.zhengchalei.itachi.infrastructure.config

import io.github.zhengchalei.itachi.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import java.util.*


/**
 * Security configuration
 *
 * @constructor Security configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = false)
class SecurityConfiguration {

    val log: Logger = LoggerFactory.getLogger(SecurityConfiguration::class.java)

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests {
                it.anyRequest().authenticated()
                    .and()
                    .cors().disable()
                    .csrf().disable()
            }
            .httpBasic()
        return http.build()
    }


    @Bean
    fun userDetailsService(userRepository: UserRepository): UserDetailsService {
        return object : UserDetailsService {
            /**
             * Locates the user based on the username. In the actual implementation, the search
             * may possibly be case sensitive, or case insensitive depending on how the
             * implementation instance is configured. In this case, the `UserDetails`
             * object that comes back may have a username that is of a different case than what
             * was actually requested..
             * @param username the username identifying the user whose data is required.
             * @return a fully populated user record (never `null`)
             * @throws UsernameNotFoundException if the user could not be found or the user has no
             * GrantedAuthority
             */
            override fun loadUserByUsername(username: String): UserDetails {
                val user = userRepository.findByUsername(username).orElseThrow { ServiceException("username not exists") }
                return SecurityUser(user.id, username, user.password, setOf(), setOf())
            }

        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        log.debug("if you need default password: 123456, encoder: ${UserPasswordEncoder.Instance.encode("123456")}")
        return UserPasswordEncoder.Instance
    }

}

class UserPasswordEncoder : BCryptPasswordEncoder() {
    companion object {
        val Instance = UserPasswordEncoder()
    }
}

class SecurityUtils {

    companion object {
        fun getUser(): SecurityUser {
            return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map { it as SecurityUser }
                .orElseThrow { ServiceException("当前用户未登录") }
        }

        fun getId(): Long {
            return Optional.of(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map { it as SecurityUser }
                .map { it.id }
                .orElseThrow { ServiceException("当前用户未登录") }

        }
    }

}


class SecurityUser(
    val id: Long,
    private val username: String,
    private val password: String,
    private val roles: Set<String>,
    private val authorities: Set<String>,
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val roleList = roles.map { "ROLE_$it" }.map { SimpleGrantedAuthority(it) }
        val authorityList = authorities.map { SimpleGrantedAuthority(it) }
        return (roleList union authorityList).toMutableList()
    }

    override fun getPassword(): String = this.password
    override fun getUsername(): String = this.username
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun isEnabled(): Boolean = true
}