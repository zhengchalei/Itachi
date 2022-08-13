package io.github.zhengchalei.itachi.infrastructure.jpa


import com.querydsl.core.types.EntityPath
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer
import org.springframework.data.repository.NoRepositoryBean
import java.time.LocalDateTime
import javax.persistence.*

/**
 * Base entity
 *
 * @constructor Base entity
 * @author 郑查磊
 * @since 1.0.0
 */
@MappedSuperclass
open class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0

    @Version
    val version: Int = 0

}

@MappedSuperclass
abstract class AuditEntity : BaseEntity() {

    @CreatedDate
    var createTime: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    var updateTime: LocalDateTime? = null

    @CreatedBy
    var createBy: Long = 0L

    @LastModifiedBy
    var lastModifiedBy: Long? = null
}

@NoRepositoryBean
interface BaseRepository<T, ID> : JpaRepository<T, ID>, JpaSpecificationExecutor<T>, QuerydslPredicateExecutor<T> {}

@NoRepositoryBean
interface QueryDslRepository<T, ID, Q : EntityPath<T>> : BaseRepository<T, ID>, QuerydslBinderCustomizer<Q> {

}