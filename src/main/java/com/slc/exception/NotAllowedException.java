/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.slc.exception;

/**
 *
 * @author jberroteran
 */
public class NotAllowedException extends RuntimeException {
    public NotAllowedException() {
            super();
    }

    public NotAllowedException(String s) {
            super(s);
    }

    public NotAllowedException(String s, Throwable t) {
            super(s, t);
    }
}
