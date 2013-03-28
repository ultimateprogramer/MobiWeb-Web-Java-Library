/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.mobiweb.web.expeptions;

/**
 *
 * @author Ahmed Maawy
 */
public class InvalidHeaderException extends Exception {

    /**
     * Creates a new instance of <code>InvalidHeaderException</code> without detail message.
     */
    public InvalidHeaderException() {
    }


    /**
     * Constructs an instance of <code>InvalidHeaderException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public InvalidHeaderException(String msg) {
        super(msg);
    }
}
