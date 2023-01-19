package gov.va.vba.rbps.rulesengine;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;


import java.util.ArrayList;
import java.util.List;

public class TestAppender extends AppenderSkeleton {



    public List<LoggingEvent> messages = new ArrayList<>();

    @Override
    protected void append(LoggingEvent event) {
        messages.add( event );
    }

    @Override
    public void close() {

    }

    @Override
    public boolean requiresLayout() {
        return false;
    }

    public List<LoggingEvent> getMessages() {
        return messages;
    }
}
