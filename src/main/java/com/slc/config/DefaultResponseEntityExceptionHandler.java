/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.config;

import java.util.Set;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

public class DefaultResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleNoSuchRequestHandlingMethod(NoSuchRequestHandlingMethodException ex, 
                    HttpHeaders headers, HttpStatus status, WebRequest request) {
        pageNotFoundLogger.warn("Requested call cannot be complete. Please check your request.");

        return super.handleNoSuchRequestHandlingMethod(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, 
                    HttpHeaders headers, HttpStatus status, WebRequest request) {
        pageNotFoundLogger.warn(ex.getMessage());

        Set<HttpMethod> supportedMethods = ex.getSupportedHttpMethods();
        if (!supportedMethods.isEmpty()) {
                headers.setAllow(supportedMethods);
        }

        //START Changes from super. Code not in here is copied from super 
        String message = "Requested call cannot be complete. Please check your request.";
        pageNotFoundLogger.warn(message);
        return handleExceptionInternal(ex, message, headers, status, request);
        //END changes from super
    }

    /**
     * grant access to superHandleExceptionInternal needed by {@link GlobalRestExceptionHandler} 
     */
    protected ResponseEntity<Object> doHandleExceptionInternal(Exception ex, Object body, HttpHeaders headers, 
                    HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }
}
