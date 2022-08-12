package io.github.zhengchalei.itachi.infrastructure.config

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.web.config.EnableSpringDataWebSupport

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
class QueryDslConfiguration {

    /**
     * Jpa query factory
     *
     * @param entityManager EntityManager
     */
    @Bean
    fun jpaQueryFactory(entityManager: EntityManager) = JPAQueryFactory(entityManager)

}