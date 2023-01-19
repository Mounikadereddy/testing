/*
 * KeyValueListFormatter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.util.CollectionUtils;



/**
 *    Take a list of key/value pairs and format the list so that the output
 *    looks something like this:
 *    <pre>
 *        Key1            : value1
 *        AVeryLongKey    : longKeys' value
 *    </pre>
 *    The List may contain a <code>Pair</code> or a <code>Map.Entry</code>
 */
public final class KeyValueListFormatter
{
    private Collection<Object>          _items;
    private int                         minKeyLength = 0;


    public KeyValueListFormatter()
    {
        //    Do nothing.
    }


    public KeyValueListFormatter( final int minKeyLength )
    {
        this.minKeyLength = minKeyLength;
    }


    /**
     *    Format the key/value list so that it looks nice.
     *    <p>
     *    @param indent   value used to indent the list.
     */
    private String format( final Collection<Object>    keyValueList,
                           final String                indent )
    {
        if ( CollectionUtils.isEmpty( keyValueList ) )
        {
            return "";
        }


        StringBuffer    buf;
        int             maxLength;
        Object          value;


        buf           = new StringBuffer( 2 * keyValueList.size() + 1 );
        maxLength     = 0;

        //
        //    Calculate the largest key name length.
        //
        maxLength = calculateLargestKeyLength( keyValueList );

        buf.append( "\n" );

        for ( Object    tmp : keyValueList )
        {
            String          name;

            if ( tmp instanceof LineBreak )
            {
                buf.append( indent );
                buf.append( ((LineBreak) tmp).getBreakMarker() );
                buf.append( "\n" );

                continue;
            }
            else if ( tmp instanceof String )
            {
                buf.append( (String) tmp ).append( "\n" );
                continue;
            }

            //
            //    I'm not sure why this is assumed to be a Pair here.
            //
            Pair<?,?>    pair;
            pair = (Pair<?,?>) tmp;

            name = pair.getKey().toString();

            buf.append( indent );
            buf.append( name );
            buf.append( spaces( maxLength - name.length() ) );

            value = pair.getValue();

            buf.append( spacer( value ) );
            buf.append( value != null ? value : "" );
            buf.append( "<\n" );
        }

        return buf.toString();
    }


    private String spacer( final Object value ) {

        if ( value == null ) {

            return " : >";
        }

        return String.format( " : %s >", value.getClass().getSimpleName() );
    }


    private int calculateLargestKeyLength( final Collection<Object>     keyValueList ) {

        int maxLength       = minKeyLength;

        for ( Object    tmp : keyValueList )
        {
            String          name;

            if ( ! (tmp instanceof Pair<?,?>) )
            {
                continue;
            }

            Pair<?,?>    pair;
            pair      = (Pair<?,?>) tmp;
            name      = pair.getKey().toString();
            maxLength = Math.max( maxLength, name.length() );
        }

        return maxLength;
    }


    public String spaces( final int    count )
    {
        return repeat( " ", count );
    }


    public String repeat( final String    add,
                          final int       count )
    {
        StringBuffer    buf = new StringBuffer();

        for ( int ii = 0; ii < count; ++ii )
        {
            buf.append( add );
        }

        return buf.toString();
    }


    private Collection<Object> getItems()
    {
        if ( _items == null )
        {
            _items = new ArrayList<Object>();
        }

        return _items;
    }


    /**
     *    Add a key/value pair to the list of items to be formatted.
     *    <p>
     *    @param key    the key for the value
     *    @param value  the value to be printed/formatted with the associated
     *                  label
     */
    public KeyValueListFormatter add( final Object       key,
                                      final Object       value )
    {
        getItems().add( new Pair<Object,Object>( key, value ) );

        return this;
    }


    /**
     *    Add a key/value pair to the list of items to be formatted.
     *    <p>
     *    @param key    the key for the value
     *    @param value  the value to be printed/formatted with the associated
     *                  label
     */
    public KeyValueListFormatter append( final Object       key,
                                         final Object       value )
    {
        return add( key, value );
    }


    public KeyValueListFormatter append( final String     table ) {

        addLineBreak( table );

        return this;
    }


    public void addSection( final String    label )
    {
        addLineBreak();
        getItems().add( label + ":" );
    }


    /**
     *    A convenience method to allow for double values, rather
     *    than forcing the use of Doubles.
     *    <p>
     *    @param key    the key/label for the value
     *    @param value  the value to be printed/formatted with the associated
     *                      label
     */
    public void add( final Object       key,
                     final double       value )
    {
        add( key, Double.valueOf( value ) );
    }


    /**
     *    A convenience method to allow for adding maps as a whole.
     */
    public void add( final Map<?,?> map )
    {
        for ( Object key : map.keySet() )
        {
            add( key, map.get( key ) );
        }
    }


    /**
     *    A convenience method to allow for int values, rather
     *    than forcing the use of Integers.
     *    <p>
     *    @param key     the key/label for the value
     *    @param value   the value to be printed/formatted with the associated
     *                   label
     */
    public void add( final Object       key,
                     final int          value )
    {
        //    AUTOBOX
        add( key, Integer.valueOf( value ) );
    }


    /**
     *    Add a line break between items in a list.  This allows
     *    grouping in the output.
     */
    public void addLineBreak()
    {
        getItems().add( new LineBreak() );
    }


    /**
     *    Add a line break between items in a list.  This allows
     *    grouping in the output.
     */
    public void addLineBreak( final String    lineBreak )
    {
        getItems().add( new LineBreak( lineBreak ) );
    }


    /**
     *    Clear out the list of key/value pairs so we can format a new list.
     */
    public void clear()
    {
        if ( _items == null )
        {
            _items = new ArrayList<Object>();

            return;
        }

        _items.clear();
    }


    /**
     *    Format the existing list of Pairs.
     *    Right now, if the list is null, we return an empty string.
     *    <p>
     *    @return the formatted list of items.
     */
    public String format()
    {
        return format( _items, "" );
    }


    /**
     *    Format the existing list of Pairs.
     *    Right now, if the list is null, we return an empty string.
     *    <p>
     *    @return the formatted list of items.
     */
    public String format( final String    indent )
    {
        return format( _items, indent );
    }


    @Override
    public String toString() {

        return format();
    }


    //========================================
    //
    //    LineBreak class
    //
    //========================================


    private static class LineBreak
    {
        private final String       breakMarker;

        public LineBreak()
        {
            this( "" );
        }

        public LineBreak( final String    aBreakMarker )
        {
            breakMarker = aBreakMarker;
        }

        public String  getBreakMarker()
        {
            return breakMarker;
        }
    }
}
