/*
 * JobList.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva.results;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement( name="jobList" )
public class JobList {

    private Job     job;







    public Job getJob() {

        return job;
    }


    public void setJob( final Job job ) {

        this.job = job;
    }
}
