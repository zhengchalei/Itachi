package io.github.zhengchalei.itachi.repository

import io.github.zhengchalei.itachi.domain.QUser
import io.github.zhengchalei.itachi.domain.User
import io.github.zhengchalei.itachi.infrastructure.jpa.QueryDslRepository
import org.springframework.data.querydsl.binding.QuerydslBindings
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : QueryDslRepository<User, Long, QUser> {

    override fun customize(bindings: QuerydslBindings, root: QUser) {
        bindings.bind(root.id).first { path, value -> path.eq(value) }
        bindings.excluding(root.password)
    }


    fun findByUsername(username: String): Optional<User>

}