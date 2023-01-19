/*
 * VirtualVaWebServiceRunner.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva;


import java.io.File;
import java.util.List;


/**
 *      Call the web service and return a list of claims files that failed
 *      and a list of files that succeeded.
 */
public interface VirtualVaWebServiceRunner
    extends
        Runnable {

    @Override
    void run();



    //
    //      I wouldn't do this if methods could return more than
    //      one value in java, like other languages allow.
    //      And I don't want to code up a Pair<T,V> class just for this.
    //
    //      These return the names of the pdf files in question.
    //
    List<File> getSuccessfulClaimsFiles( String waitingDir );
    List<File> getFailedClaimsFiles( String waitingDir );
}
