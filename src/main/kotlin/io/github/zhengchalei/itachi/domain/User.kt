package io.github.zhengchalei.itachi.domain

import io.github.zhengchalei.itachi.infrastructure.config.UserPasswordEncoder
import io.github.zhengchalei.itachi.infrastructure.jpa.AuditEntity
import javax.persistence.Entity
import javax.persistence.Index
import javax.persistence.Table
import javax.validation.constraints.NotBlank

@Entity(name = "t_user")
@Table(
    indexes = [
        Index(unique = true, columnList = "username"),
    ]
)
class User(
    // 用户名
    @NotBlank
    var username: String,

    // 默认密码
    var password: String = UserPasswordEncoder.Instance.encode("123456")

) : AuditEntity() {

}