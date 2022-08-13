package io.github.zhengchalei.itachi.resource

import com.querydsl.core.types.Predicate
import io.github.zhengchalei.itachi.domain.User
import io.github.zhengchalei.itachi.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.querydsl.binding.QuerydslPredicate
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserResource(val userRepository: UserRepository) {

    @GetMapping
    fun list(pageable: Pageable, @QuerydslPredicate(root = User::class) predicate: Predicate): Page<User> {
        return userRepository.findAll(predicate, pageable)
    }

    @PostMapping
    fun save(@Validated @RequestBody user: User): User {
        return userRepository.save(user)
    }

}