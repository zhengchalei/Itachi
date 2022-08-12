package io.github.zhengchalei.itachi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Itachi application
 *
 * @constructor Itachi application
 * @author 郑查磊
 * @since 1.0.0
 */
@SpringBootApplication
class ItachiApplication

fun main(args: Array<String>) {
    runApplication<ItachiApplication>(*args)
}