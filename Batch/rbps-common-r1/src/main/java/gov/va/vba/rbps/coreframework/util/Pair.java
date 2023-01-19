/*
 * Pair.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


public class Pair<T,T1> {

    private T       first;
    private T1      second;

    public Pair( final T   first, final T1 second ) {

        this.first  = first;
        this.second = second;
    }


    public T getFirst() {

        return first;
    }


    public T getKey() {

        return first;
    }


    public T1 getSecond() {

        return second;
    }


    public T1 getValue() {

        return second;
    }
}
