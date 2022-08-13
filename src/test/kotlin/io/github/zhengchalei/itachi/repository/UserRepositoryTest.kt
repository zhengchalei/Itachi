package io.github.zhengchalei.itachi.repository

import io.github.zhengchalei.itachi.domain.User
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
internal class UserRepositoryTest {

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    @Transactional
    fun save() {
        val user = User("user")
        userRepository.save(user)
    }


}