package io.github.zhengchalei.itachi.infrastructure.jpa


import javax.persistence.*

/**
 * Base entity
 *
 * @constructor Base entity
 * @author 郑查磊
 * @since 1.0.0
 */
@MappedSuperclass
class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    val id: Long = 0

    @Version
    val version: Int = 0
}