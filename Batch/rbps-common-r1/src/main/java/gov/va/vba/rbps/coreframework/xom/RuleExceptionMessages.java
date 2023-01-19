/*
 * ExceptionMessages.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class RuleExceptionMessages implements Serializable {

    private static final long serialVersionUID = -8566344262608014252L;

    List<String> messages;

    public RuleExceptionMessages() {
        messages = new ArrayList<String>();
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(final List<String> messages) {
        this.messages = messages;
    }

    public void addException(final String exception) {
        messages.add(exception);
    }

    public void appendMessages(final List<String> messages) {
        this.messages.addAll(messages);
    }

    public void appendMessages(final RuleExceptionMessages messages) {
        appendMessages(messages.getMessages());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("messages", messages)
                .toString();
    }
}
