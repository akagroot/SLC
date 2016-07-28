/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.performancecarerx.exception;

/**
 *
 * @author jberroteran
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException() {
            super();
    }

    public NotFoundException(String s) {
            super(s);
    }

    public NotFoundException(String s, Throwable t) {
            super(s, t);
    }
}
