/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.config;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.performancecarerx.exception.NotAllowedException;
import com.performancecarerx.exception.NotFoundException;
import com.performancecarerx.exception.UnauthorizedException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

@ControllerAdvice
public class GlobalRestExceptionHandler extends DefaultHandlerExceptionResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestExceptionHandler.class);
    private final DefaultResponseEntityExceptionHandler responseEntExcHandler = new DefaultResponseEntityExceptionHandler();

    public final ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        return responseEntExcHandler.handleException(ex, request);
    }

    @ExceptionHandler(value = { MethodArgumentNotValidException.class })
    @ResponseBody
    public ResponseEntity<Object> processValidationError(MethodArgumentNotValidException ex, WebRequest request) {
        BindingResult result = ex.getBindingResult();
        String message = result.getFieldErrors().get(0).getDefaultMessage();
        HttpStatus status = HttpStatus.BAD_REQUEST;
        RuntimeException newEx = new RuntimeException(message);
        return handleException(newEx, status, request);
    }

    @ExceptionHandler(value = { IllegalArgumentException.class, NotAllowedException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        return handleException(e, status, request);
    }

    @ExceptionHandler(value = { NotFoundException.class })
    protected ResponseEntity<Object> handleNotFound(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleException(e, status, request);
    }

//    @ExceptionHandler(value = { UnauthorizedException.class })
//    protected ResponseEntity<Object> handleForbidden(RuntimeException e, WebRequest request) {
//        HttpStatus status = HttpStatus.FORBIDDEN;
//        return handleException(e, status, request);
//    }

//	@ExceptionHandler(value = { BadRequestException.class })
//	protected ResponseEntity<Object> handleBadRequest(RuntimeException e, WebRequest request) {
//		HttpStatus status = HttpStatus.BAD_REQUEST;
//
//		return handleException(e, status, request);
//	}

    @ExceptionHandler(value = { RestClientException.class })
    protected ResponseEntity<Object> handleRestClientRequest(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return handleException(e, status, request);
    }

    @ExceptionHandler(value = { DatatypeConfigurationException.class })
    protected ResponseEntity<Object> handleWebServiceClientRequest(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.SERVICE_UNAVAILABLE;
        return handleException(e, status, request);
    }

    @ExceptionHandler(value = { UnauthorizedException.class })
    protected ResponseEntity<Object> handleUnauthorizedException(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        return handleException(e, status, request);
    }

//	@ExceptionHandler(value = { ConcurrentLoginException.class })
//	protected ResponseEntity<Object> handleConcurrentLoginException(RuntimeException e, WebRequest request) {
//		HttpStatus status = HttpStatus.CONFLICT;
//
//		return handleException(e, status, request);
//	}

    @ExceptionHandler(value = { AccessDeniedException.class })
    protected ResponseEntity<Object> handleAccessDeniedException(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return handleException(e, status, request);
    }

    /**
     * Determines if there is a Rest End-Point JSON contract breach and issues a BAD Request.
     * 
     * @author Gopal 03/20/2015 - Sprint 17 created.
     */
    @ExceptionHandler(value = { HttpMessageNotReadableException.class })
    protected ResponseEntity<Object> handleHttpMessageNotReadableException(RuntimeException e, WebRequest request) {
        LOGGER.error("Exception processing request:", e);

        HttpStatus status;
        Throwable t = e.getCause();
        if (t != null && (t instanceof JsonMappingException)) {
            /*
             * The error message describing for e.g. a date violation, has technical words that may not be meaningful to an
             * end user. The message returned is meant for developers.
             */
            RuntimeException rte = new RuntimeException("Json is not complying with the expected format/data.");
            status = HttpStatus.BAD_REQUEST;
            return handleException(rte, status, request);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return handleException(e, status, request);
        }
    }

    private ResponseEntity<Object> handleException(RuntimeException e, HttpStatus status, WebRequest request) {
        String message = e.getMessage() != null ? e.getMessage() : status.getReasonPhrase();

        if (HttpStatus.INTERNAL_SERVER_ERROR.equals(status) || message == null) {
            LOGGER.error(e.getMessage(), e);
            message = "An unexpected error has occurred. Please try again later.";
        }

        LOGGER.debug("Handle Exception: {}", message);

        message = "{\"error\": \"" + message + "\"}";
        return this.responseEntExcHandler.doHandleExceptionInternal(e, message, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(value = { IllegalStateException.class, RuntimeException.class })
    protected ResponseEntity<Object> handleAllException(RuntimeException e, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return handleException(e, status, request);
    }

}