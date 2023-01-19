/*
 * TestingWebServiceRunner.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.fixtures;


import gov.va.vba.rbps.lettergeneration.virtualva.VirtualVaWebServiceRunner;

import java.io.File;
import java.util.List;


/**
 *      A fake web service runner so that I can have control
 *      over what it returns for failed/successful files.
 */
public class TestingWebServiceRunner
    implements
        VirtualVaWebServiceRunner {


    public boolean              hasBeenRun              = false;
    private List<File>          successfulClaims;
    private List<File>          failedClaims;



    @Override
    public void run() {
        hasBeenRun = true;
    }








    @Override
    public List<File> getSuccessfulClaimsFiles( final String waitingDir ) {
        return successfulClaims;
    }


    public void setSuccessfulClaimsFiles( final List<File> successfulClaims ) {
        this.successfulClaims = successfulClaims;
    }


    @Override
    public List<File> getFailedClaimsFiles( final String  waitingDir ) {
        return failedClaims;
    }


    public void setFailedClaimsFiles( final List<File> failedClaims ) {
        this.failedClaims = failedClaims;
    }
}
