/*
 * MarkupTest.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.fixtures.CommonFactory;
import gov.va.vba.rbps.lettergeneration.util.LetterConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;





public class MarkupTest {


    @Test
    public void shouldProcessSimpleTemplate() {

        Markup                  markup          = new Markup();
        Map<String,Object>      param           = new HashMap<String,Object>();
        String                  templateName    = "gov/va/vba/rbps/lettergeneration/resources/simpleValueTemplate.vm";

        param.put(  "name", "keya" );

        @SuppressWarnings( "unused" )
        String output = markup.mergeTemplate( templateName, param, true );

//        System.out.println( "output:\n===============\n" + output );
    }


    @Test
    public void shouldProcessTemplateWithProperty() {

        Markup                  markup          = new Markup();
        Map<String,Object>      param           = new HashMap<String,Object>();
        String                  templateName    = "gov/va/vba/rbps/lettergeneration/resources/simplePropertyTemplate.vm";
        Veteran                 veteran         = new Veteran();

        param.put(  "veteran", veteran );

        @SuppressWarnings( "unused" )
        String output = markup.mergeTemplate( templateName, param, true );


//        System.out.println( "output:\n===============\n" + output );
    }


    @Test
    public void shouldAddVeteransFirstName() {

        List<AwardSummary>      awardSummary    = new ArrayList<AwardSummary>();

        Markup                  markup          = new Markup();
        Map<String,Object>      param           = new HashMap<String,Object>();
        String                  templateName    = LetterConstant.LETTER_TEMPLATE;
        Veteran                 veteran         = CommonFactory.georgeVeteran();
        RbpsRepository          repo            = new RbpsRepository();

        repo.setVeteran( veteran );
        LetterFields            letterFields    = new LetterFields( repo, awardSummary );
        letterFields.init();

        markup.setRaiseErrorOnMissingReferences( false );
        veteran.setFirstName( "Keya" );
        param.put( "letterFields", letterFields );

        String output = markup.mergeTemplate( templateName, param, true );

        assertThat( output, containsString( "Keya" ));
//        System.out.println( "output:\n===============\n" + output );
    }
}
