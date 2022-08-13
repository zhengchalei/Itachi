package io.github.zhengchalei.itachi.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
class SecurityConfiguration {

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
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username ->
            SecurityUser(0L, username, passwordEncoder().encode("123456"), setOf(), setOf())
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return UserPasswordEncoder()
    }

}

class UserPasswordEncoder: BCryptPasswordEncoder() {
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