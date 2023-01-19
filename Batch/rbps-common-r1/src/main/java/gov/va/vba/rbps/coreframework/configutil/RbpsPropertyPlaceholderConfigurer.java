/*
 * RbpsPropertyPlaceholderConfigurer.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. use is subject to security terms
 *
 */
package gov.va.vba.rbps.coreframework.configutil;


import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
 *      Merge our properties in with Spring's properties.
 */
public class RbpsPropertyPlaceholderConfigurer extends
        PropertyPlaceholderConfigurer {

    public RbpsPropertyPlaceholderConfigurer() {
        super();
    }


    public String getProperty(final String key) {
        String value;
        try {
            value = super.mergeProperties().getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException("Could not read the property value."
                    + e.getMessage());
        }

        return value;
    }


    public Properties getProperties() throws IOException {
        return super.mergeProperties();
    }
}
