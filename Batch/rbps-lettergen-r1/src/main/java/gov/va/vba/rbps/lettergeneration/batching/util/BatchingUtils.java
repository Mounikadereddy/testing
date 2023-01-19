/*
 * BatchingUtils.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration.batching.util;


import java.util.List;


/**
 * This utility contains some methods that are useful to letter generation.
 *
 * @author vafscchowdk
 *
 */
public class BatchingUtils {



    /**
     * We were using <code>StringUtils.join</code> from apache commons, but
     * when we were testing via weblogic deployment, it turns out that
     * that method wasn't available because the version of <code>StringUtils</code>
     * that was available in deployment didn't have this method available,
     * so we wrote our own.
     *
     * @param list - the list of items we wish to join into a string
     * @param separator - the separator we want to use to separate items
     *          in the string.
     * @return a string with all the items joined together.
     */
    public String join( final List<String> list, final String separator ) {

        String      safeSeparator = separator;

        if ( separator == null ) {
            safeSeparator = "";
        }

        StringBuffer    buffer = new StringBuffer();
        boolean         first  = true;

        for ( String item : list ) {

            if ( first ) {

                first = appendFirstItemWithoutSelector( buffer, item );
                continue;
            }

            buffer.append( safeSeparator );
            buffer.append( item );
        }

        return buffer.toString();
    }


    private boolean appendFirstItemWithoutSelector( final StringBuffer    buffer,
                                                    final String          item ) {

        buffer.append(  item  );

        return false ;
    }
}
