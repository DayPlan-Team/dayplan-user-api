package com.user.api.exception

import com.user.util.Logger
import com.user.util.exception.SystemException
import com.user.util.exception.UserException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionController {

    @ResponseStatus
    @ExceptionHandler(UserException::class)
    fun handleUserException(exception: UserException): ResponseEntity<ExceptionResponse> {
        val httpStatus = HttpStatus.valueOf(exception.status)
        log.info("exception = ${exception.message}")

        return ResponseEntity(
            ExceptionResponse(
                status = exception.status,
                code = exception.errorCode,
                message = exception.message,
            ), httpStatus
        )
    }

    @ResponseStatus
    @ExceptionHandler(SystemException::class)
    fun handleSystemException(exception: SystemException): ResponseEntity<ExceptionResponse> {
        val httpStatus = HttpStatus.valueOf(exception.status)

        log.info("exception = ${exception.message}")

        return ResponseEntity(
            ExceptionResponse(
                status = exception.status,
                code = exception.errorCode,
                message = exception.message,
            ), httpStatus
        )
    }

    companion object : Logger()
}