/*
 * ScriptRunner.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching;


/**
 *      Interface for different script runners.
 *      Wanted an interface so that I can test the
 *      sender with a test script runner and not
 *      the real one.
 *
 *      Didn't use Runnable because I wanted a different
 *      label here and I wanted a return value.
 */
public interface ScriptRunner {
    boolean run();
}
