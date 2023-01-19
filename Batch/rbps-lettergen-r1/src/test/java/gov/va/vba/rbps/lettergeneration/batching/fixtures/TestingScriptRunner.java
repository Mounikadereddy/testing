/*
 * TestingScriptRunner.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.fixtures;


import gov.va.vba.rbps.lettergeneration.batching.ScriptRunner;


/**
 *      A test script runner that can return any deesired result.
 */
public class TestingScriptRunner implements ScriptRunner {

    public boolean     runStatus;


    public TestingScriptRunner( final boolean runStatus ) {
        this.runStatus = runStatus;
    }


    @Override
    public boolean run() {
        return runStatus;
    }
}
