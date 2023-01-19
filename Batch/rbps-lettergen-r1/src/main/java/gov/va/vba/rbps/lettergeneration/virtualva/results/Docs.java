/*
 * Docs.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.virtualva.results;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement( name="docs" )
public class Docs {


    List<DocResult>     docs;






    @XmlElement( name="doc" )
    public List<DocResult> getDocs() {

        return docs;
    }


    public void setDocs( final List<DocResult> docs ) {

        this.docs = docs;
    }


    @Override
    public String toString() {

        if ( docs == null )
        {
            return "no docs";
        }

        return "size: " + docs.size() + "\ndocs: " + docs;
    }
}
