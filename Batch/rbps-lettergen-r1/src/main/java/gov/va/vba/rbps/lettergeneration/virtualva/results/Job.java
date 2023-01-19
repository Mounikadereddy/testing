/*
 * Job.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva.results;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


/*
            <name>MES:07-MAY-11</name>
            <jobid>49321</jobid>
            <createdate>2011-05-07 08:09:46.0</createdate>
            <lastmodified>2011-05-07 08:10:00.0</lastmodified>
            <phasecode>P</phasecode>
            <statuscode>S</statuscode>
            <stepcode>S</stepcode>
            <docs>

 */
@XmlRootElement( name="job" )
public class Job {

    private String      name;
    private String      jobid;
    private Date        createdate;
    private Date        lastModified;
    private String      phasecode;
    private String      statuscode;
    private String      stepcode;
    private Docs        docs;









    public String getName() {

        return name;
    }

    public void setName( final String name ) {

        this.name = name;
    }

    public String getJobid() {

        return jobid;
    }

    public void setJobid( final String jobid ) {

        this.jobid = jobid;
    }

    public Date getCreatedate() {

        return createdate;
    }

    public void setCreatedate( final Date createdate ) {

        this.createdate = createdate;
    }

    public Date getLastModified() {

        return lastModified;
    }

    public void setLastModified( final Date lastModified ) {

        this.lastModified = lastModified;
    }

    public String getPhasecode() {

        return phasecode;
    }

    public void setPhasecode( final String phasecode ) {

        this.phasecode = phasecode;
    }

    public String getStatuscode() {

        return statuscode;
    }

    public void setStatuscode( final String statuscode ) {

        this.statuscode = statuscode;
    }

    public String getStepcode() {

        return stepcode;
    }

    public void setStepcode( final String stepcode ) {

        this.stepcode = stepcode;
    }

    public Docs getDocs() {

        return docs;
    }

    public void setDocs( final Docs docs ) {

        this.docs = docs;
    }
}
