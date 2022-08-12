package io.github.zhengchalei.itachi.infrastructure.jpa

import com.querydsl.core.types.EntityPath
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.querydsl.QuerydslPredicateExecutor
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer
import org.springframework.data.querydsl.binding.QuerydslBindings


interface BaseRepository<T, ID> : JpaRepository<T, ID>,
    JpaSpecificationExecutor<T>,
    QuerydslPredicateExecutor<T>

interface QueryDslRepository<T, ID, Q : EntityPath<Any>> : BaseRepository<T, ID>, QuerydslBinderCustomizer<Q> {

    /**
     * Customize
     * bindings.bind(user.username).first((path, value) -> path.contains(value))
     *
     * @param bindings
     * @param root
     */
    override fun customize(bindings: QuerydslBindings, root: Q) {
        TODO("请实现当前实体的查询条件构造器!!!")
    }

}