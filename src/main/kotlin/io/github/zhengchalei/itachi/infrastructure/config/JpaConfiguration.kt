package io.github.zhengchalei.itachi.infrastructure.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*
import javax.persistence.EntityManager

/**
 * Query dsl configuration
 *
 * @constructor Query dsl configuration
 * @author 郑查磊
 * @since 1.0.0
 */
@Configuration
@EnableSpringDataWebSupport
class JpaConfiguration {

    /**
     * Jpa query factory
     *
     * @param entityManager EntityManager
     */
    @Bean
    fun jpaQueryFactory(entityManager: EntityManager) = JPAQueryFactory(entityManager)

    @Bean
    fun auditorAware(): Optional<Long> {
        return Optional.of(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication)
            .filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal)
            .filter { it is SecurityUser }
            .map { it as SecurityUser }
            .map { it.id }
    }

}