package com.beganov.vendorservice.exception.handler;

import com.beganov.vendorservice.exception.VendorCanNotBeSentException;
import com.beganov.vendorservice.exception.VendorNotFoundException;
import com.beganov.vendorservice.exception.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;

@RestControllerAdvice
public class GeneralExceptionHandler {

    // Обрабатываем ошибку при отсутствии данных в БД
    @ExceptionHandler(VendorNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse vendorNotFoundException(VendorNotFoundException vendorNotFoundException) {
        return new ExceptionResponse(
                HttpStatus.NOT_FOUND,
                vendorNotFoundException.getClass().getName(),
                vendorNotFoundException.getMessage()
        );
    }

    // Обрабатываем ошибку, когда данные не получиться отправить в в API поставщика
    @ExceptionHandler(VendorCanNotBeSentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse vendorCanNotBeSavedException(VendorCanNotBeSentException vendorCanNotBeSentException) {
        return new ExceptionResponse(
                HttpStatus.BAD_REQUEST,
                vendorCanNotBeSentException.getClass().getName(),
                vendorCanNotBeSentException.getMessage()
        );
    }

    // Обрабатываем ошибку при таймауте
    @ExceptionHandler(ResourceAccessException.class)
    public ResponseEntity<String> handleTimeoutException(ResourceAccessException ex) {
        return new ResponseEntity<>("Timeout occurred while communicating with external API: " + ex.getMessage(), HttpStatus.REQUEST_TIMEOUT);
    }

    // Обрабатываем ошибки клиента (например, 4xx)
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientErrorException(HttpClientErrorException ex) {
        return new ResponseEntity<>("Client error occurred: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // Обрабатываем ошибки сервера (например, 5xx)
    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleServerErrorException(HttpServerErrorException ex) {
        return new ResponseEntity<>("Server error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
