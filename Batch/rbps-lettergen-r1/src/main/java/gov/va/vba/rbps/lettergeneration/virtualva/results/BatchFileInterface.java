/*
 * BatchFileInterface.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva.results;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement( name="BFI" )
public class BatchFileInterface {


    private JobList     jobList;


    private String      errorCode;
    private String      description;







    public JobList getJobList() {

        return jobList;
    }


    public void setJobList( final JobList jobList ) {

        this.jobList = jobList;
    }



    @XmlElement( name="ErrorCode" )
    public String getErrorCode() {

        return errorCode;
    }



    public void setErrorCode( final String errorCode ) {

        this.errorCode = errorCode;
    }



    @XmlElement( name="Description" )
    public String getDescription() {

        return description;
    }



    public void setDescription( final String description ) {

        this.description = description;
    }
}
