package io.github.zhengchalei.itachi.resource

import io.github.zhengchalei.itachi.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(UserResource::class)
internal class UserResourceTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var userRepository: UserRepository

    @WithMockUser
    @Test
    fun list() {
        this.mockMvc.perform(
            MockMvcRequestBuilders.get(
                "/v1/api/user/list",
                "username=xiaoshitou"
            )
        ).andDo(MockMvcResultHandlers.print()).andExpect(status().isOk)
    }

}