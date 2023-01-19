/*
 * $Id: SaiToStringStyle.java 58578 2011-08-04 19:04:57Z lucas $
 *
 ***************************************************************************
 *
 * Copyright (c) 2001-2008 Sam Six.  All rights reserved.
 *
 * Company:      http://www.samsix.com
 *
 ***************************************************************************
 */
package gov.va.vba.rbps.coreframework.util;


import java.util.Date;

import org.apache.commons.lang.builder.ToStringStyle;



//=========================================
//
//    SaiToStringStyle class
//
//=========================================



public class SaiToStringStyle 
			extends 
			ToStringStyle {
	
	private static final long serialVersionUID = -2404380848618498195L;

	
	
	/**
	 * <p>	 * Constructor.	 * </p>
	 * 
	 * <p>	 * Use the static constant rather than instantiating.	 * </p>
	 */
	public SaiToStringStyle() {
		setContentStart("\n{");
		setDefaultFullDetail(true);
		setFieldSeparator("\n    - ");

		// This method is either newer than the version
		// we have, or obsolete.
		// setFieldSeparatorAtStart( true );

		setContentEnd("\n}");
		setFieldNameValueSeparator(": ");
	}

	/**
	 * Append the start of data indicator.
	 * 
	 * @param buffer
	 *            the StringBuffer to populate
	 * @param object
	 *            the object to build a toString for, must not be null
	 */
	// @Override
	// public void appendStart( final StringBuffer buffer,
	// final Object object )
	// {
	// // buffer.append( "\n" );
	// if ( object == null )
	// {
	// appendFieldSeparator( buffer );
	// }
	// else
	// {
	// super.appendStart( buffer, object );
	// }
	// }

	// @Override
	// public void appendEnd( StringBuffer buffer,
	// Object object )
	// {
	// buffer.append( "\n" );
	// super.appendEnd( buffer, object );
	// }

	/**
	 * <p>
	 * Append the field start to the buffer.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuffer</code> to populate
	 * @param fieldName
	 *            the field name
	 */
	@Override
	protected void appendFieldStart(final StringBuffer buffer,	final String fieldName) {
		appendFieldSeparator(buffer);

		if (isUseFieldNames() && fieldName != null) {
			buffer.append(fieldName);

			for (int ii = 0; ii < (40 - fieldName.length()); ii++) {
				buffer.append(" ");
			}

			buffer.append(getFieldNameValueSeparator());
		}
	}

	@Override
	protected void appendFieldEnd(final StringBuffer buffer,
			final String fieldName) {
		// do nothing.
	}

	/**
	 * <p>
	 * Append the field end to the buffer.
	 * </p>
	 * 
	 * @param buffer
	 *            the <code>StringBuffer</code> to populate
	 * @param fieldName
	 *            the field name, typically not used as already appended
	 */
	// @Override
	// protected void appendFieldEnd( final StringBuffer buffer,
	// final String fieldName )
	// {
	// appendFieldSeparator( buffer );
	// }

	@Override
	protected void appendInternal(final StringBuffer buffer,
			final String fieldName, Object value, final boolean detail) {
		appendSummary(buffer, fieldName, value);

		if (value instanceof Date) {

			value = new SimpleDateUtils().standardLogDayFormat((Date) value);
		}

		if (detail) {
			appendDetail(buffer, fieldName, "]" + value + "[");
		}
	}
}
