package io.github.zhengchalei.itachi.infrastructure.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalException {

    val log: Logger = LoggerFactory.getLogger(GlobalException::class.java)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException::class)
    fun exception(e: RuntimeException) {
        log.error(e.message, e)
    }

}

class ServiceException(message: String) : RuntimeException(message) {

}